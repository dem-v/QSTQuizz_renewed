package vda.home.qstquizz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    static public String FilePath;
    static public boolean IS_LOADED_FLAG = false;
    TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "super.onCreated successfully!");
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "ContentView set");

        tw = (TextView) findViewById(R.id.noFileSelectedMsg);

        findViewById(R.id.exit).setOnClickListener(this);
        Log.d(LOG_TAG, "Exit button listener set");

        findViewById(R.id.baseselect).setOnClickListener(this);
        Log.d(LOG_TAG, "Second listener set");

        findViewById(R.id.starttest).setOnClickListener(this);
        Log.d(LOG_TAG, "Third listener set");
    }

    public void sendMessage() {
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(MainActivity.this, FileSelectActivity.class);
        // запуск activity
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Log.d(LOG_TAG, "FilePath received: " + FilePath);
            FilePath = data.getStringExtra("vda.home.qstquizz.PATH");
            tw.setText(FilePath);
        } else {
            Log.d(LOG_TAG, "FilePath not set...");
            tw.setHighlightColor(Color.RED);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    protected void onResume() {
        super.onResume();
        tw.setText(FilePath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.exit:
                Log.d(LOG_TAG, "Exiting on user request...");
                finish();
                break;
            case R.id.baseselect:
                Log.d(LOG_TAG, "File Picker initiating");
                try {
                    sendMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.starttest:
                Log.d(LOG_TAG, "Starting test...");
                try {
                    Intent intent = new Intent(MainActivity.this, TestMainFrameActivity.class);
                    intent.putExtra("vda.home.qstquizz.PATH", FilePath);
                    intent.putExtra("vda.home.qstquizz.MODE", findViewById(R.id.testmodetoggle).isEnabled());
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }
                break;
        }
    }
}
