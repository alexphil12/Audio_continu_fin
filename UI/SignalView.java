package UI;

import Audio.AudioSignal;
import javafx.scene.chart.Axis;
import javafx.scene.chart.*;
import javafx.animation.AnimationTimer;

import static Audio.AudioProcessor.inputSignal;

public class SignalView extends LineChart {
    public SignalView(Axis<Number> axis, Axis<Number> axis1) {
        super(axis, axis1);
    }
    private XYChart.Series tab;

    public static void main(String args[]){

    }

}





