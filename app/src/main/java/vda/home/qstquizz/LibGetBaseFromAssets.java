package vda.home.qstquizz;

import android.app.Activity;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by de on 25.01.16.
 */
public class LibGetBaseFromAssets {

    public static int QuestionNumberTotal =0;
    public static int QuestionNumberCurrent =0;
    private static int QuestionNumberCounter =0;
    private static String temp,strtmp;

//    TODO:Create db instead of class!

    static class SingleVariableOfQuestionAndAnswers
    {
        String Question;
        String[] Answer;
        Boolean[] CorrectAnswer;
        public SingleVariableOfQuestionAndAnswers() {
            Question = new String("");
            Answer = new String[4];
            CorrectAnswer = new Boolean[4];
        }
    }

    public static SingleVariableOfQuestionAndAnswers[] TestBase;

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
        TestBase = new SingleVariableOfQuestionAndAnswers[100000];
        final AssetManager am = activity.getAssets();
        InputStream inp = am.open(FilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(inp));

        temp = br.readLine();
        QuestionNumberCounter++;
        TestBase[QuestionNumberCounter].Question = br.readLine(); //TODO:NullPointer

        do {
            strtmp = br.readLine();//1st
            if (strtmp.charAt(0) == '+') {
                TestBase[QuestionNumberCounter].CorrectAnswer[0] = true;
            }
            TestBase[QuestionNumberCounter].Answer[0] = strtmp.substring(1);

            strtmp = br.readLine();//2nd
            if (strtmp.charAt(0) != '?') {
                if (strtmp.charAt(0) == '+') {
                    TestBase[QuestionNumberCounter].CorrectAnswer[1] = true;
                }
                TestBase[QuestionNumberCounter].Answer[1] = strtmp.substring(1);
            } else {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = br.readLine();
                continue;
            }

            strtmp = br.readLine();//3rd
            if (strtmp.charAt(0) != '?') {
                if (strtmp.charAt(0) == '+') {
                    TestBase[QuestionNumberCounter].CorrectAnswer[2] = true;
                }
                TestBase[QuestionNumberCounter].Answer[2] = strtmp.substring(1);
            } else {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = br.readLine();
                continue;
            }

            strtmp = br.readLine();//4th
            if (strtmp.charAt(0) != '?') {
                if (strtmp.charAt(0) == '+') {
                    TestBase[QuestionNumberCounter].CorrectAnswer[3] = true;
                }
                TestBase[QuestionNumberCounter].Answer[3] = strtmp.substring(1);
            } else {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = br.readLine();
                continue;
            }

            strtmp = br.readLine();//5th
            if (strtmp.charAt(0) != '?') {
                if (strtmp.charAt(0) == '+') {
                    TestBase[QuestionNumberCounter].CorrectAnswer[4] = true;
                }
                TestBase[QuestionNumberCounter].Answer[4] = strtmp.substring(1);
            } else {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = br.readLine();
                continue;
            }

            strtmp = br.readLine();
            if (strtmp!=null) {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = br.readLine();
            }
        }
        while (strtmp!=null);
        QuestionNumberTotal = QuestionNumberCounter;
    }

    public static boolean checkAnswer(int AnswerID)
    {
        return TestBase[QuestionNumberCurrent].CorrectAnswer[AnswerID];
    }

    public static SingleVariableOfQuestionAndAnswers getBaseElement() {
//        get current
        return TestBase[QuestionNumberCurrent];
    }

    public static void increaseCurrentQuestionNumber() {
        QuestionNumberCounter++;
    }

    public static SingleVariableOfQuestionAndAnswers getBaseElement(int ElementID) {
//        get by Id
        return TestBase[ElementID];
    }
}
