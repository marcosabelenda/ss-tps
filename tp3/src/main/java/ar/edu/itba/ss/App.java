package ar.edu.itba.ss;


import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.Model.Space;
import ar.edu.itba.ss.utils.ConfigurationManager;
import ar.edu.itba.ss.utils.DynamicFileGenerator;

import java.util.List;

public class App
{
    public static void main( String[] args ) throws Exception {
        ConfigurationManager c = new ConfigurationManager();
        DynamicFileGenerator dfg = new DynamicFileGenerator();

        int numberParticles = c.getConfigurationParser().getNumberParticles();
        double side = c.getConfigurationParser().getBoardSide();
        double v = c.getConfigurationParser().getVelocity();
        double etha = c.getConfigurationParser().getEtha();

        double m1=1,m2=1,r1=3,r2=3; //TODO FIX
        dfg.generateDymanicFile((int) Math.abs(System.currentTimeMillis()),
                numberParticles,
                side,
                m1,m2,
                r1,r2);

        Space s = new Space(side, dfg.getParticles());
//        Board b = new Board(side, (int) side,numberParticles,1,dfg.getParticles());
//        OffLattice ol = new OffLattice();
        double time = 1;
        int n = 1;
        int printn = 1;
        double printTime = c.getConfigurationParser().getPrint_time();
        while(n<1000){ //TODO fIX
            //CALCULAR
            s.advance();
            dfg.saveDynamicFile(s,n);


            n++;

        }
    }
}
