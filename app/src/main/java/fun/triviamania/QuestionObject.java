package fun.triviamania;



import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;


import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

public class QuestionObject {
	private boolean Type;
	private String question;
	private ArrayList<String> Answers = new ArrayList<String>();
	private String correctAnswer;

	@Override
	public String toString() {
		return "QuestionObject [Type=" + Type + ", question=" + question + ", Answers=" + Answers + ", correctAnswer="
				+ correctAnswer + "]";
	}

	public QuestionObject() {
		question = "failed to load question";
	}

	public boolean isType() {
		return Type;
	}

	public void setType(boolean type) {
		Type = type;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ArrayList<String> getAnswers() {
		return Answers;
	}

	public void setAnswers(JSONArray answers) throws JSONException {

		for (int i =0; i < answers.length(); i++) {

			this.Answers.add(unescapeHtml4(answers.get(i).toString()));

		}

	}

	public void appendAnswer(String answer) {
		this.Answers.add(answer);
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public ArrayList<String> shuffleAnswers() {
		Collections.shuffle(Answers);
		return Answers;
	}

}
