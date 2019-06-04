package ar.edu.itba.ss.LennardJones;


import ar.edu.itba.ss.utils.SalidaMetrics;
import javafx.util.Pair;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ContractileParticleModel {

    private double dt;
    private double tt;
    private double ti;
    private double vmax;
    private double rmin;
    private double rmax;
    private double beta;
    private double tao;
    private DynamicFileGenerator dfg;
    AtomicInteger cantidad;

    SalidaMetrics sm;

    public ContractileParticleModel(double dt, double tt, double ti, double rmin, double rmax, double vmax, double beta, double tao, DynamicFileGenerator dfg) {
        this.dt = dt;
        this.tt = tt;
        this.ti = ti;
        this.rmin = rmin;
        this.rmax = rmax;
        this.vmax = vmax;
        this.beta = beta;
        this.tao = tao;
        this.dfg = dfg;

        cantidad = new AtomicInteger(0);

        sm = new SalidaMetrics(dt);
    }


    private void avanzar(Board2 b) {
//        for(Particle p : b.particles) {
//            avanza(b,p);
//        }
        b.particles.parallelStream().forEach(p->avanza(b,p));



    }

    private void contarSalidas(Board2 b){
        //METRICAS
        int ultimas = cantidad.get();
        cantidad.set(0);
        b.particles.parallelStream().forEach(p->siSalio(b,p));
        int salieron =cantidad.get() - ultimas;
        sm.addSalieron(salieron);
    }


    private void siSalio(Board2 b, Particle p){
        if(p.target instanceof Target2){
            cantidad.incrementAndGet();
        }
    }

    private void avanza(Board2 b, Particle p){
        Set<Particle> list = b.getNeighbours(p);

        if(p.target.llegoAlTarget(p)) {
            p.setTarget(p.target.getNextTarget());
        }

        if(((p.getX()+p.getR()) >= (b.width-b.outsideWidth) && p.getX() < b.width-b.outsideWidth) &&
                (p.getY() <= (b.height-b.window)/2 || p.getY() >= (b.height+b.window)/2)) {
            list.add(new Particle(-1,b.width-b.outsideWidth+p.getR(),p.getY(), p.getR(), 0, 0, null));
        }

        if(list.isEmpty()) {
            avanzarSinVecinos(p);
        } else {
            avanzarConVecinos(p, list);
        }

    }


    private void avanzarConVecinos(Particle p1, Set<Particle> list) {
        double ex = 0, ey = 0, exmod = 0, eymod = 0;
        for(Particle p2 : list) {
            double exij = (p1.getX() - p2.getX())/Math.hypot((p1.getX() - p2.getX()), (p1.getY() - p2.getY()));
            double eyij = (p1.getY() - p2.getY())/Math.hypot((p1.getX() - p2.getX()), (p1.getY() - p2.getY()));

            ex += exij;
            exmod += Math.abs(exij);
            ey += eyij;
            eymod += Math.abs(eyij);
        }
        if(exmod != 0) {
            ex /= exmod;
        }
        if(eymod != 0) {
            ey /= eymod;
        }


        double vex = vmax*ex;
        double vey = vmax*ey;

        double x = p1.getX() + vex*dt;
        double y = p1.getY() + vey*dt;

        p1.setVx(vex);
        p1.setVy(vey);
        p1.setX(x);
        p1.setY(y);
        p1.setR(rmin);

    }

    private void avanzarSinVecinos(Particle p) {
        double vd = vmax * Math.pow(((p.getR() - rmin) / (rmax - rmin)), beta);
        Pair<Double,Double> eVersor = p.getTarget().getVersor(p);

        double vx = vd*eVersor.getKey();
        double vy = vd*eVersor.getValue();


        double x = p.getX() + vx*dt;
        double y = p.getY() + vy*dt;

        double r = p.getR();
        if(p.getR() < rmax) {
            r += rmax/(tao/dt);
        }

        p.setX(x);
        p.setY(y);
        p.setR(r);
        p.setVx(vx);
        p.setVy(vy);

    }

    public void run(Board2 b) {

        double t = dt;
        int frame = 1;
        int iteracion = 1;
        while(t < tt) {

            avanzar(b);
            contarSalidas(b);
            b.reset();

            if(iteracion % ti == 0) {
                dfg.saveDynamicFile(b, frame);
                frame++;
            }

            t += dt;
            iteracion++;
        }

        sm.saveMetrics();
    }
}
