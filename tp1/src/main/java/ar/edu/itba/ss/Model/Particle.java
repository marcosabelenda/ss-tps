package ar.edu.itba.ss.Model;

public class Particle {

    private double x;
    private double y;
    private double vx;
    private double vy;
    private Integer id;

    public Particle(Integer id, double x, double y, double vx, double vy) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
