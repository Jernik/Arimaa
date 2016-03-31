package networking;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
        System.out.println("Added object to sending queue: queue size is now: " + this.outputBuffer.size());
    }

    public void connectTo(String remote) {
        try {
            Socket s = new Socket(remote, port);
            while (!s.isConnected()) {
            }
            String local = Inet4Address.getLocalHost().getHostAddress();
            int check = remote.compareTo(local);
            if (check == 0) {
                throw new UnknownHostException("Cannot connect to myself");
            } else if (check > 0){//throw away our serversocket.accept
                this.socket = s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObjectFromInput() {
        System.out.println("Reading: " + this.inputBuffer.peek());
        while (inputBuffer.isEmpty()) {
        }//blocks until we can return something
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
                boolean inputObjectAvailible = false;
                System.out.println("Setting up streams");
                try {
                    OutputStream outStream = this.socket.getOutputStream();
                    System.out.println("Got output Stream");
                    out = new ObjectOutputStream(outStream);
                    out.flush();
                    inStream = this.socket.getInputStream();
                    System.out.println("Got input Stream");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Set up streams");
                while (running) {
                    System.out.println("Running Loop");
                    try {
                        if (!inputObjectAvailible && inStream.available() > 0) {
                            System.out.println("Checking underlying stream: availible = " + inStream.available());
                            in = new ObjectInputStream(inStream);
                            inputObjectAvailible = true;
                            System.out.println("Created objectInputStream");
                        } else if(!inputObjectAvailible && this.socket.getInputStream().available() > 0){
                            inStream = this.socket.getInputStream();
                            System.out.println("Got input Stream");
                            continue;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("Size of queue is: " + this.outputBuffer.size());
                    if (!this.outputBuffer.isEmpty()) {
                        try {
                            System.out.println("writing object...");
                            Object o = this.outputBuffer.poll();
                            out.writeObject(o);
                            new ObjectOutputStream(new FileOutputStream("Test.txt")).writeObject(o);
                            System.out.println("Size of queue is now " + this.outputBuffer.size());

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
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
