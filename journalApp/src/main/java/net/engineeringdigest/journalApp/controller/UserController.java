package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("getById/{id}")
    public ResponseEntity<Users>getById(@PathVariable ObjectId id){
        Optional<Users>users=userService.getById(id);
        return users.map(value -> new ResponseEntity<>(value, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Users>create(@RequestBody Users users){
        userService.saveUser(users);
        return new ResponseEntity<>(users,HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<?>update(@RequestBody Users users){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
     Users userIndb=userService.findByUserName(userName);
     if(userIndb!=null){
         userIndb.setUserName(users.getUserName());
         userIndb.setPassword(users.getPassword());
         userService.saveUser(userIndb);
     }
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        userRepository.DeleteByName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
