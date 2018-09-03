package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.pocketpos.data.room.UserVO;

@Dao
public interface UserDAO {

    @Insert
    void create(UserVO userVO);

    @Query("SELECT * FROM User WHERE identifier = :identifier")
    UserVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM User WHERE identifier = :identifier")
    Boolean exists(int identifier);

    @Query("SELECT * FROM User WHERE login = :login")
    UserVO retrieveByLogin(String login);

    @Update
    void update(UserVO userVO);

    @Delete
    void delete(UserVO userVO);

    @Query("SELECT * FROM User")
    List<UserVO> list();

}