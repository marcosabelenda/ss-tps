package ar.edu.itba.ss.LennardJones;

import javafx.util.Pair;

public class Target1 implements Target {

    double x;
    double ybot;
    double ytop;
    Target target;

    public Target1(double x, double ytop, double ybot, Target target) {
        this.x = x-1;
        this.ytop = ytop;
        this.ybot = ybot;
        this.target = target;
    }

    @Override
    public Pair<Double, Double> getVersor(Particle p) {

        double ex, ey;

        ex = x - p.getX();
        ey = 0;

        if(p.getY() < ybot) {
            ey = ybot - p.getY();
        } else if(p.getY() > ytop) {
            ey = ytop - p.getY();
        }

        ex /= Math.hypot(ex, ey);
        ey /= Math.hypot(ex, ey);

        return new Pair<>(ex, ey);
    }

    @Override
    public boolean llegoAlTarget(Particle p) {
        if((p.getX()+p.getR() > x) && (p.getY()-p.getR() < ytop) && (p.getY()+p.getR() > ybot)) {
            return true;
        }
        return false;
    }

    @Override
    public Target getNextTarget() {
        return this.target;
    }
}
