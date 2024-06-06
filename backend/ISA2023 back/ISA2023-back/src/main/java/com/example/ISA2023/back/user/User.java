package com.example.ISA2023.back.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String state;
    private String city;
    private String tel_number;
    private String occupation;
    private String company_info;
    private UserRole role;
    private boolean is_verified;

    private int penaltyPoints;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isVerified() {
        return is_verified;
    }

    public void setVerified(boolean verified) {
        is_verified = verified;
    }

    private boolean isFirstLogin;


    public User() {
    }

    public User(String username, String email, String password, String first_name, String last_name,
                String state, String city, String tel_number, String occupation, String company_info, UserRole role, boolean isVerified, boolean isFirstLogin, int penaltyPoints) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.state = state;
        this.city = city;
        this.tel_number = tel_number;
        this.occupation = occupation;
        this.company_info = company_info;
        this.role = role;
        this.is_verified = isVerified;
        this.isFirstLogin = isFirstLogin;
        this.penaltyPoints = penaltyPoints;
    }

    public User(Long id, String username, String email, String password, String first_name, String last_name, String state,
                String city, String tel_number, String occupation, String company_info, UserRole role, boolean isVerified, boolean isFirstLogin, int penaltyPoints) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.state = state;
        this.city = city;
        this.tel_number = tel_number;
        this.occupation = occupation;
        this.company_info = company_info;
        this.role = role;
        this.is_verified = isVerified;
        this.isFirstLogin = isFirstLogin;
        this.penaltyPoints = penaltyPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTel_number() {
        return tel_number;
    }

    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompany_info() {
        return company_info;
    }

    public void setCompany_info(String company_info) {
        this.company_info = company_info;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", tel_number='" + tel_number + '\'' +
                ", occupation='" + occupation + '\'' +
                ", company_info='" + company_info + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return  true;
    }
}
