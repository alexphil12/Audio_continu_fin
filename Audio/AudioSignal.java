package Audio;

import javax.sound.sampled.*;

import java.nio.ByteBuffer;


public class AudioSignal {
    private double[] sampleBuffer;
    private double dBlevel;
    private FrameSize framesize;

    public AudioSignal(FrameSize framesize){
        this.sampleBuffer=new double[framesize.getNombre_de_sample()];
        this.dBlevel=0.0;
        this.framesize=framesize;
    }

    public void setFrom(AudioSignal other){
        int con;
        assert(other.sampleBuffer.length>= this.sampleBuffer.length);
        for(con=0;con<sampleBuffer.length;con++)
            this.sampleBuffer[con]=other.sampleBuffer[con];
        this.dBlevel=other.dBlevel;
    }

    public boolean recordFrom(TargetDataLine audioOutput){
        byte [] byteBuffer= new byte[sampleBuffer.length*2];
        if(audioOutput.read(byteBuffer,0,byteBuffer.length)==1)
            return false;
        double somme=0;
        for(int i=0;i<sampleBuffer.length;i++){
            sampleBuffer[i]=((byteBuffer[2*i]<<8)+byteBuffer[2*i+1])/32768.0;
            somme=somme+sampleBuffer[i]*sampleBuffer[i];}
        somme=somme/sampleBuffer.length;
        setdBlevel(10*Math.log10(somme));
        return true;
    }
    public boolean playTo(SourceDataLine audioInput){
        byte [] byteBuffer= new byte[sampleBuffer.length*2];
        double somme=0;
        for(int i=0;i<sampleBuffer.length/2;i++){
            byte [] inte=ByteBuffer.allocate(4).putInt((int)(sampleBuffer[i]*32768)).array();
            somme=somme+sampleBuffer[2*i]*sampleBuffer[2*i]+sampleBuffer[2*i+1]*sampleBuffer[2*i+1]; //on transforme le double en int entre -32767 et 32678
            byteBuffer[2*i]=inte[2];   //un int étant codé sur 4 bit on le représente ici dans un tableau de 4 bit
            byteBuffer[2*i+1]=inte[3]; //On prend les deux derniers bits du tableau pour le buffer.
        }
        setdBlevel(10*Math.log10(somme));
        if(audioInput.write(byteBuffer,0,byteBuffer.length)==1)
            return(false);
        return(true);
    }
    public void setSampleBuffer(double[] other){
        sampleBuffer=other;
    }
    public void setdBlevel(double level){
        this.dBlevel=level;
    }
    public double[] getSampleBuffer(){
        return(sampleBuffer);
    }
    public double getdBlevel(){
        return(dBlevel);
    }
    public double getSample(int i){
        return(sampleBuffer[i]);
    }
    public void setSample(int i, double value){
        sampleBuffer[i]=value;
    }
    public int getFramesize(){
        return(sampleBuffer.length);
    }
    public FrameSize getFrameSize(){
        return(framesize);
    }
    public void setFrameSize(FrameSize fram){
        this.framesize=fram;
    }

}