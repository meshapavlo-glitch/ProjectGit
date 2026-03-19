package org.example;

public class Shirt extends Clothes {
    private boolean hasButtons; // Чи є ґудзики

    public Shirt(String type, String size, double price, Material material, boolean hasButtons) {
        super(type, size, price, material);
        this.hasButtons = hasButtons;
    }

    @Override
    public String toString() {
        return super.toString() + (hasButtons ? ", з ґудзиками" : ", без ґудзиків") + " (Сорочка)";
    }
}
