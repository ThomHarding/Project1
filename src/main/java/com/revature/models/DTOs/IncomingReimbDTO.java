package com.revature.models.DTOs;


public class IncomingReimbDTO {

    private String name;
    private String image;
    private int userId;

    public IncomingReimbDTO() {
    }

    public IncomingReimbDTO(String name, String image, int userId) {
        this.name = name;
        this.image = image;
        this.userId = userId;
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
        return "IncomingReimbDTO{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", userId=" + userId +
                '}';
    }
}
