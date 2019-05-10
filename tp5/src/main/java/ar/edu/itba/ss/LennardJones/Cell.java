package ar.edu.itba.ss.LennardJones;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Cell {

    List<Particle> particles;
    double side;
    double x;
    double y;

    public Cell(double side, double x, double y) {
        this.side = side;
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public double getSide() {
        return side;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
