package ar.edu.itba.ss.MetricAlgoritms;

import ar.edu.itba.ss.Model.Collision;
import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.Model.Space;
import javafx.util.Pair;

public class Brownie {


    public void calculateCollisionsTime(Space space){
        int size = space.getParticles().size();
        double dr2, dv2, dvdr, d;
        Particle p1, p2;
        for(int i = 0 ; i < size ; i++) {
            p1 = space.getParticles().get(i);
            if(p1.getVx() > 0) {
                space.getCollisions().add(new Collision(p1,((space.getL()-p1.getR()-p1.getX())/p1.getVx()), Collision.HORIZAONTAL));
            } else if(p1.getVx() < 0) {
                space.getCollisions().add(new Collision(p1,((p1.getR()-p1.getX())/p1.getVx()), Collision.HORIZAONTAL));
            }
            if(p1.getVy() > 0) {
                space.getCollisions().add(new Collision(p1,((space.getL()-p1.getR()-p1.getY())/p1.getVy()), Collision.VERTICAL));
            } else if(p1.getVy() < 0) {
                space.getCollisions().add(new Collision(p1,((p1.getR()-p1.getY())/p1.getVy()), Collision.VERTICAL));
            }

            for(int j = i+1 ; j < size ; j++) {
                p2 = space.getParticles().get(j);
                dr2 = calculatedR2(p1,p2);
                dv2 = calculatedV2(p1, p2);
                dvdr = calculatedVdR(p1, p2);
                d = calculateD(dr2, dv2, dvdr, p1.getR() + p2.getR());
                if(isCollision(dr2, d)) {
                    space.getCollisions().add(new Collision(p1, p2,-((dvdr + Math.sqrt(d))/dv2)));
                }
            }
        }
    }

    public void calculateCollisionsTime2(Space space, double t){
        //borrar coliciones que esten con p1,p2

        //avanzar tiempo coliciones
        for(Collision c : space.getCollisions()) {
            c.setTime(c.getTime()-t);
        }
        //calcular nuevas coliciones con p1,p2

    }


    public double calculatedR2(Particle p1, Particle p2) {
        return Math.pow(p1.getX()-p2.getX(),2) + Math.pow(p1.getY() - p2.getY(),2);
    }

    public double calculatedV2(Particle p1, Particle p2) {
        return Math.pow(p1.getVx() - p2.getVx(), 2) + Math.pow(p1.getVy() - p2.getVy(), 2);
    }

    public double calculatedVdR(Particle p1, Particle p2) {
        return Math.pow(p1.getVx()*p2.getX(), 2) + Math.pow(p1.getVy()*p1.getY(), 2);
    }

    public double calculateD(double dR2, double dV2, double dVdR, double omega) {
        return Math.pow(dVdR, 2) - dV2*(dR2 - Math.pow(omega, 2));
    }

    public boolean isCollision(double dR2, double d) {
        return dR2 < 0 && d >= 0;
    }

    private double calculateJ(Particle p1, Particle p2) {
        return (2*p1.getMass()*p2.getMass()*calculatedVdR(p1,p2))/((p1.getR()+p2.getR())*(p1.getMass()+p2.getMass()));
    }

    private double calculateJx(Particle p1, Particle p2, double j){
        return j*(p1.getX()-p2.getX())/(p1.getR()+p2.getR());
    }

    private double calculateJy(Particle p1, Particle p2, double j){
        return j*(p1.getY()-p2.getY())/(p1.getR()+p2.getR());
    }

    public Collision getNexCollicion(Space space) {
        Collision nextC = null;
        boolean f = true;
        for(Collision c : space.getCollisions()) {
            if(f) {
               nextC = c;
               f = false;
            } else {
                if(nextC.getTime() > c.getTime()){
                    nextC = c;
                }
            }
        }
        return nextC;
    }

    public void goCollision(Space space, double t) {
        for(Particle p : space.getParticles()) {
            p.setX(p.getVx()*t);
            p.setY(p.getVy()*t);
        }
    }

    public void collide(Collision c) {
        if(c.getP2() != null) {
            double j = calculateJ(c.getP1(), c.getP2());
            double jx = calculateJx(c.getP1(), c.getP1(),j);
            double jy = calculateJy(c.getP1(), c.getP2(), j);
            c.getP1().setVx(c.getP1().getVx() + jx*c.getP1().getMass());
            c.getP1().setVy(c.getP1().getVy() + jy*c.getP1().getMass());
            c.getP2().setVx(c.getP2().getVx() - jx*c.getP2().getMass());
            c.getP1().setVy(c.getP1().getVy() - jy*c.getP2().getMass());
        } else {
            if(c.getWall() == Collision.VERTICAL) {
                c.getP1().setVy(-1 * c.getP1().getVy());
            } else {
                c.getP1().setVx(-1 * c.getP1().getVx());
            }
        }
    }
}
