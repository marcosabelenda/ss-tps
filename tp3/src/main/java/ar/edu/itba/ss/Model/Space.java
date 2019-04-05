package ar.edu.itba.ss.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public class Space {
    private double L;
    private TreeSet<Collision> collisions;
    private List<Particle> particles;

    private ArrayList<ArrayList<Collision>> matrix;


    private int N;

    public Space(double L,List<Particle> particles){
        this.L = L;
        this.particles = particles;
        this.N = particles.size();
        this.collisions = new TreeSet<>();
    }

    public double getL() {
        return L;
    }

    public TreeSet<Collision> getCollisions() {
        return collisions;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public int getN() {
        return N;
    }
}
