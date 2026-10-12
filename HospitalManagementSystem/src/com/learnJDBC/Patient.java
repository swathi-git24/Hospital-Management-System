package com.learnJDBC;

import java.sql.*;
import java.util.*;

public class Patient {
	
	private Connection connection;
	private Scanner scanner;	
	
	public Patient(Connection connection,Scanner scanner) {//to add connects to data and also use scanners
		this.connection = connection; // whenever private is used we use .this keyword to access. 
		this.scanner = scanner;
		}
	
	// create a add patients() method , it adds the information to database by taking details from user.
	public void addPatients(){
		System.out.println("Enter Patient's Name");
		String name = scanner.next();
		
		System.out.println("Enter Patient's Age");
		int age = scanner.nextInt();
		
		System.out.println("Enter Patient's Gender");
		String gender = scanner.next();
		
		try {
			// SQL query used to insert the data
			String Query = "Insert into patients(name,age,gender) values (?,?,?)"; // (?,?,?) are the placeholder .when they take the input from the user,
			//they replace the ? with the info given by user. ?- holds the place and not leave it empty.
			
			PreparedStatement preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setString(1,name);      //set name in the query
			preparedStatement.setInt(2,age);          // set age in the query
			preparedStatement.setString(3,gender);    // set gender in the query
			
			int affectedRows = preparedStatement.executeUpdate(); //will check if the value is updated
			
			
			// if one or more rows affected patient added successfully .
			if(affectedRows>0) { //checks if executed
				System.out.println("Patient Added Successfully");
			}else {
				System.out.println("Failed to add Patients!!");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	 
	
	// viewPatients method to retrieve and display the patient details
	public void viewPatients() {
		String query = "select * from patients";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultset = preparedStatement.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+-------------+------------------+-----+--------+");
			System.out.println("| Patient ID  | Name             | Age | Gender |");
			System.out.println("+-------------+------------------+-----+--------+");
			
			//retrieve and displays all the patients 
			// loop through each patient record and display 
			while(resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				int age = resultset.getInt("age");
				String gender = resultset.getString("gender");
				
				System.out.printf("|%-13d|%-18s|%-5d|%-8s|\n",id,name,age,gender); //just to organize them with spaces and give them a neat structure.
				System.out.println("+-------------+------------------+-----+--------+");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientByID(int id)
	{ 
		//retrieve the data from the database with the id 
		String query = "select * from patients where ID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultset = preparedStatement.executeQuery(); // result set stores the result.
			
			// if resultset has a row , patient exists.
			if(resultset.next()) {
				return true;
			}
			else 
			{
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();}
		return false;
	}
	
	public static void main(String args[]) {
		
	}

}
