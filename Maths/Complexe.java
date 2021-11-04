package Maths;

public class Complexe {
    private double re;
    private double im;


    public static Complexe[] fill_tab(Complexe[] sig){
        int N=sig.length;
        for(int k=0;k<N;k++){
            sig[k]=new Complexe(Math.random(),Math.random());
        }
        return(sig);
    }
    public Complexe(double re,double im){
        this.re=re;
        this.im=im;
    }
    public void setRe(double re){
        this.re=re;
    }
    public void setIm(double im){
        this.im=im;
    }
    public double getRe(){
        return(re);
    }
    public double getIm(){
        return(im);
    }
    public Complexe(){
        this.re=0;
        this.im=0;
    }
    public Complexe add(Complexe Z){
        return(new Complexe(this.re+Z.re,this.im+Z.im));
    }
    public Complexe minus(Complexe Z){return((new Complexe(this.re-Z.re,this.im-Z.im)));}
    public Complexe Multi(Complexe Z){
        return(new Complexe(this.re*Z.re-this.im*Z.im,this.re*Z.im+this.im*Z.re));
    }
    public Complexe expo_complexe_multipli(double R,double theta){
        Complexe h=new Complexe(R*Math.cos(theta),R*Math.sin(theta));
        return(this.Multi(h));

    }
    public double module() {
        return(Math.sqrt(re*re+im*im));
    }

    public double argument(){
        return Math.atan2(im, re);
    }
    public String toString() {
            if (im == 0) return re + "";
            if (re == 0) return im + "i";
            if (im <  0) return re + " - " + (-im) + "i";
            return re + " + " + im + "i";
        }
    public static void main(String args[]){
        Complexe h=new Complexe(-1,0);
        double k=h.argument();
        System.out.println(k);

    }


}
