package vda.home.qstquizz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import vda.home.qstquizz.LibGetBaseFromAssets.DBHelper;

import static vda.home.qstquizz.LibGetBaseFromAssets.checkAnswer;
import static vda.home.qstquizz.LibGetBaseFromAssets.getAnswersArrayByElementID;
import static vda.home.qstquizz.LibGetBaseFromAssets.getAnswersArrayCurrent;
import static vda.home.qstquizz.LibGetBaseFromAssets.getQuestionByElementID;
import static vda.home.qstquizz.LibGetBaseFromAssets.getQuestionCurrent;
import static vda.home.qstquizz.LibGetBaseFromAssets.getQuestionNumberCurrent;
import static vda.home.qstquizz.LibGetBaseFromAssets.getQuestionNumberTotal;
import static vda.home.qstquizz.LibGetBaseFromAssets.increaseCurrentQuestionNumber;
import static vda.home.qstquizz.LibGetBaseFromAssets.setQuestionNumberCurrent;
import static vda.home.qstquizz.LibGetBaseFromAssets.setQuestionNumberTotal;
import static vda.home.qstquizz.LibGetBaseFromAssets.setTestBase;

public class TestMainFrameActivity extends AppCompatActivity {

    String FilePath;
    Boolean ExamTestMode;
    static String CurrentQuestion;
    static String[] CurrentAnswers;
    int[] QuestionNumberArray;
    static ProgressBar ProgressBarPoint;
    static SharedPreferences SP;
    static EditText GoTo;
    static ListView AnswersListView;
    static int AnswerID;
    static ArrayAdapter<String> adapter;
    static boolean doubleClick = false;
    static LibGetBaseFromAssets.DBHelper testBaseDB;
    static LibGetBaseFromAssets.DBHelper userStatsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main_frame);
        Context context = this.getApplicationContext();
        testBaseDB = new DBHelper(context, "testdb", null, 1);
        FilePath = getIntent().getStringExtra("vda.home.qstquizz.PATH");
        SP = PreferenceManager.getDefaultSharedPreferences(this);
        ExamTestMode = SP.getBoolean("test_mode_toggle", false);
        ProgressBarPoint = (ProgressBar) findViewById(R.id.progressBar);
        GoTo = (EditText) findViewById(R.id.goToQuestionNumber);
        AnswersListView = (ListView) findViewById(R.id.answersListView);
        AnswersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!checkAnswer(position)) {
                    Toast.makeText(getApplicationContext(), "Неправильный ответ, попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Верно!", Toast.LENGTH_SHORT).show();
                    updateThisViewWithQuestion();
                }
                return false;
            }
        });
        if (!ExamTestMode) {
            registerForContextMenu(ProgressBarPoint);
        }
        //Mode is changed on base of prefs
        loadBase();
        prepareTestVariant();
        updateThisViewWithQuestion(getQuestionNumberCurrent());

    }

    private void updateThisViewWithQuestion() {
        CurrentQuestion = getQuestionCurrent();
        CurrentAnswers = getAnswersArrayCurrent();
        increaseCurrentQuestionNumber();
    }

    private void updateThisViewWithQuestion(int questionNumberCurrent) {
        CurrentQuestion = getQuestionByElementID(questionNumberCurrent);
        CurrentAnswers = getAnswersArrayByElementID(questionNumberCurrent);
        setQuestionNumberCurrent(questionNumberCurrent++);
    }

    private void prepareTestVariant() {
        if (ExamTestMode) {
            int qNT = Integer.parseInt(SP.getString("QuestionNumberInput", "200"));
            setQuestionNumberTotal(qNT);
            QuestionNumberArray = new int[qNT];
            int[] QNA = new int[getQuestionNumberCurrent() - 1];
            for (int i = 0; i <= QNA.length; i++) {
                QNA[i] = i + 1;
            }
            Collections.shuffle(Arrays.asList(QNA));
            for (int i = 0; i < qNT; i++) {
                QuestionNumberArray[i] = QNA[i];
            }
        } else {
            QuestionNumberArray = new int[getQuestionNumberTotal()];
            for (int i = 0; i < getQuestionNumberTotal(); i++) {
                QuestionNumberArray[i] = i + 1;
            }
        }
    }

    private void loadBase() {
        try {
            setTestBase(FilePath, TestMainFrameActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_main_frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        GoTo.setVisibility(View.VISIBLE);
        GoTo.setSelected(true);
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
        GoTo.setSelected(false);
        GoTo.setVisibility(View.GONE);
        updateThisViewWithQuestion(Integer.parseInt(GoTo.getText().toString()));
    }


}
