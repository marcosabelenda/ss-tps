package ar.edu.itba.ss.LennardJones;

import javafx.util.Pair;

public class Target2 implements Target {

    double x;
    double y;

    public Target2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Pair<Double, Double> getVersor(Particle p) {
        double ex, ey;

        ex = x - p.getX();
        ey = y - p.getY();

        double module = Math.sqrt(Math.pow(Math.hypot(p.getX(), p.getY()),2)
                + Math.pow(Math.hypot(x,y),2) - 2 * (p.getX()*x + p.getY()*y));

        ex /= module;
        ey /= module;

        return new Pair<>(ex, ey);
    }

    @Override
    public boolean llegoAlTarget(Particle p) {
        return false;
    }

    @Override
    public Target getNextTarget() {
        return null;
    }
}
