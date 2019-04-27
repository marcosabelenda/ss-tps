package ar.edu.itba.ss.Model;

import ar.edu.itba.ss.utils.AlgorithmMetrics;
import ar.edu.itba.ss.utils.ConfigurationParser;
import javafx.util.Pair;

public class Beeman implements Algorithm{

    private double r;
    private double v;
    private double m;
    private double elasticidad;
    private double gamma;
    private double dt;
    private double tt;
    private AlgorithmMetrics metrics;


    public Beeman(ConfigurationParser c) {
        this.m = c.getMass();
        this.elasticidad = c.getElasticity();
        this.gamma = c.getGamma();
        this.tt = c.getTotal_time();
        this.dt = c.getDelta_time();
        this.metrics = new AlgorithmMetrics("beeman-metric");
        this.r = 1;
        this.v = -1 * (this.gamma / (2 * this.m));
    }

    @Override
    public void run() {
        double a;
        double aAnt;
        double aSig;
        double tiempo = 0;
        double vPredicted;

        a = aceleracion(r, v);

        metrics.addPosition(new Pair<>(tiempo, r));

        // Utilizando Euler para calcular r y v inicial
        this.r = this.r + dt*this.v + (Math.pow(dt,2)/2)*a;
        this.v = this.v + dt*a;

        // Seteando aceleracion a t = 0 y t = dt
        aAnt = a;
        a = aceleracion(r, v);

        tiempo = this.dt;

        while (tiempo < this.tt) {

            r = r + v * dt + ((2.0 / 3.0) * a - (1.0 / 6.0) * aAnt) * Math.pow(dt, 2);
            vPredicted = v + ( (3.0 / 2.0) * a  - (1.0 / 2.0) * aAnt) * dt;
            aSig = aceleracion(r, vPredicted);
            v = v + ( (1.0 / 3.0) * aSig + (5.0 / 6.0) * a  - (1.0 / 6.0) * aAnt) * dt;

            aAnt = a;
            a = aceleracion(r, v);

            metrics.addPosition(new Pair<>(tiempo, r));

            tiempo += dt;

        }
        metrics.saveMetrics();

    }


    private double aceleracion(double r, double v) {
        double f = (-1 * elasticidad * r)  + (-1 * gamma * v);
        return f / m;
    }
}
