package com.mateuszaksjonow;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Person extends Contact implements Serializable {

    private String surname;
    private LocalDate birthDate;
    private String gender;
    private static final long serialVersionUID = 7L;

    public Person(String name, String phoneNumber, String surname, LocalDate birthDate, String gender) {
        super(name, phoneNumber);
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Scanner scanner) {
        try {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Bad birth date!");
            }else {
                int[] date = new int[3];
                String[] splittedInput = input.split(" ");
                for (int i = 0; i < splittedInput.length; i++) {
                    date[i] = Integer.parseInt(splittedInput[i]);
                }
                this.birthDate = LocalDate.of(date[0], date[1], date[2]);
            }
        }catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Bad birth date!");
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
            this.gender = gender;
        }else {
            System.out.println("Bad gender!");
            this.gender = "[no data]";
        }
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
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
        private String surname;
        private LocalDate birthDate;
        private String gender;
        private String phoneNumber;

        Person.Builder setName(Scanner scanner) {
            System.out.print("Enter the name: ");
            this.name = scanner.nextLine();
            return this;
        }

        Person.Builder setSurname(Scanner scanner) {
            System.out.print("Enter the surname: ");
            this.surname = scanner.nextLine();
            return this;
        }

        Person.Builder setBirthDate(Scanner scanner) {
            System.out.print("Enter the birth date: ");
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    System.out.println("Bad birth date!");
                }else {
                    int[] date = new int[3];
                    String[] splittedInput = input.split(" ");
                    for (int i = 0; i < splittedInput.length; i++) {
                        date[i] = Integer.parseInt(splittedInput[i]);
                    }
                }
            }catch (InputMismatchException | NumberFormatException | DateTimeException e) {
                System.out.println("Bad birth date!");
            }
            return this;
        }

        Person.Builder setGender(Scanner scanner) {
            System.out.print("Enter the gender (M, F): ");
            String string = scanner.nextLine();
            if (string.equalsIgnoreCase("M") || string.equalsIgnoreCase("F")) {
                this.gender = string;
            }else {
                System.out.println("Bad gender!");
                this.gender = "[no data]";
            }
            return this;
        }

        Person.Builder setPhoneNumber(Scanner scanner) {
            System.out.print("Enter the number ");
            String number = scanner.nextLine();
            if (checkNumber(number)) {
                this.phoneNumber = number;
            }else {
                System.out.println("Wrong number format! ");
                this.phoneNumber = "[no number]";
            }
            return this;
        }

        Contact build() {
            return new Person(name, phoneNumber, surname, birthDate, gender);
        }
    }

    @Override
    public String type() {
        return "person";
    }

    @Override
    public Contact edit(Contact contact, Scanner scanner) {
        Person contactToEdit = (Person) contact;
        contactToEdit.setWhenLastEdited();
        System.out.println("Select a field (name, surname, birth, gender, number):");
        switch (scanner.nextLine()) {
            case "name":
                System.out.println("Enter the name:");
                contactToEdit.setName(scanner.nextLine());
                System.out.println("The record updated!");
                break;
            case "surname":
                System.out.println("Enter the surname:");
                contactToEdit.setSurname(scanner.nextLine());
                System.out.println("The record updated!");
                break;
            case "birth":
                System.out.println("Enter the birth date:");
                contactToEdit.setBirthDate(scanner);
                System.out.println("The record updated!");
                break;
            case "gender":
                System.out.println("Enter the gender (M, F):");
                contactToEdit.setGender(scanner.nextLine());
                break;
            case "number":
                System.out.println("Enter the number:");
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
