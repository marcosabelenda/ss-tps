package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.Model.Particle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OutputFileGenerator {

    public OutputFileGenerator() {

    }

    public void generateStaticalData(Board b, int seed, Metrics m, boolean isPCC) {
        try (FileWriter fw = new FileWriter("statical-data-" + seed + "-" + b.getNumberParticles() + "-" + (int) b.getSide()
                + "-" + (int) b.getCellSide() + "-" + (int) b.getParticleRadius() + "-" + isPCC + ".txt", false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("time " + m.getTime());
            out.println("total_comparisons " + m.getTotalComparisons());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateNeighbourFile(Board b, int seed, boolean isPCC) {
        try(FileWriter fw = new FileWriter("neighbour-" + seed + "-" + b.getNumberParticles() + "-" + (int)b.getSide()
                                            + "-" + (int)b.getCellSide() + "-" + (int)b.getParticleRadius() + "-" + isPCC + ".txt", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

            for(Particle p : b.getNeighbours().keySet()) {
                List<Particle> particles = b.getNeighbours().get(p);
                StringBuilder aux = new StringBuilder();
                for(Particle a : particles) {
                    aux.append("," + a.getId());
                }
                out.println(p.getId().toString() + aux.toString());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
