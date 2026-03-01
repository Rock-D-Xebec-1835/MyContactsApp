/*
 * UseCase 10: Advanced Filtering
 * Controlled Access to private fields
 * Proper Validation before Contact Filtering
 * @author: developer
 * @version: 10
 */

package com.mycontactsapp.main;

import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.service.UserService;
import com.mycontactsapp.contact.model.Contact;
import com.mycontactsapp.contact.model.Person;
import com.mycontactsapp.contact.model.Organization;
import com.mycontactsapp.contact.search.*;

import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
            System.out.println("10. Search Contacts");
            System.out.println("11. Filter Contacts");
            System.out.println("12. Exit");
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
                    	handleSearchContacts(scanner);
                    	break;
                    	
                    case 11:
                    	handleAdvancedFiltering(scanner);
                    	break;
                    	
                    case 12:
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
    
    // Search Contacts
    private static void handleSearchContacts(Scanner scanner) {

        if (!UserService.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        List<SearchCriteria> criteriaList = new ArrayList<>();

        boolean adding = true;

        while (adding) {

            System.out.println("\nAdd Search Condition:");
            System.out.println("1. Name");
            System.out.println("2. Phone");
            System.out.println("3. Email");
            System.out.println("4. Done");
            System.out.print("Choose option: ");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {

                case 1 -> {
                    System.out.print("Enter name keyword: ");
                    criteriaList.add(new NameCriteria(scanner.nextLine()));
                }

                case 2 -> {
                    System.out.print("Enter phone keyword: ");
                    criteriaList.add(new PhoneCriteria(scanner.nextLine()));
                }

                case 3 -> {
                    System.out.print("Enter email keyword: ");
                    criteriaList.add(new EmailCriteria(scanner.nextLine()));
                }

                case 4 -> adding = false;

                default -> System.out.println("Invalid option.");
            }
        }

        if (criteriaList.isEmpty()) {
            System.out.println("No search conditions added.");
            return;
        }

        System.out.println("\nCombine conditions using:");
        System.out.println("1. AND");
        System.out.println("2. OR");
        System.out.print("Choose option: ");

        int combineOption = Integer.parseInt(scanner.nextLine());

        SearchCriteria finalCriteria;

        if (combineOption == 1) {
            finalCriteria = new AndCriteria(criteriaList);
        } else if (combineOption == 2) {
            finalCriteria = new OrCriteria(criteriaList);
        } else {
            System.out.println("Invalid combination option.");
            return;
        }

        List<Contact> results = UserService.search(finalCriteria);

        if (results.isEmpty()) {
            System.out.println("No matching contacts found.");
        } else {
            System.out.println("\n--- Search Results ---");
            results.forEach(System.out::println);
        }
    }
    
 // Advanced Filtering (UC-10)

    private static void handleAdvancedFiltering(Scanner scanner) {

        if (!UserService.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<SearchCriteria> criteriaList = new ArrayList<>();

        boolean adding = true;

        while (adding) {

            System.out.println("\nAdd Filter:");
            System.out.println("1. Date Added (Range)");
            System.out.println("2. Frequently Contacted");
            System.out.println("3. Done");
            System.out.print("Choose option: ");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {

                case 1 -> {

                    System.out.print("Enter start date (yyyy-MM-dd) or leave blank: ");
                    String startInput = scanner.nextLine();

                    System.out.print("Enter end date (yyyy-MM-dd) or leave blank: ");
                    String endInput = scanner.nextLine();

                    LocalDateTime after = null;
                    LocalDateTime before = null;

                    if (!startInput.isBlank()) {
                    	try {
                            after = LocalDate.parse(startInput, formatter).atStartOfDay();
                    	}
                    	catch(DateTimeParseException e) {
                    		System.out.println("Invalid date format. Use dd-MM-yyyy");
                    	}
                    }

                    if (!endInput.isBlank()) {
                    	try {
                    		before = LocalDate.parse(endInput, formatter).atTime(23, 59, 59);
                    	}
                    	catch(DateTimeParseException e) {
                    		System.out.println("Invalid date format. Use dd-MM-yyyy");
                    	}
                    }

                    criteriaList.add(new DateRangeCriteria(after, before));
                }

                case 2 -> {

                    System.out.print("Enter minimum contact frequency: ");
                    int threshold = Integer.parseInt(scanner.nextLine());

                    criteriaList.add(new FrequentlyContactedCriteria(threshold));
                }

                case 3 -> adding = false;

                default -> System.out.println("Invalid option.");
            }
        }

        if (criteriaList.isEmpty()) {
            System.out.println("No filters selected.");
            return;
        }

        SearchCriteria finalCriteria =
                new AndCriteria(criteriaList);

        System.out.println("\nSort By:");
        System.out.println("1. Name");
        System.out.println("2. Date Added (Newest First)");
        System.out.println("3. Frequency (Most Contacted First)");
        System.out.print("Choose option: ");

        int sortOption = Integer.parseInt(scanner.nextLine());

        Comparator<Contact> comparator;

        switch (sortOption) {

            case 1 -> comparator = ContactComparators.byName;

            case 2 -> comparator = ContactComparators.byDateDesc;

            case 3 -> comparator = ContactComparators.byFrequency;

            default -> {
                System.out.println("Invalid sort option.");
                return;
            }
        }

        List<Contact> results =
                UserService.searchAndSort(finalCriteria, comparator);

        if (results.isEmpty()) {
            System.out.println("No matching contacts found.");
        } else {
            System.out.println("\n--- Filtered Results ---");
            results.forEach(System.out::println);
        }
    }
}