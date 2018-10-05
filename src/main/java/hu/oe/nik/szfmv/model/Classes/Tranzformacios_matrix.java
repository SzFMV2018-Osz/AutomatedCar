package hu.oe.nik.szfmv.model.Classes;

public class Tranzformacios_matrix {
    float m11;
    float m12;
    float m21;
    float m22;


    public Tranzformacios_matrix(float m11, float m12, float m21, float m22) {
        this.m11 = m11;
        this.m12 = m12;
        this.m21 = m21;
        this.m22 = m22;
    }

    public Tranzformacios_matrix(String m11s, String m12s, String m21s, String m22s) {
        m11 = java.lang.Float.parseFloat(m11s);
        m12 = java.lang.Float.parseFloat(m12s);
        m21 = java.lang.Float.parseFloat(m21s);
        m22 = java.lang.Float.parseFloat(m22s);
    }

    public float getM11() {
        return m11;
    }

    public void setM11(float m11) {
        this.m11 = m11;
    }

    public float getM12() {
        return m12;
    }

    public void setM12(float m12) {
        this.m12 = m12;
    }

    public float getM21() {
        return m21;
    }

    public void setM21(float m21) {
        this.m21 = m21;
    }

    public float getM22() {
        return m22;
    }

    public void setM22(float m22) {
        this.m22 = m22;
    }
}


