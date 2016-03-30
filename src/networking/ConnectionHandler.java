package networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Created by millerlj on 3/26/2016.
 */
public class ConnectionHandler implements Runnable {

    private String serverIP;
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean waiting=true;

    /**
     * To use this class, create a Connection Handler on each instance,
     * create a thread for the handler,
     * then call connectTo on both instances to set up the sockets, we don't care about the sockets, just the ones 
     * created by the serversockets
     */
    public ConnectionHandler(int port){
        try {
            this.serverSocket = new ServerSocket(port);
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.port = port;

    }

    public void connectTo(String serverIP){
        try {
            Socket s = new Socket(serverIP, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            this.socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean running = true;
        PrintWriter out;
        BufferedReader in;
        try {
            out = new PrintWriter(this.socket.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(this.socket.getInputStream());
            in = new BufferedReader(isr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (running) {
            //do stuff
        }

    }
}
