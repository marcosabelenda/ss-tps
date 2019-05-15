package ar.edu.itba.ss.utils;

import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class SalidaMetrics {

    List<Pair<Double,Integer>> salieron;

    private int contador;
    private double dt;


    public SalidaMetrics(double dt) {
        this.salieron = new LinkedList<>();
        this.contador = 0;
        this.dt =dt;
    }

    public void addSalieron(int cantidad) {
//        if(cantidad!=0)
            salieron.add(new Pair<>(contador*dt,cantidad));
        contador++;
    }


    public void saveMetrics() {
        try(FileWriter fw = new FileWriter("salen.txt", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            for(Pair<Double,Integer> p: salieron) {
                out.println(p.getKey()+" "+p.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
