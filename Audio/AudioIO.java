package Audio;
import Maths.FFT.*;
import UI.SignalView;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.XYChart;

import javax.sound.sampled.*;
import java.util.Arrays;

import static Maths.FFT.*;

public class AudioIO {
    /** A collection of static utilities related to the audio system. */

         /** Displays every audio mixer available on the current system. */
         public static void printAudioMixers() {
            System.out.println("Mixers:");
            Arrays.stream(AudioSystem.getMixerInfo())
                    .forEach(e -> System.out.println("- name=\"" + e.getName() + "\" description=\"" + e.getDescription() + " by " + e.getVendor() + "\""));
            }

        /** @return a Mixer.Info whose name matches the given string.
        Example of use: getMixerInfo("Macbook default output") */
        public static Mixer.Info getMixerInfo(String mixerName) {
            // see how the use of streams is much more compact than for() loops!
            return Arrays.stream(AudioSystem.getMixerInfo()).filter(e -> e.getName().equalsIgnoreCase(mixerName)).findFirst().get();}
          /** Return a line that's appropriate for recording sound from a microphone.
          * Example of use:
          * * TargetDataLine line = obtainInputLine("USB Audio Device", 8000);* * @param mixerName a string that matches one of the available mixers.
          * @see //AudioSystem.getMixerInfo() which provides a list of all mixers on your system.
          * */
        public static TargetDataLine obtainAudioInput(String mixerName, int sampleRate) throws LineUnavailableException {
            AudioFormat format=new AudioFormat(sampleRate,16,1,true,true);
            if(mixerName=="Default Audio Device"){
                TargetDataLine tarline=AudioSystem.getTargetDataLine(format);
                return(tarline);
            }
            else{
                Mixer.Info info=getMixerInfo(mixerName);
                TargetDataLine tarline=AudioSystem.getTargetDataLine(format,info);
                return(tarline);
            }
        }


        /** Return a line that's appropriate for playing sound to a loudspeaker. */
        public static SourceDataLine obtainAudioOutput(String mixerName, int sampleRate) throws LineUnavailableException {
            AudioFormat format=new AudioFormat(sampleRate,16,1,true,true);
            if(mixerName=="Default Audio Device"){
                SourceDataLine sourline=AudioSystem.getSourceDataLine(format);
                return(sourline);
            }
        else{
            Mixer.Info info=getMixerInfo(mixerName);
            SourceDataLine sourline=AudioSystem.getSourceDataLine(format,info);
            return(sourline);
        }
        }
        public static void start_Audio_Processing(String inputMixer, String outputMixer,int freq_ech,int temps_frame_ms) throws LineUnavailableException {
            FrameSize framesize=new FrameSize(freq_ech,temps_frame_ms);
            framesize.set_sample();
            TargetDataLine targetline=obtainAudioInput(inputMixer,framesize.getFreq_ech());
            SourceDataLine sourceline=obtainAudioOutput(outputMixer,framesize.getFreq_ech());

            AudioProcessor AP=new AudioProcessor(targetline,sourceline,framesize);
            targetline.open();targetline.start();sourceline.open();sourceline.start();

            Thread Process=new Thread(AP);Process.start();
        }
        public static void  stop_Audio_Processing(){


        }
    public static void Update_data_sig(Boolean update,double[] sig,SignalView view) {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                XYChart.Series serie=new XYChart.Series();
                if(update==true){
                    for(int k=0;k<sig.length;k++){
                        serie.getData().add(new XYChart.Data(k,sig[k]));
                    }
                    view.getData().add(serie);
                }
            }
        };
}}
