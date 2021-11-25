package Audio;
import Maths.Complexe;
import UI.SignalView;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.XYChart;

import javax.sound.sampled.*;

import static Maths.FFT.*;
import static Maths.Complexe.*;

public class AudioProcessor implements Runnable {
    public static AudioSignal inputSignal, outputSignal;
    private TargetDataLine audioInput;
    private SourceDataLine audioOutput;
    private String fonctio;
    private boolean isThreadRunning;// makes it possible to "terminate" thread

         /** Creates an AudioProcessor that takes input from the given TargetDataLine, and plays back
  * to the given SourceDataLine.
  * @param frameSize the size of the audio buffer. The shorter, the lower the latency. */
         public AudioProcessor(TargetDataLine audioInput, SourceDataLine audioOutput, FrameSize frameSize,String mode_fonctio) {
             this.audioInput=audioInput;
             this.audioOutput=audioOutput;
             this.inputSignal=new AudioSignal(frameSize);
             this.outputSignal=new AudioSignal(frameSize);
             this.fonctio=mode_fonctio;
         }

         /** Audio processing thread code. Basically an infinite loop that continuously fills the sample
  * buffer with audio data fed by a TargetDataLine and then applies some audio effect, if any,
  * and finally copies data back to a SourceDataLine.*/
         @Override
 public void run() {
         isThreadRunning = true;
         while (isThreadRunning) {
             inputSignal.recordFrom(audioInput);
             if(fonctio=="Sortie par défaut"){
             outputSignal.setFrom(inputSignal);}
             if(fonctio=="Filtre_passe_bas"){
                 filtre_passe_bas(inputSignal,1000,(double)inputSignal.getFrameSize().getFreq_ech(), outputSignal);}
             if(fonctio=="Filtre_passe_haut"){
                 filtre_passe_haut(inputSignal,2000,(double)inputSignal.getFrameSize().getFreq_ech(),outputSignal);}
             if(fonctio=="écho"){
                 echo_num(inputSignal,outputSignal,500,0.5);
             }
             // your job: copy inputSignal to outputSignal with some audio effect

             outputSignal.playTo(audioOutput);
             }
         }

         /** Tells the thread loop to break as soon as possible. This is an asynchronous process. */
         public void terminateAudioThread() {
             isThreadRunning=false;
         }
         public void filtre_passe_bas(AudioSignal input,double fc,double fe,AudioSignal output) {
             int n=input.getSampleBuffer().length;
             double g=fe/(n*fc);
             Complexe[] sig=new Complexe[n];
             Complexe[] spectre = fft(DoubletoComplexe(input.getSampleBuffer()));
             Complexe[] filtre = new Complexe[n];
             filtre=fill_tab(filtre);
             sig=fill_tab(sig);
             for(int k=0;k<n/2;k++){
                 filtre[k].setRe(1/(1+(k*g)*(k*g)));          //la fonction de transfert est un passe bas d'ordre 1 classique
                 filtre[k].setIm(-(k*g)/(1+(k*g)*(k*g)));  //sont gain statique est de 1 et il est mis sous forme algébrique(
                 filtre[n-k-1].setRe(filtre[k].getRe());        //enfin on l'applique sur le spectre entre 0 et fe/2 et sur la partie fe/2 à fe.
                 filtre[n-k-1].setIm(filtre[k].getIm());        // en le copiant symétriquement.
             }
             for(int k=0;k<n;k++){
                 spectre[k]=Complexe.multi_stat(spectre[k],filtre[k]);               //application du filtrage
             }
             sig=ifft(spectre);
             for(int k=0;k<n;k++){
                 output.setSample(k,sig[k].getRe()); // calcul de la sortie
             }
         }
         public void filtre_passe_haut(AudioSignal input,double fc,double fe, AudioSignal output){
             int n=input.getSampleBuffer().length;
             double g=fe/(n*fc);
             Complexe[] sig=new Complexe[n];
             Complexe[] spectre = fft(DoubletoComplexe(input.getSampleBuffer()));
             Complexe[] filtre = new Complexe[n];
             filtre=fill_tab(filtre);
             sig=fill_tab(sig);
             for(int k=0;k<n/2;k++){
                 filtre[k].setRe(((k*g)*(k*g))/(1+(k*g)*(k*g)));          //la fonction de transfert est un passe haut d'ordre 1 classique
                 filtre[k].setIm((k*g)/(1+(k*g)*(k*g)));  //sont gain statique est de 1 et il est mis sous forme algébrique( partie reel , partie imaginaire)
                 filtre[n-k-1].setRe(filtre[k].getRe());        //enfin on l'applique sur le spectre entre 0 et fe/2 et sur la partie fe/2 à fe.
                 filtre[n-k-1].setIm(filtre[k].getIm());        // en le copiant symétriquement.
             }
             for(int k=0;k<n;k++){
                 spectre[k]=multi_stat(spectre[k],filtre[k]);                  //application du filtrage
             }
             sig=ifft(spectre);
             for(int k=0;k<n;k++){
                 output.setSample(k,sig[k].module());// calcul de la sortie
             }
         }
         public void echo_num(AudioSignal input,AudioSignal output,int sample_retard,double ampli){
             double []sig=input.getSampleBuffer();
             assert(sample_retard<sig.length);
             int n=sig.length;
             for(int k=0;k<n-sample_retard;k++){
                 output.setSample(k,sig[k]+ampli*sig[k+sample_retard]);
             }
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
         public String getFonctio(){return(fonctio);}
         public void setFonctio(String fonc){this.fonctio=fonc;}




         /* an example of a possible test code */
         public static void main(String[] args) throws LineUnavailableException {
         FrameSize framesize=new FrameSize(8000,256);
         framesize.set_sample();
         TargetDataLine inLine = AudioIO.obtainAudioInput("Default Audio Device", 8000);
         SourceDataLine outLine = AudioIO.obtainAudioOutput("Default Audio Device", 8000);
         AudioProcessor as = new AudioProcessor(inLine, outLine,framesize,"Sortie par défaut");
         inLine.open(); inLine.start(); outLine.open(); outLine.start();
         new Thread(as).start();
         System.out.println("A new thread has been created!");
         as.terminateAudioThread();
         System.out.println("c'est fini");

         }
 }
