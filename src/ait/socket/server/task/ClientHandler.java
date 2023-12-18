package ait.socket.server.task;

import ait.socket.server.ServerSocketAppl;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Socket socket = this.socket) {
            InputStream inputStream = socket.getInputStream();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(outputStream);
            ServerSocketAppl.addPrintWriter(socketWriter);
            while (true) {
                String message = socketReader.readLine();
                if (message == null) {
                    ServerSocketAppl.removePrintWriter(socketWriter);
                    break;
                }

                System.out.println("Server received message: " + message);

                sendMessageToAll(message);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAll(String message) {
        String localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        for (PrintWriter printWriter : ServerSocketAppl.getAllPrintWriters()) {
            printWriter.println(localTime + " - " + message);
            printWriter.flush();
        }
    }
}