package com.revature.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "reimbursements")
@Component
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbid;

    private String description;

    private int amount;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    // Constructors
    public Reimbursement() {
    }

    public Reimbursement(int reimbid, String description, int amount, String status, User user) {
        this.reimbid = reimbid;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.user = user;
    }

    // Getters and Setters
    public int getReimbursementId() {
        return reimbid;
    }

    public void setReimbursementId(int reimbid) {
        this.reimbid = reimbid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Other
    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbid=" + reimbid +
                ", user=" + user +
                ", description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
