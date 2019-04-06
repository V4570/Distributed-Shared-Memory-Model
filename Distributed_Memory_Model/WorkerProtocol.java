public class WorkerProtocol {

    public String calcPi(long numSteps, long start, long stop){
        double part = 0.0;
        double step = 1.0 /(double) numSteps;

        for (long i=start; i < stop; ++i) {
            double x = ((double) i + 0.5) * step;
            part += 4.0 / (1.0 + x * x);
        }

        return Double.toString(part * step);
    }
}
