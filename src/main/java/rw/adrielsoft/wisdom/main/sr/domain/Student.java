package rw.adrielsoft.wisdom.main.sr.domain;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import rw.adrielsoft.wisdom.main.um.models.User;

@Component
@Entity
public class Student {
	@Id
	private UUID businessId;
	private String name;
	private String otherName;
	private String gender;
	private String studentId;
    private String insurance;
    private String country;
    private String city;
    @ManyToOne
    @JoinColumn(name="fatherId", nullable=true)
    private User father;
    @ManyToOne
    @JoinColumn(name="motherId", nullable=true)
    private User mother;
    @JsonIgnore
    @OneToMany(mappedBy="student")
    private List<Registration> registrations;
    
	public UUID getBusinessId() {
		return businessId;
	}
	public void setBusinessId(UUID businessId) {
		this.businessId = businessId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public List<Registration> getRegistrations() {
		return registrations;
	}
	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public User getFather() {
		return father;
	}
	public void setFather(User father) {
		this.father = father;
	}
	public User getMother() {
		return mother;
	}
	public void setMother(User mother) {
		this.mother = mother;
	}
	
	
	
}