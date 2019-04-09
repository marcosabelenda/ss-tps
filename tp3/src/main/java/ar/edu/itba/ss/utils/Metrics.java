package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.Model.Space;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Metrics {

    private List<Double> collitionTimes;
    private List<Pair<Double,Double>> bolitaPostions;

    private double lastTime;


    public Metrics() {
        this.bolitaPostions = new LinkedList<>();
        this.collitionTimes = new LinkedList<>();
        this.lastTime = 0;
    }

    public void addTime(double t) {
        collitionTimes.add(lastTime + t);
        this.lastTime += t;
    }

    public void addBolitaPos(Particle p) {
        Pair<Double,Double> pair = new Pair<>(p.getX(),p.getY());
        bolitaPostions.add(pair);
    }

    public void saveMetrics() {
        try(FileWriter fw = new FileWriter("metrics.txt", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            for(Double d : collitionTimes) {
                out.println(d);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMetricsBolita() {
        try(FileWriter fw = new FileWriter("metrics-bolita.txt", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            for(Pair p : bolitaPostions) {
                out.println(p.getKey() + " " + p.getValue());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
