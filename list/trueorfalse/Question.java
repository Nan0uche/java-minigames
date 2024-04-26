package list.trueorfalse;

public class Question {
    private int id;
    private String text;
    private boolean answer;

    public Question(int id, String text, boolean answer) {
        this.id = id; // Assurez-vous d'initialiser correctement la variable id
        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public boolean getAnswer() {
        return answer;
    }

    public int getId() {
        return id;
    }
}
