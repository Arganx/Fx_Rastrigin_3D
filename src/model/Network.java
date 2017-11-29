package model;

/**
 * Created by qwerty on 29-Nov-17.
 */
public class Network {
    private int number_of_layers;
    private int[] number_of_neurons_in_layer;
    private int number_of_inputs;
    private Neuron[][] layers;  //siec neuronow

    public Network(int number_of_layers,int[] number_of_neurons_in_layer,int number_of_inputs)
    {
        //inicjacja
        this.number_of_layers=number_of_layers;
        this.number_of_neurons_in_layer=number_of_neurons_in_layer;
        this.number_of_inputs=number_of_inputs;

        layers=new Neuron[number_of_layers][];
        //inicjacja zewnetrznej tablicy
        for(int i=0;i<number_of_layers;i++)
        {
            layers[i]=new Neuron[number_of_neurons_in_layer[i]];
        }

        //najpierw inicjuje warstwe 1 gdyz nie odwoluje sie ona do warstw poprzednich
        for(int i=0;i<number_of_neurons_in_layer[0];i++)    //petla po warstwach
        {
            layers[0][i]=new Neuron(number_of_inputs);  //wejsciami do neuronu sa wejscia do sieci
        }

        //nastepnie kolejne warstwy
        for(int i=1;i<number_of_layers-1;i++)    //petla po warstwach
        {
            for(int j=0;j<number_of_neurons_in_layer[i];j++) {  //petla po neuronach w warstwie
                layers[i][j] = new Neuron(number_of_neurons_in_layer[i-1]); //wejsciami do neuronu sa neurony poprzedniej warstwy
            }
        }

        //Dla ostatniej warstwy
        for(int j=0;j<number_of_neurons_in_layer[number_of_layers-1];j++) {  //petla po neuronach w ostatniej warstwie
            if(number_of_layers-1==0)
            {
                layers[number_of_layers - 1][j] = new Neuron_linear(number_of_inputs);
            }
            else {
                layers[number_of_layers - 1][j] = new Neuron_linear(number_of_neurons_in_layer[number_of_layers - 2]); //neuron z inna funkcja aktywacyjna
            }
        }

    }

    public double guess(double[] input)
    {
        //tworzenie tablicy pomocniczej
        double[][] help_tab=new double[number_of_layers][];
        for(int i=0;i<number_of_layers;i++) {
            help_tab[i]=new double[number_of_neurons_in_layer[i]];
        }

        //Dzialanie pierwszej warstwy
        for(int i=0;i<number_of_neurons_in_layer[0];i++)
        {
            help_tab[0][i]=layers[0][i].guess(input);
        }

        //Dzialanie reszty warstw
        for(int i=1;i<number_of_layers;i++)
        {
            for(int j=0;j<number_of_neurons_in_layer[i];j++)
            {
                help_tab[i][j]=layers[i][j].guess(help_tab[i-1]);
            }
        }
        //zakladam ze ostatnia warstwa ma 1 neuron
        return help_tab[number_of_layers-1][0]; //zwracam wynik ostatniego neuronu
    }


    public void train(double[] input,double target)
    {
        //tworzenie tablicy pomocniczej
        double[][] help_tab=new double[number_of_layers][];
        for(int i=0;i<number_of_layers;i++) {
            help_tab[i]=new double[number_of_neurons_in_layer[i]];
        }

        //Dzialanie pierwszej warstwy
        for(int i=0;i<number_of_neurons_in_layer[0];i++)
        {
            help_tab[0][i]=layers[0][i].guess(input);
        }

        //Dzialanie reszty warstw
        for(int i=1;i<number_of_layers;i++)
        {
            for(int j=0;j<number_of_neurons_in_layer[i];j++)
            {
                help_tab[i][j]=layers[i][j].guess(help_tab[i-1]);
            }
        }

        //wyliczanie errora TODO zerowanie errora

        double error = target-guess(input);
        layers[number_of_layers-1][0].setError(error);

        for(int i=number_of_layers-2;i>-1;i--)
        {
            for(int j=0;j<number_of_neurons_in_layer[i];j++)
            {
                for(int k=0;k<number_of_neurons_in_layer[i+1];k++) {
                    layers[i][j].setError(layers[i][j].getError() + layers[i + 1][k].getError() * layers[i + 1][k].getWeights()[j]);
                }
            }
        }

        //Modyfikacja wag
        double[] weights;
        double sum; //zabezpieczenie wartosci
        for(int i=0;i<number_of_layers;i++)
        {
            for(int j=0;j<number_of_neurons_in_layer[i];j++)
            {
                weights=layers[i][j].getWeights();
                if(i==0)
                {
                    sum=layers[i][j].sum(input);
                }
                else {
                    sum = layers[i][j].sum(help_tab[i - 1]);
                }
                for(int k=0;k<weights.length;k++) {
                    if(i==0) {
                        weights[k] += layers[i][j].getLr() * layers[i][j].getError() * layers[i][j].activation_derrivative(sum) *input[k];
                    }
                    else
                    {
                        weights[k] += layers[i][j].getLr() * layers[i][j].getError() * layers[i][j].activation_derrivative(sum) *help_tab[i-1][k];
                    }
                }
                layers[i][j].setBiasweight(layers[i][j].getBiasweight()+layers[i][j].getLr()*layers[i][j].getError()*layers[i][j].activation_derrivative(sum)*1);
            }
        }

        //restart errora
        layers[number_of_layers-1][0].setError(0);  //ostatni neuron

        for(int i=number_of_layers-2;i>-1;i--)  //inne neurony
        {
            for(int j=0;j<number_of_neurons_in_layer[i];j++)
            {
                    layers[i][j].setError(0);
            }
        }

    }
}
