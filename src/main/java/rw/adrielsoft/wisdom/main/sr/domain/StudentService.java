package rw.adrielsoft.wisdom.main.sr.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.stereotype.Component;

@Component
@Entity
public class StudentService {
	@Id
	private UUID businessId;
	private String comment;
    @ManyToOne
    @JoinColumn(name="registrationId", nullable=false)
    private Registration registration;
	
	public UUID getBusinessId() {
		return businessId;
	}
	public void setBusinessId(UUID businessId) {
		this.businessId = businessId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Registration getRegistration() {
		return registration;
	}
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	
	
}
