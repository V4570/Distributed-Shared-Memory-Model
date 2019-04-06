import java.io.*;
import java.net.Socket;

public class WorkerAlt {

    private static final String HOST = "localhost";
    private static final int PORT = 1337;

    public static void main(String[] args) throws IOException {
        int cores = Runtime.getRuntime().availableProcessors();
        Calc_Thread threads[] = new Calc_Thread[cores];

        Socket dataSocket = new Socket(HOST,PORT);
        InputStream inStream = dataSocket.getInputStream();
        BufferedReader in = new BufferedReader((new InputStreamReader(inStream)));
        OutputStream outStream = dataSocket.getOutputStream();
        PrintWriter out = new PrintWriter(outStream, true);


        String inmsg[] = in.readLine().split(",");
        long numSteps = Long.parseLong(inmsg[0]);
        long start = Long.parseLong(inmsg[1]);
        long stop = Long.parseLong(inmsg[2]);
        long diff = (long) Math.ceil((double)(stop - start)/cores);
        long start_i = start;
        long finish = start_i + diff;


        for(int i = 0; i<cores; i++){
            threads[i] = new Calc_Thread(numSteps, start_i, finish);
            threads[i].start();
            start_i = finish;
            finish = finish + diff;
        }

        for(int i = 0; i<cores; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) { /* empty */}
        }

        double pi = Calc_Thread.getResult();

        out.println(Double.toString(pi));
    }
}
