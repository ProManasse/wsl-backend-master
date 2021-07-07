package rw.adrielsoft.wisdom.main.sr.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Component
@Entity
public class BillState {
	@Id
	private UUID businessId;
	private Date stateDate;
	private String comment;
	private BigDecimal amountPaid;
	@Enumerated(EnumType.STRING)
	private EBillState state;
    @ManyToOne
    @JoinColumn(name="billId", nullable=false)
	private Bill bill;
	
	public UUID getBusinessId() {
		return businessId;
	}
	public void setBusinessId(UUID businessId) {
		this.businessId = businessId;
	}
	public Date getStateDate() {
		return stateDate;
	}
	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}
	public EBillState getState() {
		return state;
	}
	public void setState(EBillState state) {
		this.state = state;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	
}

