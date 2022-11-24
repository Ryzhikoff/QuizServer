package evgeniy.ryzhikov;

import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    String count = "7";
    public static void main(String[] args) {
        new Client().go();
    }
    void go() {
        try {
            Socket socket = new Socket("127.0.0.1", Constants.PORT);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Готовый отправить: " + count);

            out.write(count);
            out.newLine();
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ArrayList<Question> list = (ArrayList<Question>) in.readObject();

            for (Question question:list) {
               // System.out.println(question.getQuestion() + " " + question.getAllAnswers());
            }

            out.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
