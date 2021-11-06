package Audio;

    public class FrameSize {
        private int freq_ech;              //Avec cette réalisation on connait le temps d'une frame
                                           //et on déduit son nombre de sample en multipliant par la fréquence.
        private int temps_frame_ms;

        private int nombre_de_sample;

        public void set_sample(){  //Il faut toujours appelé set_sample après avoir construit une framesize.
            this.nombre_de_sample=this.freq_ech*this.temps_frame_ms/1000;}// le temps est ici en
        public void setFreq_ech(int i) {                                    //en ms.
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

