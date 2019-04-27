package ar.edu.itba.ss;


import ar.edu.itba.ss.Model.Analytic;
import ar.edu.itba.ss.Model.Beeman;
import ar.edu.itba.ss.Model.Gear;
import ar.edu.itba.ss.Model.Velvet;
import ar.edu.itba.ss.utils.ConfigurationParser;

public class App
{
    public static void main( String[] args ) throws Exception {
        ConfigurationParser c = new ConfigurationParser();
        c.parse("configuration.txt");
        Analytic a = new Analytic(c);
        a.run();


        Gear g = new Gear(c);
        g.run();

        Beeman b = new Beeman(c);
        b.run();

        Velvet v = new Velvet(c);
        v.run();
    }
}
