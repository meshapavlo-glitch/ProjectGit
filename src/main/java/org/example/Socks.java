package org.example;

public class Socks extends Clothes {
    private boolean isHigh;

    public Socks(String type, String size, double price, Material material, boolean isHigh) {
        super(type, size, price, material);
        this.isHigh = isHigh;
    }

    @Override
    public String toString() {
        return super.toString() + (isHigh ? ", високі" : ", короткі") + " (Шкарпетки)";
    }
}