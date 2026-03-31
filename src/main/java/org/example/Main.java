package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "input.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Store store = new Store();

    public static void main(String[] args) {
        loadFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\n--- МЕНЮ МАГАЗИН  ---");
            System.out.println("1. Додати товар");
            System.out.println("2. Пошук");
            System.out.println("3. Весь список (як є)");
            System.out.println("4. Весь список (ВІДСОРТОВАНИЙ ЗА ЦІНОЮ)");
            System.out.println("5. Вихід");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showAddMenu();
                case "2" -> showSearchMenu();
                case "3" -> displayList(store.getItems());
                case "4" -> displayList(store.getSortedItems());
                case "5" -> {
                    saveToFile();
                    running = false;
                }
            }
        }
    }

    private static void showAddMenu() {
        System.out.println("\n--- ДОДАВАННЯ ТОВАРУ ---");
        System.out.println("1. Pants | 2. Shirt | 3. Socks | 4. Jacket");
        String type = scanner.nextLine();

        try {
            System.out.print("Розмір: ");
            String s = scanner.nextLine();

            System.out.print("Ціна: ");
            double p = Double.parseDouble(scanner.nextLine());

            System.out.print("Кількість: ");
            int q = Integer.parseInt(scanner.nextLine());
            if (q <= 0) throw new IllegalArgumentException("Кількість має бути > 0");

            Clothes obj = null;
            switch (type) {
                case "1" -> {
                    System.out.print("Довжина (см): ");
                    int len = Integer.parseInt(scanner.nextLine());
                    obj = new Pants("Штани", s, p, Material.DENIM, len);
                }
                case "2" -> {
                    System.out.print("Має ґудзики (true/false): ");
                    boolean btn = Boolean.parseBoolean(scanner.nextLine());
                    obj = new Shirt("Сорочка", s, p, Material.COTTON, btn);
                }
                // ... інші кейси аналогічно
            }

            if (obj != null) {
                store.addNewClothes(obj, q);
                System.out.println("Успішно додано!");
            }

        } catch (NumberFormatException e) {
            System.out.println("ПОМИЛКА: Введено не число!");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА ВАЛІДАЦІЇ: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Виникла непередбачена помилка.");
        }
    }

    private static void displayList(List<InventoryItem> list) {
        if (list.isEmpty()) System.out.println("Список порожній.");
        else list.forEach(System.out::println);
    }

    private static void showSearchMenu() {
        System.out.println("1. Матеріал | 2. Ціна | 3. Розмір");
        String c = scanner.nextLine();
        List<InventoryItem> res = new ArrayList<>();
        try {
            if (c.equals("1")) res = store.searchByMaterial(Material.valueOf(scanner.nextLine().toUpperCase()));
            else if (c.equals("2")) res = store.searchByMaxPrice(Double.parseDouble(scanner.nextLine()));
            else if (c.equals("3")) res = store.searchBySize(scanner.nextLine());
            displayList(res);
        } catch (Exception e) { System.out.println("Помилка пошуку."); }
    }

    private static void saveToFile() {
        try (PrintWriter w = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem i : store.getItems()) {
                Clothes c = i.getClothes();
                String base = c.getSize() + ";" + c.getPrice() + ";" + c.getMaterial();
                String line = "";
                if (c instanceof Pants p) line = "Pants;" + base + ";" + p.getLength();
                else if (c instanceof Shirt s) line = "Shirt;" + base + ";" + s.isHasButtons();
                else if (c instanceof Socks so) line = "Socks;" + base + ";" + so.isHigh();
                else if (c instanceof Jacket j) line = "Jacket;" + base + ";" + j.isHasHood();
                w.println(line + ";" + i.getQuantity());
            }
        } catch (IOException e) { System.out.println("Помилка запису."); }
    }

    private static void loadFromFile() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = r.readLine()) != null) {
                String[] p = l.split(";");
                try {
                    String type = p[0]; String size = p[1];
                    double price = Double.parseDouble(p[2]);
                    Material mat = Material.valueOf(p[3]);
                    int qty = Integer.parseInt(p[p.length - 1]);
                    Clothes obj = null;
                    switch (type) {
                        case "Pants" -> obj = new Pants("Штани", size, price, mat, Integer.parseInt(p[4]));
                        case "Shirt" -> obj = new Shirt("Сорочка", size, price, mat, Boolean.parseBoolean(p[4]));
                        case "Socks" -> obj = new Socks("Шкарпетки", size, price, mat, Boolean.parseBoolean(p[4]));
                        case "Jacket" -> obj = new Jacket("Куртка", size, price, mat, Boolean.parseBoolean(p[4]));
                    }
                    if (obj != null) store.addNewClothes(obj, qty);
                } catch (Exception ignored) {}
            }
        } catch (IOException ignored) {}
    }
}
