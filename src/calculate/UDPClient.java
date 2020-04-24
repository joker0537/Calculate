package calculate;

import java.net.*;
import java.io.*;
import java.util.regex.Pattern;

public class UDPClient{

    public static void sendReqPkg(byte[] message) throws SocketException {//将message编码为 byte 序列，并将结果存储到一个byte数组中

        DatagramSocket  aSocket = new DatagramSocket();
        try {
            InetAddress aHost = InetAddress.getByName("127.0.0.1");
            DatagramPacket request = new DatagramPacket(message,message.length,aHost,1234);//发送包
            aSocket.send(request);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);//接收包
            aSocket.receive(reply);
            System.out.println("运算结果为：" + new String(reply.getData()));
        } catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        } finally {
            if(aSocket != null) aSocket.close();
        }
    }

    public static void main(String args[]) throws IOException {

        while (true) {
        	
            BufferedReader cin=new BufferedReader(new InputStreamReader(System.in));//输入流读取
            System.out.print("请输入运算数据（运算符与数字之间用空格分隔，如“1 + 1”，“23 * 45”）：");
            String str=cin.readLine();//输入一行
            String pattern = "^(\\d+)(\\s)[-*/+](\\s)(\\d+)";//正则，运算符+数字+数字
            if (Pattern.matches(pattern, str)) {
                byte[] m = str.getBytes();
                sendReqPkg(m);
            }
            else {
                System.out.println("格式错误");
            }

        }
    }
}
