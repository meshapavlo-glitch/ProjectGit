package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер програми з консольним меню та обробкою винятків.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Clothes> wardrobe = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Створити новий об’єкт");
            System.out.println("2. Вивести всі об’єкти");
            System.out.println("3. Завершити роботу");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> createClothes();
                case "2" -> printWardrobe();
                case "3" -> running = false;
                default -> System.out.println("Помилка: оберіть пункт 1-3.");
            }
        }
    }

    private static void createClothes() {
        try {
            System.out.print("Введіть тип: ");
            String type = scanner.nextLine();

            System.out.print("Введіть розмір: ");
            String size = scanner.nextLine();

            System.out.print("Введіть ціну: ");
            double price = Double.parseDouble(scanner.nextLine()); // Обробка нечислового введення

            System.out.print("Введіть матеріал: ");
            String material = scanner.nextLine();

            // Тепер передаємо всі 4 параметри!
            Clothes item = new Clothes(type, size, price, material);
            wardrobe.add(item);
            System.out.println("Об'єкт успішно додано!");

        } catch (NumberFormatException e) {
            System.out.println("Помилка: ціна повинна бути числом!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        }
    }

    private static void printWardrobe() {
        if (wardrobe.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            wardrobe.forEach(System.out::println);
        }
    }
}
