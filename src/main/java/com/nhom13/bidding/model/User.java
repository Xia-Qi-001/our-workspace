package com.nhom13.bidding.model;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String email;
    private Role role;
    private double balance;
    private boolean status;
    public User() {
    }
    public User(int id, String username, String passwordHash,String email, Role role, double balance, boolean status ) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
        this.balance = 0.0;
        this.status = true;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPasswordHash(){
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public Role getRole(){
        return role;
    }
    public void setRole(Role role){
        this.role = role;
    }
    public double getBalance(double balance){
        return balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public boolean isStatus(){
        return status;
    }
    public void setStatus(boolean status){
        this.status = status;
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



