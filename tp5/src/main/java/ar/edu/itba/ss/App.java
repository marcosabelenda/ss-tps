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

        dfg.generateDymanicFile(1, 0.01, 2, 0.3, c.getNumberParticles());

        //dfg.getParticles().add(new Particle(1,0.1, 1.3, 0.022,0.01,0,0,0,0));
        //dfg.getParticles().add(new Particle(2,0.09, 1.6, 0.022,0.01,0,0,0,0));

        //dfg.getParticles().add(new Particle(3,0.11, 1.5, 0.022,0.01,0,0,0,0));
        //TODO poner bien los tamanios y los argumentos
        Board2 b = new Board2(2, 0.3, 0.06,9.8, 100000, 200000, 0.1, dfg.getParticles());

        dfg.saveDynamicFile(b, 0);
        LennardJones lj = new LennardJones(c.getDelta_time(), c.getTotal_time(), c.getPrint_time(), dfg);

        lj.run(b);


    }
}
