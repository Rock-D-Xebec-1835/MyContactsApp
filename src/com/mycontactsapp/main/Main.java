/*
 * UseCase 3: User Profile Management
 * Update user fields
 * Controlled Access to private fields
 * Proper Validation before update
 * @author: developer
 * @version: 3
 */

package com.mycontactsapp.main;

import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.service.UserService;

import java.util.Scanner;

public class Main {
	//Main method
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("\n==== MyContacts App ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. Profile Management");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {

                    case 1:
                        handleRegistration(scanner);
                        break;

                    case 2:
                        handleLogin(scanner);
                        break;

                    case 3:
                        handleLogout();
                        break;

                    case 4:
                        handleProfileManagement(scanner);
                        break;

                    case 5:
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // User Registration
    private static void handleRegistration(Scanner scanner) {

        System.out.print("Enter user type (FREE/PREMIUM): ");
        String type = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        User user = UserService.registerUser(type, email, password, name);

        System.out.println("User registered successfully: " + user.getEmail());
    }

    // User Login
    private static void handleLogin(Scanner scanner) {

        if (UserService.isLoggedIn()) {
            System.out.println("User already logged in. Logout first.");
            return;
        }

        System.out.print("Authentication type (BASIC/OAUTH): ");
        String type = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = UserService.login(type, email, password);

        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getName());
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // Handle Logout
    private static void handleLogout() {

        if (!UserService.isLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        UserService.logout();
        System.out.println("Logged out successfully.");
    }

    // Profile Management
    private static void handleProfileManagement(Scanner scanner) {

        if (!UserService.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        boolean managing = true;

        while (managing) {

            System.out.println("\n---- Profile Management ----");
            System.out.println("1. Update Name");
            System.out.println("2. Change Password");
            System.out.println("3. Back");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            User currentUser = UserService.getCurrentUser();

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        currentUser.updateName(newName);
                        System.out.println("Name updated successfully.");
                        break;

                    case 2:
                        System.out.print("Enter current password: ");
                        String oldPassword = scanner.nextLine();

                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();

                        currentUser.changePassword(oldPassword, newPassword);
                        System.out.println("Password changed successfully.");
                        break;

                    case 3:
                        managing = false;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}