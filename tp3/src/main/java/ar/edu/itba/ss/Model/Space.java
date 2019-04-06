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
    private HashMap<Pair<Integer,Integer>,Collision> partCol;

    private Collision nextCollision = null;

    public Space(double L,List<Particle> particles){
        this.L = L;
        this.particles = particles;
        this.N = particles.size();
        this.collisions = new LinkedList<>(); //TODAS las colisiones

        initialize();

        matrix = new HashMap<>();
        wallColX = new HashMap<>(N); //colisiones ocn pared
        wallColY = new HashMap<>(N);
        partCol = new HashMap<>(); //colisiones con particulas que no sean infitrno

        //cargo a las estructuras auxiliares
        for(Collision c: collisions){
           if(c.getP2()!=null){
               if(Double.POSITIVE_INFINITY!=c.getTime()){
                   partCol.put(new Pair<>(c.getP1().getId(),c.getP2().getId()),c);
               }
               matrix.put(new Pair<>(c.getP1().getId(),c.getP2().getId()), c);
           }else{
               if(c.getWall() == Collision.HORIZONTAL){
                   wallColX.put(c.getP1().getId(),c);
               }else{
                   wallColY.put(c.getP1().getId(),c);
               }
           }
        }
        getNextCollision2();

    }

    private void getNextCollision(){
        Collision candidate = collisions.get(1);
        double bestTime = Double.POSITIVE_INFINITY;

        for(Collision c: collisions){
            if(c.getTime()<bestTime && c.getTime()>0.000000001){

                bestTime = c.getTime();
                candidate = c;
            }
        }
        this.nextCollision = candidate;
    }

    private void getNextCollision2(){ //version optimizada d ela original
        Collision candidate = collisions.get(1);
        double bestTime = Double.POSITIVE_INFINITY;

        for(Collision c: wallColX.values()){
            if(c.getTime()<bestTime && c.getTime()>0.000000001){

                bestTime = c.getTime();
                candidate = c;
            }
        }
        for(Collision c: wallColY.values()){
            if(c.getTime()<bestTime && c.getTime()>0.000000001){

                bestTime = c.getTime();
                candidate = c;
            }
        }
        for(Collision c: partCol.values()){
            if(c.getTime()<bestTime && c.getTime()>0.000000001){

                bestTime = c.getTime();
                candidate = c;
            }
        }
        this.nextCollision = candidate;
//        System.out.println(candidate);
    }


    public void advance(){
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
        getNextCollision2();

    }

    private void updateCollision(Particle p1){
        for(int i=0; i < N; i ++){
            if(p1.getId()==i)
                continue;
            Particle p2 = particles.get(i);
            double t = getTimeOfCollision2(p1,p2);
            Collision c = getCollision(p1,p2);
            if(t==Double.POSITIVE_INFINITY)
                partCol.remove(new Pair<>(c.getP1().getId(),c.getP2().getId()));
            else
                partCol.put(new Pair<>(c.getP1().getId(),c.getP2().getId()),c);
            c.setTime(t);
        }

        if (p1.getVx() > 0) {
            if(this.wallColX.get(p1.getId())==null){
                System.out.print(p1.getId());
            }
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

    private Collision getCollision(Particle p1, Particle p2) {
        int p1id=p1.getId(),p2id=p2.getId();
        if(p1id>p2id)
            return matrix.get(new Pair<>(p2id,p1id));
        else if(p1id==p2id)
            return null;
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
                } else {
                    this.collisions.add(
                            new Collision(p1, ((p1.getX() - p1.getR()) / Math.abs(p1.getVx())), Collision.HORIZONTAL)
                    );
                }
                if (p1.getVy() > 0) {
                    this.collisions.add(
                            new Collision(p1, ((this.L - p1.getR() - p1.getY()) / Math.abs(p1.getVy())), Collision.VERTICAL)
                    );
                } else  {
                    this.collisions.add(
                            new Collision(p1, (( p1.getY() - p1.getR()) / Math.abs(p1.getVy())), Collision.VERTICAL)
                    );
                }


                for (int j = i + 1; j < size; j++) {
                    p2 = this.particles.get(j);
                    this.collisions.add(new Collision(p1, p2, getTimeOfCollision2(p1,p2)));
                }

            }
    }

    private double getTimeOfCollision2(Particle p1, Particle p2){
        double dX = p2.getX() - p1.getX();
        double dY = p2.getY() - p1.getY();
        double dVx = p2.getVx() - p1.getVx();
        double dVy = p2.getVy() - p1.getVy();
        double dVdR = dVx*dX + dVy*dY;


        if (Double.compare(dVdR, 0) >= 0){
            return Double.POSITIVE_INFINITY;
        }

        double dVdV = Math.pow(dVx, 2) + Math.pow(dVy, 2);

        double dRdR = Math.pow(dX, 2) + Math.pow(dY, 2);

        double sigma = p1.getR() + p2.getR();


        double d = Math.pow(dVdR, 2) - dVdV * (dRdR - Math.pow(sigma ,2));
        if (Double.compare(d, 0) < 0){
            return Double.POSITIVE_INFINITY;
        }
        double ptc = (-1) * ((dVdR + Math.sqrt(d)) / dVdV);
        return ptc;
    }

    public void collide(Collision c) {
        if(c.getP2() != null) {
            Particle p1=c.getP1(), p2=c.getP2();
            double dX = p2.getX() - p1.getX();
            double dY = p2.getY() - p1.getY();
            double dVx = p2.getVx() - p1.getVx();
            double dVy = p2.getVy() - p1.getVy();

            double dVdR = dVx*dX + dVy*dY;
            double sigma = p1.getR() + p2.getR();

            double J = (2*p1.getMass()*p2.getMass()*dVdR) / (sigma * (p1.getMass() + p2.getMass()));
            double Jx = J * dX / sigma;
            double Jy = J * dY / sigma;

            p1.setVx(p1.getVx() + Jx/p1.getMass());
            p1.setVy(p1.getVy() + Jy/p1.getMass());

            p2.setVx(p2.getVx() - Jx/p2.getMass());
            p2.setVy(p2.getVy() - Jy/p2.getMass());
        } else {
            if(c.getWall() == Collision.VERTICAL) {
                c.getP1().setVy(-1 * c.getP1().getVy());
            } else {
                c.getP1().setVx(-1 * c.getP1().getVx());
            }
        }
    }



    private double calcJ(Particle p1, Particle p2) {
        double dX = p2.getX() - p1.getX();
        double dY = p2.getY() - p1.getY();
        double dVx = p2.getVx() - p1.getVx();
        double dVy = p2.getVy() - p1.getVy();
        double dVdR = dVx*dX + dVy*dY;
        return (2*p1.getMass()*p2.getMass()* dVdR)/((p1.getR()+p2.getR())*(p1.getMass()+p2.getMass()));
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
