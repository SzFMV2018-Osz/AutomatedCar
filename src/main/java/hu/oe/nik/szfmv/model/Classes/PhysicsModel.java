package hu.oe.nik.szfmv.model.Classes;

public class PhysicsModel {
   public int height;
   public int width;
   public int weight;
   public int damage;
   public int damagelimit;
   public boolean isdead;


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getWeight() {
        return weight;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isIsdead() {
        return isdead;
    }

    public int getDamagelimit() {
        return damagelimit;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setIsdead(boolean isdead) {
        this.isdead = isdead;
    }

    public void setDamagelimit(int damagelimit) {
        this.damagelimit = damagelimit;
    }

    public int getArea() {
        return height * width;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public void CalculateDead() {
        if(damage == damagelimit){
         isdead=true;
        }
        else{
            isdead=false;
        }
    }

    public PhysicsModel(int width,int height,int weight, int damage, int damagelimit, boolean isdead){
        this.width=width;
        this.height=height;
        this.weight=weight;
        this.damage=damage;
        this.damagelimit =damagelimit;
        this.isdead=isdead;
    }

}
