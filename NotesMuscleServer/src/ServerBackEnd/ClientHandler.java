package ServerBackEnd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    
    MainServer mainServer;
    Socket clientSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;


    public ClientHandler(Socket clientSocket, MainServer mainServer){
        this.clientSocket = clientSocket;
        this.mainServer = mainServer;
        try{
            dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());
        }catch(IOException IOex){
            closeEveryThing();
            IOex.printStackTrace();
        }
    }

    public void closeEveryThing(){
        try{
            if(dataInputStream != null){
                dataInputStream.close();
            }
            if(dataOutputStream != null){
                dataOutputStream.close();
            }

            if(clientSocket != null){
                clientSocket.close();
            }
            mainServer.removeSpecificClient(this);
        }catch(IOException IOex){
            IOex.printStackTrace();
        }
    //we need to notify the arraylist of client handlers to remove this client
    }

}
