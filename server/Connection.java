package server;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

public class Connection implements Runnable {
    String address;
    InputStream inStream;
    OutputStream outStream;
    static AtomicInteger req = new AtomicInteger();
    static int id;

    public Connection(String address, InputStream inStream, OutputStream outStream) {
        this.address = address;
        this.inStream = inStream;
        this.outStream = outStream;

        id = req.incrementAndGet();
    }



    public void run() {
        try {

            System.out.printf("Сессия id = %d\n",id);
            System.out.printf("Соединение установлено c адреса %s\n", address);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));
            String s = reader.readLine().replaceAll("PRN", "\r\n");
            System.out.println("Получены следующие текстовые данные, которй будут сохранены в файл in.txt");
            System.out.printf("%s", s);
            System.out.println("\nСессия id=" + id + " завершена");
            try (FileWriter writer = new FileWriter("in.txt", true)) {
                // запись всей строки
                writer.write("\n");
                writer.write(s);

                OutputStreamWriter out = new OutputStreamWriter(outStream, Charset.forName("UTF-8"));
                out.write("Ответ принят\n");
                out.flush();
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}



