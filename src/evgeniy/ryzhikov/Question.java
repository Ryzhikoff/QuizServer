package evgeniy.ryzhikov;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    private ArrayList<String> answers = new ArrayList<>();

    private String id;
    private String question;
    private String trueAnswer;
    private String answer2;
    private String answer3;
    private String answer4;

    public Question () {
    }

    public Question(String question, String trueAnswer, String answer2, String answer3, String answer4) {
        this.question = question;
        this.trueAnswer = trueAnswer;
        answers.add(trueAnswer);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isTrueAnswer (String answer) {
        return answer.equals(this.trueAnswer);
    }
    public ArrayList<String> getListAnswers () {
        ArrayList<String> list = new ArrayList<>(4);
        while (answers.size() != 0) {
            int num = (int) Math.random() * answers.size();
            list.add(answers.get(num));
            answers.remove(num);
        }
        return list;
    }

}