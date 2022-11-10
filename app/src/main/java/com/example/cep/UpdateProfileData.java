package com.example.cep;

public class UpdateProfileData {

    String f_name, username, email, phone, password;

    public UpdateProfileData() {
    }

    public UpdateProfileData(String f_name, String username, String email, String phone, String password) {
        this.f_name = f_name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
