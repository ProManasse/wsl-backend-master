package rw.adrielsoft.wisdom.main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.adrielsoft.wisdom.main.sr.domain.Bill;
import rw.adrielsoft.wisdom.main.sr.domain.EBillState;
import rw.adrielsoft.wisdom.main.sr.domain.Registration;
import rw.adrielsoft.wisdom.main.sr.domain.WClass;
import rw.adrielsoft.wisdom.main.sr.domain.WService;
import rw.adrielsoft.wisdom.main.sr.repository.BillRepository;
import rw.adrielsoft.wisdom.main.sr.repository.BillStateRepository;
import rw.adrielsoft.wisdom.main.sr.repository.RegistrationRepository;
import rw.adrielsoft.wisdom.main.sr.repository.StudentRepository;
import rw.adrielsoft.wisdom.main.sr.repository.WClassRepository;
import rw.adrielsoft.wisdom.main.sr.repository.WServiceRepository;
import rw.adrielsoft.wisdom.main.um.models.ERole;
import rw.adrielsoft.wisdom.main.um.models.Role;
import rw.adrielsoft.wisdom.main.um.models.User;
import rw.adrielsoft.wisdom.main.um.payload.request.LoginRequest;
import rw.adrielsoft.wisdom.main.um.payload.request.SignupRequest;
import rw.adrielsoft.wisdom.main.um.payload.response.JwtResponse;
import rw.adrielsoft.wisdom.main.um.payload.response.MessageResponse;
import rw.adrielsoft.wisdom.main.um.repository.RoleRepository;
import rw.adrielsoft.wisdom.main.um.repository.UserRepository;
import rw.adrielsoft.wisdom.main.um.security.jwt.JwtUtils;
import rw.adrielsoft.wisdom.main.um.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/wsl/web")
public class WebController {
	@Autowired
	WClassRepository classRepo;
	
	@Autowired
	WServiceRepository serviceRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	RegistrationRepository registrationRepo;
	
	@Autowired
	BillRepository billRepo;
	
	@Autowired
	BillStateRepository billStateRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	User teacher;
	
	
    @GetMapping(value = "/")
    public String test(){
        return "Web Gate Is Ready!";
    }
    
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getPhoneno(),signUpRequest.getName(),
				signUpRequest.getNid(),signUpRequest.getSalary(),signUpRequest.getBranch(),signUpRequest.getUserType());
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.PARENT).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "master":
					Role adminRole = roleRepository.findByName(ERole.MASTER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "accountant":
					Role modRole = roleRepository.findByName(ERole.ACCOUNTANT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;		
				default:
					Role userRole = roleRepository.findByName(ERole.PARENT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

    
    @PostMapping(value = "/wclass")
    public ResponseEntity<?> registerClass(@RequestBody WClass wclass){
    	wclass.setBusinessId(UUID.randomUUID());
    	teacher=userRepository.findById(UUID.fromString(wclass.getCtId())).get();
    	wclass.setClassTeacher(teacher);
        return new ResponseEntity<WClass>(classRepo.save(wclass), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/wclasses")
    public ResponseEntity<?> getAllClasses(){
    	return new ResponseEntity<>(classRepo.findAll(), HttpStatus.OK);
    }
    
    
    @GetMapping(value = "/employees")
    public ResponseEntity<?> getAllEmployees(){
    	List<User> users=userRepository.findAll();
    	List<User> employees=new ArrayList<>();
    	for(User u:users) {
    		if(u.getUserType().equals("Teacher") ||
    				u.getUserType().equals("Accountant") || 
    				u.getUserType().equals("Doctor") || 
    				u.getUserType().equals("DOS") ||
    				u.getUserType().equals("DOD") ||
    				u.getUserType().equals("DAF")) {
    			employees.add(u);
    		}
    	}
    	return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    
    @PostMapping(value = "/employee")
    public ResponseEntity<?> getAllEmployees(@RequestBody User user){
		if (userRepository.existsByUsername(user.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		user.setId(UUID.randomUUID());
		user.setEmail(user.getUsername()+"@wisdomschool.rw");
		user.setPassword(encoder.encode(user.getPassword()));
    	return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }
    
    @PostMapping(value = "/wservice")
    public ResponseEntity<?> addService(@RequestBody WService wservice){
    	wservice.setServiceId(UUID.randomUUID());
    	return new ResponseEntity<>(serviceRepo.save(wservice), HttpStatus.OK);
    }
    
    @GetMapping(value = "/wservices")
    public ResponseEntity<?> getAllServices(){
    	return new ResponseEntity<>(serviceRepo.findAll(), HttpStatus.OK);
    }
	
    @GetMapping(value = "/registrations")
    public ResponseEntity<?> getAllRegistrations(){
    	List<Registration> allRegReqs=registrationRepo.findAll();
    	List<Registration> waitingReqs=new ArrayList<>();
    	for(Registration reg:allRegReqs) {
    		if(reg.getRegStates().equals("ACC_WAITING")) {
    			waitingReqs.add(reg);
    		}
    	}
    	return new ResponseEntity<>(waitingReqs, HttpStatus.OK);
    }
    
    @GetMapping(value = "/students")
    public ResponseEntity<?> getAllStudents(){
    	List<Registration> allRegReqs=registrationRepo.findAll();
    	List<Registration> waitingReqs=new ArrayList<>();
    	for(Registration reg:allRegReqs) {
    		if(reg.getRegStates().equals("ACC_APPROVED")) {
    			waitingReqs.add(reg);
    		}
    	}
    	return new ResponseEntity<>(waitingReqs, HttpStatus.OK);
    }
    
    @PutMapping(value = "/approve")
    public ResponseEntity<?> approveStudent(@RequestBody Registration registration){
    	Registration reg=registrationRepo.findById(registration.getBusinessId()).get();
    	reg.setRegStates("ACC_APPROVED");
    	return new ResponseEntity<>(registrationRepo.save(reg), HttpStatus.OK);
    }
    
    @GetMapping(value = "/unpaids")
    public ResponseEntity<?> getAllUnPaids(){
    	List<Bill> allBills=billRepo.findAll();
    	List<Bill> unpaidsBills=new ArrayList<>();
    	for(Bill bill:allBills) {
    		if(bill.getState().toString().equals(EBillState.NOT_PAID)) {
    			unpaidsBills.add(bill);
    		}
    	}
    	return new ResponseEntity<>(unpaidsBills, HttpStatus.OK);
    }
    
    @GetMapping(value = "/paids")
    public ResponseEntity<?> getAPaids(){
    	List<Bill> allBills=billRepo.findAll();
    	List<Bill> unpaidsBills=new ArrayList<>();
    	for(Bill bill:allBills) {
    		if(bill.getState().toString().equals(EBillState.FULLY_PAID) || bill.getState().toString().equals(EBillState.PARTIALLY_PAID)) {
    			unpaidsBills.add(bill);
    		}
    	}
    	return new ResponseEntity<>(unpaidsBills, HttpStatus.OK);
    }
    

    
}
