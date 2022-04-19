package ServerBackEnd;

import java.util.ArrayList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServer extends Thread{

    ServerSocket serverSocket;
    ArrayList<ClientHandler> clients;
    DataBaseManager dataBaseManager;

    private boolean keepServerRunning = true;

    public MainServer() throws Exception{
        serverSocket  = new ServerSocket(4444);
        clients = new ArrayList<>();
        dataBaseManager = new DataBaseManager();
    }

    public void run(){
        //we need the server to be on a different thread because of the GUI
        while(keepServerRunning){
            try{
                if(serverSocket == null){
                    //start the server again
                    serverSocket = new ServerSocket(4444);
                }
                Socket clientSocket = serverSocket.accept(); //wait for a new client a blocking method 
                //if the server is closed on the blocked accept() the SocketException will be thrown which is normal
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandler.start();
                clients.add(clientHandler);
                
            }catch(IOException IOex){
                closeEveryThing();
                IOex.printStackTrace();
            }
        }
        System.out.println("SERVER CLOSED");
    }

    public void setServerState(boolean keepServerRunning){
        this.keepServerRunning = keepServerRunning;
    }

    public void removeSpecificClient(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public void closeEveryThing(){
        //need to close all client sockets also
        try{
            for(ClientHandler client : clients){
                client.closeEveryThing();
            }
            if(serverSocket != null){
                serverSocket.close();
            }
            dataBaseManager = null;
        }catch(IOException IOex){
            IOex.printStackTrace();
        }
    }
}
