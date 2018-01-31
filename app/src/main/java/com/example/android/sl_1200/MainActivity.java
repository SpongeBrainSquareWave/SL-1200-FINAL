package com.example.android.sl_1200;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText editText1;
    private final int[] radioGroupIds = {R.id.radio_group_question_two, R.id.radio_group_question_three,
            R.id.radio_group_question_four, R.id.radio_group_question_five,
            R.id.radio_group_question_six, R.id.radio_group_question_seven};
    private final int[] correctButtons = {R.id.q2oc, R.id.q3ob, R.id.q4od, R.id.q5oc, R.id.q6od, R.id.q7ob};
    private final boolean[] checkBoxCorrect = {true, false, true, false, true, false, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.et1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //do not give the editView focus automatically when activity starts
        editText1.clearFocus();
    }

    //The code below saves the scroll position on orientation change.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        ScrollView scrollView = findViewById(R.id.svl);
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putIntArray("SCROLL_POSITION", new int[]{scrollView.getScrollX(), scrollView.getScrollY()});
    }

    // The code below restores the scroll position on orientation change.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        final ScrollView scrollView = findViewById(R.id.svl);
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
        if (position != null)
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(position[0], position[1]);
                }
            });
    }

    //method for button directing it to checkScore method and also displays toast  message
    public void start_go_button(View view) {
        int score = checkScore();
        Toast.makeText(this, getString(R.string.toast) + score, Toast.LENGTH_SHORT).show();
    }

    //score checking method.
    private int checkScore() {
        //initializes points variable
        int points = 0;
        //Q1 logic
        EditText et1 = findViewById(R.id.et1);
        // converts string to uppercase and compares to correct answer (TECHNICS)
        String question_one_answer = et1.getText().toString().toUpperCase();
        //if correct adds 5 points.
        if (question_one_answer.contains(getString(R.string.question_one_answer))) {
            points += 5;
        }
        // LOGIC for Questions 2 through 7
        for (int a = 0; a <= 5; a++) {
            //using the above logic and the radioQuestionsCheck method to cycle through all radio groups check answers and adds points systematically
            points += radioQuestionsCheck(radioGroupIds[a], correctButtons[a]);
        }
        CheckBox q9a = (findViewById(R.id.q9oa));
        CheckBox q9b = (findViewById(R.id.q9ob));
        CheckBox q9c = (findViewById(R.id.q9oc));
        CheckBox q9d = (findViewById(R.id.q9od));
        CheckBox q9e = (findViewById(R.id.q9oe));
        CheckBox q9f = (findViewById(R.id.q9of));
        CheckBox q9g = (findViewById(R.id.q9og));
        // checks condition of Checkboxes q9a through q9g and declares results in checkBoxIdsBoolean array.
        boolean[] checkBoxIdsBoolean = {q9a.isChecked(), q9b.isChecked(), q9c.isChecked(),
                q9d.isChecked(), q9e.isChecked(), q9f.isChecked(), q9g.isChecked()};
        // compares checkBoxIdsBoolean to predefined array of correct answers (checkBoxCorrect) and if correct adds 5 points.
        if (Arrays.equals(checkBoxIdsBoolean, checkBoxCorrect)) {
            points += 5;
        }
        return points;
    }

    // checks RadioButton answers
    private int radioQuestionsCheck(int radioGroupId, int correctButton) {
        int radioScore = 0;
        int userSelectedButton = getUserButton(radioGroupId);
        if (userSelectedButton == correctButton) {
            radioScore += 5;
        }
        return radioScore;
    }

    // gets user selected button from the current group.
    private int getUserButton(int radioGroupId) {
        RadioGroup radioGroup = findViewById(radioGroupId);
        return radioGroup.getCheckedRadioButtonId();
    }
}
