package vda.home.qstquizz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import static android.content.Context.ACTIVITY_SERVICE;
import static vda.home.qstquizz.MainActivity.LOG_TAG;

/**
 * Created by de on 25.01.16.
 */
public class LibGetBaseFromAssets {

    public static int QuestionNumberTotal = 0;
    public static int QuestionNumberCurrent = 1;
    private static int QuestionNumberCounter = 0;
    private static String temp, strtmp;
    public static Cursor cursor;
    public static SQLiteDatabase db;

    public static int getQuestionNumberTotal() {
        return QuestionNumberTotal;
    }

    public static void setQuestionNumberTotal(int questionNumberTotal) {
        QuestionNumberTotal = questionNumberTotal;
    }

    public static int getQuestionNumberCurrent() {
        return QuestionNumberCurrent;
    }

    public static void setQuestionNumberCurrent(int questionNumberCurrent) {
        QuestionNumberCurrent = questionNumberCurrent;
    }

    public static void setTestBase(String FilePath, Activity activity) throws IOException {
        Log.d(LOG_TAG, "Setting DB up...");
        ContentValues contentValues = new ContentValues();
        final AssetManager am = activity.getAssets();
        InputStream inp = am.open(FilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(inp));

        db = TestMainFrameActivity.testBaseDB.getWritableDatabase();
        temp = br.readLine();
        if (temp.charAt(0) == '?') {
            QuestionNumberCounter++;
            contentValues.put("question", br.readLine());
            do {
                strtmp = br.readLine();//1st
                if (strtmp.charAt(0) == '?') {
                    Log.e(LOG_TAG, "No answers is available. Bad file. Skipping...");
                    continue;
                }
                if (strtmp.charAt(0) == '+') contentValues.put("corr1", 1);
                else contentValues.put("corr1", 0);
                contentValues.put("answer1", strtmp.substring(1));

                strtmp = br.readLine();//2nd
                if (strtmp.charAt(0) != '?') {
                    if (strtmp.charAt(0) == '+') contentValues.put("corr2", 1);
                    else contentValues.put("corr2", 0);
                    contentValues.put("answer2", strtmp.substring(1));
                } else {
                    QuestionNumberCounter++;
                    long rowID = db.insert("testdb", null, contentValues);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                    contentValues.clear();
                    contentValues.put("question", br.readLine());
                    continue;
                }

                strtmp = br.readLine();//3rd
                if (strtmp.charAt(0) != '?') {
                    if (strtmp.charAt(0) == '+') contentValues.put("corr3", 1);
                    else contentValues.put("corr3", 0);
                    contentValues.put("answer3", strtmp.substring(1));
                } else {
                    QuestionNumberCounter++;
                    long rowID = db.insert("testdb", null, contentValues);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                    contentValues.clear();
                    contentValues.put("question", br.readLine());
                    continue;
                }

                strtmp = br.readLine();//4th
                if (strtmp.charAt(0) != '?') {
                    if (strtmp.charAt(0) == '+') contentValues.put("corr4", 1);
                    else contentValues.put("corr4", 0);
                    contentValues.put("answer4", strtmp.substring(1));
                } else {
                    QuestionNumberCounter++;
                    long rowID = db.insert("testdb", null, contentValues);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                    contentValues.clear();
                    contentValues.put("question", br.readLine());
                    continue;
                }

                strtmp = br.readLine();//5th
                if (strtmp.charAt(0) != '?') {
                    if (strtmp.charAt(0) == '+') contentValues.put("corr5", 1);
                    else contentValues.put("corr5", 0);
                    contentValues.put("answer5", strtmp.substring(1));
                } else {
                    QuestionNumberCounter++;
                    long rowID = db.insert("testdb", null, contentValues);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                    contentValues.clear();
                    contentValues.put("question", br.readLine());
                    continue;
                }

                strtmp = br.readLine();
                if (strtmp != null) {
                    QuestionNumberCounter++;
                    long rowID = db.insert("testdb", null, contentValues);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                    contentValues.clear();
                    contentValues.put("question", br.readLine());
                    continue;
                }
            }
            while (strtmp != null);
            QuestionNumberTotal = QuestionNumberCounter;
        }
    }

    public static boolean checkAnswer(int AnswerID) {
        cursor = db.query("testdb", null, null, null, null, null, null);
        cursor.moveToPosition(AnswerID);
        boolean ans = false;
        int corrColIndex = cursor.getColumnIndex("corr" + AnswerID);
        if (cursor.getInt(corrColIndex) == 1) ans = true;
        return ans;
//        TODO:Stats recording;
    }

    public static String getQuestionCurrent() {
        cursor = db.query("testdb", null, null, null, null, null, null);
        cursor.moveToPosition(QuestionNumberCurrent);
        String t = cursor.getString(cursor.getColumnIndex("question"));
        cursor.close();
        return t;
    }

    public static String getQuestionByElementID(int ElementID) {
        cursor = db.query("testdb", null, null, null, null, null, null);
        cursor.moveToPosition(ElementID);
        String t = cursor.getString(cursor.getColumnIndex("question"));
        cursor.close();
        return t;
    }

    public static String[] getAnswersArrayCurrent() {
        cursor = db.query("testdb", null, null, null, null, null, null);
        cursor.moveToPosition(QuestionNumberCurrent);
        String[] t = {
                cursor.getString(cursor.getColumnIndex("answer1")),
                cursor.getString(cursor.getColumnIndex("answer2")),
                cursor.getString(cursor.getColumnIndex("answer3")),
                cursor.getString(cursor.getColumnIndex("answer4")),
                cursor.getString(cursor.getColumnIndex("answer5"))
        };
        cursor.close();
        return t;
    }

    public static String[] getAnswersArrayByElementID(int ElementID) {
        cursor = db.query("testdb", null, null, null, null, null, null);
        cursor.moveToPosition(ElementID);
        String[] t = {
                cursor.getString(cursor.getColumnIndex("answer1")),
                cursor.getString(cursor.getColumnIndex("answer2")),
                cursor.getString(cursor.getColumnIndex("answer3")),
                cursor.getString(cursor.getColumnIndex("answer4")),
                cursor.getString(cursor.getColumnIndex("answer5"))
        };
        cursor.close();
        return t;
    }

/*    public static SingleVariableOfQuestionAndAnswers getBaseElement() {
//        get current
        return TestBase[QuestionNumberCurrent];
    }*/

/*    public static SingleVariableOfQuestionAndAnswers getBaseElement(int ElementID) {
//        get by Id
        return TestBase[ElementID];
    }*/

    public static void increaseCurrentQuestionNumber() {
        QuestionNumberCounter++;
    }

    public static class DBHelper extends SQLiteOpenHelper {
        public String dbname = "";

        public DBHelper(Context context, String base_name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, base_name, factory, version);
            dbname = base_name;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(LOG_TAG, "No db found, creating new");
            switch (dbname) {
                case "testdb":
                    db.execSQL("create table testdb ("
                            + "id integer primary key autoincrement,"
                            + "question text,"
                            + "answer1 text,"
                            + "answer2 text,"
                            + "answer3 text,"
                            + "answer4 text,"
                            + "answer5 text,"
                            + "corr1 int,"
                            + "corr2 int,"
                            + "corr3 int,"
                            + "corr4 int,"
                            + "corr5 int" + ");");
                    break;
                case "userstatsdb":
                    db.execSQL("create table userstatsdb ("
                            + "id integer primary key autoincrement,"
                            + "BaseCompleted text,"
                            + "NumberOfQuestionsCompleted int,"
                            + "NumberOfQuestionsCorrect int,"
                            + "PercentOfSuccess real,"
                            + "DateCompleted int,"
                            + "TimeElapsedSecs int" + ");");
                    break;
                default:
                    Log.e(LOG_TAG, "No such basename allowed. Skipping...");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(LOG_TAG, "Found old db. Old Version=" + oldVersion + "; New Version=" + newVersion + ". Updating...");
        }
    }
}
