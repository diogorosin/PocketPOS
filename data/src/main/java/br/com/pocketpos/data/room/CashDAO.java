package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CashDAO {

    @Insert
    Long create(CashVO cashVO);

    @Query("SELECT C.* FROM Cash C WHERE C.identifier = :identifier")
    CashVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM Cash C WHERE C.identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(CashVO cashVO);

    @Delete
    void delete(CashVO cashVO);

    @Query("SELECT (COUNT(*) > 0) AND (C.operation <> 'FEC') FROM Cash C ORDER BY C.dateTime DESC LIMIT 1")
    LiveData<Boolean> isOpen();

    @Query("SELECT IFNULL(" +
            "(SELECT IFNULL(SUM(E1.value), 0) " +
            "FROM Cash E1 " +
            "WHERE E1.type = 'E' AND E1.identifier >= (SELECT MAX(E2.identifier) FROM Cash E2 WHERE E2.operation == 'ABE')) " +
            "-" +
            "(SELECT IFNULL(SUM(S1.value), 0) " +
            "FROM Cash S1 " +
            "WHERE S1.type = 'S' AND S1.identifier >= (SELECT MAX(S2.identifier) FROM Cash S2 WHERE S2.operation == 'ABE')) " +
            ", 0)")
    LiveData<Double> value();

    @Query("SELECT C1.operation AS 'operation', C1.dateTime AS 'dateTime', C1.value AS 'value', C1.type AS 'type' " +
            "FROM Cash C1 " +
            "WHERE C1.identifier = (SELECT MAX(S1.identifier) FROM Cash S1 WHERE S1.operation = 'ABE') AND C1.operation = 'ABE' " +
            "UNION ALL " +
            "SELECT C2.operation AS 'operation', MAX(C2.dateTime) AS 'dateTime', SUM(C2.value) AS 'value', C2.type AS 'type' " +
            "FROM Cash C2 " +
            "WHERE C2.identifier > (SELECT MAX(S2.identifier) FROM Cash S2 WHERE S2.operation = 'ABE') " +
            "GROUP BY 1, 4 " +
            "ORDER BY 2")
    LiveData<List<CashModel>> cashSummary();

    @Query(" SELECT C1.operation AS 'operation', C1.dateTime AS 'dateTime', C1.value AS 'value', C1.type AS 'type', U1.name AS 'user_name' " +
            "FROM Cash C1 " +
            "INNER JOIN User U1 on U1.identifier = C1.user " +
            "WHERE C1.identifier = (SELECT MAX(S1.identifier) FROM Cash S1 WHERE S1.operation = 'ABE') AND C1.operation = 'ABE' " +
            "UNION ALL " +
            "SELECT C2.operation AS 'operation', MAX(C2.dateTime) AS 'dateTime', SUM(C2.value) AS 'value', C2.type AS 'type', U2.name AS 'user_name' " +
            "FROM Cash C2 " +
            "INNER JOIN User U2 on U2.identifier = C2.user " +
            "WHERE C2.identifier > (SELECT MAX(S2.identifier) FROM Cash S2 WHERE S2.operation = 'ABE') AND C2.operation = 'VEN' " +
            "GROUP BY 1, 4 " +
            "UNION ALL " +
            "SELECT C3.operation AS 'operation', C3.dateTime AS 'dateTime', C3.value AS 'value', C3.type AS 'type', U3.name AS 'user_name' " +
            "FROM Cash C3 " +
            "INNER JOIN User U3 on U3.identifier = C3.user " +
            "WHERE C3.identifier > (SELECT MAX(S3.identifier) FROM Cash S3 WHERE S3.operation = 'ABE') AND C3.operation = 'SAN' " +
            "UNION ALL " +
            "SELECT C4.operation AS 'operation', C4.dateTime AS 'dateTime', C4.value AS 'value', C4.type AS 'type', U4.name AS 'user_name' " +
            "FROM Cash C4 " +
            "INNER JOIN User U4 on U4.identifier = C4.user " +
            "WHERE C4.identifier > (SELECT MAX(S4.identifier) FROM Cash S4 WHERE S4.operation = 'ABE') AND C4.operation = 'COM' " +
            "UNION ALL " +
            "SELECT C5.operation AS 'operation', C5.dateTime AS 'dateTime', C5.value AS 'value', C5.type AS 'type', U5.name AS 'user_name' " +
            "FROM Cash C5 " +
            "INNER JOIN User U5 on U5.identifier = C5.user " +
            "WHERE C5.identifier > (SELECT MAX(S5.identifier) FROM Cash S5 WHERE S5.operation = 'ABE') AND C5.operation = 'FEC' " +
            "ORDER BY 2")
    List<CashModel> cashSummaryReport();

    @Query("SELECT C1.*, " +
            "U1.identifier AS 'user_identifier', " +
            "U1.active AS 'user_active', " +
            "U1.name AS 'user_name', " +
            "U1.login AS 'user_login', " +
            "U1.password AS 'user_password', " +
            "U1.level AS 'user_level' " +
            "FROM Cash C1 " +
            "LEFT JOIN User U1 ON U1.identifier = C1.user " +
            "WHERE C1.identifier >= (SELECT MAX(C2.identifier) FROM Cash C2 WHERE C2.operation = 'ABE') " +
            "ORDER BY C1.dateTime")
    LiveData<List<CashModel>> cashEntry();

}