package ar.edu.itba.ss.Model;

public class Particle {

    private double x;
    private double y;
    private double vx;
    private double vy;
    private double r;
    private Integer id = -1;
    private double mass;

    public Particle(Integer id, double x, double y, double vx, double vy, double mass,double r) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.r =r;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Integer getId() {
        return id;
    }

    public double getVx() {
        return this.vx;
    }

    public double getVy() {
        return this.vy;
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

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getMass() {
        return mass;
    }

    public double getR() {
        return r;
    }
}
