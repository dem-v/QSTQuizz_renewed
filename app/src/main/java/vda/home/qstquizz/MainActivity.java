package vda.home.qstquizz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    static public String FilePath;
    static public boolean IS_LOADED_FLAG = false;
    final int ACTIVITY_CHOOSE_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "super.onCreated successfully!");
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "ContentView set");

        final Button exit = (Button) this.findViewById(R.id.exit);
        exit.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d(LOG_TAG, "Exiting on user request...");
                                        finish();
                                    }
                                }
        );
        Log.d(LOG_TAG, "Exit button listener set");

        final Button openFilePicker = (Button) this.findViewById(R.id.baseselect);
        openFilePicker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "File Picker initiating");
                try {
                    sendMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d(LOG_TAG, "Second listener set");

        final Button startTest = (Button) this.findViewById(R.id.starttest);
        startTest.setOnClickListener(new OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Log.d(LOG_TAG, "Starting test...");
                                             try {
                                                 Intent intent = new Intent(MainActivity.this, TestMainFrameActivity.class);
                                                 intent.putExtra("vda.home.qstquizz.PATH", FilePath);
                                                 intent.putExtra("vda.home.qstquizz.MODE", MainActivity.this.findViewById(R.id.testmodetoggle).isEnabled());
                                                 startActivity(intent);
                                             } catch (Exception e) {
                                                 Log.e(LOG_TAG, e.getMessage());
                                                 e.printStackTrace();
                                             }
                                         }
                                     }
        );
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
        switch (requestCode) {
            case ACTIVITY_CHOOSE_FILE: {
                if (resultCode == RESULT_OK) {
                    Log.d(LOG_TAG, "FilePath received: " + FilePath);
                    FilePath = data.getStringExtra("vda.home.qstquizz.PATH");
                    final TextView tw = (TextView) this.findViewById(R.id.noFileSelectedMsg);
                    tw.setText(FilePath);
                } else {
                    Log.d(LOG_TAG, "FilePath not set...");
                    final TextView tw = (TextView) this.findViewById(R.id.noFileSelectedMsg);
                    tw.setHighlightColor(0xFF0000);
                }
            }
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
        final TextView tw = (TextView) this.findViewById(R.id.noFileSelectedMsg);
        tw.setText(FilePath);
    }
}
