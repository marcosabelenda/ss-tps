package ar.edu.itba.ss.Model;

public class Particle {

    private double x;
    private double y;
    private double v;
    private double angle;
    private Integer id;

    public Particle(Integer id, double x, double y, double v, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.v = v;
        this.angle = angle;
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
