import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ServerSetup
 */
public class OCR_Server {

    public static OCR_Server ocr_server;
    static{
        ocr_server = null;
    }
    private ServerSocket serverSocket;
    private OCR_ClientSocket clientSocket; //the ocr server accepts only bytes
    private int serverPort = 4445;
    private ArrayList<OCR_ClientSocket> clients;
    private Processing_Queue processing_Queue;

    public static void main(String[] args) {
        OCR_Server ocr_Server = OCR_Server.getInstance();
        ocr_Server.startServer();
    }

    public static OCR_Server getInstance(){
        if(OCR_Server.ocr_server == null){
            OCR_Server.ocr_server = new OCR_Server();
        }
        return OCR_Server.ocr_server;
    }

    private OCR_Server(){
        clients = new ArrayList<>();
        processing_Queue = new Processing_Queue();
    }

    public void startServer(){
        try {
            processing_Queue.start();
            System.out.println("TURNING OCR SERVER ON...");
            serverSocket = new ServerSocket(serverPort);
            while(true){   
                System.out.println("OCR SERVER WAITING FOR CLIENT CONNECTION");
                Socket socket = serverSocket.accept();
                clientSocket = new OCR_ClientSocket(socket);
                clients.add(clientSocket);
                processing_Queue.accessQueueForChange(clientSocket, false);
            }
        } catch (IOException e) {
            System.out.println("OCR SERVER FAILED TO START");
            e.printStackTrace(System.err);
        }
    
    }

    public synchronized void removeOCRClient(OCR_ClientSocket ocr_ClientSocket){
        clients.remove(ocr_ClientSocket);
    }
}