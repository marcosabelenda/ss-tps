package ar.edu.itba.ss.LennardJones;

import javafx.util.Pair;

import java.util.*;

public class Board2 {

    Map<Pair<Integer, Integer>, Cell2> cells;
    Map<Particle, Set<Particle>> neighbours;
    List<Particle> particles;

    double height;
    double width;
    double e;
    double rm;
    double cellSize;

    double g;
    double kn;
    double kt;
    double window;


    @Deprecated
    public Board2(double height, double width, double e, double rm, double cellSide, List<Particle> particles) {
        this.height = height;
        this.width = width;
        this.cellSize = cellSide;
        this.cells = new HashMap<>();
        this.e = e;
        this.rm = rm;
        this.neighbours = new HashMap<>();
        this.particles = particles;
        this.ordenarParticulas(particles);
    }


    public Board2(double height, double width, double e, double rm, double cellSize,
                  double g, double kn, double kt, double window,
                  List<Particle> particles) {
        this.height = height;
        this.width = width;
        this.cellSize = cellSize;
        this.cells = new HashMap<>();
        this.e = e;
        this.rm = rm;

                this.g =g;
        this.kn = kn;
        this.kt = kt;
        this.window = window;

        this.neighbours = new HashMap<>();
        this.particles = particles;
        this.ordenarParticulas(particles);
    }


    public Cell2 getCell(Pair<Integer, Integer> position) {
        if (!cells.containsKey(position)) {
            cells.put(position, new Cell2(this.cellSize, position.getValue(), position.getKey()));
        }
        return cells.get(position);
    }


    private void ordenarParticulas(List<Particle> particles) {
        for (Particle p : particles) {
            Pair<Integer, Integer> position = getCellIndex(p.x, p.y);
            cells.get(position).particles.add(p);
        }
    }

    public Pair<Integer, Integer> getCellIndex(double x, double y) {
        int xp = (int) Math.floor(x / cellSize);
        int yp = (int) Math.floor(y / cellSize);
        return new Pair<>(xp, yp);
    }

    public void setNeighbour(Particle p1, Particle p2) {
        addNeighbour(p1,p2);
        addNeighbour(p2,p1);
    }

    private void addNeighbour(Particle p1,Particle p2){
        if(!neighbours.containsKey(p1)){
            Set<Particle> nP = new HashSet<>();
            neighbours.put(p1,nP);
        }
        neighbours.get(p1).add(p2);
    }

    public boolean areNeighbours(Particle p1, Particle p2) {
        try {
            return getDistance(p1, p2) <= Math.max(p1.getR(), p2.getR());
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
        neighbours.clear();
        cells.clear();
        ordenarParticulas(particles);
    }

    public void rearrangeNeighbours(){
        for(Cell2 c: cells.values()){
            for(Particle p: c.particles){
                reposicionarParticulas(p,c);
            }
        }
    }

    private void reposicionarParticulas(Particle p, Cell2 cell) {
        int x = cell.x;
        int y = cell.y;
        for (int i = -1; i <= 1; i++) { //TODO OPTIMIZAR
            for (int j = -1; j <= 1; j++) {
                Pair<Integer, Integer> pos = new Pair<>(x + i, y + i);
                if (cells.containsKey(pos)) {
                    for (Particle p2 : getCell(pos).particles) {
                        if (areNeighbours(p, p2)) {
                            if(neighbours.get(p).contains(p2)){
                                break;
                            }
                            setNeighbour(p, p2);
                        }
                    }
                }
            }
        }

    }

}