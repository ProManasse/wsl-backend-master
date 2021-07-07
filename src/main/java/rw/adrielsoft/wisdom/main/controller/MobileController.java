package rw.adrielsoft.wisdom.main.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.adrielsoft.wisdom.main.sr.domain.Bill;
import rw.adrielsoft.wisdom.main.sr.domain.BillState;
import rw.adrielsoft.wisdom.main.sr.domain.EBillState;
import rw.adrielsoft.wisdom.main.sr.domain.Registration;
import rw.adrielsoft.wisdom.main.sr.domain.Student;
import rw.adrielsoft.wisdom.main.sr.domain.StudentService;
import rw.adrielsoft.wisdom.main.sr.domain.WClass;
import rw.adrielsoft.wisdom.main.sr.domain.WService;
import rw.adrielsoft.wisdom.main.sr.dto.StudentRegistrationRequest;
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

@RestController
@RequestMapping("/wsl/mobile")
public class MobileController {
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
	User father;
	
	@Autowired
	Student student;
	
	@Autowired
	Registration registration;
	
	@Autowired
	WService service;
	
	@Autowired
	Bill bill;
	
	@Autowired
	BillState billState;
	
	@Autowired
	WClass wClass;
	
	
    @GetMapping(value = "/")
    public String test(){
        return "Mobile Gate Is Ready!";
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
	
	@PostMapping("/registration")
	public ResponseEntity<?> createRegistration(@RequestBody StudentRegistrationRequest regRequest) {
		System.out.println(regRequest.getBranch());
	    try {
	    	father.setName(regRequest.getFname());
	    	father.setPhoneno(regRequest.getfPhone());
	    	father.setNid(regRequest.getfNid());
	    	father.setUsername(UUID.randomUUID().toString());
	    	father.setEmail(UUID.randomUUID().toString());
	    	father.setBranch(regRequest.getBranch());
	    	father.setPassword("not yet set");
	    	father.setUserType("Parent");
	    	father.setSalary(0.0);
	    	User ftr=userRepository.save(father);
	    	
	    	User mother=new User();
	    	mother.setName(regRequest.getmName());
	    	mother.setPhoneno(regRequest.getmPhone());
	    	mother.setNid(regRequest.getmNid());
	    	mother.setUsername(UUID.randomUUID().toString());
	    	mother.setEmail(UUID.randomUUID().toString());
	    	mother.setBranch(regRequest.getBranch());
	    	mother.setPassword("not yet set");
	    	mother.setUserType("Parent");
	    	mother.setSalary(0.0);
	    	User mtr=userRepository.save(mother);
	    	
	    	student.setBusinessId(UUID.randomUUID());
	    	student.setName(regRequest.getName());
	    	student.setOtherName(regRequest.getOtherName());
	    	student.setGender(regRequest.getGender());
	    	student.setCountry(regRequest.getCountry());
	    	student.setCity(regRequest.getCity());
	    	student.setInsurance(regRequest.getInsurance());
	    	student.setFather(ftr);
	    	student.setMother(mtr);
	    	Student std=studentRepo.save(student);
	    	
	    	registration.setBusinessId(UUID.randomUUID());
	    	registration.setStudent(student);
	    	registration.setDate(new Date());
	    	System.out.println("Class Id: "+regRequest.getcId());
	    	wClass=classRepo.findById(UUID.fromString(regRequest.getcId())).get();
	    	registration.setStudyClass(wClass);
	    	registration.setStudent(std);
	    	registration.setStudyMood(regRequest.getStudyMood());
	    	registration.setTrim(regRequest.getTerm());
	    	//System.out.println(regRequest.getwServices());
	    	registration.setTotalDue(regRequest.getTotalDue());
	    	registration.setRegStates("ACC_WAITING");
	    	registration.setBranch(regRequest.getBranch());
	    	//registration.setServices(regRequest.getwServices());
	    	Registration reg=registrationRepo.save(registration);
	    	
	    	bill.setRegistration(reg);
	    	bill.setState(EBillState.NOT_PAID);
	    	bill.setTotalDue(new BigDecimal(regRequest.getTotalDue()));
	    	bill.setBusinessId(UUID.randomUUID());
	    	bill.setBalance(new BigDecimal(0));
	    	Bill bl=billRepo.save(bill);
	    	
	    	billState.setBill(bl);
	    	billState.setBusinessId(UUID.randomUUID());
	    	billState.setComment("no comment yet");
	    	billState.setState(EBillState.NOT_PAID);
	    	billState.setStateDate(new Date());
	    	billState.setAmountPaid(new BigDecimal(0));
	    	billStateRepo.save(billState);
	    	System.out.println("Success");
	      return new ResponseEntity<>(regRequest, HttpStatus.OK);
	    } catch (Exception e) {
	    	System.out.println("Proplem");
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	


    
    @GetMapping(value = "/wclasses")
    public ResponseEntity<?> getAllClasses(){
    	return new ResponseEntity<>(classRepo.findAll(), HttpStatus.OK);
    }
}
