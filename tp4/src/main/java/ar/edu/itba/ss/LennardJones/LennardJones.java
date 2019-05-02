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

    public void calculateNeighbours(Board b) {
        for (Cell c : b.getCells()) {
            int size = c.getParticles().size();
            for (int i = 0; i < size; i++) {
                calculateNeighbours(c.getParticles().get(i), i, b);
            }
        }
    }

    private void calculateNeighbours(Particle p, int particleIndex, Board b) {
        if (p == null || b == null) {
            return;
        }

        double auxX;
        double auxY;
        boolean flag = false;
        for (int i = 0; i <= 1; i++) {
            if (i + Math.floor(p.getY() / b.getCellSide()) >= b.getCantCellPerRow()) break;
            for (int j = -1; j <= 1; j++) {
                if (j + Math.floor(p.getX() / b.getCellSide()) >= b.getCantCellPerLine()) {
                    break;
                }
                if (!flag) {
                    j = 0;
                    flag = true;
                }
                boolean wFlag = false;
                if (((Math.floor(p.getX() / b.getCellSide()) - 1 == b.getCantCellPerLine() / 2 && j == 1) ||
                        (Math.floor(p.getX() / b.getCellSide()) == b.getCantCellPerLine() / 2 && j == -1)) &&
                        (p.getY() < (b.getHeight() / 2 - 5) || p.getY() > (b.getHeight() / 2 + 5))) {
                    if (i == 1 && p.getY() - (b.getHeight() / 2 + 5) > 0 && p.getY() - (b.getHeight() / 2 + 5) < 5) {
                        wFlag = false;
                    } else {
                        wFlag = true;
                    }
                }
                if (p.getX() + j * b.getCellSide() > 0) {
                    auxX = p.getX() + j * b.getCellSide();
                    auxY = p.getY() + i * b.getCellSide();

                    Cell c = b.getCells().get(b.getCellIndex(auxX, auxY));
                    if (i == 0 && j == 0) {
                        int size = c.getParticles().size();
                        for (int k = particleIndex + 1; k < size; k++) {
                            Particle auxP = c.getParticles().get(k);
                            if (b.areNeighbours(p, auxP)) {
                                b.setNeighbour(p, auxP);
                                b.setNeighbour(auxP, p);
                            }
                        }
                    } else {
                        if (!wFlag) {
                            for (Particle particle : c.getParticles()) {
                                if (b.areNeighbours(p, particle)) {
                                    b.setNeighbour(p, particle);
                                    b.setNeighbour(particle, p);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    public void calculateForce(Board b, int type) {
        for (Particle p : b.getParticles()) {
            Set<Particle> list = b.getNeighbours().get(p);
            Pair<Double, Double> f = calculateForce(b, p, list, b.getE(), b.getRm());
            if(type == 1) {
                p.setNewax(f.getKey() / p.getM());
                p.setNeway(f.getValue() / p.getM());
            } else if(type == 0) {
                p.setAx(f.getKey() / p.getM());
                p.setAy(f.getValue() / p.getM());
            }
        }
    }

    private Pair<Double,Double> calculateForce(Board b, Particle p, Set<Particle> list, double e, double rm) {
        double fx = 0, fy = 0;

        if (list != null) {
            for (Particle p2 : list) {
                Pair<Double, Double> f = getForceAgainstParticle(p, p2, e, rm);
                fx += f.getKey();
                fy += f.getValue();
            }
        }

        if (p.getY() <= b.getInteractionRadius()) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, p.getX(), 0, p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();
        } else if (b.getHeight() - p.getY() <= b.getInteractionRadius()) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, p.getX(), b.getHeight(), p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();
        }

        if (p.getX() <= b.getInteractionRadius()) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, 0, p.getY(), p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();
        } else if (b.getWidth() - p.getX() <= b.getInteractionRadius()) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, b.getWidth(), p.getY(), p.getR(), 0, 0, 0, 0, 0), e, rm);
            fx += f.getKey();
            fy += f.getValue();
        } else if ((Math.abs(b.getWidth() / 2 - p.getX()) <= b.getInteractionRadius()) &&
                !(p.getY() > (b.getHeight() / 2 - 5) && p.getY() < (b.getHeight() / 2 + 5))) {
            Pair<Double, Double> f = getForceAgainstParticle(p, new Particle(-1, b.getWidth() / 2, p.getY(), p.getR(), 0, 0, 0, 0, 0), e, rm);
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

        double f = - a * e * (Math.pow(b, 13) - Math.pow(b, 7));

        double fx = f * dx/r;
        double fy = f * dy/r;

        return new Pair<>(fx, fy);
    }


    public void calculateNewPosition(Board b) {
        for (Particle p : b.getParticles()) {
            velvet(p);
        }
        b.reset();
        calculateNeighbours(b);
        calculateForce(b, 0);
        calculatePotential(b);
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


    public void calculateNewPositionThread(Board b) throws InterruptedException {
        int index =0;
        Thread[] threads = {null,null,null,null};
        for (Cell c: b.getCells()){
            threads[index]=new BeemanThreadOne(c.getParticles(),dt);
            threads[index].run();
            if(index==3){
                index=0;
                for(Thread t: threads){
                    t.join();
                }
            }else{
                index++;
            }
        }
        for(Thread t: threads){
            t.join();
        }
        b.reset();
        calculateNeighbours(b);
        calculateForce(b, 1);
        for (Cell c: b.getCells()){
            threads[index]=new BeemanThreadTwo(c.getParticles(),dt);
            threads[index].run();
            if(index==3){
                index=0;
                for(Thread t: threads){
                    t.join();
                }
            }else{
                index++;
            }
        }
        for(Thread t: threads){
            t.join();
        }
    }

    private void eulerInitialStep(Board b) {

        for(Particle p : b.getParticles()) {
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

    public void run(Board b) throws InterruptedException {
        calculateNeighbours(b);
        calculateForce(b, 0);
        for(Particle p : b.getParticles()) {
            p.setVx(p.getVx() + dt*p.getAx());
            p.setVy(p.getVy() + dt*p.getAy());
            p.setX(p.getX() + dt*p.getVx() + Math.pow(dt,2)*p.getAx()/2);
            p.setY(p.getY() + dt*p.getVy() + Math.pow(dt,2)*p.getAy()/2);
        }
        b.reset();
        calculateNeighbours(b);
        calculateForce(b, 0);
        double t = dt;
        int frame = 1;
        int tImp = 1;
        while(t < tt) {

            //calculateNewPositionThread(b);

            calculateNewPosition(b);

            if(tImp % ti == 0) {
                dfg.saveDynamicFile(b, frame);
                frame++;
            }

            tImp++;
            t += dt;
        }
    }


//    private void beemanFirstPart(Particle p) {
//        double x = p.getX() + p.getVx() * dt + ((2.0 / 3.0) * p.getAx() - (1.0 / 6.0) * p.getOldax()) * Math.pow(dt, 2);
//        p.setX(x);
//        double y = p.getY() + p.getVy() * dt + ((2.0 / 3.0) * p.getAy() - (1.0 / 6.0) * p.getOlday()) * Math.pow(dt, 2);
//        p.setY(y);
//    }
//
//    private void beemanSeconfPart(Particle p) {
//        double vx = p.getVx() + ( (1.0 / 3.0) * p.getNewax() + (5.0 / 6.0) * p.getAx()  - (1.0 / 6.0) * p.getOldax()) * dt;
//        p.setVx(vx);
//        double vy = p.getVy() + ( (1.0 / 3.0) * p.getNeway() + (5.0 / 6.0) * p.getAy()  - (1.0 / 6.0) * p.getOlday()) * dt;
//        p.setVy(vy);
//        p.setOldax(p.getAx());
//        p.setOlday(p.getAy());
//        p.setAx(p.getNewax());
//        p.setAy(p.getNeway());
//    }


    private double getPotential(Particle p1,Particle p2){
        double d = Math.sqrt(Math.pow(p1.getX() - p2.getX(),2)+Math.pow(p1.getY() - p2.getY(),2));
        double sigma = 0.32 * Math.pow(10,-9);
        double ep = 1.08 * Math.pow(10,-21);
        double rm = Math.pow(2,1/6.0) * sigma;
        rm = 1;
        double lj = ep *
                (Math.pow(rm/d,12) - 2*Math.pow(rm/d,6));
        return lj;
    }


    public void calculatePotential(Board b){
        for (Particle p : b.getParticles()) {
            Set<Particle> list = b.getNeighbours().get(p);
            double pot = calcPotential(p, list);
            p.setPotential(pot);
        }
    }

    private double calcPotential(Particle p, Set<Particle> list){
        double total =0;
        if (list != null) {
            for (Particle p2 : list) {
                total+= getPotential(p, p2);
            }
        }
        return total;
    }


}