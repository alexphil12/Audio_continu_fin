package Audio;

import javax.sound.sampled.*;


public class AudioSignal {
    private double[] sampleBuffer;
    private double dBlevel;

    public AudioSignal(FrameSize framesize){
        this.sampleBuffer=new double[framesize.getNombre_de_sample()];
        this.dBlevel=0.0;
    }

    public void setFrom(AudioSignal other){
        int con;
        assert(other.sampleBuffer.length>= this.sampleBuffer.length);
        for(con=0;con<sampleBuffer.length;con++)
            this.sampleBuffer[con]=other.sampleBuffer[con];
        this.dBlevel=other.dBlevel;
    }

    public boolean recordFrom(TargetDataLine audioInput){
        byte [] byteBuffer= new byte[sampleBuffer.length*2];
        if(audioInput.read(byteBuffer,0,byteBuffer.length)==1)
            return false;
        double somme=0;
        for(int i=0;i<sampleBuffer.length;i++){
            sampleBuffer[i]=((byteBuffer[2*i]<<8)+byteBuffer[2*i+1])/32768.0;
            somme=somme+sampleBuffer[i]*sampleBuffer[i];}
        dBlevel=10*Math.log10(somme);
        return true;
    }
    public boolean playTo(SourceDataLine audioOutput){
        byte [] byteBuffer= new byte[sampleBuffer.length*2];
        for(int i=0;i<sampleBuffer.length;i++){
            byteBuffer[2*i]=(byte)(0xFF &(byte)(sampleBuffer[i]*32768)>>8);
            byteBuffer[2*i+1]=(byte)(0xFF &(byte) (sampleBuffer[i]*128));

        }
        if(audioOutput.write(byteBuffer,0,byteBuffer.length)==1)
            return(false);
        return(true);
    }
    public static void main(String[] args) throws LineUnavailableException, InterruptedException {
        FrameSize frame_size=new FrameSize(8000,2000);
        frame_size.set_sample();
        AudioFormat format=new AudioFormat(8000,16,1,true,true);
        TargetDataLine source= AudioSystem.getTargetDataLine(format);
        SourceDataLine sortie=AudioSystem.getSourceDataLine(format);
        AudioSignal sig=new AudioSignal(frame_size);
        source.open();
        source.start();
        sortie.open();
        sortie.start();
        Thread reco_thread=new Thread(){
            @Override
            public void run(){
                while(true){
                    sig.recordFrom(source);
                }
            }


        };
        Thread play_thread=new Thread(){
            @Override
            public void run(){
                while(true){
                    sig.playTo(sortie);
                }
            }
        };
        System.out.println("début enregistrement");
        reco_thread.start();
        reco_thread.sleep(5000);
        System.out.println("fin enregistrement");
        System.out.println("début play");
        play_thread.start();
        play_thread.sleep(5000);
        System.out.println("fin play");

        source.stop();
        source.close();
        sortie.stop();
        sortie.close();
        System.out.println("fin du code");
        System.out.println("longueur de sig:"+sig.sampleBuffer.length);
        System.out.println(sig.dBlevel);
    }

}
