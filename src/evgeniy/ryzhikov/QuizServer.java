package evgeniy.ryzhikov;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;

public class QuizServer {

    ArrayList<Question> questionsList;

    public static void main(String[] args) {
        new QuizServer().startServer();
    }

    private void startServer () {
        try {
            ServerSocket serverSocket = new ServerSocket(Constants.PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Есть подключение");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //получаем запрос
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            //Количество вопросов
                            ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
//                            int count = Integer.parseInt(reader.readLine());

                            int count = (int) reader.readObject();

                            createQuestionList(count);

                            //отправляем список вопросов
                            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                            out.writeObject(questionsList);
                            out.flush();
                            
                            reader.close();
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void outQuestions() {
        for (Question question:questionsList) {
            //System.out.println(question.getQuestion() + " " + question.getAllAnswers());
        }
    }

    private void createQuestionList(int count) {

        DBHelper dbHelper = new DBHelper();
        dbHelper.open();

        ResultSet rs = dbHelper.getRandomRow(count);
        questionsList = new ArrayList<Question>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String quest = rs.getString("question");
                String trueAnswer = rs.getString("trueanswer");
                String answer2 = rs.getString("answer2");
                String answer3 = rs.getString("answer3");
                String answer4 = rs.getString("answer4");
                String category = rs.getString("category");

                Question question = new Question(quest, trueAnswer, answer2, answer3, answer4);
                questionsList.add(question);

                /*System.out.println("id: " + id +
                        " question: " + quest +
                        " TrueAnswer: " + trueAnswer +
                        " Answer 2: " + answer2 +
                        " Answer 3: " + answer3 +
                        " Answer 4: " + answer4 +
                        " Category: " + category);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbHelper.close();

    }


}
