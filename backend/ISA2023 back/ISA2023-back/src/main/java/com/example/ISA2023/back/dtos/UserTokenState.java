package com.example.ISA2023.back.dtos;

public class UserTokenState {
    private String username;
    private String accessToken;
    private Long expiresIn;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }
    public UserTokenState(String accessToken, long expiresIn, String username) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.username = username;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Long getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
