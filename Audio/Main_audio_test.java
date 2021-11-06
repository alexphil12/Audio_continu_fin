package Audio;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main_audio_test {
    public static Mixer mixer;
    public static void main(String[] args)throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        //Mixer.Info[] mixInfo=AudioSystem.getMixerInfo();
        //for(Mixer.Info info : mixInfo)
        //{
        //    System.out.println(info.getName() +"---"+info.getDescription());
        //}
        Scanner scanner=new Scanner(System.in);
        File file=new File("C:\\Users\\alexa\\OneDrive\\Bureau\\musique_java\\La_quete.wav");
        AudioInputStream audiostream=AudioSystem.getAudioInputStream(file);
        Clip clip=AudioSystem.getClip();
        clip.open(audiostream);
        String response="";
        while(!response.equals("Q")){
            System.out.println("P=play, S=Stop, R=reset, Q=Quit");
            System.out.print("Enter your choice: ");
            response= scanner.next();
            response=response.toUpperCase();
            switch(response){
                case("P"):clip.start();
                    break;
                case("S"):clip.stop();
                    break;
                case("R"):clip.setMicrosecondPosition(0);
                    break;
                case("Q"):clip.close();
                    break;
                default: System.out.println("Commande non valide");
            }

        }
        System.out.println("Astal vuego !!!");









    }
}