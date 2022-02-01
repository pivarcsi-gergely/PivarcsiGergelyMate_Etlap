package hu.petrik.etlap;

public class Etlap {
    private int id;
    private String nev;
    private String leiras;
    private int ar;
    private Kategoria kategoria;

    public Etlap(int id, String nev, String leiras, int ar, Kategoria kategoria) {
        this.id = id;
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.kategoria = kategoria;
    }

    public Etlap(int id, String nev, String leiras, int ar, String katNev) {
        this.id = id;
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.kategoria = Kategoria.fromNev(katNev);
    }

    public Etlap(int id, String nev, String leiras, int ar, int katId) {
        this.id = id;
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.kategoria = Kategoria.fromId(katId);
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

    public Kategoria getKategoria() {
        return kategoria;
    }

    public String getKategoriaNev() {
        return kategoria.getNev();
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }
}
