package hu.petrik.etlap;

public class Etlap {
    private int id;
    private String nev;
    private String leiras;
    private int ar;
    private int kategoria_id;

    public Etlap(int id, String nev, String leiras, int ar, int kategoria_id) {
        this.id = id;
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.kategoria_id = kategoria_id;
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

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public int getKategoria_id() {
        return kategoria_id;
    }

    public void setKategoria_id(int kategoria_id) {
        this.kategoria_id = kategoria_id;
    }
}
