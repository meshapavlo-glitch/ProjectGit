package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Скільки елементів одягу ви хочете додати? ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера після числа

        // Створення масиву об'єктів (Варіант 1)
        Clothes[] wardrobe = new Clothes[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Введення даних для предмета #" + (i + 1));
            System.out.print("Тип (напр. Футболка): ");
            String type = scanner.nextLine();

            System.out.print("Розмір (напр. L): ");
            String size = scanner.nextLine();

            System.out.print("Ціна: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Очищення буфера

            wardrobe[i] = new Clothes(type, size, price);
        }

        System.out.println("\nВаш список одягу:");
        for (Clothes item : wardrobe) {
            System.out.println(item.toString());
        }
    }
}