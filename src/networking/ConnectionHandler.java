package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by millerlj on 3/26/2016.
 */
public class ConnectionHandler implements Runnable {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;

    public ConnectionHandler(String serverIP, int port){
        this.port = port;
        try {
            this.socket = new Socket(serverIP, port);
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

    }
}
