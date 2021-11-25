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
        return(new Complexe(this.re*Z.getRe()-this.im*Z.getIm(),this.re*Z.getIm()+this.im*Z.getIm()));
    }
    public static Complexe multi_stat(Complexe Z1,Complexe Z2){
        return(new Complexe(Z1.getRe()*Z2.getRe()-Z1.getIm()*Z2.getIm(),Z1.getIm()*Z2.getRe()+Z1.getRe()*Z2.getIm()));

    }
    public Complexe expo_complexe_multipli(double R,double theta){
        Complexe h=new Complexe(R*Math.cos(theta),R*Math.sin(theta));
        return(this.Multi(h));

    }
    public double module() {
        return(Math.sqrt(re*re+im*im));
    }
    public Complexe conjugate(){
        return (new Complexe(re,-im));
    }
    public Complexe scale(double alpha){
        return(new Complexe(alpha*re,alpha*im));
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
        Complexe h=new Complexe(-1,1);
        double k=h.argument();
        h=h.conjugate();
        System.out.println(k);
        System.out.println(h);

    }


}
