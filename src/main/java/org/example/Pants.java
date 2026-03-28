package org.example;

public class Pants extends Clothes {
    private int length; // Довжина штанин

    public Pants(String type, String size, double price, Material material, int length) {
        super(type, size, price, material);
        this.length = length;
    }

      public int getLength() {
        return length;
    }


    @Override
    public String toString() {
        return super.toString() + ", Довжина: " + length + " см (Штани)";
    }
}
