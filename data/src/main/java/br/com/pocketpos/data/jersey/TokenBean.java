package br.com.pocketpos.data.jersey;

import java.io.Serializable;


public class TokenBean implements Serializable{

    private String token;

    public String getToken() {

        return token;

    }

    public void setToken(String token) {

        this.token = token;

    }

}