package ar.edu.itba.ss;


import ar.edu.itba.ss.MetricAlgoritms.OffLattice;
import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.utils.DynamicFileGenerator;
import ar.edu.itba.ss.utils.Metrics;

import java.util.List;

public class App
{
    public static void main( String[] args ) throws Exception {

        DynamicFileGenerator dfg = new DynamicFileGenerator();
        Metrics m = new Metrics();
        dfg.generateDymanicFile(15,50,10,0.01);
        Board b = new Board(10,10,50,1,dfg.getParticles());
        OffLattice ol = new OffLattice();
        double time = 1;
        int n = 0;
        int printn = 1;
        double printTime = 3;
        while(n <= 10000) {
            m.addPolarization(b.getParticles());
            ol.calculateNeighbours(b);
            List<Particle> particles = ol.calculateParticles(b,0.1,time);
            b = new Board(10,10,50,1, particles);
            if(n*time % printTime == 0) {
                dfg.saveDynamicFile(b,printn);
                printn++;
            }
            n++;
        }

        m.saveMetrics();
    }
}
