package chat_app;
import java.net.*;
import java.io.*;

class Server {
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("server to ready to accept connection");
            System.out.println("waiting");
            socket = server.accept();

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
            try{
            while (true) {
                
                    String msg = br.readLine();
                    if (msg.equals("exit")) 
                    {
                        System.out.println("Clinet terminated the chat");
                        socket.close();
                        break;

                    }
                    System.out.println("Clinet :" + msg);

                } 
                

            }catch(Exception e){
                //e.printStackTrace();
                System.out.println("connetion is closed");

            }

        };
        new Thread(r1).start();

    }

    public void starWriting() {
        Runnable r2 = () -> {
            System.out.println("writter started...");
            try {
            while (!socket.isClosed()) {
                
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    out.println(content);
                    out.flush();
                    
                    if (content.equals("exit")) {
                        socket.close();
                        break;

                    }

                } 

            }catch (Exception e) {
              //  e.printStackTrace();
              System.out.println("connetion is closed");

                
            }

        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("this is server ..going to start server");
        new Server();
        
    }

}