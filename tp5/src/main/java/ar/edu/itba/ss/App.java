package ar.edu.itba.ss;


import ar.edu.itba.ss.LennardJones.*;
import ar.edu.itba.ss.utils.ConfigurationParser;

public class App
{
    public static void main( String[] args ) throws Exception {

        ConfigurationParser c = new ConfigurationParser();
        c.parse("configuration.txt");
        DynamicFileGenerator dfg = new DynamicFileGenerator();

        dfg.generateDymanicFile(1,c.getNumberParticles(), 200, c.getVelocity(), c.getMass(), 1);


        //TODO poner bien los tamanios y los argumentos
        Board2 b = new Board2(200, 400, 5, 40, 80, dfg.getParticles());

        dfg.saveDynamicFile(b, 0);
        LennardJones lj = new LennardJones(c.getDelta_time(), c.getTotal_time(), c.getPrint_time(), dfg);

        lj.run(b);


    }
}
