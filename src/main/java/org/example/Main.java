package org.example;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver-клас для керування ієрархією одягу.
 * Реалізує меню, роботу з ArrayList та демонстрацію поліморфізму.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // Внутрішня колекція, яка на початку роботи програми є порожньою
    private static final ArrayList<Clothes> inventory = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n========== ГОЛОВНЕ МЕНЮ ==========");
            System.out.println("1. Створити новий об’єкт");
            System.out.println("2. Вивести інформацію про всі об’єкти");
            System.out.println("3. Завершити роботу програми");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showCreationMenu();
                case "2" -> showInventory();
                case "3" -> {
                    System.out.println("Завершення роботи...");
                    running = false;
                }
                default -> System.out.println("Помилка: Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    /**
     * Опція 1: Меню створення об'єктів різних типів.
     */
    private static void showCreationMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП НОВОГО ОБ'ЄКТА ---");
        System.out.println("1. Базовий одяг (Clothes)");
        System.out.println("2. Штани (Pants)");
        System.out.println("3. Сорочка (Shirt)");
        System.out.println("4. Шкарпетки (Socks)");
        System.out.println("5. Куртка (Jacket)");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine();

        // Можливість повернення до головного меню без створення об'єкта
        if (typeChoice.equals("0")) return;

        try {
            // Загальні дані для всіх об'єктів
            System.out.print("Введіть розмір: ");
            String size = scanner.nextLine();
            System.out.print("Введіть ціну: ");
            double price = Double.parseDouble(scanner.nextLine());

            switch (typeChoice) {
                case "1" -> {
                    inventory.add(new Clothes("Одяг", size, price, Material.COTTON));
                    System.out.println("Об'єкт Clothes додано.");
                }
                case "2" -> {
                    System.out.print("Введіть довжину штанин (см): ");
                    int len = Integer.parseInt(scanner.nextLine());
                    inventory.add(new Pants("Штани", size, price, Material.DENIM, len));
                    System.out.println("Об'єкт Pants додано.");
                }
                case "3" -> {
                    System.out.print("Чи має ґудзики? (true/false): ");
                    boolean btn = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Shirt("Сорочка", size, price, Material.COTTON, btn));
                    System.out.println("Об'єкт Shirt додано.");
                }
                case "4" -> {
                    System.out.print("Це високі шкарпетки? (true/false): ");
                    boolean high = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Socks("Шкарпетки", size, price, Material.WOOL, high));
                    System.out.println("Об'єкт Socks додано.");
                }
                case "5" -> {
                    System.out.print("Чи є капюшон? (true/false): ");
                    boolean hood = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Jacket("Куртка", size, price, Material.LEATHER, hood));
                    System.out.println("Об'єкт Jacket додано.");
                }
                default -> System.out.println("Помилка: Невірний тип об'єкта.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ПОМИЛКА: Вводьте лише числа для ціни та числових параметрів!");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА ВАЛІДАЦІЇ: " + e.getMessage());
        }
    }

    /**
     * Опція 2: Виведення всієї колекції через поліморфний виклик toString().
     */
    private static void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("\nКолекція порожня. Додайте спочатку об'єкти.");
            return;
        }

        System.out.println("\n========== ВМІСТ КОЛЕКЦІЇ (Поліморфізм) ==========");
        for (Clothes item : inventory) {
            // Поліморфізм: викликається toString відповідного похідного класу
            System.out.println(item.toString());
        }
        System.out.println("==================================================");
    }
}
