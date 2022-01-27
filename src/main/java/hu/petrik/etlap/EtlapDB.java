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
        String sql = "SELECT * FROM etlap";
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