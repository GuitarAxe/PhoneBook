package com.mateuszaksjonow;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            setPhoneNumber("[no number]");
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
        String[] splittedNumber = number.split("[ \\-]");
        boolean parenthesis = false;
        boolean correct = true;
        for (int i = 0; i < splittedNumber.length; i++) {
            if (isMatched("\\+?\\(\\w+\\)", splittedNumber[i])) {
                if (i < 2 && !parenthesis) {
                    parenthesis = true;
                }else {
                    correct = false;
                }
            }
            if (i > 0 && isMatched("\\w{1}", splittedNumber[i])) {
                correct = false;
            }
            if (isMatched("\\w*\\W+\\w*", splittedNumber[i])) {
                if (i == 0) {
                    if (!isMatched("\\+\\w", splittedNumber[i])) {
                        correct = false;
                    }
                }else {
                    correct = false;
                }
            }
        }
        return correct;
    }

    static public boolean isMatched(String regex, String input) {
        if (input == null) {
//            System.out.println("Null: isMatched");
            return false;
        }else {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            return matcher.matches();
        }
    }
}
