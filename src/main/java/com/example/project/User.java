package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User extends Tema1 {
    String username, password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /** introduce datele utilizatorului in fisier */
    public static void insertUserInFile(User u, String fileName) {
        try {
            File users = new File(fileName);
            Scanner myReader = new Scanner(users);
            String username;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                username = getSubstring(line, "'", 0);
                if (u.username.compareTo(username) == 0) {
                    User.printUserExists();
                    return;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        String userDetails = u.username + "'" + u.password + "'";
        insertLineInFile(userDetails, "users.txt");
        User.printUserOK();

    }

    /** returneaza 1 daca userul se afla in sistem */
    public static int searchUser(User u, String fileName) {
        String username, password;
        try {
            File users = new File(fileName);
            Scanner myReader = new Scanner(users);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                username = getSubstring(line, "'", 0);
                password = getSubstring(line, "'", 1);
                if (u.username.compareTo(username) == 0 && u.password.compareTo(password) == 0) {
                    return 1;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return -1;
    }

    static void printUserExists() {
        System.out.println("{'status':'error','message':'User already exists'}");
    }

    static void printPasswordError() {
        System.out.println("{'status':'error','message':'Please provide password'}");
    }


    static void printUsernameError() {
        System.out.println("{'status':'error','message':'Please provide username'}");
    }

    static void printUserOK() {
        System.out.println("{'status':'ok','message':'User created successfully'}");
    }

}
