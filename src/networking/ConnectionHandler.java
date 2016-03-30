package networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by millerlj on 3/26/2016.
 */
public class ConnectionHandler implements Runnable {

    private String serverIP;
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean waiting = true;
    private boolean connected = false;
    private Queue<Object> outputBuffer = new LinkedList<>();
    private Queue<Object> inputBuffer = new LinkedList<>();

    /**
     * To use this class, create a Connection Handler on each instance,
     * create a thread for the handler,
     * then call connectTo on both instances to set up the sockets, we don't care about the sockets, just the ones
     * created by the serversockets
     */
    public ConnectionHandler(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.port = port;

    }

    public void sendObject(Object o) {
        this.outputBuffer.add(o);
    }

    public void connectTo(String serverIP) {
        try {
            Socket s = new Socket(serverIP, port);
            while (!s.isConnected()) {
            }//blocks til s is connected
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObjectFromInput() {
        System.out.println("Reading: " + this.inputBuffer.peek());
        return this.inputBuffer.poll();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Outer Loop");
            if (!connected) {
                try {
                    this.socket = serverSocket.accept();
                    System.out.println("Connected");
                    this.connected = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                boolean running = true;
                ObjectOutputStream out = null;
                ObjectInputStream in = null;
                InputStream inStream = null;
                boolean inputObjectAvailible=false;
                System.out.println("Setting up streams");
                try {
                    OutputStream outStream = this.socket.getOutputStream();
                    System.out.println("Got output Stream");
                    out = new ObjectOutputStream(outStream);
                    out.flush();
                    inStream = this.socket.getInputStream();
                    System.out.println("Got input Stream");


                    System.out.println("Created ObjectInputStream");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Set up streams");
                while (running) {
                    System.out.println("Running Loop");
                    try {
                        System.out.println("Checking underlying stream: availible = " + inStream.available());
                        if (inStream.available() > 0) {
                            in = new ObjectInputStream(inStream);
                            inputObjectAvailible = true;
                            System.out.println("Created objectInputStream");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!this.outputBuffer.isEmpty()) {
                        try {
                            out.writeObject(this.outputBuffer.poll());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                       if (inputObjectAvailible && in.available() > 0) {
                            System.out.println("reading...");
                            this.inputBuffer.add(in.readObject());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
