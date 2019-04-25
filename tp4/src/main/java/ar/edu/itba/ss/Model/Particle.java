package ar.edu.itba.ss.Model;

public class Particle {

    private double y;
    private double x;
    private double v;
    private double a;
    private double a1;
    private double a2;
    private double a3;
    private double angle;
    private Integer id;

    public Particle(Integer id, double x, double y, double v, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.v = v;
        this.angle = angle;
    }

    public Particle(double x, double v, double a, double a1, double a2, double a3){
        this.x = x;
        this.v = v;
        this.a = a;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public double getA3() {
        return a3;
    }

    public void setA3(double a3) {
        this.a3 = a3;
    }

    public double getAngle() {
        return angle;
    }

    public Integer getId() {
        return id;
    }

    public double getVx() {
        return this.v*Math.cos(this.angle);
    }


    public double getVy() {
        return this.v*Math.sin(this.angle);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Particle)) {
            return false;
        }

        Particle p = (Particle) o;

        return this.id.equals(p.getId());
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
