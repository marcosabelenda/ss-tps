package ar.edu.itba.ss.Model;


import java.util.*;

public class Space {
    private double L; //tamanio
    private int N; //cantidad de particulas

    private LinkedList<Collision> collisions;
    private List<Particle> particles;

    private ArrayList<ArrayList<Collision>> matrix;
    private ArrayList<Collision> wallColX;

    private ArrayList<Collision> wallColY;




    public Space(double L,List<Particle> particles){
        this.L = L;
        this.particles = particles;
        this.N = particles.size();
        this.collisions = new LinkedList<>();

        initialize();

        // wally
        wallColX = new ArrayList<>(N);
        for(int i=0; i<N; i++){
            wallColX.add(i, null);
        }
        wallColY = new ArrayList<>(N);
        for(int i=0; i<N; i++){
            wallColY.add(i, null);
        }

        //armo la matriz
        matrix = new ArrayList<> (N);
         for(int i=0; i<N; i++){
            matrix.add(i, new ArrayList<Collision>(N));
             for(int j=0; j<N; j++){
                 matrix.get(i).add(j,null);
             }
        }
        for(Collision c: collisions){
           if(c.getP2()!=null){
             matrix.get(c.getP1().getId()).add(c.getP2().getId(),c);
           }else{
               if(c.getWall() == Collision.HORIZONTAL){
                   wallColX.add(c.getP1().getId(),c);
               }else{
                   wallColY.add(c.getP1().getId(),c);
               }
           }
        }
    }

    private void goCollision(double t) { //TODO GO GO POWER RANGERS
        for(Particle p : this.particles) {
            p.setX(p.getVx()*t);
            p.setY(p.getVy()*t);
        }
    }

    public void calculateCollisionsTime2(double t){
        //borrar coliciones que esten con p1,p2

        //avanzar tiempo coliciones
        for(Collision c : this.collisions) {
            c.setTime(c.getTime()-t);
        }
        goCollision(t);
        //calcular nuevas coliciones con p1,p2

    }

    private void initialize(){
            int size = this.N;
            double dr2, dv2, dvdr, d;
            Particle p1, p2;
            for(int i = 0 ; i < size ; i++) {
                p1 = this.particles.get(i);
                //veo primero los casos de las paredes
                if (p1.getVx() > 0) {
                    this.collisions.add(
                            new Collision(p1, ((this.L - p1.getR() - p1.getX()) / p1.getVx()), Collision.HORIZONTAL) //TODO FIX
                    );
                } else if (p1.getVx() < 0) {
                    this.collisions.add(
                            new Collision(p1, ((p1.getR() - p1.getX()) / p1.getVx()), Collision.HORIZONTAL) //TODO FIX
                    );
                }
                if (p1.getVy() > 0) {
                    this.collisions.add(
                            new Collision(p1, ((this.L - p1.getR() - p1.getY()) / p1.getVy()), Collision.VERTICAL) //TODO FIX
                    );
                } else if (p1.getVy() < 0) {
                    this.collisions.add(
                            new Collision(p1, ((p1.getR() - p1.getY()) / p1.getVy()), Collision.VERTICAL) //TODO FIX
                    );
                }

                for (int j = i + 1; j < size; j++) {
                    p2 = this.particles.get(j);
                    dr2 = calcdR2(p1, p2);
                    dv2 = calcdV2(p1, p2);
                    dvdr = calcdVdR(p1, p2);
                    d = calcD(dr2, dv2, dvdr, p1.getR() + p2.getR());
                    if (willCollide(dr2, d)) {
                        this.collisions.add(new Collision(p1, p2, -((dvdr + Math.sqrt(d)) / dv2)));
                    } else {
                        this.collisions.add(new Collision(p1, p2, Double.POSITIVE_INFINITY));
                    }
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

    public void collide(Collision c) { //TODO USAR
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


    public double calcdR2(Particle p1, Particle p2) {
        return Math.pow(p1.getX()-p2.getX(),2) + Math.pow(p1.getY() - p2.getY(),2);
    }

    public double calcdV2(Particle p1, Particle p2) {
        return Math.pow(p1.getVx() - p2.getVx(), 2) + Math.pow(p1.getVy() - p2.getVy(), 2);
    }

    public double calcdVdR(Particle p1, Particle p2) {
        return Math.pow(p1.getVx()*p2.getX(), 2) + Math.pow(p1.getVy()*p1.getY(), 2);
    }

    public double calcD(double dR2, double dV2, double dVdR, double omega) {
        return Math.pow(dVdR, 2) - dV2*(dR2 - Math.pow(omega, 2));
    }

    public boolean willCollide(double dR2, double d) {
        return dR2 < 0 && d >= 0;
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
