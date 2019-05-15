package ar.edu.itba.ss.LennardJones;

import javafx.util.Pair;

import java.util.Set;

public class LennardJones {

    private double dt;
    private double tt;
    private double ti;
    private DynamicFileGenerator dfg;

    public LennardJones(double dt, double tt, double ti, DynamicFileGenerator dfg) {
        this.dt = dt;
        this.tt = tt;
        this.ti = ti;
        this.dfg = dfg;
    }

    public void calculateForce(Board2 b) {
        for (Particle p : b.particles) {
            Set<Particle> list = b.neighbours.get(p);
            Pair<Double, Double> f = calculateForce(b, p, list);
            p.setAx(f.getKey() / p.getM());
            p.setAy(f.getValue() / p.getM());
        }
    }

    private Pair<Double,Double> calculateForce(Board2 b, Particle p, Set<Particle> list) {
        double fx = 0, fy = -p.getM()*b.g;
        //calculo con los vecines
        if (list != null) {
            for (Particle p2 : list) {
                Pair<Double, Double> f = getForceAgainstParticle(p, p2, b);
                fx += f.getKey();
                fy += f.getValue();
            }
        }

        //calculo f contra las paredes laterales
        if(p.getX() <= p.getR()) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, -p.getR(), p.getY(), p.getR(), 0, 0, 0, 0, 0), b);
            fx += f.getKey();
            fy += f.getValue();
        } else if(p.getX() >= (b.width-p.getR())) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, b.width+p.getR(), p.getY(), p.getR(), 0, 0, 0, 0, 0), b);
            fx += f.getKey();
            fy += f.getValue();
        }

        //calculo f contra las paredes horizaontales
        if(p.getY() >= (b.height-p.getR())) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, p.getX(), b.height-p.getR(), p.getR(), 0, 0, 0, 0, 0), b);
            fx += f.getKey();
            fy += f.getValue();
        } else if((p.getY() <= (b.height/2+p.getR()) && p.getY() >= (b.height/2))
                &&
                (p.getX() < (b.width-b.window)/2 || p.getX() > (b.width+b.window)/2)) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, p.getX(), b.height/2-p.getR(), p.getR(), 0, 0, 0, 0, 0), b);
            fx += f.getKey();
            fy += f.getValue();
        }
        //TODO ver vertices de la ventana

        return new Pair<>(fx,fy);
    }

    private Pair<Double, Double> getForceAgainstParticle(Particle p1, Particle p2, Board2 b) {
        //double ri = Math.hypot(p1.x,p1.y);
        //double rj = Math.hypot(p2.x,p2.y);

        double d = Math.hypot(p1.x - p2.x, p1.y - p2.y);

        double eps=p1.r+p2.r-d;

        double ex = (p2.x - p1.x ) / d;
        double ey = (p2.y - p1.y ) / d;

        double fn = - b.kn * eps;
        double ft = - b.kt * eps * ((p1.vx - p2.vx) * (-ey) + (p1.vy - p2.vy) * ex);

        double fx = fn * ex + ft * (-ey);
        double fy = fn * ey + ft * ex;

        return new Pair<>(fx,fy);

    }


    public void calculateNewPosition(Board2 b) {
        for (Particle p : b.particles) {
            velvet(p);
        }
        b.reset();
        b.rearrangeNeighbours();
        calculateForce(b);

    }

    private void velvet(Particle p) {
        double x = 2 * p.getX() - p.getPrevx() + p.getAx() * Math.pow(dt, 2);
        double y = 2 * p.getY() - p.getPrevy() + p.getAy() * Math.pow(dt, 2);

        double vx = (x - p.getPrevx())/(dt*2);
        double vy = (y - p.getPrevy())/(dt*2);

        p.setX(x);
        p.setY(y);
        p.setVx(vx);
        p.setVy(vy);

    }


    private void eulerInitialStep(Board2 b) {

        for(Particle p : b.particles) {
            double x = p.getX() + dt*p.getVx() + (Math.pow(dt,2)/2)*p.getAx();
            double vx = p.getVx() + dt*p.getAx();
            double y = p.getY() + dt*p.getVy() + (Math.pow(dt,2)/2)*p.getAy();
            double vy = p.getVy() + dt*p.getAy();

            p.setX(x);
            p.setY(y);
            p.setVx(vx);
            p.setVy(vy);

            p.setOldax(p.getAx());
        }
    }

    public void run(Board2 b) throws InterruptedException {
        b.rearrangeNeighbours();
        calculateForce(b);
        for(Particle p : b.particles) {
            p.setVx(p.getVx() + dt*p.getAx());
            p.setVy(p.getVy() + dt*p.getAy());
            p.setX(p.getX() + dt*p.getVx() + Math.pow(dt,2)*p.getAx()/2);
            p.setY(p.getY() + dt*p.getVy() + Math.pow(dt,2)*p.getAy()/2);
        }
        b.reset();
        b.rearrangeNeighbours();
        calculateForce(b);
        double t = dt;
        int frame = 1;
        int tImp = 1;
        while(t < tt) {

            calculateNewPosition(b);

            if(tImp % ti == 0) {
                dfg.saveDynamicFile(b, frame);
                frame++;
                /*
                double aux = 0;
                for(Particle p : b.particles) {
                    aux += Math.hypot(p.getVx(),p.getVy());
                }
                System.out.println(aux);
                */
            }

            tImp++;
            t += dt;
        }
    }

}