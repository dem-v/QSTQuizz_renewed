package vda.home.qstquizz;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import vda.home.qstquizz.LibGetBaseFromAssets;

public class TestMainFrameActivity extends AppCompatActivity {

    String FilePath;
    Boolean ExamTestMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main_frame);
        FilePath = getIntent().getStringExtra("vda.home.qstquizz.PATH");
        ExamTestMode = getIntent().getBooleanExtra("vda.home.qstquizz.MODE",false);
        final ProgressBar ProgressBarPoint = (ProgressBar) this.findViewById(R.id.progressBar);
        
        LibGetBaseFromAssets.SingleVariableOfQuestionAndAnswers ThisQuestion =
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
