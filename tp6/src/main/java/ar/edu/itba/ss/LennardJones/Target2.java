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

        ex /= Math.hypot(ex, ey);
        ey /= Math.hypot(ex, ey);

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
