package model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by qwerty on 17-Oct-17.
 */
public class Neuron_linear extends Neuron {
    private double[] weights;
    private double error;
    private Double biasweight;
    private Double lr = new Double(0.1);

    public Double getLr() {
        return lr;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double get_weight(int weight_number)
    {
        return weights[weight_number];
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setLr(Double lr) {
        this.lr = lr;
    }

    private double activation(Double sum)
    {
        return sum;
    }

    public double activation_derrivative(double x)
    {
        return 1;
    }

    Neuron_linear()
    {

    }
    Neuron_linear(int size)
    {
        error=0;
        weights = new double[size];
        for(int i=0;i<weights.length;i++) {
            weights[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
        biasweight=ThreadLocalRandom.current().nextDouble(-1, 1);
    }

    public void show()
    {
        for(int i=0;i<weights.length;i++) {
            System.out.println(weights[i]);
        }
        System.out.println(biasweight);
    }



    public double sum(double[] inputs)
    {
        Double sum= 0.0;
        for(int i=0;i<weights.length;i++)
        {
            sum+=inputs[i]*weights[i];
        }
        sum+=biasweight;
        return sum;
    }

    public Double guess(double[] inputs)    //mozna podac wiecej wejsc niz rozmiar neuronu ale nie beda one uwzgledniane
    {
        Double sum= 0.0;
        for(int i=0;i<weights.length;i++)
        {
            sum+=inputs[i]*weights[i];
        }
        sum+=biasweight;
        Double out = activation(sum);

        return out;
    }

    public void train(double[] input, Integer target)
    {

        //double guess =guess(input);
        //double error = target - guess;

        for(int i=0;i<weights.length;i++)
        {
            weights[i]+=error*input[i]*lr;
        }
        biasweight+=error*lr;

    }

}
