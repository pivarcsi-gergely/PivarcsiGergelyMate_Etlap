package hu.petrik.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtlapDB {
    Connection dbConn;

    public EtlapDB() throws SQLException {
        dbConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }

    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> etlapList = new ArrayList<>();
        Statement stmt = dbConn.createStatement();
        String sql = "SELECT * FROM etlapList";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String nev = result.getString("nev");
            String leiras = result.getString("leiras");
            int ar = result.getInt("ar");
            String kategoria = result.getString("kategoria");
            Etlap etlap = new Etlap(id, nev, leiras, ar, kategoria);
            etlapList.add(etlap);
        }
        return etlapList;
    }
}