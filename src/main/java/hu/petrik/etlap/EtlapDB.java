package hu.petrik.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EtlapDB {
    Connection dbConn;

    public EtlapDB() throws SQLException {
        dbConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }

    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> etlapList = new ArrayList<>();
        Statement etlapStmt = dbConn.createStatement();
        String etlapSql = "SELECT * FROM etlap";
        ResultSet etlapResult = etlapStmt.executeQuery(etlapSql);

        while (etlapResult.next()) {
            int id = etlapResult.getInt("id");
            String nev = etlapResult.getString("nev");
            String leiras = etlapResult.getString("leiras");
            int ar = etlapResult.getInt("ar");
            int kategoria_id = etlapResult.getInt("kategoria_id");

            Etlap etlap = new Etlap(id, nev, leiras, ar, kategoria_id);
            etlapList.add(etlap);
        }
        return etlapList;
    }

    public HashSet<Kategoria> getKategoria() throws SQLException {
        HashSet<Kategoria> kategoriaHashSet = new HashSet<>();
        Statement katStmt = dbConn.createStatement();
        String katSql = "SELECT * FROM kategoria";
        ResultSet katResult = katStmt.executeQuery(katSql);

        while (katResult.next()) {
            int id = katResult.getInt("id");
            String katNev = katResult.getString("nev");

            Kategoria kategoria = new Kategoria(id, katNev);
            kategoriaHashSet.add(kategoria);
        }
        return kategoriaHashSet;
    }

    public int etelHozzaadasa(String nev, String leiras, int ar, String kategoria) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria) VALUES (?,?,?,?)";
        PreparedStatement pStmt = dbConn.prepareStatement(sql);
        pStmt.setString(1, nev);
        pStmt.setString(2, leiras);
        pStmt.setInt(3, ar);
        pStmt.setString(4, kategoria);
        return pStmt.executeUpdate();
    }

    public boolean etelTorlese(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement pStmt = dbConn.prepareStatement(sql);
        pStmt.setInt(1, id);
        int erintettSorok = pStmt.executeUpdate();
        return erintettSorok == 1;
    }

    public int etelNovelSzazalek(int szazalek, int selectedIndex) throws SQLException {
        if (selectedIndex == 0) {
            String sqlOsszes = "UPDATE etlap SET ar = ar + ar * (?/100)";
            PreparedStatement pStmtOsszes = dbConn.prepareStatement(sqlOsszes);
            pStmtOsszes.setInt(1, szazalek);
            int sikeresEOsszes = pStmtOsszes.executeUpdate();
            return sikeresEOsszes;
        }
        else {
            String sqlEgyedi = "UPDATE etlap SET ar = ar + ar * (?/100) WHERE id = ?";
            PreparedStatement pStmtEgyedi = dbConn.prepareStatement(sqlEgyedi);
            pStmtEgyedi.setInt(1, szazalek);
            pStmtEgyedi.setInt(2, selectedIndex);
            int sikeresEEgyedi = pStmtEgyedi.executeUpdate();
            return sikeresEEgyedi;
        }
    }

    public int etelNovelForint(int forintErtek, int selectedIndex) throws SQLException {
        if (selectedIndex == 0) {
            String sqlOsszes = "UPDATE etlap SET ar = ar + ?";
            PreparedStatement pStmtOsszes = dbConn.prepareStatement(sqlOsszes);
            pStmtOsszes.setInt(1, forintErtek);
            int sikeresEOsszes = pStmtOsszes.executeUpdate();
            return sikeresEOsszes;
        }
        else {
            String sqlEgyedi = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
            PreparedStatement pStmtEgyedi = dbConn.prepareStatement(sqlEgyedi);
            pStmtEgyedi.setInt(1, forintErtek);
            pStmtEgyedi.setInt(2, selectedIndex);
            int sikeresEEgyedi = pStmtEgyedi.executeUpdate();
            return sikeresEEgyedi;
        }    }
}