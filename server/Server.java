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
                    int id = req.incrementAndGet();
                    System.out.printf("Сессия id = %d\n",id);
                    System.out.printf("Соединение установлено c адреса %s\n", cs.getInetAddress().toString());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(cs.getInputStream(), Charset.forName("UTF-8")));
                    String s = reader.readLine().replaceAll("PRN","\r\n");
                    System.out.println("Получены следующие текстовые данные, которй будут сохранены в файл in.txt");
                    System.out.printf("%s", s);
                    System.out.println("\nСессия id=" + id + " завершена");
                    try(FileWriter writer = new FileWriter("in.txt", true))
                    {
                        // запись всей строки
                        writer.write("\n");
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