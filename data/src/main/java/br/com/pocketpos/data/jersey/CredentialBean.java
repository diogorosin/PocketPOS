package br.com.pocketpos.data.jersey;

import java.io.Serializable;

public class CredentialBean implements Serializable{

    private String login;

    private String password;

    private Integer company;

    public String getLogin() {

        return login;

    }

    public void setLogin(String login) {

        this.login = login;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

    public Integer getCompany() {

        return company;

    }

    public void setCompany(Integer company) {

        this.company = company;

    }

}