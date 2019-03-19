package ar.edu.itba.ss.MetricAlgoritms;

import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.Model.Cell;
import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.utils.Metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class OffLattice {

    public void calculateNeighbours(Board b) {
        for(Cell c: b.getCells()) {
            int size = c.getParticles().size();
            for(int i = 0 ; i < size ; i++) {
                calculateNeighbours(c.getParticles().get(i), i, b);
            }
        }
    }

    public double calculateMediaAngle(Particle p, Board b, double etha, Random r) {
        double mSin = Math.sin(p.getAngle()), mCos = Math.cos(p.getAngle());
        Set<Particle> particles = b.getNeighbours().get(p);
        if(particles != null) {
            for(Particle particle : particles) {
                mSin += Math.sin(particle.getAngle());
                mCos += Math.cos(particle.getAngle());
            }
            mSin /= (particles.size()+1);
            mCos /= (particles.size()+1);
        }

        return Math.atan2(mSin,mCos) + r.nextDouble()*etha - etha/2;
    }

    public List<Particle> calculateParticles(Board b, double etha, double time) {
        List<Particle> particles = new ArrayList<>();
        Random r = new Random(System.currentTimeMillis());
        for(Particle p : b.getParticles()) {
            double newAngle = this.calculateMediaAngle(p, b, etha, r);
            Particle newP = new Particle(p.getId(), setM(p.getX()+p.getV()*Math.cos(p.getAngle()), b.getSide()),
                    setM(p.getY()+p.getV()*Math.sin(p.getAngle()), b.getSide()),
                    p.getV(), newAngle);
            particles.add(newP);
        }
        return particles;
    }

    public double setM(double m, double limit) {
        if(m < 0) {
            return m + limit;
        } else if(m > limit) {
            return m - limit;
        }
        return m;
    }

    private void calculateNeighbours(Particle p, int particleIndex, Board b) {
        if(p == null || b == null) {
            return;
        }

        double auxX;
        double auxY;
        String type;
        Cell c;
        for(int i = 0; i < 5 ; i++){

            switch (i){
                case 0://(0,0)
                    auxX = p.getX();
                    auxY = p.getY();
                    type = Board.NC;
                    c = b.getCells().get(b.getCellIndex(auxX, auxY));
                    int size = c.getParticles().size();
                    for(int k = particleIndex + 1 ; k < size ; k++) {
                        Particle particle = c.getParticles().get(k);
                        if(b.areNeighbours(p, particle, type)) {
                            b.setNeighbour(p, particle);
                            b.setNeighbour(particle, p);
                        }
                    }
                    break;
                case 1://(0,1)

                    if(1 + Math.floor(p.getX() / b.getCellSide()) >= b.getCantCellPerLine()) {
                        auxX = 0;
                        type = Board.CWR;
                    } else {
                        auxX = p.getX() + b.getCellSide();
                        type = Board.NC;
                    }
                    auxY = p.getY();
                    c = b.getCells().get(b.getCellIndex(auxX, auxY));
                    for (Particle particle : c.getParticles()) {
                        if(b.areNeighbours(p, particle, type)) {
                            b.setNeighbour(p, particle);
                            b.setNeighbour(particle, p);
                        }
                    }
                    break;
                case 2://(1, -1)
                    if (1 + Math.floor(p.getY() / b.getCellSide()) >= b.getCantCellPerLine()) {
                        auxY = 0;
                        if(p.getX() - b.getCellSide() <= 0) {
                            auxX = (b.getCantCellPerLine() - 1)*b.getCellSide();
                            type = Board.CWHL;
                        } else {
                            auxX = p.getX() - b.getCellSide();
                            type = Board.CH;
                        }
                    } else {
                        auxY = p.getY() + b.getCellSide();
                        if(p.getX() - b.getCellSide() <= 0) {
                            auxX = (b.getCantCellPerLine() - 1)*b.getCellSide();
                            type = Board.CWL;
                        } else {
                            auxX = p.getX() - b.getCellSide();
                            type = Board.NC;
                        }
                    }
                    c = b.getCells().get(b.getCellIndex(auxX, auxY));

                    for (Particle particle : c.getParticles()) {
                        if(b.areNeighbours(p, particle, type)) {
                            b.setNeighbour(p, particle);
                            b.setNeighbour(particle, p);
                        }
                    }
                    break;
                case 3://(1,0)
                    if (1 + Math.floor(p.getY() / b.getCellSide()) >= b.getCantCellPerLine()) {
                        auxY = 0;
                        auxX = p.getX();
                        type = Board.CH;
                    } else {
                        auxY = p.getY() + b.getCellSide();
                        auxX = p.getX();
                        type = Board.NC;
                    }
                    c = b.getCells().get(b.getCellIndex(auxX, auxY));

                    for (Particle particle : c.getParticles()) {
                        if(b.areNeighbours(p, particle, type)) {
                            b.setNeighbour(p, particle);
                            b.setNeighbour(particle, p);
                        }
                    }
                    break;
                case 4://(1,1)
                    if (1 + Math.floor(p.getY() / b.getCellSide()) >= b.getCantCellPerLine()) {
                        auxY = 0;
                        if(1 + Math.floor(p.getX() / b.getCellSide()) >= b.getCantCellPerLine()) {
                            auxX = 0;
                            type = Board.CWHR;
                        } else {
                            auxX = p.getX() + b.getCellSide();
                            type = Board.CH;
                        }
                    } else {
                        auxY = p.getY() + b.getCellSide();
                        if(1 + Math.floor(p.getX() / b.getCellSide()) >= b.getCantCellPerLine()) {
                            auxX = 0;
                            type = Board.CWR;
                        } else {
                            auxX = p.getX() + b.getCellSide();
                            type = Board.NC;
                        }
                    }
                    c = b.getCells().get(b.getCellIndex(auxX, auxY));

                    for (Particle particle : c.getParticles()) {
                        if(b.areNeighbours(p, particle, type)) {
                            b.setNeighbour(p, particle);
                            b.setNeighbour(particle, p);
                        }
                    }
                    break;
            }
        }
    }

}
