
package tcp.chat.room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    

    @Override
    public void run(){
        try
        {
            ServerSocket server = new ServerSocket(8898);
            Socket client = server.accept();
            
            
        }catch (IOException e)
        {
            //
        }
    }
    
    
    class ConnectionHandler implements Runnable {
        
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String username;
        
        public ConnectionHandler(Socket client){
            this.client = client;
        }
        
        @Override
        public void run(){
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please Enter a Username: ");
                username = in.readLine();
                System.out.println(username + " Connected!");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    
}
