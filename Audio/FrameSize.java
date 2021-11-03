package Audio;

public class FrameSize {
    private int freq_ech;
    private int temps_frame_ms;

    private int nombre_de_sample;

    public void set_sample(){
        this.nombre_de_sample=this.freq_ech*this.temps_frame_ms/1000;
    }

    public void setFreq_ech(int i) {
        this.nombre_de_sample=i;
    }
    public int getFreq_ech(){
        return(this.nombre_de_sample);
    }
    public void setTemps_frame_ms(int i){
        this.temps_frame_ms=i;
    }
    public int getTemps_frame_ms(){
        return(this.temps_frame_ms);
    }
    public int getNombre_de_sample(){
        return(this.nombre_de_sample);
    }
    FrameSize(int freq, int temps){
        this.temps_frame_ms=temps;
        this.freq_ech=freq;
    }

}

