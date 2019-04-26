package ar.edu.itba.ss.Model;

public class Beeman{

    private double r;
    private double v;
    private double rSig;
    private double a;
    private double aAnt;
    private double aSig;
    private double m;
    private double elasticidad;
    private double gamma;

    public Beeman() {

    }

    public void run(double tiempoTotal, double dt) {

        this.a = aceleracion(r,v);
        this.rSig = this.r + dt*this.v + ((dt*dt)/2)*a;
        this.v = this.v + dt*a;

        this.r = rSig;
        this.aAnt = a;
        this.a = aceleracion(r,v);

        double tiempo = dt;
        double vPredicted;

        while (tiempo < tiempoTotal) {

            rSig = r + v * dt + ((2 / 3) * a - (1 / 6) * aAnt) * Math.pow(dt, 2); //TODO arreglar error de casteo flotante
            vPredicted = v + (3 / 2) * a * dt - (1 / 2) * aAnt * dt;
            aSig = aceleracion(rSig, vPredicted);
            v = v + (1 / 3) * aSig * dt + (5 / 6) * a * dt - (1 / 6) * aAnt * dt;

            r = rSig;
            aAnt = a;
            a = aSig;
            tiempo++; //TODO arreglar, el tiempo NO es discreto
        }
    }


    private double aceleracion(double r, double v) {
        double f = - this.elasticidad*r - this.gamma*v;
        return f/this.m;
    }
}
