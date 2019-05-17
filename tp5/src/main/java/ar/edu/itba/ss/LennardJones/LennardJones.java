package ar.edu.itba.ss.LennardJones;

import ar.edu.itba.ss.utils.SalidaMetrics;
import javafx.util.Pair;

import java.util.Random;
import java.util.Set;

public class LennardJones {

    private double dt;
    private double tt;
    private double ti;
    private DynamicFileGenerator dfg;

    SalidaMetrics sm;
    Random r=new Random(1);


    public LennardJones(double dt, double tt, double ti, DynamicFileGenerator dfg) {
        this.dt = dt;
        this.tt = tt;
        this.ti = ti;
        this.dfg = dfg;

        sm = new SalidaMetrics(dt);
    }

    public void sumaFuerzas(Board2 b) {
        for (Particle p : b.particles) {
            Set<Particle> list = b.getNeighbours(p);
            Pair<Double, Double> f = sumaFuerzas(b, p, list);
            p.setAx(f.getKey() / p.getM());
            p.setAy(f.getValue() / p.getM());
        }
    }


    private Pair<Double,Double> sumaFuerzas(Board2 b, Particle p, Set<Particle> list) {
        p.resetearPresion();
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


        //agrego presion
        p1.agregarPresion(fn);



        double ft = - b.kt * eps * ((p1.vx - p2.vx) * (-ey) + (p1.vy - p2.vy) * ex);


        double fx = fn * ex + ft * (-ey);
        double fy = fn * ey + ft * ex;


        return new Pair<>(fx,fy);

    }


    public void calculateNewPosition(Board2 b) {
        int contar=0;
        for (Particle p : b.particles) {
            if(velret(p,b))
                contar++;
        }
        b.reset();

        sm.addSalieron(contar);
        sumaFuerzas(b);

    }

    private boolean velret(Particle p, Board2 b) {
        double x = 2 * p.getX() - p.getPrevx() + p.getAx() * Math.pow(dt, 2);
        double y = 2 * p.getY() - p.getPrevy() + p.getAy() * Math.pow(dt, 2);

        double vx = (x - p.getPrevx())/(dt*2);
        double vy = (y - p.getPrevy())/(dt*2);

        boolean salio = false;

        if(y<(b.height/2) && p.y>=(b.height/2))
            salio = true;

        p.setX(x);
        p.setY(y);
        p.setVx(vx);
        p.setVy(vy);

//        if(y<((b.height/2)-b.height/20)){
//            reponerAlSiloLaParticula(p,b);
//        }
        return salio;
    }

    @Deprecated
    private void reponerAlSiloLaParticula(Particle p1,Board2 b){
        double auxX, auxY, auxR;

        int intentosTotales = 5000;
        int intentos = 0;
        while(intentos < intentosTotales) {
            auxR = 0.02 + r.nextDouble() * 0.01;
            auxX = auxR + r.nextDouble() * (b.width-2*auxR);
            auxY = ((b.height / 2) + auxR) + + r.nextDouble() * ((b.height / 2) - 2 * auxR);
            boolean posCorrecta = true;
            Set<Particle> bbb=b.getNeighbours(new Particle(0,auxX,auxY,auxR,0,0,0,0,0));
            for(Particle p: bbb){
            //            for(Particle p: b.particles){
                if(Math.pow(auxX - p.getX(),2) + Math.pow(auxY - p.getY(), 2) <=  Math.pow(auxR + p.getR(), 2)){
                    posCorrecta = false;
                    break;
                }
            }

            if(posCorrecta) {
                p1.x=auxX; //TODO implementar metodo
                p1.y=auxY;
                p1.vx=0;
                p1.vy=0;
                p1.prevx=0;
                p1.prevy=0;
                eulerInitialParticle(p1,b);
                break;
            } else {
                intentos++;
            }

        }
        if(intentos>=intentosTotales)
            System.out.println("No se pudo reposicionar la particula");
    }


    private void eulerInitialStep(Board2 b) {

        for(Particle p : b.particles) {
            eulerInitialParticle(p,b);
        }
    }

    private void eulerInitialParticle(Particle p, Board2 b){
        double x = p.getX() + dt*p.getVx() + (Math.pow(dt,2)/2)*p.getAx();
        double vx = p.getVx() + dt*p.getAx();
        double y = p.getY() + dt*p.getVy() + (Math.pow(dt,2)/2)*p.getAy();
        double vy = p.getVy() + dt*p.getAy();

        p.setY(y);
        p.setX(x);
        p.setVx(vx);
        p.setVy(vy);

        p.setOldax(p.getAx());
    }

    public void run(Board2 b) {
        //b.rearrangeNeighbours();
        sumaFuerzas(b);
        for(Particle p : b.particles) {
            p.setVx(p.getVx() + dt*p.getAx());
            p.setVy(p.getVy() + dt*p.getAy());
            p.setX(p.getX() + dt*p.getVx() + Math.pow(dt,2)*p.getAx()/2);
            p.setY(p.getY() + dt*p.getVy() + Math.pow(dt,2)*p.getAy()/2);
        }
        b.reset();
        //b.rearrangeNeighbours();
        sumaFuerzas(b);
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
        sm.saveMetrics();
    }

}