package ar.edu.itba.ss.Model;

public class Collision {

    public static final Integer HORIZAONTAL = 0, VERTICAL = 1;
    private  Particle p1;
    private Particle p2; //ES NULL SI ES PARED
    private double time;
    private int wall; //0 para techo/piso, 1 para pared izq/der

    public Collision(Particle p1,Particle p2, double time){
        this.p1 = p1;
        this.p2 = p2;
        this.time = time;
    }


    public Collision(Particle p1, double time, int wall){
        this.p1 = p1;
        this.time = time;
        this.wall = wall;
    }

    public Particle getP1() {
        return p1;
    }

    public void setP1(Particle p1) {
        this.p1 = p1;
    }

    public Particle getP2() {
        return p2;
    }

    public void setP2(Particle p2) {
        this.p2 = p2;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getWall() {
        return wall;
    }
}
