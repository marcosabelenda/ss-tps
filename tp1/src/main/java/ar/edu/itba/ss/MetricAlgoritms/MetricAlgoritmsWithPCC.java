package ar.edu.itba.ss.MetricAlgoritms;

import ar.edu.itba.ss.Model.Board;
import ar.edu.itba.ss.Model.Cell;
import ar.edu.itba.ss.Model.Particle;
import ar.edu.itba.ss.utils.Metrics;

public class MetricAlgoritmsWithPCC implements MetricAlgoritm {

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

    private void calculateNeighbours(Particle p, int particleIndex, Board b, Metrics m) {
        if(p == null || b == null) {
            return;
        }
        int aux = (int)(Math.ceil(b.getParticleRadius()/b.getCellSide()));
        double auxX;
        double auxY;
        String type;
        boolean flag = false;
        for(int i = 0 ; i <= aux ; i++) {

            if (i + Math.floor(p.getY() / b.getCellSide()) >= b.getCantCellPerLine()) {

                auxY = ((i + Math.floor(p.getY() / b.getCellSide())) - b.getCantCellPerLine())*b.getCellSide();
                type = Board.CH;

            } else {
                auxY = p.getY() + i*b.getCellSide();
                type = Board.NC;
            }
            for(int j = -aux ; j <= aux ; j++) {
                if(!flag) {
                    j = 0;
                    flag = true;
                }
                if(j + Math.floor(p.getX()/b.getCellSide()) >= b.getCantCellPerLine()) {
                    auxX = ((j + Math.floor(p.getX() / b.getCellSide())) - b.getCantCellPerLine())*b.getCellSide();
                    if(type.equals(Board.CH)) {
                        type = Board.CWHR;
                    } else {
                        type = Board.CWR;
                    }
                } else if(p.getX() + j*b.getCellSide() < 0) {
                    auxX = (b.getCantCellPerLine() - (Math.abs(j) - Math.floor(p.getX()/b.getCellSide())))*b.getCellSide();
                    if(type.equals(Board.CH) || type.equals(Board.CWHL)) {
                        type = Board.CWHL;
                    } else {
                        type = Board.CWL;
                    }
                } else {
                    auxX = p.getX() + j*b.getCellSide();
                    if(type.equals(Board.NC) || type.equals(Board.CWL)) {
                        type = Board.NC;
                    } else {
                        type = Board.CH;
                    }
                }

                m.increaseComparisons();

                Cell c = b.getCells().get(b.getCellIndex(auxX, auxY));
                int size = c.getParticles().size();
                if(i == 0 && j == 0) {
                    for(int k = particleIndex + 1 ; k < size ; k++) {
                        Particle particle = c.getParticles().get(k);
                        if(b.areNeighbours(p, particle, type)) {
                            b.setNeighbour(p, particle);
                            b.setNeighbour(particle, p);
                        }
                    }
                } else {
                    for (Particle particle : c.getParticles()) {
                        if(b.areNeighbours(p, particle, type)) {
                            b.setNeighbour(p, particle);
                            b.setNeighbour(particle, p);
                        }
                    }
                }
            }
        }
    }

}
