package ar.edu.itba.ss.Model;

import ar.edu.itba.ss.utils.AlgorithmMetrics;
import ar.edu.itba.ss.utils.ConfigurationParser;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Gear implements Algorithm {

    private double elasticity;
    private double gamma;
    private double mass;
    private double dt;
    private double tt;
    private double position;
    private double velocity;


    private AlgorithmMetrics metrics;



    private static final int ORDER = 5;
    private static final int[] factorials = {1, 1, 2, 6, 24, 120};
    private static final double[] alphaOrder5 = {3.0/16, 251.0/360, 1.0, 11.0/18, 1.0/6, 1.0/60};

    public Gear(ConfigurationParser c){
        this.metrics = new AlgorithmMetrics("gear-metric");
        elasticity = c.getElasticity();
        gamma = c.getGamma();
        mass = c.getMass();
        dt = c.getDelta_time();
        tt = c.getTotal_time();
        position = 0; // TODO CHECK
        velocity = 0;

    }


    @Override
    public void run() {
        List<Double> lderivadas = derivadas(ORDER);
        double position = lderivadas.get(0);
        for(double tiempo=0; tiempo<this.tt; tiempo+=dt){
            metrics.addPosition(new Pair<>(tiempo,position));

            List <Double> pred = predicciones(lderivadas);
            double error = evaluar(pred);
            lderivadas = corregir(pred, error);
            position = lderivadas.get(0);
        }
        metrics.saveMetrics();
    }

    private List<Double> derivadas(int order){
        List<Double>  ld = new LinkedList<>();
        ld.add(this.position);
        ld.add(this.velocity);
        for(int i=1;i<order;i++){
            int size = ld.size();
            double value = (this.elasticity * ld.get(size - 2) + this.gamma * ld.get(size - 1));
            value = - value / this.mass;
            ld.add(value);
        }
        return ld;
    }

    private List<Double> predicciones(List<Double> der){
        int size = der.size();
        List<Double> pred = new LinkedList<>();
        for (int i = 0; i < (size+1); i++) {
            pred.add(0.0);
        }
        for (int i = 0; i < size; i++){
            for (int j = 0; j <= i; j++){
                double cache = pred.get(size-i-1)
                        + der.get(size - i + j - 1) * Math.pow(this.dt, j) / factorials[j];
                pred.set(size-i-1,cache); //TODO CHECK
            }
        }
        return pred;
    }

    private double evaluar(List<Double> pred){
        double realA = this.elasticity * pred.get(0) + this.gamma * pred.get(1);
        realA = - realA / this.mass;
        double err = realA - pred.get(2);
        err = err * Math.pow(this.dt, 2) / factorials[2];
        return err;
    }

    private List<Double> corregir(List<Double> pred,double err){
        List<Double> corr = new LinkedList<>();
//        int i=0;
//        for(Double p: pred){
//            double cache = p +
//                            (alphaOrder5[i] * err * factorials[i] /
//                            Math.pow(this.dt, i));
//            i++;
//        }
        for (int i = 0; i < pred.size()-1; i++){ //TODO FIJAR
            double value = pred.get(i) + alphaOrder5[i] * err * factorials[i] / Math.pow(dt, i);
            corr.add(value);
        }
        return corr;
    }

}
