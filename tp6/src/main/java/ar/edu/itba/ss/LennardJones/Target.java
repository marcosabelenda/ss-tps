package ar.edu.itba.ss.LennardJones;


import javafx.util.Pair;

public interface Target {

    Pair<Double, Double> getVersor(Particle p);
    boolean llegoAlTarget(Particle p);
    Target getNextTarget();
}
