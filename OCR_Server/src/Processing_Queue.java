import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

public class Processing_Queue extends Thread{
    
    Queue<OCR_ClientSocket> processing_queue; //must be synchronised
    Server_Process_Interface image_processor;
    
    public Processing_Queue(){
        processing_queue = new LinkedList<OCR_ClientSocket>();
    }

    public synchronized OCR_ClientSocket accessQueueForChange(OCR_ClientSocket ocr_ClientSocket ,boolean remove){
        if(remove){
            if(processing_queue.isEmpty()){
                return null;
            }
            return processing_queue.remove();
        }else{
            processing_queue.add(ocr_ClientSocket);
            return null;
        }
    }

    @Override
    public void run(){
            while(true){
                OCR_ClientSocket ocr_ClientSocket = accessQueueForChange(null, true);
                if(ocr_ClientSocket == null){
                    continue;
                }
                DataInputStream dataInputStream = ocr_ClientSocket.getInputStream();
                DataOutputStream dataOutputStream = ocr_ClientSocket.getOutputStream();
                try {
                    int img_byte_arr_length = dataInputStream.readInt();
                    byte[] img_bytes = new byte[img_byte_arr_length];
                    dataInputStream.readFully(img_bytes);
                    ByteArrayInputStream bis = new ByteArrayInputStream(img_bytes);
                    BufferedImage bImage = ImageIO.read(bis);
                    File in_process_img_file = new File(Server_Process_Interface.IMG_PATH);
                    ImageIO.write(bImage, "png", in_process_img_file);

                    //after image to text conversion
                    //String img_to_txt = new String(testStrings(), StandardCharsets.UTF_8);
                    String img_to_txt = image_processor.getProcessedString();
                    dataOutputStream.writeUTF(img_to_txt);
                    dataOutputStream.flush();
                    //after processing end the request
                    ocr_ClientSocket.closeEverything();

                    //delete the buffered image file
                    if(in_process_img_file.exists()){
                        in_process_img_file.delete();
                    }

                } catch (IOException e) {
                    ocr_ClientSocket.closeEverything();
                    e.printStackTrace();
                } 
            }
    }

    private byte[] testStrings(){
        byte[] random_arr = new byte[10];
        new Random().nextBytes(random_arr);
        return random_arr;
    }

}
