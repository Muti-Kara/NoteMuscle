import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OCR_ClientSocket{
    
    
    private Socket clientsocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public OCR_ClientSocket(Socket socket) throws IOException{
        clientsocket = socket;
        dataInputStream = new DataInputStream(clientsocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientsocket.getOutputStream());
    }

    public DataInputStream getInputStream(){
        return this.dataInputStream;
    }

    public DataOutputStream getOutputStream(){
        return this.dataOutputStream;
    }

    public void closeEverything(){
        try{
            if(clientsocket != null){
                clientsocket.close();
                clientsocket = null;
            }
            if(dataInputStream != null){
                dataInputStream.close();
                dataInputStream = null;
            }
            if(dataOutputStream != null){
                dataOutputStream.close();
                dataOutputStream = null;
            }
            OCR_Server.getInstance().removeOCRClient(this);
        }catch(IOException ioException){
            ioException.printStackTrace(System.err);
        }
    }
}
