package ar.edu.itba.ss.LennardJones;

import java.util.Collection;

public class BeemanThreadTwo extends Thread {
    private Collection<Particle> particles;
    private double dt;

    public BeemanThreadTwo(Collection<Particle> p, double dt){
        this.particles = p;
        this.dt = dt;
    }

    @Override
    public void run() {
        for(Particle p: particles)
            beemanSeconfPart(p);
    }


    private void beemanSeconfPart(Particle p) {
        double vx = p.getVx() + ( (1.0 / 3.0) * p.getNewax() + (5.0 / 6.0) * p.getAx()  - (1.0 / 6.0) * p.getOldax()) * dt;
        p.setVx(vx);
        double vy = p.getVy() + ( (1.0 / 3.0) * p.getNeway() + (5.0 / 6.0) * p.getAy()  - (1.0 / 6.0) * p.getOlday()) * dt;
        p.setVy(vy);
        p.setOldax(p.getAx());
        p.setOlday(p.getAy());
        p.setAx(p.getNewax());
        p.setAy(p.getNeway());
    }
}

