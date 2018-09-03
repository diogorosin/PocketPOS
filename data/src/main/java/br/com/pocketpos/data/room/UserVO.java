package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "User")
public class UserVO {

    @PrimaryKey
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @ColumnInfo(name="active")
    private Boolean active;

    @ColumnInfo(name="level")
    private Integer level;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="login")
    private String login;

    @ColumnInfo(name="password")
    private String password;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public Boolean getActive() {

        return active;

    }

    public void setActive(Boolean active) {

        this.active = active;

    }

    public Integer getLevel() {

        return level;

    }

    public void setLevel(Integer level) {

        this.level = level;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

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

}