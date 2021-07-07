package rw.adrielsoft.wisdom.main.sr.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Registration {
	@Id
	private UUID businessId;
    @Temporal(TemporalType.DATE)
	private Date date;
    @ManyToOne
    @JoinColumn(name="studentId", nullable=false)
    private Student student;
    private String trim;
    private String studyMood;
    private String regStates;
    @ManyToOne
    @JoinColumn(name="classId", nullable=false)
    private WClass studyClass;
    @Transient
    private String totalDue;
    @OneToOne(mappedBy = "registration")
    private Bill bill;
    private String branch;
    public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	@OneToMany(mappedBy="registration")
    private List<StudentService> services;
    
	public UUID getBusinessId() {
		return businessId;
	}
	public void setBusinessId(UUID businessId) {
		this.businessId = businessId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public WClass getStudyClass() {
		return studyClass;
	}
	public void setStudyClass(WClass studyClass) {
		this.studyClass = studyClass;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public List<StudentService> getServices() {
		return services;
	}
	public void setServices(List<StudentService> services) {
		this.services = services;
	}
	public String getTotalDue() {
		return totalDue;
	}
	public void setTotalDue(String totalDue) {
		this.totalDue = totalDue;
	}
	public String getTrim() {
		return trim;
	}
	public void setTrim(String trim) {
		this.trim = trim;
	}
	public String getStudyMood() {
		return studyMood;
	}
	public void setStudyMood(String studyMood) {
		this.studyMood = studyMood;
	}
	public String getRegStates() {
		return regStates;
	}
	public void setRegStates(String regStates) {
		this.regStates = regStates;
	}
    
    
    
}
