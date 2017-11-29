package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Network;
import model.TestData;

/**
 * Created by qwerty on 29-Nov-17.
 */
public class ChartController {
    @FXML
    private ScatterChart chart;

    @FXML
    private Button button;

    @FXML
    private NumberAxis y;

    @FXML
    private NumberAxis x;

    @FXML
    private Label whatToDo;

    @FXML
    private TextField input_field;

    @FXML
    private Button confirm;

    @FXML
    private Text information;

    private int confirm_count =0;
    private int number_of_layers=0;
    private int[] number_of_neurons_in_layer;
    private int tmp=0;
    private boolean ready=false;

    private int N = 20;
    private StringBuilder stringBuilder = new StringBuilder("");

    @FXML
    public void initialize() {
        chart.setTitle("Wykres bledu");
        XYChart.Series series = new XYChart.Series();
        series.setName("Seria 1");
        chart.getData().add(series);
        stringBuilder.append("Liczba warstw: ");
        information.setText(stringBuilder.toString());


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //series.getData().add(new XYChart.Data(5,5));
                if(ready)
                {
                    int numberoftrainings = 5000;
                    double whatIgot=0;
                    double whatiwant =0;
                    Network network = new Network(number_of_layers,number_of_neurons_in_layer,2);
                    TestData[] testData = new TestData[N];
                    for(int i=0;i<N;i++)
                    {
                        testData[i]=new TestData();
                    }
                    for(int j=0;j<numberoftrainings;j++) {
                        for (int i = 0; i < N; i++) {
                            network.train(testData[i].getTab(), testData[i].getResult());
                            //whatIgot = network.guess(testData[i].getTab());
                            //whatiwant = testData[i].getResult();
                            //series.getData().add(new XYChart.Data(whatIgot, whatiwant));
                        }
                    }
                    for(int i=0;i<N;i++) {
                        System.out.println("Co mialo wyjsc: " + testData[i].getResult());
                        System.out.println("Co wyszlo: " + network.guess(testData[i].getTab()));
                        series.getData().add(new XYChart.Data(testData[i].getResult(), network.guess(testData[i].getTab())));
                    }


                }
            }
        });

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch (confirm_count) {
                    case 0:
                        if(!input_field.getText().equals("") && isNumeric(input_field.getText()))
                        {
                            number_of_layers=Integer.parseInt(input_field.getText());
                            number_of_neurons_in_layer = new int[number_of_layers];
                            confirm_count=1;
                            whatToDo.setText("Podaj liczbe neuronow w warstwie 0");
                            input_field.setText("");
                            stringBuilder.append(number_of_layers+"\n");
                            information.setText(stringBuilder.toString());
                        }
                        break;
                    case 1:
                        if(!input_field.getText().equals("") && isNumeric(input_field.getText()))
                        {
                            number_of_neurons_in_layer[tmp]=Integer.parseInt(input_field.getText());;
                            stringBuilder.append("Liczba neuronow w warstwie: " + tmp +": " +number_of_neurons_in_layer[tmp]+"\n");
                            information.setText(stringBuilder.toString());
                            tmp++;
                            input_field.setText("");
                            whatToDo.setText("Podaj liczbe neuronow w warstwie "+tmp);
                            if(tmp==number_of_layers)
                            {
                                confirm_count++;
                                whatToDo.setText("To juz wszystkie dane");
                                ready = true;
                            }
                        }
                        else
                        {
                            whatToDo.setText("Podaj liczbe neuronow w warstwie (musisz podac liczbe)");
                        }
                        break;
                    default:
                        whatToDo.setText("PODALES JUZ WSZYSTKO");
                }

            }
        });
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}