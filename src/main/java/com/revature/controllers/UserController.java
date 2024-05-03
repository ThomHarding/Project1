package com.revature.controllers;

import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.IncomingUserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    private final UserDAO userDAO;
    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public UserController(UserDAO userDAO, ReimbursementDAO reimbursementDAO) {
        this.userDAO = userDAO;
        this.reimbursementDAO = reimbursementDAO;
    }

    //see all users (manager)
    @GetMapping
    ResponseEntity<List<User>> getAllUsers() {
        System.out.println(userDAO.findAll());
        return ResponseEntity.ok().body(userDAO.findAll());
    }

    @GetMapping("/{userId}")
    ResponseEntity<Object> getUser(@PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        return ResponseEntity.ok().body(u.get());
    }

    @PostMapping("/login")
    ResponseEntity<Object> loginUser(@RequestBody IncomingUserDTO incomingUserDTO) {
        Optional<User> u = userDAO.findByUsernameAndPassword(incomingUserDTO.getUsername(), incomingUserDTO.getPassword());
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        return ResponseEntity.ok().body(u.get());
    }

    //create an account (user)
    @PostMapping
    ResponseEntity<User> insertUser(@RequestBody User user) {
        System.out.println("cmon man");
        System.out.println(user);
        User u = userDAO.save(user);
        return ResponseEntity.status(201).body(u);
    }

    @PutMapping("/{userId}")
    ResponseEntity<Object> updateUser(@PathVariable int userId, @RequestBody User user) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        u.get().setFirstName(user.getFirstName());
        u.get().setLastName(user.getLastName());
        userDAO.save(u.get());
        return ResponseEntity.ok().body(u.get());
    }

    //OPTIONAL: Update an employee's role to manager (manager)
    @PatchMapping("/{userId}/promote")
    ResponseEntity<Object> updateUserName(@PathVariable int userId, @RequestBody User user) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        if (user.getFirstName() != null) {
            u.get().setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            u.get().setLastName(user.getLastName());
        }
        u.get().setRole("Manager");

        userDAO.save(u.get());
        return ResponseEntity.ok().body(u.get());
    }

    //delete a user and cascade (manager)
    @DeleteMapping("/{userId}")
    ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        userDAO.deleteById(userId);
        return ResponseEntity.ok().body("Deleted user " +
                u.get().getFirstName() + " " + u.get().getLastName() + " and all associated reimbursements.");
    }

    /*
    ** Methods on user's reimbursement collection
    */

    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<Object> getReimbursementsByUser(@PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        List<Reimbursement> reimbursementList = reimbursementDAO.findByUserUserId(userId);
        return ResponseEntity.ok().body(reimbursementList);
    }

    @PostMapping("/{userId}/reimbursements")
    public ResponseEntity<Object> insertReimbursement(@RequestBody Reimbursement reimbursement, @PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist.");
        }
        reimbursement.setUser(u.get());
        Reimbursement b = reimbursementDAO.save(reimbursement);
        return ResponseEntity.status(201).body(b);
    }
}
