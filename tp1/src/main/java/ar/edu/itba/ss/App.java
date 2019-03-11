package ar.edu.itba.ss;

import ar.edu.itba.ss.MetricAlgoritms.MetricAlgoritmsWithPCC;
import ar.edu.itba.ss.MetricAlgoritms.MetricAlgoritmsWithoutPCC;
import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.utils.ConfigurationManager;
import ar.edu.itba.ss.utils.Metrics;
import ar.edu.itba.ss.utils.OutputFileGenerator;

public class App
{
    public static void main( String[] args ) throws Exception {

        ConfigurationManager cm = new ConfigurationManager();

        Board b = new Board(cm.getConfigurationParser().getBoardSide(), cm.getConfigurationParser().getCellSide(),
                            cm.getConfigurationParser().getNumberParticles(), cm.getConfigurationParser().getRadius(),
                            cm.getParticles());

        Metrics m = new Metrics();
        if(cm.getConfigurationParser().isPcc()) {
            MetricAlgoritmsWithPCC ma = new MetricAlgoritmsWithPCC();
            ma.calculateNeighbours(b, m);
        } else {
            MetricAlgoritmsWithoutPCC ma = new MetricAlgoritmsWithoutPCC();
            ma.calculateNeighbours(b, m);
        }

        OutputFileGenerator ofg = new OutputFileGenerator();
        ofg.generateNeighbourFile(b, cm.getConfigurationParser().getSeed(), cm.getConfigurationParser().isPcc());
        ofg.generateStaticalData(b, cm.getConfigurationParser().getSeed(), m, cm.getConfigurationParser().isPcc());
    }
}
