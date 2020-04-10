package com.mateuszaksjonow;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<Contact> contactList = new LinkedList<>();
//        File phoneBook = new File("phoneBook.bin");
        if (deserialize("phoneBook.bin") != null) {
            contactList = (List<Contact>) deserialize("phoneBook.bin");
        }
        System.out.println("open phonebook.db");
        mainMenu(contactList, scanner);
    }

    private static void mainMenu(List<Contact> list, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            switch (scanner.nextLine()) {
                case "add":
                    addContact(list, scanner);
                    serialize(list, "phoneBook.bin");
                    break;
                case "list":
                    listMenu(list, scanner);
                    break;
                case "search":
                    search(list, scanner);
                    break;
                case "count":
                    System.out.println("The Phone Book has " + list.size() + " records.");
                    break;
                case "exit":
                    exit = true;
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }

    private static void listMenu(List<Contact> list, Scanner scanner) {
        printContacts(list);
        boolean exit = false;
        while (!exit) {
            System.out.print("[list] Enter action ([number], menu): ");
            String input = scanner.nextLine();
            //Try to enter record
            if (!input.equalsIgnoreCase("menu")) {
                try {
                    int index = Integer.parseInt(input) - 1;
                    contactInfo(list.get(index));
                    recordMenu(list.get(index), list, scanner);
                    //catching exception means user didn't pass a number
                } catch (InputMismatchException | IndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Invalid command");
                }
            }
            //Exits to menu if menu was entered
            exit = true;
        }
    }

    private static void searchMenu(List<Contact> list, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.print("[search] Enter action ([number], menu, again): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("menu")) {
                exit = true;
            } else if (input.equalsIgnoreCase("again")) {
                search(list, scanner);
            } else {
                //Try to enter record
                try {
                    int index = Integer.parseInt(input);
                    info(list, index);
                    //catching exception means user didn't pass a number
                } catch (InputMismatchException | IndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Invalid command");
                }
            }
        }
    }

    private static boolean search(List<Contact> list, Scanner scanner) {
        System.out.print("Enter search query: ");
        String input = scanner.nextLine();
        int counter = 0;
        List<Contact> listToPrint = new LinkedList<>();
        for (Contact contact : list) {
            //Searching in person fields
            if (contact.type().equalsIgnoreCase("person")) {
                Person person = (Person) contact;
                if (person.getName().toLowerCase().contains(input.toLowerCase())
                        || person.getSurname().toLowerCase().contains(input.toLowerCase())
                        || person.getGender().toLowerCase().contains(input.toLowerCase())
                        || person.getPhoneNumber().toLowerCase().contains(input.toLowerCase())) {
                    counter++;
                    listToPrint.add(person);
                }
                //Searching in organization fields
            } else if (contact.type().equalsIgnoreCase("organization")) {
                Organization organization = (Organization) contact;
                if (organization.getName().toLowerCase().contains(input.toLowerCase())
                        || organization.getAddress().toLowerCase().contains(input.toLowerCase())
                        || organization.getPhoneNumber().toLowerCase().contains(input.toLowerCase())) {
                    counter++;
                    listToPrint.add(contact);
                }
            }
        }
        //Printing result
        if (counter == 0) {
            System.out.println("No matches found");
            System.out.println();
            return false;
        } else if (counter == 1) {
            System.out.println("Found 1 result:");
            printContacts(listToPrint);
        } else {
            System.out.println("Found " + counter + " results:");
            printContacts(listToPrint);
        }
        searchMenu(listToPrint, scanner);
        return true;
    }

    private static void recordMenu(Contact contact, List<Contact> list, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.print("[record] Enter action (edit, delete, menu): ");
            switch (scanner.nextLine()) {
                case "edit":
                    editContact(contact, scanner);
                    serialize(list, "phoneBook.bin");
                    exit = true;
                    break;
                case "delete":
                    removeContact(list, contact);
                    serialize(list, "phoneBook.bin");
                    exit = true;
                    break;
                case "menu":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }

    private static void printContacts(List<Contact> list) {
        if (list.isEmpty()) {
            System.out.println("The contact list is empty!");
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).type().equalsIgnoreCase("person")) {
                    System.out.println(i + 1 + ". " + list.get(i).getName() + " " + ((Person) list.get(i)).getSurname());
                } else {
                    System.out.println(i + 1 + ". " + list.get(i).getName());
                }
            }
        }
        System.out.println();
    }

    private static void info(List<Contact> list, int index) {
        index -= 1;
        if (index > list.size()) {
            System.out.println("No such record");
        } else {
            Contact contact = list.get(index);
            contactInfo(contact);
        }
    }

    private static void contactInfo(Contact contact) {
        if (contact.type().equalsIgnoreCase("person")) {
            System.out.println("Name: " + contact.getName());
            System.out.println("Surname: " + ((Person) contact).getSurname());
            if ((((Person) contact).getBirthDate()) == null) {
                System.out.println("Birth date: [no data]");
            } else {
                System.out.println("Birth date: " + ((Person) contact).getBirthDate());
            }
            System.out.println("Gender: " + ((Person) contact).getGender());
            System.out.println("Number: " + contact.getPhoneNumber());
        } else if (contact.type().equalsIgnoreCase("organization")) {
            System.out.println("Organization name: " + contact.getName());
            System.out.println("Address: " + ((Organization) contact).getAddress());
            System.out.println("Number: " + contact.getPhoneNumber());
        }
        System.out.println("Time created: " + contact.getWhenCreated());
        System.out.println("Time last edit: " + contact.getWhenLastEdited());
        System.out.println();
    }

    //Contact builder
    private static void addContact(List<Contact> list, Scanner scanner) {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();
        if (type.equalsIgnoreCase("person")) {
            Contact contact = new Person.Builder()
                    .setName(scanner)
                    .setSurname(scanner)
                    .setBirthDate(scanner)
                    .setGender(scanner)
                    .setPhoneNumber(scanner)
                    .build();
            list.add(contact);
            System.out.println("The record added");
        } else if (type.equalsIgnoreCase("organization")) {
            Contact contact = new Organization.Builder()
                    .setName(scanner)
                    .setAddress(scanner)
                    .setPhoneNumber(scanner)
                    .build();
            list.add(contact);
            System.out.println("The record added");
        } else {
            System.out.println("Invalid type!");
        }
        System.out.println();
    }

    private static void removeContact(List<Contact> list, Contact contact) {
        if (list.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            list.remove(contact);
            System.out.println("The record removed!");
        }
        System.out.println();
    }

    private static void editContact(Contact contact, Scanner scanner) {
        if (contact.type().equalsIgnoreCase("person")) {
            Person person = (Person) contact;
            person.edit(person, scanner);
        } else if (contact.type().equalsIgnoreCase("organization")) {
            Organization organization = (Organization) contact;
            organization.edit(organization, scanner);
        }
        contactInfo(contact);
        System.out.println();
    }

    public static void serialize(Object obj, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            oos.writeObject(obj);
        }catch (IOException e) {
            e.getMessage();
        }
    }

    public static Object deserialize(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            Object obj = ois.readObject();
            return obj;
        }catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        }
        return null;
    }
}
