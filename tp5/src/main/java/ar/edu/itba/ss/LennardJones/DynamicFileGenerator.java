package ar.edu.itba.ss.LennardJones;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class    DynamicFileGenerator {

    private List<Particle> particles;

    public DynamicFileGenerator() {
        this.particles = new ArrayList<>();
    }

    public void generateDymanicFile(int seed, double m, double height, double width, double minR, double maxR) {
        Random ran = new Random(seed);
        double auxX, auxY, auxR;

        int intentosTotales = 5000;
        int intentos = 0;
        int id = 0;
        while(intentos < intentosTotales) {
            auxR = minR + ran.nextDouble() * (maxR-minR);
            auxX = auxR + ran.nextDouble() * (width-2*auxR);
            auxY = ((height / 2) + auxR) + + ran.nextDouble() * ((height / 2) - 2 * auxR);
            boolean posCorrecta = true;
            for(Particle p: particles){
                if(Math.pow(auxX - p.getX(),2) + Math.pow(auxY - p.getY(), 2) <=  Math.pow(auxR + p.getR(), 2)){
                    posCorrecta = false;
                    break;
                }
            }

            if(posCorrecta) {
                particles.add(new Particle(id, auxX, auxY, auxR, m, 0, 0, 0, 0));
                id++;
                intentos = 0;
            } else {
                intentos++;
            }

        }
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void saveDynamicFile(Board2 b, int frame) {
        try(FileWriter fw = new FileWriter("frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(b.particles.size());
            out.println("");
            for(Particle p : b.particles) {

                out.println(p.getX() + " " + p.getY() + " " + p.getVx() + " " + p.getVy()  + " " + p.getR() +  " " + p.getPresion() + " " + p.getKinetic());

            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
