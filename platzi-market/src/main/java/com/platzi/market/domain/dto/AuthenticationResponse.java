package com.platzi.market.domain.dto;

public class AuthenticationResponse {
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    private String jwt;
}
