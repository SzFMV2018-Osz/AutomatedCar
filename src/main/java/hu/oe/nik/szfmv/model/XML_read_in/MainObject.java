//package hu.oe.nik.szfmv.model.XML_read_in;
package hu.oe.nik.szfmv.model.XML_read_in;

public class MainObject {
    String type;
    int x;
    int y;
    double m11;
    double m12;
    double m21;
    double m22;
    int roadpainting_1;
    int roadpainting_2;
    int roadpainting_3;

    MainObject(String name,int x, int y, double m11, double m12, double m21, double m22, int p1, int p2, int p3){
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
