/*
 * UseCase 7: Delete Contact
 * Controlled Access to private fields
 * Proper Validation before Contact Delete
 * @author: developer
 * @version: 7
 */

package com.mycontactsapp.main;

import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.service.UserService;
import com.mycontactsapp.contact.model.Contact;
import com.mycontactsapp.contact.model.Person;
import com.mycontactsapp.contact.model.Organization;

import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("\n==== MyContacts App ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. Profile Management");
            System.out.println("5. Add Contact");
            System.out.println("6. View Contact");
            System.out.println("7. Edit Contact");
            System.out.println("8. Delete Contact");
            System.out.println("9. Bulk Operations");
            System.out.println("10. Exit");
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
                        handleAddContact(scanner);
                        break;
                        
                    case 6:
                    	handleViewContact(scanner);
                    	break;
                    	
                    case 7:
                    	handleEditContact(scanner);
                    	break;

                    case 8:
                    	handleDeleteContact(scanner);
                    	break;
                    	
                    case 9:
                    	handleBulkOperations(scanner);
                    	break;
                    	
                    case 10:
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

    // Registration

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

    // Login

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

    // Logout

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

    // Add Contact

    private static void handleAddContact(Scanner scanner) {

        if (!UserService.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        System.out.print("Enter contact type (PERSON/ORGANIZATION): ");
        String type = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Contact contact;

        if (type.equalsIgnoreCase("PERSON")) {
            contact = new Person(name, phone, email);
        }
        else if (type.equalsIgnoreCase("ORGANIZATION")) {
            contact = new Organization(name, phone, email);
        }
        else {
            throw new IllegalArgumentException("Invalid contact type");
        }

        UserService.addContact(contact);

        System.out.println("Contact added successfully!");
    }
    
    private static void handleViewContact(Scanner scanner) {

        if (!UserService.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        User currentUser = UserService.getCurrentUser();

        if (currentUser.getContacts().isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\n--- Your Contacts ---");

        for (Contact contact : currentUser.getContacts()) {
            System.out.println("ID: " + contact.getId() + " | Name: " + contact.getName());
        }

        System.out.print("\nEnter Contact ID to view details: ");
        String id = scanner.nextLine();

        Contact contact = UserService.viewContact(id);

        if (contact != null) {
            System.out.println("\n--- Contact Details ---");
            System.out.println(contact); // calls toString()
        } else {
            System.out.println("Contact not found.");
        }
    }
    
    // Edit Contact

    private static void handleEditContact(Scanner scanner) {

        if (!UserService.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        User currentUser = UserService.getCurrentUser();

        if (currentUser.getContacts().isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\n--- Your Contacts ---");

        for (Contact contact : currentUser.getContacts()) {
            System.out.println("ID: " + contact.getId() + " | Name: " + contact.getName());
        }

        System.out.print("\nEnter Contact ID to edit: ");
        String id = scanner.nextLine();

        Contact contact = UserService.viewContact(id);

        if (contact == null) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Name");
        System.out.println("2. Phone");
        System.out.println("3. Email");
        System.out.print("Choose option: ");

        int option = Integer.parseInt(scanner.nextLine());

        String newName = null;
        String newPhone = null;
        String newEmail = null;

        switch (option) {

            case 1:
                System.out.print("Enter new name: ");
                newName = scanner.nextLine();
                break;

            case 2:
                System.out.print("Enter new phone: ");
                newPhone = scanner.nextLine();
                break;

            case 3:
                System.out.print("Enter new email: ");
                newEmail = scanner.nextLine();
                break;

            default:
                System.out.println("Invalid option.");
                return;
        }

        UserService.editContact(id, newName, newPhone, newEmail);

        System.out.println("Contact updated successfully!");
    }

    // Handle Delete
    private static void handleDeleteContact(Scanner scanner) {

        if (!UserService.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        User currentUser = UserService.getCurrentUser();

        if (currentUser.getContacts().isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\n--- Your Contacts ---");

        for (Contact contact : currentUser.getContacts()) {
            System.out.println("ID: " + contact.getId() + " | Name: " + contact.getName());
        }

        System.out.print("\nEnter Contact ID to delete: ");
        String id = scanner.nextLine();

        System.out.println("\n1. Soft Delete");
        System.out.println("2. Permanent Delete");
        System.out.print("Choose option: ");

        int option = Integer.parseInt(scanner.nextLine());

        System.out.print("Are you sure? (YES/NO): ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("YES")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        try {
            if (option == 1) {
                UserService.softDeleteContact(id);
                System.out.println("Contact soft deleted.");
            } 
            else if (option == 2) {
                UserService.hardDeleteContact(id);
                System.out.println("Contact permanently deleted.");
            } 
            else {
                System.out.println("Invalid option.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
 // Bulk Operations

    private static void handleBulkOperations(Scanner scanner) {

        if (!UserService.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        User currentUser = UserService.getCurrentUser();

        if (currentUser.getContacts().isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\n--- Your Contacts ---");

        for (Contact contact : currentUser.getContacts()) {
            System.out.println("ID: " + contact.getId() + " | Name: " + contact.getName());
        }

        System.out.print("\nEnter Contact IDs (comma separated): ");
        String input = scanner.nextLine();

        List<String> ids = Arrays.stream(input.split(","))
                .map(String::trim)
                .toList();

        System.out.println("\nChoose Bulk Operation:");
        System.out.println("1. Soft Delete");
        System.out.println("2. Permanent Delete");
        System.out.println("3. Export");
        System.out.print("Choose option: ");

        int option = Integer.parseInt(scanner.nextLine());

        try {

            switch (option) {

                case 1 -> {
                    UserService.bulkSoftDelete(ids);
                    System.out.println("Selected contacts soft deleted.");
                }

                case 2 -> {
                    UserService.bulkHardDelete(ids);
                    System.out.println("Selected contacts permanently deleted.");
                }

                case 3 -> {
                    List<Contact> exported = UserService.bulkExport(ids);

                    if (exported.isEmpty()) {
                        System.out.println("No matching contacts.");
                    } else {
                        System.out.println("\n--- Exported Contacts ---");
                        exported.forEach(System.out::println);
                    }
                }

                default -> System.out.println("Invalid option.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}