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
    private int userId;

    // Constructors
    public Reimbursement() {
    }

    public Reimbursement(int reimbid, String description, int amount, String status, int userId) {
        this.reimbid = reimbid;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Other
    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbid=" + reimbid +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
