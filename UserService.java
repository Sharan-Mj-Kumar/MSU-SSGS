package com.example.SSGS.Service;

import com.example.SSGS.Repository.UserRepository;
import com.example.SSGS.Tables.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users getUserById(Integer staff_id){
        if(staff_id == null){
            throw new IllegalArgumentException("User ID must not be null");
        }
        return userRepository.findById(staff_id).orElse(null);
    }

    public void updateUserProfile(Users user){
        if (user.getStaff_id() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        Users existingUser = userRepository.findById(user.getStaff_id()).orElse(null);
        if(existingUser != null){
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            if(user.getPassword() != null && !user.getPassword().isEmpty()){
                existingUser.setPassword(user.getPassword());
            }
            userRepository.save(existingUser);
        }else{
            throw new RuntimeException("User not found");
        }
    }
}
