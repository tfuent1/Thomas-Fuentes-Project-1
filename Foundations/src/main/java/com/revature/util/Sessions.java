package com.revature.util;

import java.util.List;

import com.revature.model.fundUser;
import com.revature.model.ticket;
import com.revature.repository.fundUserRepository;
import com.revature.repository.ticketRepository;

import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

public class Sessions {
	
	//Connect to Javalin
	Javalin app = Javalin.create().start(8000);
	ticketRepository ticketRep = new ticketRepository();
	fundUserRepository userRep = new fundUserRepository();
	
	
//-------------------------------------------------------------
	public void register() {

		app.post("/register", ctx -> {
				
			//Creating HttpSessions
			HttpSession session = ctx.req().getSession();
			fundUser newUser = ctx.bodyAsClass(fundUser.class);
			
			if(!userRep.availableName(newUser.getFundUser_name())) {
				ctx.result("Username Unavailable.");
			}else if (userRep.userIsValid(newUser)) {
				userRep.addUser(newUser);
				ctx.result("User Created");
			}else {
				ctx.result("Invalid Input");
			}
			
			System.out.println(ctx.body());
		});
	}
	
//-------------------------------------------------------------
//Login Session
	public void eLogin() {
		app.post("/eLogin", ctx -> {
			fundUser user = ctx.bodyAsClass(fundUser.class);
			fundUser authUser = userRep.authenticateEmployee(user);
			
			if(authUser.getFundUser_id() > 0) {
				HttpSession session = ctx.req().getSession();
				session.setAttribute("role", "Employee");
				session.setAttribute("user_name", authUser.getFundUser_name());
			}
		});
	}
	
//-------------------------------------------------------------
//	//Login Session
//	public void login() {
//		
//		app.post("/login", ctx -> {
//			
//			fundUser user = ctx.bodyAsClass(fundUser.class);
//			fundUser authUser = userRep.authenticate(user);
//			
//			if(authUser.getFundUser_id() > 0) {
//				HttpSession session = ctx.req().getSession();
//				//Cookie username = new Cookie("")
//				ctx.cookie("jwt", JwtFactory.);
//			}
//			
//			System.out.println(ctx.body());
//		});
//	}
	
//-------------------------------------------------------------
	public void pastTickets() {

		app.get("/tickets/employee", ctx -> {
				
			//Creating HttpSessions
			HttpSession session = ctx.req().getSession(false);
			if(session == null) {
				ctx.json("Not Authenticated");
			}else if(session.getAttribute("role").equals("Employee")) {
				String name = (String) session.getAttribute("user_name");
				fundUserRepository UserRepository = new fundUserRepository();
				
				if(UserRepository.validateUserId(name)) {
					ctx.json(ticketRep.listByUser(name));
				}else {
					ctx.result("Bad Request");
				}
			}else {
				ctx.result("Role Not Authorized");
			}
		});
	}
	
//-------------------------------------------------------------
	public void newTicket() {

		app.post("/tickets/create", ctx -> {
			System.out.println("Line 105");
			HttpSession session = ctx.req().getSession(false);
			if(session == null) {
				ctx.json("Not Authenticated");
			}else if (session.getAttribute("role").equals("Employee")) {
				ticket Ticket = ctx.bodyAsClass(ticket.class);
				ticketRep.addTicket(Ticket);
				ctx.result("Ticket Added");
			}else {
				ctx.result("Role Not Authorized");
			}
			System.out.println(ctx.body());
		});
	}
	
//-------------------------------------------------------------
//	//Manager Login Session
	public void mLogin() {

		app.post("/mLogin", ctx -> {
			fundUser user = ctx.bodyAsClass(fundUser.class);
			fundUser authUser = userRep.authenticateManager(user);
			
			if(authUser.getFundUser_id() > 0) {
				
				HttpSession session = ctx.req().getSession();
				session.setAttribute("role", "Manager");
			}
		});
	}

//-------------------------------------------------------------
	public void allTickets() {

		app.get("/tickets/all", ctx -> {
				
			//Creating HttpSessions
			HttpSession session = ctx.req().getSession(false);
			if(session == null) {
				ctx.json("Not Authenticated");
			}else if (session.getAttribute("role").equals("Manager")) {
				List<ticket> ticketList = ticketRep.allTickets();
			
				if(ticketList.size() ==0) {
				ctx.result("No Results");
				}else {
				ctx.json(ticketList);
				}
			}else {
				ctx.result("Role Not Authorized");
			}
		});
	}
	
//-------------------------------------------------------------
	public void pendingTickets() {

		app.get("/tickets/pending", ctx -> {
				
			//Creating HttpSessions
			HttpSession session = ctx.req().getSession(false);
			if(session == null) {
				ctx.json("Not Authenticated");
			}else if(session.getAttribute("role").equals("Manager")) {
				List<ticket> ticketList = ticketRep.pendingTickets();
			
				if(ticketList.size() == 0) {
					ctx.result("No Results");
				}else {
					ctx.json(ticketList);
				}
			}else {
				ctx.result("Role Not Authorized");
			}
		});
	}	
	
//-------------------------------------------------------------
	public void approvedTickets() {

		app.get("/tickets/approved", ctx -> {
				
			//Creating HttpSessions
			HttpSession session = ctx.req().getSession(false);
			if(session == null) {
				ctx.json("Not Authenticated");
			}else if(session.getAttribute("role").equals("Manager")) {
				List<ticket> ticketList = ticketRep.approvedTickets();
			
				if(ticketList.size() == 0) {
					ctx.result("No Results");
				}else {
					ctx.json(ticketList);
				}
			}else {
				ctx.result("Role Not Authorized");
			}
		});
	}
	
//-------------------------------------------------------------
	public void deniedTickets() {

		app.get("/tickets/denied", ctx -> {
			//Creating HttpSessions
			HttpSession session = ctx.req().getSession(false);
			if(session == null) {
				ctx.json("Not Authenticated");
			}else if(session.getAttribute("role").equals("Manager")) {
				List<ticket> ticketList= ticketRep.deniedTickets();
			
				if(ticketList.size() ==0) {
					ctx.result("No Results");
				}else {
					ctx.json(ticketList);
				}
			}else {
				ctx.result("Role Not Authorized");
			}
		});
	}	
	
//-------------------------------------------------------------
	public void updateStatus() {

		app.put("/update/{ticket_id}", ctx -> {
				
			//Creating HttpSessions
			HttpSession session = ctx.req().getSession(false);
			ticket Ticket = ticketRep.findTicketById(Integer.parseInt(ctx.pathParam("ticket_id")));
			String status = ctx.queryParam("status");
			if(session == null) {
				ctx.json("Not Authenticated"); 
			}else if(session.getAttribute("role").equals("Manager")) {
				
				ticketRep.updateStatus(Ticket, status);
				ctx.result("Ticket Updated");
			}else {
				ctx.result("Role Not Authorized");
			}
			System.out.println(ctx.body());
		});
	}	
	
//-------------------------------------------------------------
	//Logout Function
	public void logout() {
		
		app.get("/logout", ctx -> {
			
			//To get rid of HttpSession
			HttpSession session = ctx.req().getSession(false);
			if(session != null) session.invalidate();
		});
	}
	
//-------------------------------------------------------------
	public void Login() {

		app.post("/mLogin", ctx -> {
				
			//Creating HttpSessions
			HttpSession session = ctx.req().getSession();
			session.setAttribute("role", "Manager");
		});
	}
}