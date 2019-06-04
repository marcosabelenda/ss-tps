package ar.edu.itba.ss.LennardJones;

import javafx.util.Pair;

public class Target1 implements Target {

    double x;
    double ybot;
    double ytop;
    Target next_target;
    double maxr;

    public Target1(double x, double ytop, double ybot, Target next_target, double maxr) {
        this.x = x;
        this.ytop = ytop;
        this.ybot = ybot;
        this.next_target = next_target;
        this.maxr = maxr;
    }

    @Override
    public Pair<Double, Double> getVersor(Particle p) {

        double ex, ey, auxY =p.y;

        //
        ex = x - p.getX();
//        ex = Math.abs(p.x - x);
        ey = 0;
        if(p.getY() < ybot) {
            ey = ybot - p.getY();
            auxY = ybot;
        } else if(p.getY() > ytop) {
            ey = ytop - p.getY();
            auxY =ytop;
        }
        double module = Math.sqrt(Math.pow(Math.hypot(p.getX(), p.getY()),2)
                + Math.pow(Math.hypot(x,auxY),2) - 2 * (p.getX()*x + p.getY()*auxY));
        ex /= module;
        ey /= module;
//
//        ex /= ex+ey;
//        ey /= ex+ey;

        return new Pair<>(ex, ey);

    }

    @Override
    public boolean llegoAlTarget(Particle p) {
            if (p.getX()+p.getR() > x+maxr) return true;

            if((p.getX()+p.getR() > x) && (p.getY()-p.getR() < ytop)
                && (p.getY()+p.getR() > ybot)) {
            return true;
        }
        return false;
    }

    @Override
    public Target getNextTarget() {
        return this.next_target;
    }
}
