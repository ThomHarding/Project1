package com.revature.controllers;

import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.Reimbursement;
import com.revature.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/reimbursements")
public class ReimbursementController {
    private final ReimbursementDAO reimbursementDAO;
    private final UserDAO userDAO;

    @Autowired
    public ReimbursementController(ReimbursementDAO reimbursementDAO, UserDAO userDAO) {
        this.reimbursementDAO = reimbursementDAO;
        this.userDAO = userDAO;
    }

    //see all reimbursements (manager)
    @GetMapping
    public ResponseEntity<List<Reimbursement>> getAllReimbursements() {
        return ResponseEntity.ok(reimbursementDAO.findAll());
    }

    //create a new reimbursement (user)
    @PostMapping
    ResponseEntity<Reimbursement> insertUser(@RequestBody Reimbursement reimb) {
        Reimbursement r = reimbursementDAO.save(reimb);
        return ResponseEntity.status(201).body(r);
    }

    //see their own reimbursement tickets (user)
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getReimbursementByUserId(@PathVariable int userId) {
        System.out.println("testing getting reimb by userid");
        List<Reimbursement> b = reimbursementDAO.findByUserUserId(userId);
        System.out.println(b);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("No reimbursements for that user.");
        }

        return ResponseEntity.ok().body(b);
    }

    //see their pending reimbursement tickets (user)
    @GetMapping("/{userId}/pending")
    public ResponseEntity<Object> getPendingReimbursements(@PathVariable int userId) {
        System.out.println("testing getting reimb by userid while pending");
        List<Reimbursement> b = reimbursementDAO.findByUserUserIdAndStatusLike(userId, "Pending");
        System.out.println(b);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("Reimbursement does not exist.");
        }
        return ResponseEntity.ok().body(b);
    }

    //see all pending reimbursements (manager)
    @GetMapping("/pending")
    public ResponseEntity<Object> getAllPendingReimbursements() {
        System.out.println("testing getting reimb by userid while pending");
        List<Reimbursement> b = reimbursementDAO.findByStatusLike("Pending");
        System.out.println(b);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("Reimbursement does not exist.");
        }
        return ResponseEntity.ok().body(b);
    }

    //"resolve a reimbursement" (manager)
    @PutMapping("/{reimbursementId}")
    public ResponseEntity<Object> updateReimbursement(@RequestBody Reimbursement reimbursement, @PathVariable int reimbursementId) {
        Optional<Reimbursement> b = reimbursementDAO.findById(reimbursementId);
        if (b.isEmpty()) {
            return ResponseEntity.badRequest().body("Reimbursement does not exist.");
        }
        b.get().setDescription(reimbursement.getDescription());
        b.get().setAmount(reimbursement.getAmount());
        b.get().setStatus(reimbursement.getStatus());
        b.get().setUser(reimbursement.getUser());
        reimbursementDAO.save(reimbursement);
        return ResponseEntity.ok().body(b.get());
    }

    // @PatchMapping("/{reimbursementId}") optional and make this work later
    // public ResponseEntity<Object> patchReimbursement(@RequestBody Reimbursement reimbursement, @PathVariable int reimbursementId) {
    //     Optional<Reimbursement> b = reimbursementDAO.findById(reimbursementId);
    //     if(b.isEmpty()) {
    //         return ResponseEntity.status(404).body("No reimbursement found with ID of: " + reimbursementId);
    //     }
    //     if (reimbursement.getDescription() != null) {
    //         b.get().setDescription(reimbursement.getDescription());
    //     }
    //     if (reimbursement.getAmount() != null) {
    //         b.get().setAmount(reimbursement.getAmount());
    //     }
    //     if (reimbursement.getStatus() != null) {
    //         b.get().setStatus(reimbursement.getStatus());
    //     }
    //     if (reimbursement.getUserId() != null) {
    //         b.get().setUserId(reimbursement.getUserId());
    //     }
    //     reimbursementDAO.save(reimbursement);
    //     return ResponseEntity.ok().body(reimbursement);
    // }

    @DeleteMapping("/{reimbursementId}")
    public ResponseEntity<Object> deleteReimbursement (@PathVariable int reimbursementId) {
        Optional<Reimbursement> b = reimbursementDAO.findById(reimbursementId);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("No reimbursement at ID " + reimbursementId + "found");
        }

        Reimbursement reimbursement = b.get();

        reimbursementDAO.deleteById(reimbursementId);
        return ResponseEntity.ok().body(reimbursement.getDescription() + " deleted from Reimbursements");
    }
}
