package ar.edu.itba.ss.MetricAlgoritms;

import ar.edu.itba.ss.Model.Collision;
import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.Model.Space;

public class Brownie {
//
//
//    public void calculateCollisionsTime(Space space){
//        int size = space.getParticles().size();
//        double dr2, dv2, dvdr, d;
//        Particle p1, p2;
//        for(int i = 0 ; i < size ; i++) {
//            p1 = space.getParticles().get(i);
//            //veo primero los casos de las paredes
//            if(p1.getVx() > 0) {
//                space.getCollisions().add(
//                        new Collision(p1,((space.getL()-p1.getR()-p1.getX())/p1.getVx()), Collision.HORIZONTAL) //TODO FIX
//                );
//            } else if(p1.getVx() < 0) {
//                space.getCollisions().add(
//                        new Collision(p1,((p1.getR()-p1.getX())/p1.getVx()), Collision.HORIZONTAL) //TODO FIX
//                );
//            }
//            if(p1.getVy() > 0) {
//                space.getCollisions().add(
//                        new Collision(p1,((space.getL()-p1.getR()-p1.getY())/p1.getVy()), Collision.VERTICAL) //TODO FIX
//                );
//            } else if(p1.getVy() < 0) {
//                space.getCollisions().add(
//                        new Collision(p1,((p1.getR()-p1.getY())/p1.getVy()), Collision.VERTICAL) //TODO FIX
//                 );
//            }
//
//            for(int j = i+1 ; j < size ; j++) {
//                p2 = space.getParticles().get(j);
//                dr2 = calcdR2(p1,p2);
//                dv2 = calcdV2(p1, p2);
//                dvdr = calcdVdR(p1, p2);
//                d = calcD(dr2, dv2, dvdr, p1.getR() + p2.getR());
//                if(willCollide(dr2, d)) {
//                    space.getCollisions().add(new Collision(p1, p2,-((dvdr + Math.sqrt(d))/dv2)));
//                } else {
//                    space.getCollisions().add(new Collision(p1, p2,Double.POSITIVE_INFINITY));
//                }
//            }
//        }
//    }




//
//    public double calcdR2(Particle p1, Particle p2) {
//        return Math.pow(p1.getX()-p2.getX(),2) + Math.pow(p1.getY() - p2.getY(),2);
//    }
//
//    public double calcdV2(Particle p1, Particle p2) {
//        return Math.pow(p1.getVx() - p2.getVx(), 2) + Math.pow(p1.getVy() - p2.getVy(), 2);
//    }
//
//    public double calcdVdR(Particle p1, Particle p2) {
//        return Math.pow(p1.getVx()*p2.getX(), 2) + Math.pow(p1.getVy()*p1.getY(), 2);
//    }
//
//    public double calcD(double dR2, double dV2, double dVdR, double omega) {
//        return Math.pow(dVdR, 2) - dV2*(dR2 - Math.pow(omega, 2));
//    }
//
//    public boolean willCollide(double dR2, double d) {
//        return dR2 < 0 && d >= 0;
//    }
//
//    private double calcJ(Particle p1, Particle p2) {
//        return (2*p1.getMass()*p2.getMass()* calcdVdR(p1,p2))/((p1.getR()+p2.getR())*(p1.getMass()+p2.getMass()));
//    }
//
//    private double calcJx(Particle p1, Particle p2, double j){
//        return j*(p1.getX()-p2.getX())/(p1.getR()+p2.getR());
//    }
//
//    private double calcJy(Particle p1, Particle p2, double j){
//        return j*(p1.getY()-p2.getY())/(p1.getR()+p2.getR());
//    }
//
//    public Collision getNextCollision(Space space) {
//        Collision nextC = null;
//        boolean f = true;
//        for(Collision c : space.getCollisions()) {
//            if(f) {
//               nextC = c;
//               f = false;
//            } else {
//                if(nextC.getTime() > c.getTime()){
//                    nextC = c;
//                }
//            }
//        }
//        return nextC;
//    }
//
//
//
//    public void collide(Collision c) {
//        if(c.getP2() != null) {
//            double j = calcJ(c.getP1(), c.getP2());
//            double jx = calcJx(c.getP1(), c.getP1(),j);
//            double jy = calcJy(c.getP1(), c.getP2(), j);
//            c.getP1().setVx(c.getP1().getVx() + jx*c.getP1().getMass());
//            c.getP1().setVy(c.getP1().getVy() + jy*c.getP1().getMass());
//            c.getP2().setVx(c.getP2().getVx() - jx*c.getP2().getMass());
//            c.getP1().setVy(c.getP1().getVy() - jy*c.getP2().getMass());
//        } else {
//            if(c.getWall() == Collision.VERTICAL) {
//                c.getP1().setVy(-1 * c.getP1().getVy());
//            } else {
//                c.getP1().setVx(-1 * c.getP1().getVx());
//            }
//        }
//    }
}
