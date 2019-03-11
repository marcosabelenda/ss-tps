package ar.edu.itba.ss.MetricAlgoritms;

import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.Model.Cell;
import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.utils.Metrics;

public class MetricAlgoritmsWithoutPCC implements MetricAlgoritm {

    @Override
    public void calculateNeighbours(Board b, Metrics m) {
        m.startTime();
        for(Cell c: b.getCells()) {
            int size = c.getParticles().size();
            for(int i = 0 ; i < size ; i++) {
                calculateNeighbours(c.getParticles().get(i), i, b, m);
            }
        }
        m.stopTime();
    }

    //podria mejorarse el metodo, con xp+r,yp+r y ver si llega al borde de la celda
    private void calculateNeighbours(Particle p, int particleIndex,Board b, Metrics m) {
        if(p == null || b == null) {
            return;
        }
        int aux = (int)(Math.ceil(b.getParticleRadius()/b.getCellSide()));
        double auxX = p.getX();
        double auxY = p.getY();
        String type = Board.NC;
        boolean flag = false;
        for(int i = 0 ; i <= aux ; i++) {
            if (i + Math.floor(p.getY() / b.getCellSide()) >= b.getCantCellPerLine()) break;
            for(int j = -aux; j <= aux ; j++) {
                if(j + Math.floor(p.getX()/b.getCellSide()) >= b.getCantCellPerLine()) {
                    break;
                }
                if(!flag) {
                    j = 0;
                    flag = true;
                }
                if(p.getX() + j*b.getCellSide() > 0) {
                    auxX = p.getX() + j * b.getCellSide();
                    auxY = p.getY() + i * b.getCellSide();
                    m.increaseComparisons();

                    Cell c = b.getCells().get(b.getCellIndex(auxX, auxY));
                    int size = c.getParticles().size();
                    if (i == 0 && j == 0) {
                        for (int k = particleIndex + 1; k < size; k++) {
                            Particle auxP = c.getParticles().get(k);
                            if (b.areNeighbours(p, auxP, type)) {
                                b.setNeighbour(p, auxP);
                                b.setNeighbour(auxP, p);
                            }
                        }
                    } else {
                        for (Particle particle : c.getParticles()) {
                            if (b.areNeighbours(p, particle, type)) {
                                b.setNeighbour(p, particle);
                                b.setNeighbour(particle, p);
                            }
                        }
                    }
                }
            }


        }
    }
}
