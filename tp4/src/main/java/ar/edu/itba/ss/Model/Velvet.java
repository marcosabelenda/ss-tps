package ar.edu.itba.ss.Model;

import ar.edu.itba.ss.utils.AlgorithmMetrics;
import ar.edu.itba.ss.utils.ConfigurationParser;
import javafx.util.Pair;

public class Velvet implements Algorithm {


    private double r;
    private double v;
    private double m;
    private double elasticidad;
    private double gamma;
    private double dt;
    private double tt;
    private AlgorithmMetrics metrics;

    public  Velvet(ConfigurationParser c) {
        this.tt = c.getTotal_time();
        this.dt = c.getDelta_time();
        this.gamma = c.getGamma();
        this.elasticidad = c.getElasticity();
        this.m = c.getMass();
        this.r = 1.0;
        this.v = -1 * (c.getGamma() / (2.0 * c.getMass()));
        this.metrics  = new AlgorithmMetrics("velvet-metric");
    }

    @Override
    public void run() {
        double a;
        double vIntermedio;
        double aSig;
        double tiempo = 0;
        double vPredicted;

        a = aceleracion(r, v);

        metrics.addPosition(new Pair<>(tiempo, r));

        while (tiempo < tt) {
            tiempo += dt;

            r = r + v * dt + Math.pow(dt, 2) * a;
            vIntermedio = v + a * (dt / 2.0);
            aSig = aceleracion(r, vIntermedio);
            v = v + (a + aSig)*dt/2;

            a = aceleracion(r, v);

            metrics.addPosition(new Pair<>(tiempo, r));
        }
        metrics.saveMetrics();
    }

    private double aceleracion(double r, double v) {
        double f = (-1 * elasticidad * r)  + (-1 * gamma * v);
        return f / m;
    }
}
