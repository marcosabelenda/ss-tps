package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Board;
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

    public void generateDymanicFile(int seed, int numberParticles, double side, double v) {
        try(FileWriter fw = new FileWriter("frame-0.xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            Random r = new Random(seed);
            out.println(numberParticles);
            out.println("");

            double auxX, auxY, auxAngle;
            for(int i = 0; i < numberParticles ; i++) {
                auxX = r.nextDouble() + r.nextInt((int)side);
                auxY = r.nextDouble() + r.nextInt((int)side);
                auxAngle = Math.toRadians(r.nextDouble() + r.nextInt(360));
                out.println(auxX + " " + auxY + " " + auxAngle);
                particles.add(new Particle(i, auxX, auxY, v, auxAngle));
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDynamicFile(Board b, int frame) {
        try(FileWriter fw = new FileWriter("frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(b.getNumberParticles());
            out.println("");
            for(Particle p : b.getParticles()) {
                out.println(p.getX() + " " + p.getY() + " " + p.getAngle());
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
