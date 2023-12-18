package ait.socket.client;

import ait.socket.client.task.ChatSocketReader;
import ait.socket.client.task.ChatSocketWriter;

import java.io.*;
import java.net.Socket;

public class ClientSocketAppl {
    public static void main(String[] args) {
        String serverHost = "127.0.0.1"; // localhost
        int port = 9000;
        try (Socket socket = new Socket(serverHost, port)) {
            ChatSocketWriter writer = new ChatSocketWriter(socket);
            ChatSocketReader reader = new ChatSocketReader(socket);
            Thread threadReader = new Thread(reader);
            Thread threadWriter = new Thread(writer);

            threadWriter.start();
            threadReader.start();
            threadWriter.join(); // waiting for end writer
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}