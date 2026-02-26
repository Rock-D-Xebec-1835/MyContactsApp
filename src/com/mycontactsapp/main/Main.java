package com.mycontactsapp.main;
import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.service.*;


import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to MyContactsApp");
		
		try {
			System.out.print("Enter user type (FREE / PREMIUM): ");
			String type = scanner.nextLine();
			System.out.print("Enter email: ");
			String email = scanner.nextLine();
			System.out.print("Enter password: ");
			String password = scanner.nextLine();
			System.out.print("Enter name: ");
			String name = scanner.nextLine();
			
			User user = UserService.registerUser(type, email, password, name);
			System.out.println("\nUser Registered Successfully!");
			System.out.println("Email: " + user.getEmail());
            System.out.println("Name: " + user.getName());
            System.out.println("User Type: " + user.getClass().getSimpleName());
		}
		catch(Exception e) {
			System.out.println("\nRegistration Failed: " + e.getMessage());
		}
		try {
			System.out.print("\nEnter user type (FREE / PREMIUM): ");
			String type = scanner.nextLine();
			System.out.print("Enter email: ");
			String email = scanner.nextLine();
			System.out.print("Enter password: ");
			String password = scanner.nextLine();
			System.out.print("Enter name: ");
			String name = scanner.nextLine();
			
			User user = UserService.registerUser(type, email, password, name);
			System.out.println("\nUser Registered Successfully!");
			System.out.println("Email: " + user.getEmail());
            System.out.println("Name: " + user.getName());
            System.out.println("User Type: " + user.getClass().getSimpleName());
		}
		catch(Exception e) {
			System.out.println("\nRegistration Failed: " + e.getMessage());
		}
		scanner.close();
	}

}
