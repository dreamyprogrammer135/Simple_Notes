package com.dreamyprogrammer.simplenotes;

/**
 * homework com.example.lesson10
 *
 * @author Amina
 * 24.06.2021
 */
public class UserAuth {

    public static  String nameUser;
    private static UserAuth userData = new UserAuth();
    public UserAuth() {

    }
      public static UserAuth getUserData(String name) {
        nameUser = name;
        return userData;
    }

    public static UserAuth getUserData() {
        return userData;
    }
}
