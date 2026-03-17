package org.example;

/**
 * Перерахування можливих матеріалів одягу.
 */
public enum Material {
    COTTON("Бавовна"),
    DENIM("Денім"),
    WOOL("Вовна"),
    POLYESTER("Поліестер");

    private final String title;

    Material(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
