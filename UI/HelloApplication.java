package UI;


import Audio.AudioIO;
import Audio.AudioProcessor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.Group;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            root.setTop(createToolbar());
            root.setBottom(createStatusbar());
            root.setCenter(createMainContent());
            root.setLeft(createSigtemp());
            root.setRight(createSpectro());
            Scene scene = new Scene(root, 1300, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("The JavaFX audio processor");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Node createToolbar() {
        Button button = new Button("Lance le processor");
        Button button1 = new Button("lance la fft");
        Button button2 = new Button("Arrête le processor");
        ToolBar tb = new ToolBar(button, new Label("Sélection de l'input"), new Separator());
        ComboBox<String> cbin = new ComboBox<>();
        ComboBox<String> cbout = new ComboBox<>();
        ComboBox<Number> cbfreq = new ComboBox<>();
        ComboBox<Number> cbtemps = new ComboBox<>();
        cbfreq.getItems().addAll(2000, 4000, 8000, 16000);//les valeurs sont telles que freq_ech*temps_frame/1000=2^n pour que le calcul de la fft soit toujours possible.
        cbtemps.getItems().addAll(256, 512, 1024, 2048);
        cbout.getItems().addAll("Default Audio Device", "Port Speakers (Realtek(R) Audio)", "Périphérique audio principal", "Speakers (Realtek(R) Audio)");
        cbin.getItems().addAll("Default Audio Device", "Microphone Array (Realtek(R) Audio)", "Pilote de capture audio principal", "Port Microphone Array (Realtek(R) Au");
        tb.getItems().addAll(cbin, button1, new Label("Sélection de l'output"), cbout, button2, new Label("Sélection de la fréquence(hz)"), cbfreq, new Label("Sélection du temps de frame (ms)"), cbtemps);
        button.setOnAction(event -> {
            try {
                AudioIO.start_Audio_Processing(cbin.getValue(), cbout.getValue(), (int) cbfreq.getValue(), (int) cbtemps.getValue());
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
        statusbar.getChildren().addAll(new Label("Name:"), new TextField(" "));
        return statusbar;
    }

    private Node createMainContent() {
        Region I = new Region();
        Pane u = new Pane();
        Button b=new Button("lance fft");
        NumberAxis xAxisfreq = new NumberAxis(0, 4096, 10);
        xAxisfreq.setLabel("Fréquence en hertz");
        NumberAxis yAxisfreq = new NumberAxis(0, 15, 0.5);
        yAxisfreq.setLabel("Amplitude du Spectre");
        SignalView sigfreq = new SignalView(xAxisfreq, yAxisfreq);
        Group h = new Group(sigfreq,b);
        Thread thread=new Thread(){
            @Override
            public void run(){
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        AudioIO.Update_data_fft(true,sigfreq);
                    }

                };
                timer.start();
            }
        };
        b.setOnAction(actionEvent -> {thread.start();System.out.println("traitement fft lancé");});
        return (h);
        // ici en utilisant g.getChildren().add(...) vous pouvez ajouter tout élément graphique souhaité de type Nod
    }

    private Node createSigtemp() {
        Button b = new Button("lance temp");
        NumberAxis xAxis = new NumberAxis(0, 4096 ,16);
        xAxis.setLabel("Nombre d'échantillon");
        NumberAxis yAxis = new NumberAxis(-1, 1, 0.1);
        yAxis.setLabel("intensité du signal en sortie");
        SignalView sigtemp = new SignalView(xAxis, yAxis);
        Canvas u=new Canvas(2,15);
        u.getGraphicsContext2D();
        Group k = new Group(sigtemp,b,u);
        Thread thread=new Thread(){
            @Override
            public void run(){
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        AudioIO.Update_data_sig(true,sigtemp);
                    }

                };
                timer.start();
            }
        };
        b.setOnAction(actionEvent -> {thread.start();System.out.println("traitement temps lancé");});
        return k;
    }

    private Node createSpectro() {
        NumberAxis xAxis = new NumberAxis(0, 10, 1);
        xAxis.setLabel("Temps");
        NumberAxis yAxis = new NumberAxis(-1, 1, 0.01);
        yAxis.setLabel("Fréquence");
        SignalView sigfreq = new SignalView(xAxis, yAxis);
        Group k = new Group(sigfreq);
        return k;
    }
}




