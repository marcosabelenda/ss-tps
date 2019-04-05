package ar.edu.itba.ss.Model;


import com.sun.xml.internal.ws.model.ParameterImpl;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Space {
    private double L; //tamanio
    private int N; //cantidad de particulas

    private LinkedList<Collision> collisions;
    private List<Particle> particles;

    private HashMap<Pair<Integer,Integer>, Collision> matrix;
    private HashMap<Integer,Collision> wallColX;
    private HashMap<Integer,Collision> wallColY;

    private Collision nextCollision = null;

    public Space(double L,List<Particle> particles){
        this.L = L;
        this.particles = particles;
        this.N = particles.size();
        this.collisions = new LinkedList<>();

        initialize();
        getNextCollision();

        matrix = new HashMap<>();
        wallColX = new HashMap<>(N);
        wallColY = new HashMap<>(N);

        //cargo a las estructuras auxiliares
        for(Collision c: collisions){
           if(c.getP2()!=null){
               matrix.put(new Pair<>(c.getP1().getId(),c.getP2().getId()), c);
           }else{
               if(c.getWall() == Collision.HORIZONTAL){
                   wallColX.put(c.getP1().getId(),c);
               }else{
                   wallColY.put(c.getP1().getId(),c);
               }
           }
        }
    }

    private void getNextCollision(){
        Collision candidate = null;
        double bestTime = Double.POSITIVE_INFINITY;

        for(Collision c: collisions){
            if(c.getTime()<bestTime){
                bestTime = c.getTime();
                candidate = c;
            }
        }
        this.nextCollision = candidate;
    }



    public void advance(){
        System.out.println(nextCollision.getP1().getId()+" aaa "+nextCollision.getP2());
        double t = nextCollision.getTime();

        for(Particle p : this.particles) {
            p.setX(p.getX()+p.getVx()*t);
            p.setY(p.getY()+p.getVy()*t);
            if(p.getY()<0 && p.getX()<0){
                System.out.print("Error");
            }
        }


        for(Collision c : this.collisions) {
            c.setTime(c.getTime()-t);
        }

        collide(nextCollision);
        updateCollision(nextCollision.getP1());
        if(nextCollision.getP2()!=null)
            updateCollision(nextCollision.getP2());
        getNextCollision();
        //calcular nuevas coliciones con p1,p2

    }

    private void updateCollision(Particle p1){
        for(int i=0; i < N; i ++){
            if(p1.getId()==i)
                continue;
            Particle p2 = particles.get(i);
            getCollision(p1,p2).setTime(getTimeOfCollision(p1,p2));
        }

        if (p1.getVx() > 0) {
            this.wallColX.get(p1.getId()).setTime((this.L - p1.getR() - p1.getX()) /Math.abs(p1.getVx()));
        } else if (p1.getVx() < 0) {
            this.wallColX.get(p1.getId()).setTime((p1.getX() - p1.getR() ) /Math.abs(p1.getVx()));
        }
        if (p1.getVy() > 0) {
            this.wallColY.get(p1.getId()).setTime((this.L - p1.getR() - p1.getY()) / Math.abs(p1.getVy()));

        } else if (p1.getVy() < 0) {
            this.wallColY.get(p1.getId()).setTime((p1.getY() - p1.getR()) / Math.abs(p1.getVy()));
        }

    }

    private Collision getCollision(Particle p1, Particle p2){
        int p1id=p1.getId(),p2id=p2.getId();
        if(p1id>p2id)
            return matrix.get(new Pair<>(p2id,p1id));
        else
            return matrix.get(new Pair<>(p1id,p2id));
    }

    private void initialize(){
            int size = this.N;
            double dr2, dv2, dvdr, d;
            Particle p1, p2;
            for(int i = 0 ; i < size ; i++) {
                p1 = this.particles.get(i);
                //veo primero los casos de las paredes
                float time = 0;
                if (p1.getVx() > 0) {
                    this.collisions.add(
                            new Collision(p1, ((this.L - p1.getR() - p1.getX()) / Math.abs(p1.getVx())), Collision.HORIZONTAL)
                    );
                } else if (p1.getVx() < 0) {
                    this.collisions.add(
                            new Collision(p1, ((p1.getX() - p1.getR()) / Math.abs(p1.getVx())), Collision.HORIZONTAL)
                    );
                }
                if (p1.getVy() > 0) {
                    this.collisions.add(
                            new Collision(p1, ((this.L - p1.getR() - p1.getY()) / Math.abs(p1.getVy())), Collision.VERTICAL)
                    );
                } else if (p1.getVy() < 0) {
                    this.collisions.add(
                            new Collision(p1, (( p1.getY() - p1.getR()) / Math.abs(p1.getVy())), Collision.VERTICAL)
                    );
                }


                for (int j = i + 1; j < size; j++) {
                    p2 = this.particles.get(j);
//                    dr2 = calcdR2(p1, p2);
//                    dv2 = calcdV2(p1, p2);
//                    dvdr = calcdVdR(p1, p2);
//                    d = calcD(dr2, dv2, dvdr, p1.getR() + p2.getR());
//                    if (dr2 < 0 && d >= 0) {
//                        this.collisions.add(new Collision(p1, p2, -((dvdr + Math.sqrt(d)) / dv2)));
//                    } else {
//                        this.collisions.add(new Collision(p1, p2, Double.POSITIVE_INFINITY));
//                    }
                    this.collisions.add(new Collision(p1, p2, getTimeOfCollision(p1,p2)));
                }
            }
    }

    private double getTimeOfCollision(Particle p1, Particle p2){
        double dr2, dv2, dvdr, d;
        dr2 = calcdR2(p1, p2);
        dv2 = calcdV2(p1, p2);
        dvdr = calcdVdR(p1, p2);
        d = calcD(dr2, dv2, dvdr, p1.getR() + p2.getR());
        if(dr2 < 0 && d >= 0){
            return -((dvdr + Math.sqrt(d)) / dv2);
        }else{
            return Double.POSITIVE_INFINITY;
        }
    }

    public void collide(Collision c) {
        if(c.getP2() != null) {
            double j = calcJ(c.getP1(), c.getP2());
            double jx = calcJx(c.getP1(), c.getP1(),j);
            double jy = calcJy(c.getP1(), c.getP2(), j);
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


    private double calcdR2(Particle p1, Particle p2) {
        return Math.pow(p1.getX()-p2.getX(),2) + Math.pow(p1.getY() - p2.getY(),2);
    }

    private double calcdV2(Particle p1, Particle p2) {
        return Math.pow(p1.getVx() - p2.getVx(), 2) + Math.pow(p1.getVy() - p2.getVy(), 2);
    }

    private double calcdVdR(Particle p1, Particle p2) {
        return Math.pow(p1.getVx()*p2.getX(), 2) + Math.pow(p1.getVy()*p1.getY(), 2);
    }

    private double calcD(double dR2, double dV2, double dVdR, double omega) {
        return Math.pow(dVdR, 2) - dV2*(dR2 - Math.pow(omega, 2));
    }

    private double calcJ(Particle p1, Particle p2) {
        return (2*p1.getMass()*p2.getMass()* calcdVdR(p1,p2))/((p1.getR()+p2.getR())*(p1.getMass()+p2.getMass()));
    }

    private double calcJx(Particle p1, Particle p2, double j){
        return j*(p1.getX()-p2.getX())/(p1.getR()+p2.getR());
    }

    private double calcJy(Particle p1, Particle p2, double j){
        return j*(p1.getY()-p2.getY())/(p1.getR()+p2.getR());
    }


    public double getL() {
        return L;
    }
    public int getN() {
        return N;
    }

    public LinkedList<Collision> getCollisions() {
        return collisions;
    }

    public List<Particle> getParticles() {
        return particles;
    }


}
