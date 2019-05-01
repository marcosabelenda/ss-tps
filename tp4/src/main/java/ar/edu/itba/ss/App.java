package ar.edu.itba.ss;


import ar.edu.itba.ss.LennardJones.Board;
import ar.edu.itba.ss.LennardJones.DynamicFileGenerator;
import ar.edu.itba.ss.LennardJones.LennardJones;
import ar.edu.itba.ss.LennardJones.Particle;
import ar.edu.itba.ss.Model.Analytic;
import ar.edu.itba.ss.Model.Beeman;
import ar.edu.itba.ss.Model.Gear;
import ar.edu.itba.ss.Model.Velvet;
import ar.edu.itba.ss.utils.ConfigurationParser;

public class App
{
    public static void main( String[] args ) throws Exception {

        DynamicFileGenerator dfg = new DynamicFileGenerator();

        dfg.generateDymanicFile(1,1000, 200, 10, 0.1, 1);
        //dfg.getParticles().add(new Particle(1, 100.0, 50.0, 1, 0.1, 1, 0, 0, 0));
        //dfg.getParticles().add(new Particle(2, 106.0, 50.0, 1, 0.1, -1, 0, 0, 0));

        Board b = new Board(200, 400, 5, 40, 80, 5,2,1, dfg.getParticles());
        dfg.saveDynamicFile(b, 0);
        LennardJones lj = new LennardJones(0.0000001, 5, dfg);

        lj.run(b);

        /*
        ConfigurationParser c = new ConfigurationParser();
        c.parse("configuration.txt");

        Analytic a = new Analytic(c);
        a.run();


        Gear g = new Gear(c);
        g.run();

        Beeman b = new Beeman(c);
        b.run();

        Velvet v = new Velvet(c);
        v.run();
        */
    }
}
