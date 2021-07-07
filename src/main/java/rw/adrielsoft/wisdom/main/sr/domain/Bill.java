package rw.adrielsoft.wisdom.main.sr.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

@Component
@Entity	
public class Bill {
	@Id
	private UUID businessId;
	private BigDecimal totalDue;
	private BigDecimal balance;
	@Enumerated(EnumType.STRING)
	private EBillState state;
    @OneToOne
    @JoinColumn(name = "registrationId")
    private Registration registration;
	@OneToMany(mappedBy="bill")
	private List<BillState> billStates;
	
	public UUID getBusinessId() {
		return businessId;
	}
	public void setBusinessId(UUID businessId) {
		this.businessId = businessId;
	}
	public BigDecimal getTotalDue() {
		return totalDue;
	}
	public void setTotalDue(BigDecimal totalDue) {
		this.totalDue = totalDue;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public EBillState getState() {
		return state;
	}
	public void setState(EBillState state) {
		this.state = state;
	}
	public List<BillState> getBillStates() {
		return billStates;
	}
	public void setBillStates(List<BillState> billStates) {
		this.billStates = billStates;
	}
	public Registration getRegistration() {
		return registration;
	}
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
}
