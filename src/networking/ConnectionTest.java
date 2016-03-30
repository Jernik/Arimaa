package networking;

/**
 * Created by millerlj on 3/30/2016.
 */
public class ConnectionTest {
    public static void main(String[] args){
        ConnectionHandler c = new ConnectionHandler(6574);
        c.connectTo("137.112.239.225");
        String x = "Hello World " + c.hashCode();
        c.sendObject(x);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((String)c.getObjectFromInput());
    }
}
