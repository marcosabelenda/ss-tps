package ar.edu.itba.ss.LennardJones;

import java.util.Collection;

public class BeemanThreadOne extends Thread {
    private Collection<Particle> particles;
    private double dt;

    public BeemanThreadOne(Collection<Particle> p, double dt){
        this.particles = p;
        this.dt = dt;
    }

    @Override
    public void run() {
        for(Particle p: particles)
            beemanFirstPart(p);
    }
    private void beemanFirstPart(Particle p) {
        double x = p.getX() + p.getVx() * dt + ((2.0 / 3.0) * p.getAx() - (1.0 / 6.0) * p.getOldax()) * Math.pow(dt, 2);
        p.setX(x);
        double y = p.getY() + p.getVy() * dt + ((2.0 / 3.0) * p.getAy() - (1.0 / 6.0) * p.getOlday()) * Math.pow(dt, 2);
        p.setY(y);
    }

}

