package ar.edu.itba.ss.MetricAlgoritms;

import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.utils.Metrics;

public interface MetricAlgoritm {

    void calculateNeighbours(Board b, Metrics m);
}
