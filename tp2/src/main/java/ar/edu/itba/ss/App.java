package ar.edu.itba.ss;


import ar.edu.itba.ss.MetricAlgoritms.OffLattice;
import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.utils.ConfigurationManager;
import ar.edu.itba.ss.utils.DynamicFileGenerator;
import ar.edu.itba.ss.utils.Metrics;

import java.util.List;

public class App
{
    public static void main( String[] args ) throws Exception {
        ConfigurationManager c = new ConfigurationManager();
        DynamicFileGenerator dfg = new DynamicFileGenerator();
        Metrics m = new Metrics();

        int numberParticles = c.getConfigurationParser().getNumberParticles();
        double side = c.getConfigurationParser().getBoardSide();
        double v = c.getConfigurationParser().getVelocity();
        double etha = c.getConfigurationParser().getEtha();


        dfg.generateDymanicFile((int) System.currentTimeMillis(),numberParticles,side, v);
        Board b = new Board(side, (int) side,numberParticles,1,dfg.getParticles());
        OffLattice ol = new OffLattice();
        double time = 1;
        int n = 0;
        int printn = 1;
        double printTime = c.getConfigurationParser().getPrint_time();
        while(n <= c.getConfigurationParser().getTotal_time()) {
            m.addPolarization(b.getParticles());
            ol.calculateNeighbours(b);
            List<Particle> particles = ol.calculateParticles(b,etha,time);
            b = new Board(side, (int) side,numberParticles,1, particles);
            if(n*time % printTime == 0) {
                dfg.saveDynamicFile(b,printn);
                printn++;
            }
            n++;
        }

        m.saveMetrics();
    }
}
