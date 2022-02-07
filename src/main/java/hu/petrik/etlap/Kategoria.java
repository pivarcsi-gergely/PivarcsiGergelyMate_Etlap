package hu.petrik.etlap;

import java.sql.*;
import java.util.HashSet;

public class Kategoria {
    private int id;
    private String nev;
    static private HashSet<Kategoria> kategoriaHashSet;
    static Connection conn;
    public static Kategoria DEFAULT_KATEGORIA = new Kategoria(0, "nincs szűrés");

    public static void initialize(EtlapDB etlapDB) throws SQLException {
        conn = etlapDB.dbConn;
        kategoriaHashSet = new HashSet<>();
        Statement katStmt = conn.createStatement();
        String katSql = "SELECT * FROM kategoria";
        ResultSet katResult = katStmt.executeQuery(katSql);
        kategoriaHashSet.add(DEFAULT_KATEGORIA);
        while (katResult.next()) {
            Kategoria kategoria = new Kategoria(katResult.getInt("id"),
                                                katResult.getString("nev"));
            kategoriaHashSet.add(kategoria);
        }
    }

    public Kategoria(int id, String nev) {
        this.id = id;
        this.nev = nev;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    static public Kategoria fromId(int id) {
        return kategoriaHashSet.stream().filter(kategoria -> kategoria.id == id).findFirst().get();
    }

    static public Kategoria fromNev(String nev) {
        return kategoriaHashSet.stream().filter(kategoria -> kategoria.nev.equals(nev)).findFirst().get();
    }

    static public HashSet<Kategoria> getKategoriaHashSet() {
        return Kategoria.kategoriaHashSet;
    }

    @Override
    public String toString() {
        return this.getNev();
    }
}
