package ar.edu.itba.ss;


import ar.edu.itba.ss.MetricAlgoritms.OffLattice;
import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.utils.DynamicFileGenerator;

import java.util.List;

public class App
{
    public static void main( String[] args ) throws Exception {

        DynamicFileGenerator dfg = new DynamicFileGenerator();
        dfg.generateDymanicFile(1,20,10,0.3);
        Board b = new Board(10,10,20,1,dfg.getParticles());
        OffLattice ol = new OffLattice();
        double time = 1;
        int n = 0;
        int printn = 1;
        double printTime = 3;
        while(n <= 60) {
            ol.calculateNeighbours(b);
            List<Particle> particles = ol.calculateParticles(b,0.1,time);
            b = new Board(10,10,20,1, particles);
            if(n*time % printTime == 0) {
                dfg.saveDynamicFile(b,printn);
                printn++;
            }
            n++;
        }
    }
}
