package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(1111);
        ExecutorService p = Executors.newCachedThreadPool();
        while (true) {
            final  Socket cs = ss.accept();
            String address = cs.getInetAddress().toString();
            InputStream inStream =cs.getInputStream();
            OutputStream outStream = cs.getOutputStream();
            p.execute(new Connection(address,inStream,outStream));

        }
    }
}