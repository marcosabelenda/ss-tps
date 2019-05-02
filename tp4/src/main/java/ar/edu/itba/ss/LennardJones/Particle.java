package ar.edu.itba.ss.LennardJones;

public class Particle {

    private double x;
    private double y;
    private double prevx;
    private double prevy;
    private double r;
    private double m;
    private double vx;
    private double vy;
    private double ax;
    private double oldax;
    private double ay;
    private double olday;
    private double newax;
    private double neway;
    private Integer id;
    private double potential;

    public Particle(Integer id, double x, double y, double r, double m, double vx, double vy, double ax, double ay) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.prevx = x;
        this.prevy = y;
        this.r = r;
        this.m = m;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.prevx = this.x;
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.prevy = this.y;
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public double getM() {
        return this.m;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public double getOldax() {
        return oldax;
    }

    public void setOldax(double oldax) {
        this.oldax = oldax;
    }

    public double getOlday() {
        return olday;
    }

    public void setOlday(double olday) {
        this.olday = olday;
    }

    public double getNewax() {
        return newax;
    }

    public void setNewax(double newax) {
        this.newax = newax;
    }

    public double getNeway() {
        return neway;
    }

    public void setNeway(double neway) {
        this.neway = neway;
    }

    public double getPrevx() {
        return prevx;
    }

    public double getPrevy() {
        return prevy;
    }


    public Integer getId() {
        return id;
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

    public double getPotential() {
        return potential;
    }

    public void setPotential(double potential) {
        this.potential = potential;
    }

}
