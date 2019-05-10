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



    public void calculateForce(Board2 b, int type) {
        for (Particle p : b.particles) {
            Set<Particle> list = b.neighbours.get(p);
            Pair<Double, Double> f = calculateForce(b, p, list, b.e, b.rm);
            if(type == 1) {
                p.setNewax(f.getKey() / p.getM());
                p.setNeway(f.getValue() / p.getM());
            } else if(type == 0) {
                p.setAx(f.getKey() / p.getM());
                p.setAy(f.getValue() / p.getM());
            }
        }
    }


    private Pair<Double,Double> calculateForce(Board2 b, Particle p, Set<Particle> list, double e, double rm) {
        double fx = 0, fy = -p.getM()*b.g;
        //calculo con los vecines
        if (list != null) {
            for (Particle p2 : list) {
                Pair<Double, Double> f = getForceAgainstParticle(p, p2, e, rm);
                fx += f.getKey();
                fy += f.getValue();
            }
        }

        //calculo las paredes
        if (p.getY() <= p.getR()) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, p.getX(), 0, p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();
        } else if (b.height - p.getY() <= p.getR()) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, p.getX(), b.height, p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();
        }
        //calculo las paredes
        if (p.getX() <= p.getR()) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, 0, p.getY(), p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();
        } else if (b.width - p.x <= p.r) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, b.width, p.getY(), p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();

        } else if (( p.y <= p.r) &&
                !(p.x > (b.width  - b.window)/2
                        && p.x < (b.width + b.window)/2)) {
//        } else if ((Math.abs(b.getWidth() / 2 - p.getX()) <= p.getR()) &&
//                !(p.getY() > (b.getHeight() / 2 - 5)
//                        && p.getY() < (b.getHeight() / 2 + 5))) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, b.width / 2, p.getY(), p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();
        }

        return new Pair<>(fx,fy);
    }

    private Pair<Double, Double> getForceAgainstParticle(Particle p1, Particle p2, double e, double rm) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double r = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy,2));

        double a = 12.0 / rm;
        double b = rm / r;

        double f = a * e * (Math.pow(b, 13) - Math.pow(b, 7));


        double fx = f * dx/r;
        double fy = f * dy/r;

        return new Pair<>(fx, fy);
    }


    private Pair<Double, Double> getForceAgainstParticle2(Particle p1, Particle p2, double e, double rm, Board2 b) {
        double ri = Math.hypot(p1.x,p1.y);
        double rj = Math.hypot(p2.x,p2.y);

        double eps=p1.r+p2.r-Math.abs(rj - ri );

        double fn = - b.kn * eps;
        double ft = - b.kt * eps; //TODO MAGIA, falta completar

        double ex = (p2.x - p1.x ) / Math.abs(rj - ri );
        double ey = (p2.y - p1.y ) / Math.abs(rj - ri );

        double fx = fn * ex + ft * (-ey);
        double fy = fn * ey + ft * ex;

        return new Pair<>(fx,fy);

    }


    public void calculateNewPosition(Board2 b) {
        for (Particle p : b.particles) {
            velvet(p);
        }
        b.reset();
//        calculateNeighbours(b);
        b.rearrangeNeighbours();
        calculateForce(b, 0);
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
        calculateForce(b, 0);
        for(Particle p : b.particles) {
            p.setVx(p.getVx() + dt*p.getAx());
            p.setVy(p.getVy() + dt*p.getAy());
            p.setX(p.getX() + dt*p.getVx() + Math.pow(dt,2)*p.getAx()/2);
            p.setY(p.getY() + dt*p.getVy() + Math.pow(dt,2)*p.getAy()/2);
        }
        b.reset();
        b.rearrangeNeighbours();
        calculateForce(b, 0);
        double t = dt;
        int frame = 1;
        int tImp = 1;
        while(t < tt) {

            calculateNewPosition(b);

            if(tImp % ti == 0) {
                dfg.saveDynamicFile(b, frame);
                frame++;
            }

            tImp++;
            t += dt;
        }
    }



}