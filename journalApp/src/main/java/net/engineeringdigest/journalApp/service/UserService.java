package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public static PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveUser(Users user){
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);

    }
    public void saveAdmin(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
    public void saveUserEntry(Users users){
        userRepository.save(users);
    }
    public List<Users>getAll(){
        return userRepository.findAll();
    }
    public Optional<Users> getById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public Users findByUserName(String name){
        return userRepository.findByUserName(name);
    }
    
}
