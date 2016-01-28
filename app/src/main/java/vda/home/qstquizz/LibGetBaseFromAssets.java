package vda.home.qstquizz;

import android.app.Activity;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by de on 25.01.16.
 */
public class LibGetBaseFromAssets {

    public static int QuestionNumberTotal =0;
    public static int QuestionNumberCurrent =0;
    private static int QuestionNumberCounter =0;
    private static String temp,strtmp;

    public class SingleVariableOfQuestionAndAnswers
    {
        String Question = "";
        String[] Answer = new String[4];
        Boolean[] CorrectAnswer = new Boolean[4];
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
        final AssetManager am = activity.getAssets();
        InputStream inp = am.open(FilePath);
        Scanner scanner = new Scanner(inp);

        temp = scanner.next("/r/n"); //TODO:Type mismatch here
        QuestionNumberCounter++;
        TestBase[QuestionNumberCounter].Question = scanner.nextLine();

        while (scanner.hasNextLine()) {
            strtmp = scanner.nextLine();//1st
            if (strtmp.charAt(0) == '+') {
                TestBase[QuestionNumberCounter].CorrectAnswer[0] = true;
            }
            TestBase[QuestionNumberCounter].Answer[0] = strtmp.substring(1);

            strtmp = scanner.nextLine();//2nd
            if (strtmp.charAt(0) != '?') {
                if (strtmp.charAt(0) == '+') {
                    TestBase[QuestionNumberCounter].CorrectAnswer[1] = true;
                }
                TestBase[QuestionNumberCounter].Answer[1] = strtmp.substring(1);
            } else {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = scanner.nextLine();
                continue;
            }

            strtmp = scanner.nextLine();//3rd
            if (strtmp.charAt(0) != '?') {
                if (strtmp.charAt(0) == '+') {
                    TestBase[QuestionNumberCounter].CorrectAnswer[2] = true;
                }
                TestBase[QuestionNumberCounter].Answer[2] = strtmp.substring(1);
            } else {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = scanner.nextLine();
                continue;
            }

            strtmp = scanner.nextLine();//4th
            if (strtmp.charAt(0) != '?') {
                if (strtmp.charAt(0) == '+') {
                    TestBase[QuestionNumberCounter].CorrectAnswer[3] = true;
                }
                TestBase[QuestionNumberCounter].Answer[3] = strtmp.substring(1);
            } else {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = scanner.nextLine();
                continue;
            }

            strtmp = scanner.nextLine();//5th
            if (strtmp.charAt(0) != '?') {
                if (strtmp.charAt(0) == '+') {
                    TestBase[QuestionNumberCounter].CorrectAnswer[4] = true;
                }
                TestBase[QuestionNumberCounter].Answer[4] = strtmp.substring(1);
            } else {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = scanner.nextLine();
                continue;
            }

            if (scanner.hasNext()) {
                QuestionNumberCounter++;
                TestBase[QuestionNumberCounter].Question = scanner.nextLine();
            }
        }
        QuestionNumberTotal = QuestionNumberCounter;
    }

    public boolean CheckAnswer(int AnswerID)
    {
        return TestBase[QuestionNumberCurrent].CorrectAnswer[AnswerID];
    }

    public SingleVariableOfQuestionAndAnswers getBaseElement() {
//        get current
        return TestBase[QuestionNumberCurrent];
    }

    public SingleVariableOfQuestionAndAnswers getBaseElement(int ElementID) {
//        get by Id
        return TestBase[ElementID];
    }
}
