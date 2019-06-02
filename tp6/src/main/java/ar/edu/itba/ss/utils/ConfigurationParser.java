package ar.edu.itba.ss.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationParser {
    private final static String HEIGHT = "height",
                                SEED = "seed",
                                WIDTH = "width",
                                CELL_SIDE = "cell_side",
                                WINDOW = "window",
                                MAX_R = "max_radius",
                                MIN_R = "min_radius",
                                PRINT_TIME = "print_time",
                                DELTA_TIME = "delta_time",
                                CANT_MAXIMA = "cantidad_maxima",
                                TOTAL_TIME = "total_time",
                                MAX_V = "max_velocity",
                                BETA = "beta",
                                TAO = "tao",
                                OUTSIDE_WIDTH = "outside_width";

    private final static String VALUE_SEPARATOR = ":";
    private final static int HEADER = 0, VALUE = 1;



    private double height;
    private double width;
    private double window;

    private double cellSide;

    private double maxR;
    private double minR;
    private double maxV;
    private double outsideWidth;

    private double beta, tao;

    private int cant_maxima;

    private int seed = 1;

    private double print_time;
    private double delta_time;
    private int total_time;


    public void parse(String fileName) throws IOException {

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null) {

            String [] args = line.split(VALUE_SEPARATOR);

            switch(args[HEADER]) {

                case SEED:
                    this.seed = Integer.parseInt(args[VALUE]);
                    break;

                case HEIGHT:
                    this.height = Double.parseDouble(args[VALUE]);
                    break;

                case MAX_R:
                    this.maxR = Double.parseDouble(args[VALUE]);
                    break;

                case MIN_R:
                    this.minR = Double.parseDouble(args[VALUE]);
                    break;

                case MAX_V:
                    this.maxV = Double.parseDouble(args[VALUE]);
                    break;

                case BETA:
                    this.beta = Double.parseDouble(args[VALUE]);
                    break;

                case TAO:
                    this.tao = Double.parseDouble(args[VALUE]);
                    break;

                case OUTSIDE_WIDTH:
                    this.outsideWidth = Double.parseDouble(args[VALUE]);
                    break;

                case WIDTH:
                    this.width = Double.parseDouble(args[VALUE]);
                    break;

                case CELL_SIDE:
                    this.cellSide = Double.parseDouble(args[VALUE]);
                    break;

                case WINDOW:
                    this.window = Double.parseDouble(args[VALUE]);
                    break;

                case PRINT_TIME:
                    this.print_time = Double.parseDouble(args[VALUE]);
                    break;

                case TOTAL_TIME:
                    this.total_time = Integer.parseInt(args[VALUE]);
                    break;

                case DELTA_TIME:
                    this.delta_time = Double.parseDouble(args[VALUE]);
                    break;

                case CANT_MAXIMA:
                    this.cant_maxima = Integer.parseInt(args[VALUE]);
                    break;

                default:

            }
        }
        bufferedReader.close();

    }

    public double getPrint_time() {
        return print_time;
    }

    public int getTotal_time() {
        return total_time;
    }

    public double getDelta_time() {
        return delta_time;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getWindow() {
        return window;
    }

    public double getCellSide() {
        return cellSide;
    }

    public int getSeed() {
        return seed;
    }

    public double getMaxR() {
        return maxR;
    }

    public double getMinR() {
        return minR;
    }

    public int getCant_maxima() {
        return cant_maxima;
    }

    public double getMaxV() {
        return maxV;
    }

    public double getBeta() {
        return beta;
    }

    public double getTao() {
        return tao;
    }

    public double getOutsideWidth() {
        return outsideWidth;
    }
}
