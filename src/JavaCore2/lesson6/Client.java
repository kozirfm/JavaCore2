package JavaCore2.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        final String IP_ADDRESS = "localhost";
        final int PORT = 5050;

        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;
        Scanner message = new Scanner(System.in);

        try {
            socket = new Socket(IP_ADDRESS, PORT);
            System.out.println("Подключение к серверу: OK");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                try {
                    while(true){
                        out.writeUTF(message.nextLine());
                    }
                } catch (IOException e) {
                    System.err.println("Клиент не подключен к серверу");
                }

            }).start();

            while(true){
                try{
                    System.out.println("Сервер: " + in.readUTF());
                }catch (EOFException e){
                    System.err.println("Сервер отключился");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Сервер не найден");
        }finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
