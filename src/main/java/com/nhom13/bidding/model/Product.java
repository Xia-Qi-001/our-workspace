package com.nhom13.bidding.model;

public class Product {
    private int id;
    private String name;
    private double currentPrice;
    private double stepPrice;
    private int currentWinnerId;

    private String description;
    private String imagePath;

    public Product(int id, String name, double startingPrice, double stepPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = startingPrice;
        this.stepPrice = stepPrice;
        this.currentWinnerId = -1; // -1 nghĩa là chưa có ai đặt giá
    }

    public boolean isValidBid(double newBid) {
        return newBid >= (this.currentPrice + this.stepPrice);
    }

    public void updateBid(double newBid, int userId) {
        this.currentPrice = newBid;
        this.currentWinnerId = userId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getCurrentPrice() { return currentPrice; }
    public double getStepPrice() { return stepPrice; }
    public int getCurrentWinnerId() { return currentWinnerId; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }

    public void setDescription(String description) { this.description = description; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}