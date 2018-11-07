package hu.oe.nik.szfmv.model.Classes;

public class PhysicsModel {
    public int height;
    public int width;
    public int weight;
    public int damage;
    public int damageLimit;
    public boolean isDead;
    public boolean fatal;

    public PhysicsModel() {
    }

    public boolean isFatal() {
        return fatal;
    }

    public void setFatal(boolean fatal) {
        this.fatal = fatal;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int width) {
        this.weight = weight;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getDamageLimit() {
        return damageLimit;
    }

    public void setDamageLimit(int damageLimit) {
        this.damageLimit = damageLimit;
    }

    public int getArea() {
        return height * width;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public void calculateDead() {
        if (!fatal) {
            if (damage == damageLimit) {
                isDead = true;
            } else {
                isDead = false;
            }
        } else {
            isDead = true;
        }

    }


   /* public PhysicsModel(int width,int height,int Weight, int Damage, int DamageLimit, boolean IsDead){
        this.width=width;
        this.height=height;
        this.Weight=Weight;
        this.Damage=Damage;
        this.DamageLimit =DamageLimit;
        this.IsDead=IsDead;
    }*/

}
