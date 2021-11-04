package Maths;

public class Complexe {
    private double re;
    private double im;

    public Complexe(double re,double im){
        this.re=re;
        this.im=im;
    }
    public Complexe add(Complexe Z){
        return(new Complexe(this.re+Z.re,this.im+Z.im));
    }
    public Complexe Multi(Complexe Z){
        return(new Complexe(this.re*Z.re-this.im*Z.im,this.re*Z.im+this.im*Z.re));
    }
    public Complexe expo_complexe_multipli(double R,double theta){
        Complexe h=new Complexe(R*Math.cos(theta),R*Math.sin(theta));
        return(this.Multi(h));

    }
    public double module(Complexe z){
        return(Math.sqrt(z.re*z.re+z.im*z.im));
    }
    public double argument(Complexe z){
        return Math.atan2(im, re);
    }
    public String ToString() {
            if (im == 0) return re + "";
            if (re == 0) return im + "i";
            if (im <  0) return re + " - " + (-im) + "i";
            return re + " + " + im + "i";
        }


}
