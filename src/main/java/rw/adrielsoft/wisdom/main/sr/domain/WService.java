package rw.adrielsoft.wisdom.main.sr.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class WService {
	@Id
	private UUID serviceId;
	private String name;
	private double price;
	
	public UUID getServiceId() {
		return serviceId;
	}
	public void setServiceId(UUID serviceId) {
		this.serviceId = serviceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
