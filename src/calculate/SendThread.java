package calculate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.LinkedBlockingQueue;

public class SendThread extends Thread {
    private LinkedBlockingQueue queue;//输出队列
    private DatagramSocket socket;
    
    public SendThread(DatagramSocket socket, LinkedBlockingQueue queue){
        this.queue = queue;
        this.socket = socket;
    }

    public void run() {
        while (true){    	
                try {
                    DatagramPacket respMsg = (DatagramPacket) queue.take();//出队
                    socket.send(respMsg);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
