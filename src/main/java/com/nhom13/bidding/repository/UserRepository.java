package com.nhom13.bidding.repository;
import com.nhom13.bidding.model.User;
import java.util.ArrayList;
import java.util.List;
//tạo database ảo để lưu nguời dùng
public class UserRepository {
    private List<User> mockDatabase = new ArrayList<>();
    private int autoIncremenId = 1;
    //lưu người dùng mới
    public boolean save(User user) {
        user.setId(autoIncremenId++);//tự động tăng id
        mockDatabase.add(user);
        return true;
    }
    //tìm người dùng theo tên
    public User findByUsername(String username){
        for (User user : mockDatabase){
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    //kiểm tra email đã tồn tại chưa
    public boolean exitsByEmail(String email) {
        for (User user : mockDatabase){
            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
    //lấy toàn bộ danh sánh cho admin
    public List<User> findAll() {
        return new ArrayList<>(mockDatabase);
    }
}

