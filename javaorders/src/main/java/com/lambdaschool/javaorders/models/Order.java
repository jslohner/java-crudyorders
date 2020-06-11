package com.lambdaschool.javaorders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ordnum;

	private double ordamount;
	private double advanceamount;
	private String orderdescription;

	@ManyToMany()
	@JoinTable(
			name = "orderspayments",
			joinColumns = @JoinColumn(name = "ordnum"),
			inverseJoinColumns = @JoinColumn(name = "paymentid")
	)
	@JsonIgnoreProperties("orders")
	private List<Payment> payments = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "custcode", nullable = false)
	@JsonIgnoreProperties("orders")
	private Customer customer;

	public Order() {
	}

	public Order(double ordamount, double advanceamount, Customer customer, String orderdescription) {
		this.ordamount = ordamount;
		this.advanceamount = advanceamount;
		this.orderdescription = orderdescription;
		this.customer = customer;
	}

	public long getOrdnum() {
		return ordnum;
	}

	public void setOrdnum(long ordnum) {
		this.ordnum = ordnum;
	}

	public double getOrdamount() {
		return ordamount;
	}

	public void setOrdamount(double ordamount) {
		this.ordamount = ordamount;
	}

	public double getAdvanceamount() {
		return advanceamount;
	}

	public void setAdvanceamount(double advanceamount) {
		this.advanceamount = advanceamount;
	}

	public String getOrderdescription() {
		return orderdescription;
	}

	public void setOrderdescription(String orderdescription) {
		this.orderdescription = orderdescription;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public void addPayment(Payment payment) {
		payments.add(payment);
		payment.getOrders()
				.add(this);
	}

	@Override
	public String toString() {
		return "Order{" +
					   "ordnum=" + ordnum +
					   ", ordamount=" + ordamount +
					   ", advanceamount=" + advanceamount +
					   ", orderdescription='" + orderdescription + '\'' +
					   ", customer=" + customer +
					   ", payments=" + payments +
					   '}';
	}
}
