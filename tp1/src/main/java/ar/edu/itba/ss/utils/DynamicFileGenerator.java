package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Particle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DynamicFileGenerator {

    private List<Particle> particles;

    public DynamicFileGenerator() {
        this.particles = new ArrayList<>();
    }

    public void generateDymanicFile(int seed, int numberParticles, double side) {
        try(FileWriter fw = new FileWriter("dynamic-" + seed + "-" + numberParticles + ".txt", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            Random r = new Random(seed);

            double auxX, auxY;
            for(int i = 0; i < numberParticles ; i++) {
                auxX = r.nextDouble() + r.nextInt((int)side);
                auxY = r.nextDouble() + r.nextInt((int)side);
                out.println(auxX + "," + auxY + ",0,0");
                particles.add(new Particle(i, auxX, auxY, 0, 0));
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }
}
