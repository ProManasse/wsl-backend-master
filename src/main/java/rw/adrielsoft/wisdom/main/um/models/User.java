package rw.adrielsoft.wisdom.main.um.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rw.adrielsoft.wisdom.main.sr.domain.Student;
import rw.adrielsoft.wisdom.main.sr.domain.WClass;

@Component
@Entity
@Table(	name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),@UniqueConstraint(columnNames = "email") })
public class User {
	@Id
	private UUID id;

//	@NotBlank
//	@Size(max = 20)
	private String username;

//	@NotBlank
//	@Size(max = 50)
//	@Email
	private String email;

//	@NotBlank
//	@Size(max = 120)
	private String password;
	
//	@NotBlank
//	@Size(max = 10, min=10)
	private String phoneno;
	
//	@NotBlank
//	@Size(max = 45, min=5)
	private String name;
	
//	@NotBlank
//	@Size(max = 16, min=16)
	private String nid;

	private Double salary;
	
	private String branch;
	
	private String userType;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
    public List<Student> getfChildren() {
		return fChildren;
	}



	public void setfChildren(List<Student> fChildren) {
		this.fChildren = fChildren;
	}



	public List<Student> getmChildren() {
		return mChildren;
	}



	public void setmChildren(List<Student> mChildren) {
		this.mChildren = mChildren;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy="father")
    private List<Student> fChildren;
	@JsonIgnore
    @OneToMany(mappedBy="mother")
    private List<Student> mChildren;
	@JsonIgnore
    @OneToMany(mappedBy="classTeacher")
    private List<WClass> classes;
	
	public User() {
	}

	public User(String username, String email, String password, String phoneno, String name, String nid,
			Double salary, String branch, String userType) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.phoneno = phoneno;
		this.name = name;
		this.nid = nid;
		this.salary = salary;
		this.branch = branch;
		this.userType = userType;
	}


	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}



	public String getBranch() {
		return branch;
	}



	public void setBranch(String branch) {
		this.branch = branch;
	}



	public List<WClass> getClasses() {
		return classes;
	}



	public void setClasses(List<WClass> classes) {
		this.classes = classes;
	}



	public String getUserType() {
		return userType;
	}



	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
