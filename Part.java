package com.example.carpartsstore;

public class Part {
    private String name;
    private String category; // "Performance" or "Visual"
    private int price;
    private boolean certified;

    public Part(String name, String category, int price, boolean certified) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.certified = certified;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public boolean isCertified() { return certified; }

    public void setCategory(String category) { this.category = category; }
    public void setCertified(boolean certified) { this.certified = certified; }
}
