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

    public void generateDymanicFile(int seed, int numberParticles, double side, double v,
                                    double m, double r) {
        try(FileWriter fw = new FileWriter("frame-0.xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            Random ran = new Random(seed);
            out.println(numberParticles);
            out.println("");

            double auxX, auxY, auxAngle;
            for(int i = 0; i < numberParticles ; i++) {
                while(true){
                    auxX = r + ran.nextDouble() * (side-2*r);
                    auxY = r + ran.nextDouble() * (side-2*r);
                    boolean f = true;
                    for(Particle p: particles){
                        if(Math.pow(auxX - p.getX(),2) + Math.pow(auxY - p.getY(), 2) <=  Math.pow(r + p.getR(), 2)){
                            f = false;
                            break;
                        }
                    }

                    if(f) {
                        break;
                    }
                }

                auxAngle = Math.toRadians(ran.nextDouble() + ran.nextInt(360));
                out.println(auxX + " " + auxY + " " + Math.cos(auxAngle) * v + " " + Math.sin(auxAngle) * v + " " + r);
                particles.add(new Particle(i, auxX, auxY, r, m, Math.cos(auxAngle) * v, Math.sin(auxAngle) * v, 0, 0));
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void saveDynamicFile(Board b, int frame) {
        try(FileWriter fw = new FileWriter("frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(b.getParticles().size());
            out.println("");
            for(Particle p : b.getParticles()) {
                out.println(p.getX() + " " + p.getY() + " " + p.getVx() + " " + p.getVy()  + " " + p.getR());

            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
