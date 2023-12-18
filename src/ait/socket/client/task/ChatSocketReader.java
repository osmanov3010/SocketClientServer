package ait.socket.client.task;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatSocketReader implements Runnable {

    private Socket socket;

    public ChatSocketReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Socket socket = this.socket) {
            InputStream inputStream = socket.getInputStream();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String response = socketReader.readLine();
                System.out.println(response);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Exit initiated");
        }
    }
}
