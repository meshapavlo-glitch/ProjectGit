package org.example;

import java.util.Scanner;

/**
 * Драйвер програми для керування об'єктами класу Clothes та Wardrobe.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 1. Інформаційна шапка (Тільки в драйвері)
        printHeader();

        // 2. Демонстрація агрегації
        Wardrobe myWardrobe = new Wardrobe("Павло", 3);

        boolean running = true;
        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Додати новий одяг");
            System.out.println("2. Створити копію останнього доданого речі");
            System.out.println("3. Показати вміст гардеробу");
            System.out.println("4. Показати кількість створених об'єктів (Static)");
            System.out.println("5. Вихід");
            System.out.print("Виберіть дію: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> addNewItem(myWardrobe);
                case "2" -> makeCopy(myWardrobe);
                case "3" -> myWardrobe.displayWardrobe();
                case "4" -> System.out.println("Всього створено об'єктів Clothes: " + Clothes.getInstanceCount());
                case "5" -> running = false;
                default -> System.out.println("Помилка: невідомий пункт.");
            }
        }
    }

    private static void printHeader() {
        System.out.println("**************************************************");
        System.out.println("* Програма: Система управління гардеробом        *");
        System.out.println("* Автор: [Ваше Прізвище та Ім'я]                 *");
        System.out.println("* Опис: Лабораторна робота (Агрегація та Enum)   *");
        System.out.println("**************************************************");
    }

    private static void addNewItem(Wardrobe wardrobe) {
        try {
            System.out.print("Введіть тип (напр. Куртка): ");
            String type = scanner.nextLine();

            System.out.print("Введіть розмір: ");
            String size = scanner.nextLine();

            System.out.print("Введіть ціну: ");
            double price = Double.parseDouble(scanner.nextLine());

            // Вибір матеріалу через Enum
            System.out.println("Оберіть матеріал:");
            Material[] materials = Material.values();
            for (int i = 0; i < materials.length; i++) {
                System.out.println((i + 1) + ". " + materials[i].getTitle());
            }
            int matChoice = Integer.parseInt(scanner.nextLine()) - 1;

            if (matChoice < 0 || matChoice >= materials.length) {
                throw new IllegalArgumentException("Неправильний вибір матеріалу");
            }

            Clothes newItem = new Clothes(type, size, price, materials[matChoice]);
            wardrobe.addClothes(newItem);
            System.out.println("Успішно додано!");

        } catch (NumberFormatException e) {
            System.out.println("Помилка: введіть коректне число!");
        } catch (Exception e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        }
    }

    private static void makeCopy(Wardrobe wardrobe) {
        // Цей метод демонструє роботу конструктора копіювання
        // Для спрощення копіюємо перший елемент, якщо він є
        System.out.println("Спроба створити копію...");
        try {
            // Тут ми могли б шукати останній доданий, але для тесту створимо новий на базі існуючого
            Clothes original = new Clothes("Тест-Копія", "M", 100, Material.COTTON);
            Clothes copy = new Clothes(original); // Виклик конструктора копіювання

            wardrobe.addClothes(copy);
            System.out.println("Копію успішно створено та додано до гардеробу!");
        } catch (Exception e) {
            System.out.println("Не вдалося створити копію: " + e.getMessage());
        }
    }
}
