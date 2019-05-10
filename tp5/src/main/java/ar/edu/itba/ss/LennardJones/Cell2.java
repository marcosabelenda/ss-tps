package ar.edu.itba.ss.LennardJones;

import java.util.ArrayList;
import java.util.List;

public class Cell2 {

    List<Particle> particles;
    double size;
    int x;
    int y;

    public Cell2(double side, int x, int y) {
        this.size = side;
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
    }

}
