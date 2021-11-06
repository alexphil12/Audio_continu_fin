package Audio;
import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;


public class Main_test_2 {
    public static Mixer mixer;
    public static void main(String[] args) throws LineUnavailableException, InterruptedException {
        AudioFormat format=new AudioFormat(8000,8,1,true,true);
        SourceDataLine sourline=AudioSystem.getSourceDataLine(format);
        sourline.open();

        TargetDataLine targetline=AudioSystem.getTargetDataLine(format);
        targetline.open();

        final ByteArrayOutputStream out=new ByteArrayOutputStream();

        Thread sourceThread=new Thread(){
            @Override public void run(){
                sourline.start();
                while (true){
                    sourline.write(out.toByteArray(),0,out.toByteArray().length);
                }

            }
        };
        Thread targetThread=new Thread(){
            @Override public void run(){
                targetline.start();
                byte[] data=new byte[targetline.getBufferSize()];
                int readBytes;
                while (true){
                    readBytes=targetline.read(data,0,data.length);
                    out.write(data,0,readBytes);
                }


            }
        };
        targetThread.start();
        System.out.println("début d'enregistrement");
        targetThread.sleep(10000);
        targetThread.stop();
        System.out.println("fin de l'enregistrement");
        targetline.stop();
        targetline.close();

        sourceThread.start();
        System.out.println("début du play");
        sourceThread.sleep(10000);
        System.out.println("fin du play");
        sourline.stop();
        sourline.close();

    }
}
