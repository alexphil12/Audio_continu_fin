package UI;


import Audio.AudioIO;
import Audio.AudioProcessor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Group;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class HelloApplication extends Application {
    static int compte=0;
    static int compte2=0;
    static public AudioProcessor AP;
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            root.setTop(createToolbar());
            root.setBottom(createStatusbar());
            root.setCenter(createMainContent());
            Scene scene = new Scene(root, 1525, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("The JavaFX audio processor");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Node createToolbar() {
        Button button = new Button("Lance le processor");
        Button button2 = new Button("Arrête le processor");
        ToolBar tb = new ToolBar(button, new Label("Sélection de l'input"), new Separator());
        ComboBox<String> cb=new ComboBox();
        ComboBox<String> cbin = new ComboBox<>();
        ComboBox<String> cbout = new ComboBox<>();
        ComboBox<Number> cbfreq = new ComboBox<>();
        ComboBox<Number> cbtemps = new ComboBox<>();
        cbfreq.getItems().addAll(2000, 4000, 8000, 16000);//les valeurs sont telles que freq_ech*temps_frame/1000=2^n pour que le calcul de la fft soit toujours possible.
        cbtemps.getItems().addAll(256, 512, 1024, 2048);
        cbout.getItems().addAll("Default Audio Device", "Port Speakers (Realtek(R) Audio)", "Périphérique audio principal", "Speakers (Realtek(R) Audio)");
        cbin.getItems().addAll("Default Audio Device", "Microphone Array (Realtek(R) Audio)", "Pilote de capture audio principal", "Port Microphone Array (Realtek(R) Au");
        cb.getItems().addAll("Sortie par défaut","Filtre_passe_bas","Filtre_passe_haut","écho");
        tb.getItems().addAll(cbin,new Label("Sélection de l'output"), cbout, button2, new Label("Fréq_éch(hz)"), cbfreq, new Label("Temps frame(ms)"), cbtemps,new Label("mode"),cb);
        button.setOnAction(event -> {
            try {
                AudioIO.start_Audio_Processing(cbin.getValue(), cbout.getValue(), (int) cbfreq.getValue(), (int) cbtemps.getValue(),cb.getValue());
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            System.out.println("début de l'audio processing");
        });
        button2.setOnAction(actionEvent -> {
            AudioIO.stop_Audio_Processing();
            System.out.println("fin de l'audio processing");
        });
        return tb;
    }

    private Node createStatusbar() {
        HBox statusbar = new HBox();

        statusbar.getChildren().addAll(new Label("Effet Audio du processeur"),new Separator());
        return statusbar;
    }

    private Node createMainContent(){
        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane);
        Pane pane1 = new Pane();
        BorderPane pane2 = new BorderPane();
        Pane pane3=new Pane();
        HBox controlbar=new HBox();
        Pane pane4=new Pane(controlbar);
        rootPane.setRight(pane3);
        rootPane.setCenter(pane1);
        rootPane.setLeft(pane2);
        rootPane.setTop(pane4);
        Button b=new Button("Initialisation affichage");
        Button b1=new Button("Pause, start fft");
        NumberAxis xAxisfreq = new NumberAxis(0,4000, 1000);
        xAxisfreq.setLabel("Fréquence en hertz");
        NumberAxis yAxisfreq = new NumberAxis(-50, 0, 10);
        yAxisfreq.setLabel("Amplitude du Spectre en dB");
        SignalView sigfreq = new SignalView(xAxisfreq, yAxisfreq);
        sigfreq.setPrefWidth(500);
        sigfreq.setMaxHeight(400);
        sigfreq.setTitle("FFT du signal");
        sigfreq.setVerticalGridLinesVisible(false);
        sigfreq.setHorizontalGridLinesVisible(false);

        Button b1temp=new Button("Pause, lance temp");
        NumberAxis xAxis = new NumberAxis(0, 4096 ,1000);
        xAxis.setLabel("échantillon");
        NumberAxis yAxis = new NumberAxis(-1, 1, 0.5);
        yAxis.setLabel("intensité ");
        SignalView sigtemp = new SignalView(xAxis, yAxis);
        sigtemp.setTitle("Signal sonore");
        sigtemp.setPrefWidth(500);
        sigtemp.setMaxHeight(400);
        sigtemp.setHorizontalGridLinesVisible(false);
        sigtemp.setVerticalGridLinesVisible(false);
        final Boolean bool2[]={true};
        final boolean booltemp [] = {true};
        pane1.getChildren().addAll(sigfreq,b);
        Thread thread=new Thread() {
            @Override
            public void run() {
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        compte2++;
                        SignalView.Update_data_fft(bool2[0], sigfreq, compte2);
                        SignalView.Update_data_sig(booltemp[0], sigtemp, compte2);
                        if (compte2 == 4) {
                            compte2 = 0;
                        }


                    }
                };
                timer.start();
            }
        };
        b1.setOnAction(actionEvent ->{if(bool2[0]==true){
            bool2[0]=false;}
        else{
            bool2[0]=true;}});


        b1temp.setOnAction(actionEvent -> {if(booltemp[0]==true){
            booltemp[0]=false;}
        else{
            booltemp[0]=true;}});
        pane2.setCenter(sigtemp);
        NumberAxis xAxis2 = new NumberAxis(0, 10, 1);
        xAxis2.setLabel("Temps");
        NumberAxis yAxis2 = new NumberAxis(-1, 1, 0.5);
        yAxis2.setLabel("Fréquence");
        SignalView sigfreq2 = new SignalView(xAxis2, yAxis2);
        sigfreq2.setPrefWidth(500);
        sigfreq2.setMaxHeight(400);
        sigfreq2.setTitle("Spectrogramme");
        sigfreq2.setHorizontalGridLinesVisible(false);
        sigfreq2.setVerticalGridLinesVisible(false);
        pane3.getChildren().add(sigfreq2);
        Vumeter c=new Vumeter(50,500);
        GraphicsContext gc= c.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(30,50,50,250);
        Text text=new Text(20,30,"Level dB");
        text.setFill(Color.BLACK);
        Text anno1=new Text(20,60,"0");
        Text anno2=new Text(10,105,"-10");
        Text anno3=new Text(10,150,"-20");
        Text anno4=new Text(10,195,"-30");
        Text anno5=new Text(10,240,"-40");
        Text anno6=new Text(10,285,"-50");
        Thread threadlevel=new Thread() {
            @Override
            public void run() {
                AnimationTimer timer12 = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        Vumeter.update_level(gc);
                    }
                };
                timer12.start();
            }


        };
        b.setOnAction(actionEvent -> {thread.start();System.out.println("traitement initié");});
        Button b3=new Button("lance capture du niveau");
        b3.setOnAction(actionEvent -> {threadlevel.start();});
        pane2.setLeft(c);
        pane2.getChildren().addAll(text,anno1,anno2,anno3,anno4,anno5,anno6);
        controlbar.getChildren().addAll(b,new Separator(),b1,new Separator(),b1temp,new Separator(),b3);
        return (rootPane);
        // ici en utilisant g.getChildren().add(...) vous pouvez ajouter tout élément graphique souhaité de type Nod
    }



};





