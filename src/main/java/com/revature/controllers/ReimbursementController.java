package com.revature.controllers;

import com.revature.daos.ReimbursementDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Reimbursement;
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

    @GetMapping
    public ResponseEntity<List<Reimbursement>> getAllReimbursements() {
        return ResponseEntity.ok(reimbursementDAO.findAll());
    }

    @GetMapping("/{reimbursementId}")
    public ResponseEntity<Object> getReimbursement(@PathVariable int reimbursementId) {
        Optional<Reimbursement> b = reimbursementDAO.findById(reimbursementId);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("Reimbursement does not exist.");
        }
        return ResponseEntity.ok().body(b.get());
    }

    @PutMapping("/{reimbursementId}")
    public ResponseEntity<Object> updateReimbursement(@RequestBody Reimbursement reimbursement, @PathVariable int reimbursementId) {
        Optional<Reimbursement> b = reimbursementDAO.findById(reimbursementId);
        if (b.isEmpty()) {
            return ResponseEntity.badRequest().body("Reimbursement does not exist.");
        }
        b.get().setDescription(reimbursement.getDescription());
        b.get().setAmount(reimbursement.getAmount());
        b.get().setStatus(reimbursement.getStatus());
        b.get().setUserId(reimbursement.getUserId());
        reimbursementDAO.save(reimbursement);
        return ResponseEntity.ok().body(b.get());
    }

    // @PatchMapping("/{reimbursementId}") optional and make his work later
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
