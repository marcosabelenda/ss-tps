package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Particle;

import java.io.IOException;
import java.util.List;

public class ConfigurationManager {


    private static final String CONFIGURATION_FILE_NAME = "configuration.txt";
    private ConfigurationParser configurationParser;
    private List<Particle> particles;


    public ConfigurationManager() {


        this.configurationParser = new ConfigurationParser();
        DynamicParser dynamicParser = new DynamicParser();

        //cargo configuracion
        try {
            configurationParser.parse(CONFIGURATION_FILE_NAME);
        } catch (IOException e) {

        } catch (Exception e) {

        }

        //cargo valores dinamicos de las particulas
        try {
            dynamicParser.parse(configurationParser.getDynamicFileName(), configurationParser.getParticleRadius());
            this.particles = dynamicParser.getParticles();
        } catch (IOException e) {
            DynamicFileGenerator d = new DynamicFileGenerator();
            d.generateDymanicFile(configurationParser.getSeed(), configurationParser.getNumberParticles(), configurationParser.getBoardSide(), configurationParser.getParticleRadius());
            this.particles = d.getParticles();
        }
    }

    public ConfigurationParser getConfigurationParser() {
        return configurationParser;
    }

    public List<Particle> getParticles() {
        return particles;
    }
}
