package vda.home.qstquizz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileSelectActivity extends AppCompatActivity {

    String filePath;
    List<String> FilePathRouteRecorder = new ArrayList<String>();
    List<String> FilesLister = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);

        final ListView lw = (ListView) this.findViewById(R.id.listOfFiles);
        final AssetManager am = FileSelectActivity.this.getAssets();
        String[] tt = null;
        try {
            tt = am.list("KROK");
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
        FilePathRouteRecorder.add("KROK/");
        FilesLister = Arrays.asList(tt);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilesLister);
        lw.setAdapter(adapter);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = TextUtils.join("", FilePathRouteRecorder.toArray());
                filePath = s + FilesLister.get(position);
                if (!isDir(filePath)) {
                    Intent answerIntent = new Intent();
                    answerIntent.putExtra("vda.home.qstquizz.PATH", filePath);
                    setResult(RESULT_OK, answerIntent);
                    finish();
                } else if (isDir(filePath)) {
                    String[] tt = null;
                    FilePathRouteRecorder.add(FilesLister.get(position) + "/");
                    try {
                        tt = am.list(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        finish();
                    }
                    FilesLister = Arrays.asList(tt);
                    adapter.notifyDataSetChanged();
                    adapter = new ArrayAdapter<String>(FileSelectActivity.this, android.R.layout.simple_list_item_1, FilesLister);
                    lw.setAdapter(adapter);
                }
            }
        });
    }

    @NonNull
    protected Boolean isDir(String path) {
        String[] list;
        try {
            list = FileSelectActivity.this.getAssets().list(path);
        } catch (IOException e) {
            return false;
        }
        return list.length > 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_select, menu);
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
