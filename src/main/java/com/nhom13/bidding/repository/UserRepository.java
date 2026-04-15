package com.nhom13.bidding.repository;
import com.nhom13.bidding.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> mockDatabase = new ArrayList<>();
    private int autoIncremenId = 1;
    public boolean save(User user) {
        user.setId(autoIncremenId++);
        mockDatabase.add(user);
        return true;
    }
    public User findByUsername(String username){
        for (User user : mockDatabase){
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public boolean exitsByEmail(String email) {
        for (User user : mockDatabase){
            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
}