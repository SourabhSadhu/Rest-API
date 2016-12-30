package restTesting;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "bank")
public class DBPojo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer accountnumber;

	private String address;

	private double amount;

	private Integer ifsccode;

	private String name;

	private Integer no;

	private String type;

	public DBPojo() {
	}

	public Integer getAccountnumber() {
		return this.accountnumber;
	}

	public void setAccountnumber(Integer accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Integer getIfsccode() {
		return this.ifsccode;
	}

	public void setIfsccode(Integer ifsccode) {
		this.ifsccode = ifsccode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNo() {
		return this.no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
