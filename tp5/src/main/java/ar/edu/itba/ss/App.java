package ar.edu.itba.ss;


import ar.edu.itba.ss.LennardJones.*;
import ar.edu.itba.ss.utils.ConfigurationParser;

import java.util.List;

public class App
{
    public static void main( String[] args ) throws Exception {

        ConfigurationParser c = new ConfigurationParser();
        c.parse("configuration.txt");
        DynamicFileGenerator dfg = new DynamicFileGenerator();


        dfg.generateDymanicFile(c.getSeed(), 0.01, c.getHeight(), c.getWidth(), c.getMinR(), c.getMaxR(), c.getCant_maxima());


//        Board2 b = new Board2(c.getHeight(), c.getWidth(), c.getCellSide(),9.8, 100000, 200000, c.getWindow(), dfg.getParticles(), c.getSeed());

        // usa random System.millis
        Board2 b = new Board2(c.getHeight(), c.getWidth(), c.getCellSide(),9.8, 100000, 200000, c.getWindow(), dfg.getParticles(), (int) System.currentTimeMillis());


        dfg.saveDynamicFile(b, 0);
        LennardJones lj = new LennardJones(c.getDelta_time(), c.getTotal_time(), c.getPrint_time(), dfg);

        lj.run(b);


    }
}
