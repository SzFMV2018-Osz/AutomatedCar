package hu.oe.nik.szfmv.model.Classes;

public class Targy {
    String type;
    Tranzformacios_matrix tran_matrix;
    float x;
    float y;

    public Targy(String type, float m11, float m12, float m21, float m22, float x, float y) {
        this.type = type;
        tran_matrix = new Tranzformacios_matrix(m11 ,m12 ,m21 ,m22);
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Tranzformacios_matrix getTran_matrix() {
        return tran_matrix;
    }

    public void setTran_matrix(Tranzformacios_matrix tran_matrix) {
        this.tran_matrix = tran_matrix;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
