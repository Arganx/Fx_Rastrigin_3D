package model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by qwerty on 25-Nov-17.
 */
public class TestData {
    private double x1;
    private double x2;

    private double result;

    private double[] tab;

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double[] getTab() {
        return tab;
    }

    public double getResult() {
        return result;
    }

    public TestData() {
        x1= ThreadLocalRandom.current().nextDouble(-2., 2.);
        x2= ThreadLocalRandom.current().nextDouble(-2., 2.);

        tab = new double[] {x1,x2};

        result = f();
    }

    public TestData(double x1, double x2) {
        this.x1 = x1;
        this.x2 = x2;
        tab = new double[] {x1,x2};

        result = f();
    }



    private double f()
    {
        double sum=0;
        for(int i=0;i<2;i++)
        {
            sum+=Math.pow(tab[i],2)-10*Math.cos(2*Math.PI*tab[i]);
        }
        return 10*3+sum;
    }


    public void show()
    {
        for(int i=0;i<2;i++)
        {
            System.out.println("x"+(i+1)+": "+tab[i]);
        }
        System.out.println("Wynik: "+result);
    }
}
