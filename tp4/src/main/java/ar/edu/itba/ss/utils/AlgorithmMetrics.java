package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Particle;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class AlgorithmMetrics {

    private List<Pair<Double,Double>> positions;
    private String filename;

    public AlgorithmMetrics(String filename) {
        this.filename = filename;
        this.positions = new LinkedList<>();
    }

    public void addPosition(Pair<Double,Double> pos) {
        this.positions.add(pos);
    }

    public void saveMetrics(){
        try{
            FileWriter fw = new FileWriter(filename, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            for (Pair<Double,Double> p : positions) {
                out.println(p.getKey()+" "+p.getValue());
            }
            out.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }




}
