import java.io.*;
import java.net.Socket;

public class MasterThread extends Thread{

    private static volatile double pi = 0.0;

    private Socket dataSocket;
    private InputStream inStream;
    private BufferedReader in;
    private OutputStream outStream;
    private PrintWriter out;
    private double part;
    private long start;
    private long stop;
    private long numSteps;

    public MasterThread(Socket socket, long numSteps, long start, long stop){
        this.numSteps = numSteps;
        this.start = start;
        this.stop = stop;

        dataSocket = socket;
        try{

            inStream = dataSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(inStream));
            outStream = dataSocket.getOutputStream();
            out = new PrintWriter(outStream, true);
        }
        catch (IOException e){
            System.out.println("I/O Error: " + e);
        }
    }

    public void run(){

        out.println(this.numSteps + "," + this.start + "," + this.stop);
        try{
            this.part = Double.parseDouble(in.readLine());
            updatePi();
        }
        catch (IOException ioex){
            System.out.println("I/O Error: " + ioex);
        }
    }

    private synchronized void updatePi(){
        pi = pi + part;
    }

    public static double getPi(){
        return pi;
    }
}
