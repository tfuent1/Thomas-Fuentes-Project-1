package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionFactory {

		public static Connection getConnection() throws SQLException {
			Properties props = new Properties();
	
//------------------------------------------------------------------------------------------------			
			//set up FileReader and handle Exception
			try(FileReader reader = new FileReader("src/main/resources/application.properties")){
				props.load(reader);
			}catch(IOException e) {
				e.printStackTrace();
			}
			
//------------------------------------------------------------------------------------------------			
			return DriverManager.getConnection(
					props.getProperty("url"),
					props.getProperty("username"),
					props.getProperty("password"));
				
		
		}
}
