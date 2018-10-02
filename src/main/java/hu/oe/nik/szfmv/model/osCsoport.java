package hu.oe.nik.szfmv.model;

public class osCsoport {
    String type;
    String x;
    String y;
    String m11;
    String m12;
    String m21;
    String m22;
    String roadpainting_1;
    String roadpainting_2;
    String roadpainting_3;

    osCsoport(String name,String x, String y, String m11, String m12, String m21, String m22, String p1, String p2, String p3){
        this.type=name;
        this.x=x;
        this.y=y;
        this.m11 =m11;
        this.m12=m12;
        this.m21=m21;
        this.m22=m22;
        this.roadpainting_1=p1;
        this.roadpainting_2=p2;
        this.roadpainting_3=p3;
    }
}
