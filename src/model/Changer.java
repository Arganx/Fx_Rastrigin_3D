package model;

/**
 * Created by qwerty on 14-Dec-17.
 */
public class Changer {

    private double min;
    private double max;

    private double mymin;
    private double mymax;

    public Changer() {
        min = 10.0;
        max = 54.522867132120396;

        mymin = -1;
        mymax = 1;

    }

    public double from_01_to_normal(double number)
    {
        return (max-min)/(mymax-mymin)*(number-mymax)+max;
    }

    public double from_normal_to_01(double number)
    {
        // ( x - min ) / ( max - min )
        //(max'-min')/(max-min)*(value-max)+max'
            return (mymax-mymin)/(max-min)*(number-max)+mymax;
    }

    public double activation(Double sum)
    {
        return(2/(1+Math.pow(Math.E,-2*sum)))-1;
    }
}
