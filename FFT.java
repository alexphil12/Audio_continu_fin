package Maths;

public class FFT {
    public static Complexe[] fft(Complexe[] x) {
        int n = x.length;
        // Implémentation récursive
        // cas n=1
        if (n == 1) return new Complexe[] { x[0] };

        // Algorithme FFT de Cooley Turkey
        if (n % 2 != 0) {
            throw new IllegalArgumentException("n n'est pas une puissance de 2");
        }

        // compute FFT of even terms
        Complexe[] paire = new Complexe[n/2];
        for (int k = 0; k < n/2; k++) {
            paire[k] = x[2*k];
        }
        Complexe[] paireFFT = fft(paire);

        // compute FFT of odd terms
        Complexe[] impaire  = new Complexe[n/2];
        for (int k = 0; k < n/2; k++) {
            impaire[k] = x[2*k + 1];
        }
        Complexe[] impaireFFT = fft(impaire);

        // combine
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
        for(int k=0;k<n;k++){
            y[k].re=input_reel[k];
            y[k].im=0;
        }
        return (y);
    }
    public static double[] modulefft(Complexe [] sig){
        int n=sig.length;
        double[] y=new double[n];
        for(int k=0;k<n;k++){
            y[k]=sig[k].module();
        }
        return(y);
    }
}
