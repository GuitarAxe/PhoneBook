package com.mateuszaksjonow;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Organization extends Contact {

    private String address;

    public Organization(String name, String phoneNumber, String address) {
        super(name, phoneNumber);
        this.address = address;
        isPerson = false;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public LocalDateTime getWhenCreated() {
        return super.getWhenCreated();
    }

    @Override
    public LocalDateTime getWhenLastEdited() {
        return super.getWhenLastEdited();
    }

    @Override
    public void setWhenLastEdited() {
        super.setWhenLastEdited();
    }

    static class Builder {

        private String name;
        private String phoneNumber;
        private String address;

        Organization.Builder setAddress(Scanner scanner) {
            System.out.print("Enter the address:");
            this.address = scanner.nextLine();
            return this;
        }

        Organization.Builder setName(Scanner scanner) {
            System.out.print("Enter the name:");
            this.name = scanner.nextLine();
            return this;
        }

        Organization.Builder setPhoneNumber(Scanner scanner) {
            System.out.print("Enter the number");
            String number = scanner.nextLine();
            if (checkNumber(number)) {
                this.phoneNumber = number;
            }else {
                System.out.println("Wrong number format!");
                this.phoneNumber = "[no number]";
            }
            return this;
        }

        Contact build() {
            return new Organization(name, phoneNumber, address);
        }
    }

    @Override
    public String type() {
        return "organization";
    }

    @Override
    public Contact edit(Contact contact, Scanner scanner) {
        Organization contactToEdit = (Organization) contact;
        contactToEdit.setWhenLastEdited();
        System.out.println("Select a field (name, address, number):");
        switch (scanner.nextLine()) {
            case "name":
                System.out.print("Enter the name:");
                contactToEdit.setName(scanner.nextLine());
                System.out.println("The record updated!");
                break;
            case "address":
                System.out.print("Enter the address");
                contactToEdit.setAddress(scanner.nextLine());
                System.out.println("The record updated!");
                break;
            case "number":
                System.out.print("Enter the number:");
                contactToEdit.setPhoneNumber(scanner.nextLine());
                System.out.println("The record updated!");
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
        return contactToEdit;
    }
}
