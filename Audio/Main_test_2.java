package Audio;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main_test_2 {
    public static Mixer mixer;
    public static void main(String[] args) throws LineUnavailableException, InterruptedException {
        AudioFormat format=new AudioFormat(8000,8,1,true,true);
        SourceDataLine sourline=AudioSystem.getSourceDataLine(format);
        sourline.open();
        sourline.start();




    }
}