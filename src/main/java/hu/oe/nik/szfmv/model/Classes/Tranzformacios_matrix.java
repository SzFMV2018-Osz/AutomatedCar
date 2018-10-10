package hu.oe.nik.szfmv.model.Classes;

public class Tranzformacios_matrix {
    int m11;
    int m12;
    int m21;
    int m22;


    public Tranzformacios_matrix(int m11, int m12, int m21, int m22) {
        this.m11 = m11;
        this.m12 = m12;
        this.m21 = m21;
        this.m22 = m22;
    }

    public Tranzformacios_matrix(String m11s, String m12s, String m21s, String m22s) {
        try
        {
            m11 = java.lang.Integer.parseInt(m11s);
            m12 = java.lang.Integer.parseInt(m12s);
            m21 = java.lang.Integer.parseInt(m21s);
            m22 = java.lang.Integer.parseInt(m22s);
        }
        catch (NumberFormatException e)
        {
            m11 = Math.round(java.lang.Float.parseFloat(m11s));
            m12 = Math.round(java.lang.Float.parseFloat(m12s));
            m21 = Math.round(java.lang.Float.parseFloat(m21s));
            m22 = Math.round(java.lang.Float.parseFloat(m22s));
        }
    }

    public float getM11() {
        return m11;
    }

    public void setM11(int m11) {
        this.m11 = m11;
    }

    public float getM12() {
        return m12;
    }

    public void setM12(int m12) {
        this.m12 = m12;
    }

    public float getM21() {
        return m21;
    }

    public void setM21(int m21) {
        this.m21 = m21;
    }

    public float getM22() {
        return m22;
    }

    public void setM22(int m22) {
        this.m22 = m22;
    }
}


