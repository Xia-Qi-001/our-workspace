package com.nhom13.bidding.service;
import com.nhom13.bidding.model.Role;
import com.nhom13.bidding.model.User;
import com.nhom13.bidding.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;//thư viện giúp mã hóa mật khẩu tránh bị hack

public class UserService {
    private UserRepository userRepository;
    public UserService() {
        this.userRepository = new UserRepository();
    }
    public boolean register(String username, String rawPassword,String email,Role role) throws Exception{
        if (username.length()<3){
            throw new Exception("Tên đăng nhập phải dài hơn 3 ký tự");
        }
        if(rawPassword.length()<6){
            throw new Exception("Mật khẩu quá ngắn!");
        }
        if(userRepository.findByUsername(username)!=null){
            throw new Exception("Tên đăng nhập đã tồn tại!");
        }
        if(userRepository.exitsByEmail(email)){
            throw new Exception("Email đã tồn tại!");
        }
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
        User newUser = new User(0, username, hashedPassword, email, role, 0.0, true);
        return userRepository.save(newUser);
    }
    public User login(String username, String rawPassword) throws Exception{
        User user =userRepository.findByUsername(username);
        if(user ==null) {
            throw new Exception("Sai tên đăng nhập hoặc mật khẩu");
        }
        if(!user.isStatus()){
            throw new Exception("Tài khoản này đã bị khóa");
        }
        boolean isMatch = BCrypt.checkpw(rawPassword, user.getPasswordHash());
        if(!isMatch){
            throw new Exception("sai tên đăng nhập hoặc mật khẩu!");
        }
        return user;
    }
}
