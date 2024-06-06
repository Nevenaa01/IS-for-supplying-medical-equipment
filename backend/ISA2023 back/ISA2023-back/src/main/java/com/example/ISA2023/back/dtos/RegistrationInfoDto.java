package com.example.ISA2023.back.dtos;

public class RegistrationInfoDto {
    public String email;
    public String username;
    public String password;
    public String confirmPassword;
    public String name;
    public String surname;
    public String city;
    public String country;
    public String phone;
    public String profession;

    public String companyName;
    public String address;

    public RegistrationInfoDto()
    {

    }
    public RegistrationInfoDto(String email, String username, String password, String confirmPassword, String name, String surname, String city, String country, String phone, String profession, String companyName, String address) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.profession = profession;
        this.companyName = companyName;
        this.address = address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
