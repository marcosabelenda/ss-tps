package ar.edu.itba.ss.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationParser {
    private final static String SEED_HEADER = "seed", NUMBER_PARTICLES_HEADER = "number_particles",
                                BOARD_SIDE_HEADER = "board_side", CELL_SIDE_HEADER = "cell_side",
                                RADIUS_HEADER = "radius", PCC_HEADER = "PCC", DYNAMIC_FILE_HEADER = "dynamic_file";
    private final static String VALUE_SEPARATOR = ":";
    private final static int HEADER = 0, VALUE = 1;

    private int seed;
    private int numberParticles;
    private double boardSide;
    private double cellSide;
    private double radius;
    private boolean pcc;
    private String dynamicFileName;

    public ConfigurationParser(){
    }

    public void parse(String fileName) throws IOException {

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null) {

            String [] args = line.split(VALUE_SEPARATOR);

            switch(args[HEADER]) {
                case SEED_HEADER:
                    this.seed = Integer.parseInt(args[VALUE]);
                    break;

                case NUMBER_PARTICLES_HEADER:
                    this.numberParticles = Integer.parseInt(args[VALUE]);
                    break;

                case BOARD_SIDE_HEADER:
                    this.boardSide = Double.parseDouble(args[VALUE]);
                    break;

                case CELL_SIDE_HEADER:
                    this.cellSide = Double.parseDouble(args[VALUE]);
                    break;

                case RADIUS_HEADER:
                    this.radius = Double.parseDouble(args[VALUE]);
                    break;

                case PCC_HEADER:
                    this.pcc = Integer.parseInt(args[VALUE]) != 0;
                    break;

                case DYNAMIC_FILE_HEADER:
                    this.dynamicFileName = args[VALUE];
                    break;
                default:

            }
        }
        bufferedReader.close();

    }

    public int getSeed() {
        return seed;
    }

    public int getNumberParticles() {
        return numberParticles;
    }

    public double getBoardSide() {
        return boardSide;
    }

    public double getCellSide() {
        return cellSide;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isPcc() {
        return pcc;
    }

    public String getDynamicFileName() {
        return dynamicFileName;
    }
}
