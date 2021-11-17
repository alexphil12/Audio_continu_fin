package Maths;

import java.util.Arrays;

import static Maths.Complexe.fill_tab;

public class FFT {
    public static Complexe[] fft(Complexe[] x) {
        int n = x.length;
        // Implémentation récursive
        // cas n=1
        if (n == 1) return new Complexe[] { x[0] };

        // Algorithme FFT de Cooley Turkey il faut donc un signal de longueur 2^n
        if (n % 2 != 0) {
            throw new IllegalArgumentException("n n'est pas une puissance de 2");
        }

        // Exécution de la fft sur les termes paires
        Complexe[] paire = new Complexe[n/2];
        for (int k = 0; k < n/2; k++) {
            paire[k] = x[2*k];
        }
        Complexe[] paireFFT = fft(paire);

        // Exécution de la fft sur les termes impaires
        Complexe[] impaire  = new Complexe[n/2];
        for (int k = 0; k < n/2; k++) {
            impaire[k] = x[2*k + 1];
        }
        Complexe[] impaireFFT = fft(impaire);

        // On combine les résultats
        Complexe[] y = new Complexe[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complexe wk = new Complexe(Math.cos(kth), Math.sin(kth));
            y[k]       = paireFFT[k].add (wk.Multi(impaireFFT[k]));
            y[k + n/2] = paireFFT[k].minus(wk.Multi(impaireFFT[k]));
        }
        return y;
    }
    public static Complexe[] DoubletoComplexe(double [] input_reel){
        int n=input_reel.length;
        Complexe[] y=new Complexe[n];
        y=fill_tab(y);
        for(int k=0;k<n;k++){
            y[k].setRe(input_reel[k]);
            y[k].setIm(0);
        }
        return (y);
    }
    public static double[] modulefft(Complexe [] sig){
        int n=sig.length;
        double[] y=new double[n];
        for(int k=0;k<n;k++){
            y[k]=sig[k].module();//normalisation pour simplifier l'affichage.
        }
        return(y);
    }

    public static void main(String args[]){
        int l=(int)Math.pow(2,10);
        double [] x=new double[l];
        for(int j=0;j<x.length;j++){
            x[j]=Math.sin(2*Math.PI*(50.0/1024.0)*j);
        }
        double[] x1=modulefft(fft(DoubletoComplexe(x)));
        for(int k=0;k<x.length;k++){
            System.out.println("Y"+ k +"="+x1[k]);
        }

    }

}
