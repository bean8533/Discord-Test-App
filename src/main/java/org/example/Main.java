package org.example;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) {
        try {
            AppManager app = new AppManager();
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }
    }
}