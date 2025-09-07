package client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;


public class Client {
    static  void send(String s,Socket cs ) throws IOException {
       OutputStreamWriter writer = new OutputStreamWriter(cs.getOutputStream(), Charset.forName("UTF-8"));
        writer.write(s);
       writer.flush();
    }
    public static void main(String[] args) throws IOException {
        try(Socket cs = new Socket("localhost",1111)) {
            String out = "";
            try(FileReader reader = new FileReader("out.txt"))
            {// читаем посимвольно
               int c;
                while((c=reader.read())!=-1){
                   out = out + (char) c;
                }
            }
            System.out.println(out);
            send(out.replaceAll("\r\n","PRN") +"\n",cs);
            BufferedReader reader = new BufferedReader(new InputStreamReader(cs.getInputStream(),Charset.forName("UTF-8")));
            System.out.println(reader.readLine());
        }
    }
}