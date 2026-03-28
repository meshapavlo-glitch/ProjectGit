package org.example;

public class Jacket extends Clothes {
    private boolean hasHood;

    public Jacket(String type, String size, double price, Material material, boolean hasHood) {
        super(type, size, price, material);
        this.hasHood = hasHood;
    }

    @Override
    public String toString() {
        return super.toString() + (hasHood ? ", з капюшоном" : ", без капюшона") + " (Куртка)";
    }
}
