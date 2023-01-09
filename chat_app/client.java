package chat_app;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public client() {

        try {
            System.out.println("sending request to server");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("connetion done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            starReading();
            starWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void starReading() {
        Runnable r1 = () -> {
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the chat");
                        socket.close();

                        break;

                    }
                    System.out.println("Server :" + msg);

                }

            } catch (Exception e) {
               // e.getStackTrace();
               System.out.println("connection is closed");


            }

        };
        new Thread(r1).start();

    }

    public void starWriting() {
        Runnable r2 = () -> {
            System.out.println("writter started...");
            try {
                while ( !socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;

                    }

                }
                System.out.println("connetion is closed");

            } catch (Exception e) {
                e.printStackTrace();

            }

        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("this is client");
        new client();

    }

}
