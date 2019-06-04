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


        // creo los dos target y se los paso a las particulas
        Target t2 = new Target2(c.getWidth()-c.getOutsideWidth()/2,(c.getHeight()/2));
        Target t1 = new Target1(c.getWidth()-c.getOutsideWidth(),(c.getHeight() + c.getWindow())/2-c.getMaxR(),(c.getHeight() - c.getWindow())/2+c.getMaxR(), t2, c.getMaxR());
        dfg.generateDymanicFile(c.getSeed(), c.getHeight(), c.getWidth()-c.getOutsideWidth(), c.getMaxR(), c.getCant_maxima(), t1);

        // usa random System.millis
        Board2 b = new Board2(c.getHeight(), c.getWidth(), c.getCellSide(), c.getWindow(), c.getOutsideWidth(), dfg.getParticles());


        dfg.saveDynamicFile(b, 0);
        ContractileParticleModel cpm = new ContractileParticleModel(c.getDelta_time(), c.getTotal_time(), c.getPrint_time(), c.getMinR(), c.getMaxR(), c.getMaxV(), c.getBeta(), c.getTao(), dfg);

        cpm.run(b);

    }
}
