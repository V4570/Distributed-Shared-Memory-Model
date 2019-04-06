public class Calc_Thread extends Thread{

    private static volatile double result = 0.0;

    private long start;
    private long stop;
    private double part;
    private double step;

    public Calc_Thread(long numSteps, long start, long stop){
        this.start = start;
        this.stop = stop;
        this.step = 1.0 / (double)numSteps;
    }

    @Override
    public void run() {
        /* do computation */
        System.out.println(this.getName() + " :: Start=" + this.start + ", Stop=" + this.stop);
        for (long i=this.start; i < this.stop; ++i) {
            double x = ((double) i + 0.5) * this.step;
            this.part += 4.0 / (1.0 + x * x);
        }

        updateResult();
    }

    private synchronized void updateResult(){
        
        result += this.step * this.part;
        System.out.println(this.getName() +" "+ this.part + " "+ getResult());
    }

    static double getResult(){
        return result;
    }
}
