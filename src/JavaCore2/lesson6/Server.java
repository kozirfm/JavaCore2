package JavaCore2.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {

        final int PORT = 5050;

        ServerSocket server = null;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;
        Scanner message = new Scanner(System.in);

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен");

            socket = server.accept();
            System.out.println("Клиент подключился");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        out.writeUTF(message.nextLine());
                    }
                } catch (IOException e) {
                    System.err.println("Ни один клиент не подключен к серверу");
                }

            }).start();

            while (true) {
                try {
                    System.out.println("Клиент: " + in.readUTF());
                }catch (EOFException e){
                    System.err.println("Клиент отключился");
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                System.out.println("Сокет закрыт");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (server != null) {
                    server.close();
                }
                System.out.println("Сервер остановлен");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
