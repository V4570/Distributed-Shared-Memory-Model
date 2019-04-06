public class PiCalc_Threads{

    public static void main(String[] args){
        int cores = Runtime.getRuntime().availableProcessors();
        long numSteps = 0;
        Calc_Thread threads[] = new Calc_Thread[cores];

        if (args.length != 1) {
            System.out.println("arguments:  number_of_steps");
            System.exit(1);
        }
        try {
            numSteps = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("argument "+ args[0] +" must be long int");
            System.exit(1);
        }

        System.out.println("Number of cores/threads=" + cores);

        //calculation start
        long diff = numSteps/cores;
        long start = 0;
        long finish = diff;

        long startTime = System.currentTimeMillis();

        for(int i = 0; i<cores; i++){
            threads[i] = new Calc_Thread(numSteps, start, finish);
            threads[i].start();
            start = finish;
            finish = finish + diff;
        }
        //calculation finish

        for(int i = 0; i<cores; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) { /* empty */}
        }

        double pi = Calc_Thread.getResult();
        long endTime = System.currentTimeMillis();

        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}
