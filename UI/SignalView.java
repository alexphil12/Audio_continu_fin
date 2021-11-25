package UI;

import Audio.AudioIO;
import Audio.AudioProcessor;
import Audio.AudioSignal;
import javafx.scene.chart.Axis;
import javafx.scene.chart.*;
import javafx.animation.AnimationTimer;

import static Audio.AudioProcessor.inputSignal;
import static Maths.FFT.*;

public class SignalView extends LineChart {
    public SignalView(Axis<Number> axis, Axis<Number> axis1) {
        super(axis, axis1);
    }
    private XYChart.Series tab;
    public static AudioProcessor AP;
    public void setTab(XYChart.Series tab){
        this.tab=tab;
    }
    public XYChart.Series getTab(){
        return(this.tab);
    }

    public static void Update_data_sig(Boolean update,SignalView view,int compte){
        XYChart.Series serie=new XYChart.Series();
        if(update==true){
            for(int k=0;k<AP.getOutputSignal().getSampleBuffer().length;k++){
                serie.getData().add(new XYChart.Data(k,AP.getOutputSignal().getSample(k)));
            }
            view.getData().add(serie);
            if(compte==4){
                view.getData().clear();
            }
        }
    };
    public static void Update_data_fft(Boolean update,SignalView view,int compte){
        XYChart.Series serie = new XYChart.Series();
        double[] sig=modulefft(fft(DoubletoComplexe(AP.getOutputSignal().getSampleBuffer())));
        if (update == true) {
            for(int k=0;k<AP.getOutputSignal().getSampleBuffer().length/2;k++){
                double j=k*((double)AP.getOutputSignal().getFrameSize().getFreq_ech()/((double)AP.getOutputSignal().getSampleBuffer().length));
                serie.getData().add(new XYChart.Data(j,sig[k]));
            }
            view.getData().add(serie);
            if(compte==4){
                view.getData().clear();
            }
        }

    }


}





