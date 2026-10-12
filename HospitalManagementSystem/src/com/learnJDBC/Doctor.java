package com.learnJDBC;

import java.sql.*;
import java.util.Scanner;

public class Doctor {
	private Connection connection;
	private Scanner scanner;

	public Doctor(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}
	
	// create a method view_doctors() 
	public void viewDoctors() {
		String query = "select * from doctors";  // SQl query to retrieve data
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultset = preparedStatement.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+------------+------------------+---------------+");
			System.out.println("| Doctor ID  | Name             | Specialization |");
			System.out.println("+------------+------------------+---------------+");
			
			//data to be generated one by one in order to do that we use loop and it will run until the data in the table is empty:
			while(resultset.next()) 
			{
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				String Specialization = resultset.getString("Specialization");
				
				System.out.printf("|%-12d|%-18s|%-16s|\n",id,name,Specialization);//just to organize them with spaces
				System.out.println("+-------------+------------------+-----+--------+");
			}
		}catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorByID(int id)
	{ //retrieve the data from the database with the id 
		String query = "select * from doctors where ID = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultset = preparedStatement.executeQuery();
			if(resultset.next()) {
				return true;
			}
			else 
			{
				return false;
			}
			
		}catch(SQLException e) {e.printStackTrace();}
		return false;
	}

	public void addDoctor() {
		// TODO Auto-generated method stub
		System.out.println("Enter Doctor Name:");
	    String name = scanner.nextLine().trim();

	    System.out.println("Enter Doctor Specialization:");
	    String specialization = scanner.nextLine().trim();
	    
	    if (name.isEmpty() || specialization.isEmpty()) {
            System.out.println("Name and specialization cannot be empty!");
            return;
        }

	    String query = "INSERT INTO doctors (name, specialization) VALUES (?, ?)";

	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(query);

	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, specialization);

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Doctor added successfully!");
	        } else {
                System.out.println("Doctor could not be added.");
            }


	    } catch (SQLException e) {
	    	 System.out.println("Error while adding doctor.");
	    	e.printStackTrace();
	    }
	
		
	}
}
