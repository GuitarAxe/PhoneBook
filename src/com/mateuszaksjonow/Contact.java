package com.mateuszaksjonow;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

public abstract class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private final LocalDateTime whenCreated = LocalDateTime.now();
    private  LocalDateTime whenLastEdited;
    private static final long serialVersionUID = 7L;

    public abstract String type();
    public abstract Contact edit(Contact contact, Scanner scanner);

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        whenLastEdited = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (checkNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }else {
            System.out.println("Wrong number format!");
            this.phoneNumber = ("[no number]");
        }
    }

    public LocalDateTime getWhenCreated() {
        return whenCreated;
    }

    public LocalDateTime getWhenLastEdited() {
        return whenLastEdited;
    }

    public void setWhenLastEdited() {
        this.whenLastEdited = LocalDateTime.now();
    }

    static public boolean checkNumber(String number) {
        String str = "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?";
        return number.matches(str);
    }
}
