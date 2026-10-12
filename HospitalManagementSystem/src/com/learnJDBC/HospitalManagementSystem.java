package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	
			private static final String url = "jdbc:mysql://localhost:3306/hospital";
			private  static final String username = "root";
			private static final String password = "!swthi!244";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(System.in);
		
		//establishing the connection and getting details from the database tables
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			Patient patient = new Patient(connection,scanner);
			Doctor doctor = new Doctor(connection, scanner);
			
			while(true)
			{
				System.out.println("Hospital Management System");
				System.out.println("1.Add Patient");
				System.out.println("2.View Patients");
				System.out.println("3.View Doctors");
				System.out.println("4.Book Appointment");
				System.out.println("5.Add Doctor");
				System.out.println("6.Exit");
				System.out.println("Please Enter Your choice ");
				// it will check the input given by user and iterate the loop accordingly .
				//input can be from 1 to 5 . 
				
				int choice = scanner.nextInt();
				scanner.nextLine();
				
				switch(choice)
				{
				
				case 1:
					//Add patient
					patient.addPatients();
					System.out.println();
					break;
				
				case 2:
					//view patient
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					//View Doctor
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					//Book Appointment
					bookAppointment(patient,doctor,connection,scanner);
					System.out.println();
					break;
				/*case 5:
					System.out.println("THNAK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!\n ");
					return;
				default:
					System.out.println("Please Enter Valid Input");
					break;*/
					
				case 5:
				    doctor.addDoctor();
				    System.out.println();
				    break;

				case 6:
				    System.out.println("Thank you for using Hospital Management System!");
				    return;	
				    
				default:
			        System.out.println("Please Enter Valid Input");
			        break;    
					
					
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	    // creating a method for book appointment 
		public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) 
		{
			System.out.println("Please Enter Patient ID:");
			int patientID = scanner.nextInt();
			System.out.println("Please Enter Doctor ID:");
			int doctorID = scanner.nextInt();
			System.out.println("Please Enter Appointment Date(YYYY-MM-DD): ");
			String appointmentDate = scanner.next(); // in coding language date will be string.
			
			// retrieve the data from database that is patientID and doctorID.
			if(patient.getPatientByID(patientID) && doctor.getDoctorByID(doctorID))
			{
				//if the patient and doctor exist . it will check for the availability of both of them.
				if(checkDoctorAvailability(doctorID,appointmentDate,connection)) {
					String appointmentQuery ="INSERT INTO appointments (patients_id,doctor_id,appointment_date) values(?,?,?)"; // ??? are the placeholder for patientID,doctorID and appointment date.
					
					try {
						PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
						preparedStatement.setInt(1, patientID);
						preparedStatement.setInt(2, doctorID);
						preparedStatement.setString(3, appointmentDate);
						
						//check if the appointment is booked or not .
						int rowsAffected = preparedStatement.executeUpdate();
						if(rowsAffected > 0)
						{
							System.out.println("Appointment booked");
						}
						else {
							System.out.println("Appointement could not be booked");
						}
					}catch(SQLException e)
					{
						e.printStackTrace();
					}
				}else {
					System.out.println("Doctor not available on this date!!");
				}
			}else {
				System.out.println("Either doctor or patient doesn't exist!!");
			}

		}
		
		public static boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection connection)
		{
			//Count(*) : getting the rows which matches the particular criteria.
			String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id =? AND appointment_date = ? ";
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, doctorID);
				preparedStatement.setString(2, appointmentDate);
				
				ResultSet resultset = preparedStatement.executeQuery(); // result set is to store the result.
				
				if(resultset.next()) {
					//if doctor is available then.
					int count = resultset.getInt(1);
					if(count==0)
					{
						return true;
					}else {
						return false;
					}
				}
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
			return false;
		}
		{
			
		}

	}


