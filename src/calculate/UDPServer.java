package calculate;

import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UDPServer {

    public static BlockingQueue<DatagramPacket> inputQueue = new LinkedBlockingQueue();
    public static BlockingQueue<DatagramPacket> outputQueue = new LinkedBlockingQueue();

    public static void main(String[] args) throws IOException{

        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(1234);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("服务端启动成功。");
        new WorkThread((LinkedBlockingQueue) inputQueue,"t1").start();//输入队列，工作线程
        new WorkThread((LinkedBlockingQueue) inputQueue,"t2").start();
        new WorkThread((LinkedBlockingQueue) inputQueue,"t3").start();
        new WorkThread((LinkedBlockingQueue) inputQueue,"t4").start();
        new WorkThread((LinkedBlockingQueue) inputQueue,"t5").start();
        new SendThread(aSocket, (LinkedBlockingQueue) outputQueue).start();//输出队列，发送线程

        while (true){
            byte[] buffer = new byte[1000];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);
            new MAINT((LinkedBlockingQueue) inputQueue,request).start();//主线程启动
        }
    }
}
class MAINT extends Thread{
    private LinkedBlockingQueue qu;
    private DatagramPacket pa;
    public MAINT(LinkedBlockingQueue qu,DatagramPacket pa){
        this.pa = pa;
        this.qu = qu;
    }
    public void run(){
        try {
            qu.put(pa);//客户端收到的数据包入队
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
