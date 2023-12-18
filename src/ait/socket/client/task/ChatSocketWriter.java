package ait.socket.client.task;

import ait.socket.server.ServerSocketAppl;

import java.io.*;
import java.net.Socket;

public class ChatSocketWriter implements Runnable {

    private Socket socket;
    PrintWriter socketWriter;

    public ChatSocketWriter(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            socketWriter = new PrintWriter(outputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your name:");
            String name = br.readLine();
            sendMessage(name + " joined chat");
            Thread.sleep(20);
            System.out.println("Enter your message or type exit for quit");
            String message = br.readLine();
            while (!"exit".equalsIgnoreCase(message)) {
                sendMessage(name + ": " + message);
                Thread.sleep(20);
                System.out.println("Enter your message or type exit for quit");
                message = br.readLine();
            }
            sendMessage(name + " left chat");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(String message) {
        socketWriter.println(message);
        socketWriter.flush();
    }
}
