package client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class Client {
    static  void otpravka(String s,Socket cs ) throws IOException {
       OutputStreamWriter writer = new OutputStreamWriter(cs.getOutputStream(), Charset.forName("UTF-8"));
        writer.write(s);
       writer.flush();
    }
    public static void main(String[] args) throws IOException {
        try(Socket cs = new Socket("localhost",1111)) {

            String out = "";


            try(FileReader reader = new FileReader("test2.txt"))
            {
                // читаем посимвольно

               int c;

                while((c=reader.read())!=-1){

                   out = out + (char) c;

                }




            }
            //out = out.replace("\n"," ");
            System.out.println(out);
            String[] massive = out.split("\n");


            for (int i =0 ; i < massive.length; i++) {
                otpravka(massive[i],cs);


            }





            BufferedReader reader = new BufferedReader(new InputStreamReader(cs.getInputStream(),Charset.forName("UTF-8")));
            System.out.println(reader.readLine());

        }
    }
}