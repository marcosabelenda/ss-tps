package ar.edu.itba.ss;


import ar.edu.itba.ss.Model.Analytic;
import ar.edu.itba.ss.utils.ConfigurationParser;

public class App
{
    public static void main( String[] args ) throws Exception {
        ConfigurationParser c = new ConfigurationParser();
        c.parse("configuration.txt");
        Analytic a = new Analytic(c);
        a.run(10000,1);
    }
}
