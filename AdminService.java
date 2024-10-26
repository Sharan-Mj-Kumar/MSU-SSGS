package com.example.SSGS.Service;

import com.example.SSGS.Repository.UserRepository;
import com.example.SSGS.Tables.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;


    public Users authenticateUser(String email, String password){
        Optional<Users> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            if(user.get().getPassword().equals(password)){
                return user.get();
            }
        }
        return null;
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users getUserById(Integer staff_id){
        Optional<Users> user = userRepository.findById(staff_id);
        return user.orElse(null);
    }

    public void updateUser(Users user){
        userRepository.save(user);
    }

    public void deleteUserById(Integer staff_id) {
        userRepository.deleteById(staff_id);
    }
}
