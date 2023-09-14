
package tcp.chat.room;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {
    

    @Override
    public void run(){
        try
        {
            ServerSocket server = new ServerSocket(8898);
        }catch (IOException e)
        {
            
        }
    }
    
    
    class ConnectionHandler implements Runnable {
        @Override
        public void run(){

        }
    }
    
    
}
