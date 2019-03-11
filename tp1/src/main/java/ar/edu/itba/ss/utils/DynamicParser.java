package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Particle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicParser {

    private final static String VALUE_SEPARATOR = ",";
    private final static int X = 0, Y = 1, VX = 2, VY = 3;

    private List<Particle> particles;

    public DynamicParser() {
        this.particles = new ArrayList<>();
    }

    public void parse(String fileName) throws IOException {

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        int index = 0;
        while((line = bufferedReader.readLine()) != null) {
            String [] args = line.split(VALUE_SEPARATOR);
            particles.add(new Particle(index,Double.parseDouble(args[X]),Double.parseDouble(args[Y]), Double.parseDouble(args[VX]), Double.parseDouble(args[VY])));
            index++;
        }
        bufferedReader.close();

    }

    public List<Particle> getParticles() {
        return particles;
    }
}
