package UI;

import Audio.AudioProcessor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Vumeter extends Canvas {
    static public AudioProcessor AP;
     Vumeter(double x,double y){super(x,y);}

     public static void update_level(GraphicsContext gc){
         double dB = AP.getInputSignal().getdBlevel();
         double level_anno = -4 * dB + 50;
         gc.clearRect(30,50,50,250);
         gc.fillRect(30, (int)level_anno, 50, (int)(300.0 - level_anno));
         if(dB>-20){
             gc.setFill(Color.RED);
         }
         if(dB>-30){
             gc.setFill(Color.ORANGE);
         }
         else{
             gc.setFill(Color.GREEN);
         }
     }


}
