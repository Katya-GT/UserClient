package com.example;

import com.example.client.UserClient;
import com.example.model.User;

public class Main {
    public static void main(String[] args) {
        UserClient client = new UserClient();
        System.out.println("Получение всех пользователей:");
        client.getAllUsers();

        User newUser = new User();
        newUser.setId(3L);
        newUser.setName("James");
        newUser.setLastName("Brown");
        newUser.setAge((byte) 35);

        System.out.println("Добавление пользователя");

        String part1 = client.createUser(newUser);
        System.out.println("Код часть 1: " + part1);

        newUser.setName("Thomas");
        newUser.setLastName("Shelby");

        System.out.println("Изменения пользователя:");
        String part2 = client.updateUser(newUser);
        System.out.println("Код часть 2: " + part2);

        System.out.println("Удаление пользователя:");
        String part3 = client.deleteUser(3L);
        System.out.println("Код часть 3: " + part3);

        String finalCode = part1 + part2 + part3;
        System.out.println("Итоговый код: " + finalCode);

    }
}
