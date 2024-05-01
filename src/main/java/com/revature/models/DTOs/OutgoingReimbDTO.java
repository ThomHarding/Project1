package com.revature.models.DTOs;

public class OutgoingReimbDTO {

    private int reimbId;
    private String name;
    private String image;
    private int userId;

    public OutgoingReimbDTO() {
    }

    public OutgoingReimbDTO(int reimbId, String name, String image, int userId) {
        this.reimbId = reimbId;
        this.name = name;
        this.image = image;
        this.userId = userId;
    }

    public int getReimbId() {
        return reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OutgoingReimbDTO{" +
                "reimbId=" + reimbId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", userId=" + userId +
                '}';
    }
}
