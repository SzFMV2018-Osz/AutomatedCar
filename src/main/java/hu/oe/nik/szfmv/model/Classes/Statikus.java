package hu.oe.nik.szfmv.model.Classes;

public class Statikus extends  Targy {
    boolean neki_lehet_menni;

    public boolean isNeki_lehet_menni() {
        return neki_lehet_menni;
    }

    public void setNeki_lehet_menni(boolean neki_lehet_menni) {
        this.neki_lehet_menni = neki_lehet_menni;
    }


    public Statikus(String type, float m11, float m12, float m21, float m22, float x, float y) {
        super(type, m11, m12, m21, m22, x, y);
        neki_lehet_menni = false;
    }
    public Statikus(String type, float m11, float m12, float m21, float m22, float x, float y , boolean neki_lehet_menni) {
        super(type, m11, m12, m21, m22, x, y);
        this.neki_lehet_menni = neki_lehet_menni;
    }
}
