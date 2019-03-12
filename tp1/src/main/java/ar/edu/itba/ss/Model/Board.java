package ar.edu.itba.ss.Model;

import java.util.*;

public class Board {

    public static final String NC = "NC", CWR = "CWR", CH = "CH", CWHR = "CWHR", CWHL = "CWHL", CWL = "CWL";

    private List<Cell> cells;
    private Map<Particle, List<Particle>> neighbours;
    private double side;
    private double cellSide;
    private int cantCellPerLine;
    private int numberParticles;
    private double interactionRadius;

    public Board(double side, int cantCellPerLine, int numberParticles, double interactionRadius, List<Particle> particles) throws Exception {
        if(side <= 0 || cantCellPerLine <= 0) throw new Exception("the sides are wrong");
        this.side = side;
        this.cellSide = side/cantCellPerLine;
        this.cells = new ArrayList<>();
        this.cantCellPerLine = cantCellPerLine;
        this.generateCell();
        this.numberParticles = numberParticles;
        this.neighbours = new HashMap<>();
        this.interactionRadius = interactionRadius;
        this.setParticles(particles);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public double getSide() {
        return side;
    }

    public double getCellSide() {
        return cellSide;
    }

    public Map<Particle, List<Particle>> getNeighbours() {
        return neighbours;
    }

    public int getCantCellPerLine() {
        return cantCellPerLine;
    }

    public int getNumberParticles() {
        return numberParticles;
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }

    private void generateCell() {
        for(int i = 0 ; i < this.cantCellPerLine ; i++) {
            for(int j = 0 ; j < this.cantCellPerLine ; j++) {
                this.cells.add(new Cell(this.cellSide,j*this.cellSide, i*this.cellSide));
            }
        }
    }

    //modificar hay que cargarlo de un archivo, el metodo este va a pasar a ser agregar pto a celda
    private void setParticles(List<Particle> particles) {
        for(Particle p : particles) {
            int aux = this.getCellIndex(p.getX(), p.getY());
            this.cells.get(aux).getParticles().add(p);
        }
    }

    public Integer getCellIndex(double x, double y) {
        return (int)(Math.floor(x/cellSide) + Math.floor(y/cellSide)*this.cantCellPerLine);
    }

    public void setNeighbour(Particle p1, Particle p2) {
        if(this.getNeighbours().containsKey(p1)) {
            this.getNeighbours().get(p1).add(p2);
        } else {
            List<Particle> neighboursParticles = new ArrayList<>();
            neighboursParticles.add(p2);
            this.getNeighbours().put(p1,neighboursParticles);
        }
    }

    public boolean areNeighbours(Particle p1, Particle p2, String type) {
        try {
            return getDistance(p1, p2, type) <= (this.interactionRadius + p1.getRadius() + p2.getRadius());
        } catch (Exception e) {
            return false;
        }
    }

    private double getDistance(Particle p1, Particle p2, String type) throws Exception {
        if(p1 == null && p2 == null) {
            throw new NullPointerException();
        }

        switch (type) {
            case NC:
                return Math.sqrt((p1.getX() - p2.getX())*(p1.getX() - p2.getX()) + (p1.getY() - p2.getY())*(p1.getY() - p2.getY()));
            case CWR:
                return Math.sqrt(Math.pow((p2.getX() + (this.side - p1.getX())),2) + Math.pow((p1.getY() - p2.getY()),2));
            case CH:
                return Math.sqrt(Math.pow((p1.getX() - p2.getX()),2) + Math.pow((p2.getY() + (this.side - p1.getY())),2));
            case CWHR:
                return Math.sqrt(Math.pow((p2.getX() + (this.side - p1.getX())),2) + Math.pow((p2.getY() + (this.side - p1.getY())),2));
            case CWHL:
                return Math.sqrt(Math.pow((p1.getX() + (this.side - p2.getX())),2) + Math.pow((p2.getY() + (this.side - p1.getY())),2));
            case CWL:
                return Math.sqrt(Math.pow((p1.getX() + (this.side - p2.getX())),2) + Math.pow((p1.getY() - p2.getY()),2));
        }
        throw new Exception("The type of measurement command is wrong!");
    }
}
