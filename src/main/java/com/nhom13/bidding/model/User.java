package com.nhom13.bidding.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;//dùng để xác định quyền trong enum
    private double balance;
    private boolean active;//dùng để xác định xem tài khoản có bị cấm do vi phạm không
    public User() {
    }
    public User(int id, String username, String password,String email, Role role, double balance, boolean status ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.balance = balance;
        this.active = status;
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
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
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
    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public boolean canAfford(double amount){
        return this.balance >= amount;//nếu lớn hơn amount thì trả về true
    }
    public void deductMoney(double amount){
        this.balance -= amount;
    }
    public void refundMoney(double amount){
        this.balance += amount;
    }
}