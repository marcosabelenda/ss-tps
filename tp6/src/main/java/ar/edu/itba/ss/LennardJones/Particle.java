package ar.edu.itba.ss.LennardJones;

public class Particle {

    double x;
    double y;
    double prevx;
    double prevy;
    double r;
    double vx;
    double vy;

    Target target;

    Integer id;



    public Particle(Integer id, double x, double y, double r, double vx, double vy, Target target) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.prevx = x;
        this.prevy = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
        this.target = target;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
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

    public void setR(double r) {
        this.r = r;
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

    public double getPrevx() {
        return prevx;
    }

    public double getPrevy() {
        return prevy;
    }

    public double getKinetic(){
        return (1/2.0)*(Math.pow(vx,2)+Math.pow(vy,2));
    }

    public Integer getId() {
        return id;
    }

    public void ponerTodoEnCero() {
        this.vx = 0;
        this.vy = 0;
        this.prevx = x;
        this.prevy = y;
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

}
