package ar.edu.itba.ss;


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
        int totalTime = c.getConfigurationParser().getTotal_time();
        int printTime = c.getConfigurationParser().getPrint_time();

        double m1=0.1,m2=100,r1=0.005,r2=0.05; //TODO FIX
        dfg.generateDymanicFile((int) Math.abs(System.currentTimeMillis()),
                numberParticles,
                side,
                v,
                m1,m2,
                r1,r2);

        Space s = new Space(side, dfg.getParticles());
        int n = 1;
        System.out.println("Iniciado!!!");
        long start = System.currentTimeMillis();
        while(n<totalTime){ //TODO fIX
            //CALCULAR
            s.advance();
            if( n % printTime == 0) {
                dfg.saveDynamicFile(s,n);
            }
            n++;

        }
        long finish =System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Tiempo en ms: "+timeElapsed);
        s.getM().saveMetrics();
        s.getM().saveMetricsBolita();

    }
}
