package ar.edu.itba.ss.LennardJones;

import java.util.*;

public class Board {

    private List<Cell> cells;
    private Map<Particle, Set<Particle>> neighbours;
    private List<Particle> particles;
    double height;
    double width;
    double g;
    double kn;
    double kt;
    double e;
    double rm;
    double window;



    private double cellSide;
    private int cantCellPerLine;
    private double interactionRadius;
    private double cantCellPerRow;

    public Board(double height, double width, double cellSide, int cantCellPerRow, int cantCellPerLine,
                 double interactionRadius, double e, double rm, List<Particle> particles) throws Exception {
        this.height = height;
        this.width = width;
        this.cellSide = cellSide;
        this.cells = new ArrayList<>();
        this.cantCellPerLine = cantCellPerLine;
        this.cantCellPerRow = cantCellPerRow;
        this.e = e;
        this.rm = rm;
        this.generateCell();
        this.neighbours = new HashMap<>();
        this.interactionRadius = interactionRadius;
        this.particles = particles;
        this.setParticles(particles);
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return width;
    }

    public double getE() {
        return e;
    }

    public double getRm() {
        return rm;
    }

    public double getCantCellPerRow() {
        return cantCellPerRow;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public double getCellSide() {
        return cellSide;
    }

    public Map<Particle, Set<Particle>> getNeighbours() {
        return neighbours;
    }

    public int getCantCellPerLine() {
        return cantCellPerLine;
    }

    @Deprecated
    public double getInteractionRadius() {
        return interactionRadius;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    private void generateCell() {
        for(int i = 0 ; i < this.cantCellPerRow ; i++) {
            for(int j = 0 ; j < this.cantCellPerLine ; j++) {
                this.cells.add(new Cell(this.cellSide,j*this.cellSide, i*this.cellSide));
            }
        }
    }

    private void setParticles(List<Particle> particles) {
        for(Particle p : particles) {
            int aux = this.getCellIndex(p.getX(), p.getY());
            if(aux < 3200 && aux >= 0 ) {
                this.cells.get(aux).getParticles().add(p);
            }
        }
    }

    public Integer getCellIndex(double x, double y) {
        return (int)(Math.floor(x/cellSide) + Math.floor(y/cellSide)*this.cantCellPerLine);
    }

    public void setNeighbour(Particle p1, Particle p2) {
        if(this.getNeighbours().containsKey(p1)) {
            this.getNeighbours().get(p1).add(p2);
        } else {
            Set<Particle> neighboursParticles = new HashSet<>();
            neighboursParticles.add(p2);
            this.getNeighbours().put(p1,neighboursParticles);
        }
    }

    public boolean areNeighbours(Particle p1, Particle p2) {
        try {
            return getDistance(p1, p2) <= Math.max(p1.getR(),p2.getR());
        } catch (Exception e) {
            return false;
        }
    }

    private double getDistance(Particle p1, Particle p2) {
        if(p1 == null && p2 == null) {
            throw new NullPointerException();
        }
        return Math.hypot((p1.getX() - p2.getX()), (p1.getY() - p2.getY()));
    }

    public void reset() {
        neighbours.clear();
        for(Cell c : cells) {
            c.getParticles();
        }
        setParticles(particles);
    }
}
