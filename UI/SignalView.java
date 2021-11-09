package UI;

import Audio.AudioSignal;
import javafx.scene.chart.Axis;
import javafx.scene.chart.*;
import javafx.animation.AnimationTimer;

public class SignalView extends LineChart {
    public SignalView(Axis<Number> axis, Axis<Number> axis1) {
        super(axis, axis1);
    }
    private XYChart.Series tab;


    public void Update_data_sig_temp(Boolean update,AudioSignal sig,SignalView view) {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                XYChart.Series serie=new XYChart.Series();
                if(update==true){
                    for(int k=0;k<sig.getSampleBuffer().length;k++){
                        serie.getData().add(new XYChart.Data(k,sig.getSample(k)));
                    }
                    view.getData().add(serie);
                }
            }
        };
    }

}





