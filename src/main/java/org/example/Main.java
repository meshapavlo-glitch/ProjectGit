package org.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.NumberFormatException;

/**
 * Головний клас програми для керування складом одягу.
 * Демонструє роботу з ArrayList, наслідування та поліморфізм.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // Єдина колекція для зберігання об'єктів базового та похідних типів
    private static final ArrayList<Clothes> inventory = new ArrayList<>();

    public static void main(String[] args) {
        printHeader();
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addBaseClothes();
                case "2" -> addPants();
                case "3" -> addShirt();
                case "4" -> showInventory();
                case "5" -> {
                    System.out.println("Програма завершена.");
                    running = false;
                }
                default -> System.out.println("Помилка: невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- ГОЛОВНЕ МЕНЮ (Колекції та Поліморфізм) ---");
        System.out.println("1. Додати базовий одяг (Clothes)");
        System.out.println("2. Додати штани (Pants)");
        System.out.println("3. Додати сорочку (Shirt)");
        System.out.println("4. Показати весь інвентар (Поліморфізм)");
        System.out.println("5. Вихід");
        System.out.print("Виберіть дію: ");
    }

    private static void addBaseClothes() {
        try {
            System.out.println("\n--- Додавання базового одягу ---");
            System.out.print("Введіть назву: ");
            String type = scanner.nextLine();

            System.out.print("Введіть ціну: ");
            // Саме тут виникає NumberFormatException, якщо ввели "abc" замість "100"
            double price = Double.parseDouble(scanner.nextLine());

            inventory.add(new Clothes(type, "M", price, Material.COTTON));
            System.out.println("Успішно додано!");

        } catch (NumberFormatException e) {
            // Якщо Double.parseDouble не зміг перетворити текст у число
            System.out.println("Помилка: Ви ввели текст замість числа. Будь ласка, вкажіть ціну цифрами (напр. 100.50)");

        } catch (IllegalArgumentException e) {
            // Якщо ціна менша за нуль (ваша логіка з Clothes.java)
            System.out.println("Помилка валідації: " + e.getMessage());

        } catch (Exception e) {
            // "Запасний" варіант для будь-яких інших помилок
            System.out.println("Сталася непередбачувана помилка: " + e.getMessage());
        }
    }

    private static void addPants() {
        try {
            System.out.println("\n--- Додавання штанів (Pants) ---");
            System.out.print("Введіть розмір: ");
            String size = scanner.nextLine();

            System.out.print("Введіть ціну: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Введіть довжину штанин (см): ");
            int length = Integer.parseInt(scanner.nextLine());

            // Додаємо в колекцію. Якщо ціна < 0, Clothes викине IllegalArgumentException
            inventory.add(new Pants("Штани", size, price, Material.DENIM, length));
            System.out.println("Штани успішно додано до інвентарю!");

        } catch (NumberFormatException e) {
            System.out.println("ПОМИЛКА: Ціна та довжина мають бути числами! (напр. 850.5 та 105)");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА ВАЛІДАЦІЇ: " + e.getMessage());
        }
    }

    private static void addShirt() {
        try {
            System.out.println("\n--- Додавання сорочки (Shirt) ---");
            System.out.print("Введіть розмір: ");
            String size = scanner.nextLine();

            System.out.print("Введіть ціну: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Чи має ґудзики? (true/false): ");
            boolean hasButtons = Boolean.parseBoolean(scanner.nextLine());

            inventory.add(new Shirt("Сорочка", size, price, Material.COTTON, hasButtons));
            System.out.println("Сорочку успішно додано!");

        } catch (NumberFormatException e) {
            System.out.println("ПОМИЛКА: Некоректний формат ціни.");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА ВАЛІДАЦІЇ: " + e.getMessage());
        }
    }

    /**
     * Демонстрація поліморфізму: проходимо по списку Clothes,
     * але для кожного об'єкта викликається його власна реалізація toString().
     */
    private static void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("\nСклад порожній.");
            return;
        }

        System.out.println("\n ВМІСТ СКЛАДУ (Поліморфне виведення) ");
        for (Clothes item : inventory) {
            // Тут працює поліморфізм: item може бути Clothes, Pants або Shirt
            System.out.println(item.toString());
        }
        System.out.println("===========================================");
    }

    private static void printHeader() {
        System.out.println("*******************************************");
        System.out.println("* Лабораторна робота: Колекції та Наслідування *");
        System.out.println("* Виконав: Павло Меша                     *");
        System.out.println("*******************************************");
    }
}
