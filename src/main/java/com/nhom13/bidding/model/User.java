package com.nhom13.bidding.model;

public class User {
    private int id;
    private String username;
    private double balance;

    public User(int id, String username, double balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public boolean canAfford(double amount){
        return this.balance >= amount;
    }

    public void deductMoney(double amount){
        this.balance -= amount;
    }

    public void refundMoney(double amount){
        this.balance += amount;
    }
}