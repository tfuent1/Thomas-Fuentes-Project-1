package com.revature.model;

import java.util.Objects;

public class ticket {
	private int ticket_id;
	private String ticket_status;
	private String ticket_reason;
	private Double ticket_price;
	private String ticket_name;
	public ticket(int ticket_id, String ticket_status, String ticket_reason, Double ticket_price, String ticket_name) {
		super();
		this.ticket_id = ticket_id;
		this.ticket_status = ticket_status;
		this.ticket_reason = ticket_reason;
		this.ticket_price = ticket_price;
		this.ticket_name = ticket_name;
	}
	public ticket() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getTicket_status() {
		return ticket_status;
	}
	public void setTicket_status(String ticket_status) {
		this.ticket_status = ticket_status;
	}
	public String getTicket_reason() {
		return ticket_reason;
	}
	public void setTicket_reason(String ticket_reason) {
		this.ticket_reason = ticket_reason;
	}
	public Double getTicket_price() {
		return ticket_price;
	}
	public void setTicket_price(Double ticket_price) {
		this.ticket_price = ticket_price;
	}
	public String getTicket_name() {
		return ticket_name;
	}
	public void setTicket_name(String ticket_name) {
		this.ticket_name = ticket_name;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ticket_id, ticket_name, ticket_price, ticket_reason, ticket_status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ticket other = (ticket) obj;
		return ticket_id == other.ticket_id && Objects.equals(ticket_name, other.ticket_name)
				&& Objects.equals(ticket_price, other.ticket_price)
				&& Objects.equals(ticket_reason, other.ticket_reason)
				&& Objects.equals(ticket_status, other.ticket_status);
	}
	@Override
	public String toString() {
		return "ticket [ticket_id=" + ticket_id + ", ticket_status=" + ticket_status + ", ticket_reason="
				+ ticket_reason + ", ticket_price=" + ticket_price + ", ticket_name=" + ticket_name + "]";
	}

	
}