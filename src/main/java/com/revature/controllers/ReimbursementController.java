package com.revature.controllers;

import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.Reimbursement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/reimbursements")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
    ResponseEntity<Reimbursement> insertReimbursement(@RequestBody Reimbursement reimb) {
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
        System.out.println("getting all pending, userid doesn't exist");
        List<Reimbursement> b = reimbursementDAO.findByStatusLike("Pending");
        System.out.println(b);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("Reimbursement does not exist.");
        }
        return ResponseEntity.ok().body(b);
    }

    //resolve a reimbursement (manager) / change description of a reimbursement (manager)
    @PatchMapping("/{reimbId}/approve")
    public ResponseEntity<Object> completeReimbursement(@RequestBody Reimbursement reimbursement, @PathVariable int reimbId) {
        Optional<Reimbursement> b = reimbursementDAO.findById(reimbId);
        if (b.isEmpty()) {
            return ResponseEntity.badRequest().body("Reimbursement does not exist.");
        }
        Reimbursement r = b.get();
        r.setDescription(reimbursement.getDescription());
        r.setAmount(reimbursement.getAmount());
        r.setStatus("Approved");
        r.setUser(reimbursement.getUser());
        reimbursementDAO.save(r);
        return ResponseEntity.ok().body(r);
    }

    @PatchMapping("/{reimbId}/deny")
    public ResponseEntity<Object> denyReimbursement(@RequestBody Reimbursement reimbursement, @PathVariable int reimbId) {
        Optional<Reimbursement> b = reimbursementDAO.findById(reimbId);
        if (b.isEmpty()) {
            return ResponseEntity.badRequest().body("Reimbursement does not exist.");
        }
        Reimbursement r = b.get();
        r.setDescription(reimbursement.getDescription());
        r.setAmount(reimbursement.getAmount());
        r.setStatus("Denied");
        r.setUser(reimbursement.getUser());
        reimbursementDAO.save(r);
        return ResponseEntity.ok().body(r);
    }

    @PatchMapping("/{reimbId}/update")
    public ResponseEntity<Object> updateReimbursementDescription(@RequestBody Reimbursement reimbursement, @PathVariable int reimbId) {
        Optional<Reimbursement> b = reimbursementDAO.findById(reimbId);
        if (b.isEmpty()) {
            return ResponseEntity.badRequest().body("Reimbursement does not exist.");
        }
        Reimbursement r = b.get();
        r.setDescription(reimbursement.getDescription());
        r.setAmount(reimbursement.getAmount());
        r.setStatus(reimbursement.getStatus());
        r.setUser(reimbursement.getUser());
        reimbursementDAO.save(r);
        return ResponseEntity.ok().body(r);
    }

    @DeleteMapping("/{reimbId}")
    public ResponseEntity<Object> deleteReimbursement (@PathVariable int reimbId) {
        Optional<Reimbursement> b = reimbursementDAO.findById(reimbId);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("No reimbursement at ID " + reimbId + "found");
        }

        Reimbursement reimbursement = b.get();

        reimbursementDAO.deleteById(reimbId);
        return ResponseEntity.ok().body(reimbursement.getDescription() + " deleted from Reimbursements");
    }
}
