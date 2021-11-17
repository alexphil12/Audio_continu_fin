package Audio;
import UI.SignalView;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.XYChart;

import javax.sound.sampled.*;

import static Maths.FFT.*;

public class AudioProcessor implements Runnable {
    public static AudioSignal inputSignal, outputSignal;
    private TargetDataLine audioInput;
    private SourceDataLine audioOutput;
    private boolean isThreadRunning; // makes it possible to "terminate" thread

         /** Creates an AudioProcessor that takes input from the given TargetDataLine, and plays back
  * to the given SourceDataLine.
  * @param frameSize the size of the audio buffer. The shorter, the lower the latency. */
         public AudioProcessor(TargetDataLine audioInput, SourceDataLine audioOutput, FrameSize frameSize) {
             this.audioInput=audioInput;
             this.audioOutput=audioOutput;
             this.inputSignal=new AudioSignal(frameSize);
             this.outputSignal=new AudioSignal(frameSize);
         }

         /** Audio processing thread code. Basically an infinite loop that continuously fills the sample
  * buffer with audio data fed by a TargetDataLine and then applies some audio effect, if any,
  * and finally copies data back to a SourceDataLine.*/
         @Override
 public void run() {
         isThreadRunning = true;
         while (isThreadRunning) {
             inputSignal.recordFrom(audioInput);

             outputSignal.setFrom(inputSignal);
             // your job: copy inputSignal to outputSignal with some audio effect

             outputSignal.playTo(audioOutput);
             }
         }

         /** Tells the thread loop to break as soon as possible. This is an asynchronous process. */
         public void terminateAudioThread() {
             isThreadRunning=false;
         }

         // todo here: all getters and setters
         public void setInputSignal(AudioSignal other){
             this.inputSignal=other;
         }
         public void setOutputSignal(AudioSignal other){
             this.outputSignal=other;
         }
         public void setAudioOutput(SourceDataLine other){
            this.audioOutput=other;
         }
         public void setAudioInput(TargetDataLine other){
            this.audioInput=other;
         }
         public void setIsthreadrunning(boolean start){
             this.isThreadRunning=start;
         }
         public AudioSignal getInputSignal(){
             return(this.inputSignal);
         }
         public AudioSignal getOutputSignal(){
             return(this.outputSignal);
         }
         public TargetDataLine getAudioInput(){
             return(audioInput);
         }
         public SourceDataLine getAudioOutput(){
             return(audioOutput);
         }
         public boolean getisThreadRunning(){
             return(isThreadRunning);
         }
         public static void Lance_fft( SignalView view, boolean update) {
         new AnimationTimer() {
             @Override
             public void handle(long l) {
                 XYChart.Series serie=new XYChart.Series();
                 if(update==true){
                     for(int k=0;k<inputSignal.getSampleBuffer().length;k++){
                         double[] sig1=modulefft(fft(DoubletoComplexe(inputSignal.getSampleBuffer())));
                         serie.getData().add(new XYChart.Data(k,sig1[k]));
                     }
                     view.getData().add(serie);
                 }
             }
         };
    }





         /* an example of a possible test code */
         public static void main(String[] args) throws LineUnavailableException {
         FrameSize framesize=new FrameSize(8000,256);
         framesize.set_sample();
         TargetDataLine inLine = AudioIO.obtainAudioInput("Default Audio Device", 8000);
         SourceDataLine outLine = AudioIO.obtainAudioOutput("Default Audio Device", 8000);
         AudioProcessor as = new AudioProcessor(inLine, outLine,framesize);
         inLine.open(); inLine.start(); outLine.open(); outLine.start();
         new Thread(as).start();
         System.out.println("A new thread has been created!");
         as.terminateAudioThread();
         System.out.println("c'est fini");

         }
 }
