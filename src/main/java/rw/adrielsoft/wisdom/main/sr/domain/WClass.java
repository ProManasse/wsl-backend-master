package rw.adrielsoft.wisdom.main.sr.domain;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rw.adrielsoft.wisdom.main.um.models.User;

@Component
@Entity
public class WClass {
	@Id
	private UUID businessId;
	private String name;
	@Transient
	private String ctId;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="classTeacherId")
	private User classTeacher;
	@JsonIgnore
    @OneToMany(mappedBy="studyClass")
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
	public List<Registration> getRegistrations() {
		return registrations;
	}
	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}
	public User getClassTeacher() {
		return classTeacher;
	}
	public void setClassTeacher(User classTeacher) {
		this.classTeacher = classTeacher;
	}
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	
	
	
}
