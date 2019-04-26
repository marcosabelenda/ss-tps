package ar.edu.itba.ss.Model;

import ar.edu.itba.ss.utils.AlgorithmMetrics;
import ar.edu.itba.ss.utils.ConfigurationParser;
import javafx.util.Pair;

public class Analytic implements Algorithm {

    private AlgorithmMetrics metrics;

    private double m;
    private double k;
    private double gamma;


    public  Analytic(ConfigurationParser c){
        this.m = c.getMass();
        this.k = c.getElasticity();
        this.gamma = c.getGamma();

        this.metrics = new AlgorithmMetrics("analytic-metric");
    }

    @Override
    public void run(double tiempoTotal, double dt) {
        double tiempo = 0;

        while (tiempo < tiempoTotal) {
            double position = equation(m,gamma,k,tiempo);
            metrics.addPosition(new Pair<>(tiempo,position));
            tiempo+=dt;
        }
        metrics.saveMetrics();
    }

    private double equation(double m,double gamma, double k,double t){
        return Math.exp(-(gamma*t)/(2*m)) * Math.cos(Math.sqrt(k/m - Math.pow(gamma,2)/ (4* (Math.pow(m,2)))) * t);
    }
}
