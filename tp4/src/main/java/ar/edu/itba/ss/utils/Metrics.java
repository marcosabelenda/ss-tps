package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Particle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Metrics {

    private List<Double> polarizations;

    public Metrics() {
        this.polarizations = new LinkedList<>();
    }

    public void addPolarization(List<Particle> list) {
        double vx = 0, vy = 0;
        if(list==null || list.size()==0)
            return;
        for (Particle p : list) {
            vx += p.getVx();
            vy += p.getVy();
        }
        vx /= (list.size()*list.get(0).getV());
        vy /= (list.size()*list.get(0).getV());

        this.polarizations.add(Math.sqrt(Math.pow(vx,2) + Math.pow(vy,2)));
    }

    public void saveMetrics() { //TODO FIX THIS MESS
        try(FileWriter fw = new FileWriter("metrics.txt", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            for(Double d : polarizations) {
                out.println(d);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
