package com.revature;

import com.revature.util.Sessions;

public class Driver {

	public static void main(String[] args) {
		//Create instance of Sessions class
		Sessions sessions = new Sessions();

		sessions.register();
		sessions.eLogin();
		sessions.pastTickets();
		sessions.newTicket();
		sessions.mLogin();
		sessions.allTickets();
		sessions.pendingTickets();
		sessions.approvedTickets();
		sessions.deniedTickets();
		sessions.updateStatus();
		sessions.logout();
	}
}

