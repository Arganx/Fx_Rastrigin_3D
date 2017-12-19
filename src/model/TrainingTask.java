package model;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

/**
 * Created by qwerty on 17-Dec-17.
 */
public class TrainingTask implements Runnable {

    int N;

    private int number_of_layers;
    private int[] number_of_neurons_in_layer;
    private Changer changer;
    XYChart.Series series;

    public TrainingTask(int n, int number_of_layers, int[] number_of_neurons_in_layer,Changer changer,XYChart.Series series) {
        N = n;
        this.number_of_layers = number_of_layers;
        this.number_of_neurons_in_layer = number_of_neurons_in_layer;
        this.changer=changer;
        this.series=series;
    }

    @Override
    public void run() {
            int numberoftrainings = 90000;
            Network network = new Network(number_of_layers,number_of_neurons_in_layer,2);
            TestData[] testData = new TestData[N];
            for(int i=0;i<N;i++)
            {
                testData[i]=new TestData();
            }

            Integer result =0;
            int trening_counter =0;
            int helper =0;
            for(;;) {
                trening_counter++;
                for (int i = 0; i < N; i++) {
                    result+=network.train(testData[i].getTab(), changer.from_normal_to_01(testData[i].getResult()));
                }
                if(result==N)
                {
                    break;
                }
                result=0;

                helper++;
                if(helper==500) {
/*                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    helper=0;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            series.getData().remove(0,series.getData().size());
                            for (int i = 0; i < N; i++) {

                                series.getData().add(new XYChart.Data(testData[i].getResult(), changer.from_01_to_normal(network.guess(testData[i].getTab()))));
                            }
                        }
                    });
                }

            }

            System.out.println("Nauczono w " + trening_counter + " treningach");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                series.getData().remove(0,series.getData().size());
                for (int i = 0; i < N; i++) {

                    System.out.println("Co mialo wyjsc: " + testData[i].getResult());
                    System.out.println("Co wyszlo: " + changer.from_01_to_normal(network.guess(testData[i].getTab())));
                    series.getData().add(new XYChart.Data(testData[i].getResult(), changer.from_01_to_normal(network.guess(testData[i].getTab()))));
                }
            }
        });




    }
}
