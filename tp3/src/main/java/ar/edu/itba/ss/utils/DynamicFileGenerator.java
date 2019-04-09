package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.Model.Space;

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
                                    double m1, double m2,
                                    double r1, double r2) {
        try(FileWriter fw = new FileWriter("frame-0.xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            Random r = new Random(seed);
            out.println(numberParticles);
            out.println("");

            double auxX, auxY, auxAngle, auxV;
            for(int i = 0; i < numberParticles ; i++) {
                if(i==0){
                    Particle bigOne = new Particle(i, side/2, side/2, 0, 0, m2, r2);
                    out.println(side/2 + " " + side/2 + " " + 0 + " " + 0 + " " + 0 + " " + r2);
                    particles.add(bigOne);
                } else {
                    while(true){
                        auxX = r1 + r.nextDouble() * (side-2*r1);
                        auxY = r1 + r.nextDouble() * (side-2*r1);
                        boolean f = true;
                        for(Particle p: particles){
                            if(Math.pow(auxX - p.getX(),2) + Math.pow(auxY - p.getY(), 2) <=  Math.pow(r1 + p.getR(), 2)){
                                f = false;
                                break;
                            }
                        }

                        if(f) {
                            break;
                        }
                    }

                    auxV = r.nextDouble() * v; //TODO MAGIC NUMBER
                    auxAngle = Math.toRadians(r.nextDouble() + r.nextInt(360));
                    out.println(auxX + " " + auxY + " " + Math.cos(auxAngle) * auxV + " " + Math.sin(auxAngle) * auxV + " " + auxAngle + " " + r1);
                    particles.add(new Particle(i, auxX, auxY, Math.cos(auxAngle)*auxV, Math.sin(auxAngle)*auxV,m1,r1));
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void saveDynamicFile(Space space, int frame) {
        try(FileWriter fw = new FileWriter("frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(space.getN());
            out.println("");
            for(Particle p : space.getParticles()) {
                out.println(p.getX() + " " + p.getY() + " " + p.getVx() + " " + p.getVy()  + " " + '0' + " " + p.getR());

            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
