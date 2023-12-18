package ait.socket.server;

import ait.socket.server.task.ClientHandler;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketAppl {

    private static List<PrintWriter> allPrintWriters;

    public static void main(String[] args) {
        allPrintWriters = new ArrayList<>();
        int port = 9000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                System.out.println("Server is waiting for new connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connection established with client host : " + socket.getInetAddress() + ":" + socket.getPort());
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.setDaemon(true);
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<PrintWriter> getAllPrintWriters() {
        return new ArrayList<>(allPrintWriters);
    }

    public static void removePrintWriter(PrintWriter printWriter) {
        allPrintWriters.remove(printWriter);
    }

    public static void addPrintWriter(PrintWriter printWriter) {
        allPrintWriters.add(printWriter);
    }
}