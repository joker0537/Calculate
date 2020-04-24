package calculate;
import java.net.DatagramPacket;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkThread extends Thread{

    private LinkedBlockingQueue queue;//输入队列
    private DatagramPacket  packet;//数据包

    public WorkThread(LinkedBlockingQueue queue, String name){
        super(name);
        this.queue = queue;
    }
    
    public void run(){

        while (true){

            DatagramPacket request = null;
            try {
                request = (DatagramPacket) queue.take();//客户发送的数据包出队
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String []calculateStr = new String(request.getData()).split("\\s");//字符串分割数组

          if("+".equals(calculateStr[1])){
                Count.incPlus();//加法计数
                int result =Integer.parseInt(calculateStr[0]) + Integer.parseInt(calculateStr[2].trim());//将字符数组转换为整型计算后存储在result中
                byte[] buffer = Integer.toString(result).getBytes();//将整型转换成字符串
                packet =  new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());
            }
            else if("-".equals(calculateStr[1])){
            	Count.incMinus();//减法计数
                int result =Integer.parseInt(calculateStr[0]) - Integer.parseInt(calculateStr[2].trim());
                byte[] buffer = Integer.toString(result).getBytes();
                packet =  new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());
            }
            else if("*".equals(calculateStr[1])){
            	Count.incMultiple();//乘法计数
                int result =Integer.parseInt(calculateStr[0]) * Integer.parseInt(calculateStr[2].trim());
                byte[] buffer = Integer.toString(result).getBytes();
                packet =  new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());
            }
            else if("/".equals(calculateStr[1])){
            	Count.incDivide();//除法计数
                try{
                    double result =Double.parseDouble(calculateStr[0]) / Integer.parseInt(calculateStr[2].trim());
                    byte[] buffer = Double.toString(result).getBytes();
                    packet =  new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
          
            System.out.println("运算统计：");
            System.out.println("加法次数："+Count.getPlusCounter()+"\n"+"减法次数："+Count.getMinusCounter()+"\n"+
                    "乘法次数："+Count.getMultipleCounter()+"\n"+"除法次数："+Count.getDivideCounter());

                try {
                    UDPServer.outputQueue.put(packet);//数据包装入输出队列
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

    }
}
