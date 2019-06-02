package ar.edu.itba.ss.LennardJones;

import javafx.util.Pair;

import java.util.*;

public class Board2 {

    Map<Pair<Integer, Integer>, Cell2> cells;
    List<Particle> particles;

    double height;
    double width;
    double cellSize;
    double outsideWidth;

    double window;


    public Board2(double height, double width, double cellSize, double window, double outsideWidth, List<Particle> particles) {
        this.height = height;
        this.width = width;
        this.cellSize = cellSize;
        this.cells = new HashMap<>();

        this.window = window;
        this.outsideWidth = outsideWidth;

        //this.neighbours = new HashMap<>();
        this.particles = particles;
        this.ordenarParticulas(particles);

    }


    private Cell2 getCell(Pair<Integer, Integer> position) {
        if (!cells.containsKey(position)) {
            cells.put(position, new Cell2(this.cellSize, position.getKey(), position.getValue()));
        }
        return cells.get(position);
    }


    private void ordenarParticulas(List<Particle> particles) {
        for (Particle p : particles) {
            Pair<Integer, Integer> position = getCellIndex(p.getX(), p.getY());
            getCell(position).particles.add(p);
        }

    }

    private Pair<Integer, Integer> getCellIndex(double x, double y) {
        int xp = (int) Math.floor(x / cellSize);
        int yp = (int) Math.floor(y / cellSize);
        return new Pair<>(xp, yp);
    }

    private boolean areNeighbours(Particle p1, Particle p2) {
        try {
            return getDistance(p1, p2) <= (p1.getR() + p2.getR());
        } catch (Exception e) {
            return false;
        }
    }

    private double getDistance(Particle p1, Particle p2) {
        if (p1 == null && p2 == null) {
            throw new NullPointerException();
        }
        return Math.hypot((p1.getX() - p2.getX()), (p1.getY() - p2.getY()));
    }

    public void reset() {
        cells.clear();
        ordenarParticulas(particles);
    }


    public Set<Particle> getNeighbours(Particle p1) {
        Pair<Integer, Integer> in = getCellIndex(p1.x, p1.y);
        int x = in.getKey();
        int y = in.getValue();
        HashSet<Particle> particles = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Pair<Integer, Integer> pos = new Pair<>(x + i, y + j);
                if (cells.containsKey(pos)) {
                    for (Particle p2 : getCell(pos).particles) {
                        if (!p1.equals(p2) && areNeighbours(p1, p2)) {
                            particles.add(p2);
                        }
                    }
                }
            }
        }
        return particles;
    }



}