package vda.home.qstquizz;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import vda.home.qstquizz.LibGetBaseFromAssets;

import static vda.home.qstquizz.LibGetBaseFromAssets.getQuestionNumberCurrent;
import static vda.home.qstquizz.LibGetBaseFromAssets.setQuestionNumberTotal;
import static vda.home.qstquizz.LibGetBaseFromAssets.setTestBase;

public class TestMainFrameActivity extends AppCompatActivity {

    String FilePath;
    Boolean ExamTestMode;
    LibGetBaseFromAssets.SingleVariableOfQuestionAndAnswers ThisQuestion;
    int[] QuestionNumberArray = new int[200];
    final ProgressBar ProgressBarPoint = (ProgressBar) findViewById(R.id.progressBar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main_frame);
        FilePath = getIntent().getStringExtra("vda.home.qstquizz.PATH");
        ExamTestMode = getIntent().getBooleanExtra("vda.home.qstquizz.MODE",false);

        //TODO:Change mode on base of button
        loadBase();
        prepareTestVariant();


    }

    private void prepareTestVariant() {
        if (ExamTestMode) {
            setQuestionNumberTotal(200);
            int[] QNA = new int[getQuestionNumberCurrent()-1];
            for (int i=0; i<=QNA.length; i++) {QNA[i]=i+1;}
            Collections.shuffle(Arrays.asList(QNA));
            for (int i = 0; i < 200; i++) {
                QuestionNumberArray[i] = QNA[i];
            }
        }
        else
        {

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
}
