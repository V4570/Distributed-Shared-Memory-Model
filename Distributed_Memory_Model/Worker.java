import java.io.*;
import java.net.Socket;

public class Worker {

    private static final String HOST = "localhost";
    private static final int PORT = 1337;

    public static void main(String[] args) throws IOException {

        Socket dataSocket = new Socket(HOST,PORT);
        InputStream inStream = dataSocket.getInputStream();
        BufferedReader in = new BufferedReader((new InputStreamReader(inStream)));
        OutputStream outStream = dataSocket.getOutputStream();
        PrintWriter out = new PrintWriter(outStream, true);

        WorkerProtocol protocol = new WorkerProtocol();

        String inmsg[] = in.readLine().split(",");
        long numSteps = Long.parseLong(inmsg[0]);
        long start = Long.parseLong(inmsg[1]);
        long stop = Long.parseLong(inmsg[2]);
        
        out.println(protocol.calcPi(numSteps,start,stop));
    }
}
