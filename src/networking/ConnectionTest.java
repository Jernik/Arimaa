package networking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by millerlj on 3/30/2016.
 */
public class ConnectionTest {
    public static void main(String[] args) {
        boolean branch1 = true;
        boolean branch2 = false;
        if (branch1) {
            ConnectionHandler c = new ConnectionHandler(6774);
            c.connectTo("137.112.223.145");
            String x = "Hello World " + c.hashCode();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.sendObject(x);
            Object o = c.getObjectFromInput();
            String s = (String) o;
            System.out.println(s);
        }
        else if(branch2){
            FileInputStream fin;
            ObjectInputStream oin;
            try {
                fin = new FileInputStream("Test.txt");
                oin = new ObjectInputStream(fin);
                String s = (String)oin.readObject();
                System.out.println(s);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
