package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(1111);
        ExecutorService p = Executors.newCachedThreadPool();
        AtomicInteger req = new AtomicInteger();

        while (true) {
            final  Socket cs = ss.accept();
            Runnable task = () -> {
                try {


                    System.out.printf("Соединение установлено c адреса %s\n", cs.getInetAddress().toString());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(cs.getInputStream(), Charset.forName("UTF-8")));
                    String s = reader.readLine();
                    System.out.printf("%s . %d\n", s,req.incrementAndGet());

                    try(FileWriter writer = new FileWriter("test.txt", true))
                    {
                        // запись всей строки

                        writer.write(s);
                        //writer.append("\n");
                        OutputStreamWriter out = new OutputStreamWriter(cs.getOutputStream(), Charset.forName("UTF-8"));
                        out.write("Ответ принят\n");
                        out.flush();

                        writer.flush();
                    }
                    catch(IOException ex){

                        System.out.println(ex.getMessage());
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            };

            p.submit(task);


        }
    }
}