package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.ticket;
import com.revature.util.ConnectionFactory;

public class ticketRepository {
	
//---------------------VERIFY TICKET STATUS IS PENDING-------------------------------
	public boolean pendingStatus(ticket Ticket) {
		boolean pendingStatus = false;
		
		if(Ticket.getTicket_status().equals("Pending")) {
			pendingStatus = true;
		}
		return pendingStatus;
	}
	
//-----------------------------------------------------------------------------------
	public ticket findTicketById(int id) {
		PreparedStatement stmt = null;
		ResultSet set = null;
		ticket returnTicket = null;
		final String SQL = "SELECT * FROM ticket WHERE ticket_id = ?";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			set = stmt.executeQuery();
			set.next();
			returnTicket = new ticket(
					set.getInt(1),
					set.getString(2),
					set.getString(3),
					set.getDouble(4),
					set.getString(5)
					);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return returnTicket;
	}
	
//----------------------UPDATE TICKET STATUS-----------------------------------------
		//MANAGER APPROVES OR DENIES
	public ticket updateStatus(ticket ticketToUpdate, String status) {
		PreparedStatement stmt = null;
		int ticketId = ticketToUpdate.getTicket_id();
		System.out.println(ticketId);
		System.out.println(status);
		//String input = null;
		final String SQL = "UPDATE ticket SET ticket_status = ? WHERE ticket_id = ? "
				+ "AND ticket_status = 'Pending'";
			
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, status);
			stmt.setInt(2, ticketId);
			stmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		ticket updatedTicket = new ticket();
		updatedTicket = findTicketById(ticketId);
		return updatedTicket;
	}
	
//-----------------------------VIEW PAST TICKETS-------------------------------------
		//EMPLOYEE VIEWS PAST TICKETS
	public List<ticket> pastTickets(String name) {
		List<ticket> tickets = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet set = null;
		ticket Ticket = new ticket();
		
		final String SQL = "SELECT * FROM ticket WHERE ticket_name = ?";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, name); //Pass in string variable
			set = stmt.executeQuery();
			
			while(set.next()) {
				tickets.add(new ticket(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getDouble(4),
						set.getString(5)
						));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return tickets;
	}

//-----------------------------------------------------------------------------------
	public List<ticket> listByUser(String name) {
		Statement stmt = null;
		ResultSet set = null;
		List<ticket> tickets = new ArrayList<>();
		final String SQL = "SELECT * FROM ticket WHERE ticket_name = '"+name+"' ORDER BY ticket_id desc";
		//WHERE ticket_name="+name+" ORDER BY ticket_id desc
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery(SQL);
			
			while(set.next()) {
				ticket newTicket = new ticket(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getDouble(4),
						set.getString(5)
						);
				tickets.add(newTicket);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tickets;
	}
	
//-------------------------------ADD NEW TICKET--------------------------------------
		//EMPLOYEE SUBMITS NEW TICKET
	public void addTicket(ticket Ticket) {
		PreparedStatement stmt = null;
		//String pending = "Pending"; //New tickets default to pending status
		final String SQL = "INSERT INTO ticket values(default, 'Pending', ?, ?, ?)";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, Ticket.getTicket_reason());
			stmt.setDouble(2, Ticket.getTicket_price());
			stmt.setString(3, Ticket.getTicket_name());
			stmt.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

//------------------------------MANAGER VIEW PENDING TICKETS-------------------------
		//MANAGER VIEWS ALL TICKETS WITH PENDING STATUS
	public List<ticket> pendingTickets() {
		List<ticket> tickets = new ArrayList<>();
		Statement stmt = null;
		ResultSet set = null;
		final String SQL = "SELECT * FROM ticket WHERE ticket_status = 'Pending'";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery(SQL);
			
			while(set.next()) {
				tickets.add(new ticket(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getDouble(4),
						set.getString(5)
						));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return tickets;
	}
	
//---------------------------MANAGER VIEW APPROVED TICKETS---------------------------
	public List<ticket> approvedTickets () {
		List<ticket> tickets = new ArrayList<>();
		Statement stmt = null;
		ResultSet set = null;
		final String SQL = "SELECT * FROM ticket WHERE ticket_status = 'Approved'";
			
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery(SQL);
				
			while(set.next()) {
				tickets.add(new ticket(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getDouble(4),
						set.getString(5)
						));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return tickets;
	}
	
//---------------------------MANAGER VIEW DENIED TICKETS---------------------------
	public List<ticket> deniedTickets () {
		List<ticket> tickets = new ArrayList<>();
		Statement stmt = null;
		ResultSet set = null;
		final String SQL = "SELECT * FROM ticket WHERE ticket_status = 'Denied'";
			
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery(SQL);
				
			while(set.next()) {
				tickets.add(new ticket(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getDouble(4),
						set.getString(5)
						));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return tickets;	
	}
		
//---------------------------MANAGER VIEW ALL TICKETS--------------------------------
	public List<ticket> allTickets () {
		List<ticket> tickets = new ArrayList<>();
		Statement stmt = null;
		ResultSet set = null;
		final String SQL = "SELECT * FROM ticket";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery(SQL);
			
			while(set.next()) {
				tickets.add(new ticket(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getDouble(4),
						set.getString(5)
						));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return tickets;
	}
	
	
}

