import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {
    private static final int PORT = 1337;

    public static void main(String[] args) throws IOException {

        ServerSocket connectionSocket = new ServerSocket(PORT);
        int cores = Runtime.getRuntime().availableProcessors();
        long numSteps = 0;
        MasterThread threads[] = new MasterThread[cores];

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
        System.out.println("Server started:\n    Cores:" + cores + "\n    Workers:" + cores + "\n    Steps:" + numSteps);

        long diff = numSteps/cores;
        long start = 0;
        long finish = diff;

        long startTime = System.currentTimeMillis();

        for(int i=0; i<cores; ++i){
            Socket dataSocket = connectionSocket.accept();
            threads[i] = new MasterThread(dataSocket, numSteps, start, finish);
            threads[i].start();
            System.out.println("Worker " + i + " connected.");
            start = finish;
            finish = finish + diff;
        }

        //wait for threads to finish
        for(int i = 0; i<cores; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) { /* empty */}
        }

        long endTime = System.currentTimeMillis();

        double pi = MasterThread.getPi();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}
