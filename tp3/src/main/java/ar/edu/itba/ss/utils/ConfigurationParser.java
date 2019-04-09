package ar.edu.itba.ss.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationParser {
    private final static String NUMBER_PARTICLES_HEADER = "number_particles",
                                BOARD_SIDE_HEADER = "board_side",
                                VELOCITY = "velocity",
                                PRINT_TIME = "print_time",
                                TOTAL_TIME = "total_time";
    private final static String VALUE_SEPARATOR = ":";
    private final static int HEADER = 0, VALUE = 1;

    private int numberParticles;
    private double boardSide;

    private double velocity;
    private int print_time;
    private int total_time;


    public ConfigurationParser(){

    }

    public void parse(String fileName) throws IOException {

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null) {

            String [] args = line.split(VALUE_SEPARATOR);

            switch(args[HEADER]) {

                case NUMBER_PARTICLES_HEADER:
                    this.numberParticles = Integer.parseInt(args[VALUE]);
                    break;

                case BOARD_SIDE_HEADER:
                    this.boardSide = Double.parseDouble(args[VALUE]);

                case VELOCITY:
                    this.velocity = Double.parseDouble(args[VALUE]);
                    break;

                case PRINT_TIME:
                    this.print_time = Integer.parseInt(args[VALUE]);
                    break;

                case TOTAL_TIME:
                    this.total_time = Integer.parseInt(args[VALUE]);
                    break;



                default:

            }
        }
        bufferedReader.close();

    }


    public int getNumberParticles() {
        return numberParticles;
    }

    public double getBoardSide() {
        return boardSide;
    }

    public double getVelocity() {
        return velocity;
    }

    public int getPrint_time() {
        return print_time;
    }

    public int getTotal_time() {
        return total_time;
    }

}
