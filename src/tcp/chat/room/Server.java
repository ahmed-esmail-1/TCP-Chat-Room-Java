
package tcp.chat.room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    
    public Server(){
        connections = new ArrayList<>();
        done = false;
    }
    
    @Override
    public void run(){
        try
        {
            ServerSocket server = new ServerSocket(8898);
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
            }
            
            
            
        }catch (IOException e)
        {
            //
        }
    }
    
    public void broadcast(String message) {
        for (ConnectionHandler ch : connections){
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }
    
    public void shutdown(){
        try{
            done = true;
            if (!server.isClosed()){
                server.close();
            }
            for (ConnectionHandler ch: connections){
                ch.shutdown();
            }
        } catch (IOException e){
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
                broadcast(username + " Joined the chat");
                String message;
                while ( (message = in.readLine()) != null ){
                    if (message.startsWith("/user ")){
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2){
                            System.out.println(username + "renamed themselves to " + messageSplit[1]);
                            username = messageSplit[1];
                            out.println("Successfully changed username to " + username);
                        } else {
                            out.println("No username provided!");
                        }
                    } else if (message.startsWith("/quit")){
                        //handle quit
                    } else {
                        broadcast(username + ": " + message);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void sendMessage(String message) {
            out.println();
        }
        
        public void shutdown(){
            try {
                in.close();
                out.close();
                if (!client.isClosed()){
                    client.close();
                }
            }catch (IOException e){
                //
            }
            
        }
    }
    
    
}
