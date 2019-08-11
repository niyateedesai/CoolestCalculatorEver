package com.example.niyat.calculatorproject;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView OutputScreen;
    String answer = "0";
    boolean answerDisplayed = true;
    boolean errorDisplayed = false;
    Double answerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OutputScreen = findViewById(R.id.id_textview_screen);
    }

    public void buttonClick_delete(View view) {
        if(answer.length()==1) {
            buttonClick_clear(view);
        }
        else{
            answer = answer.substring(0, answer.length() - 1);
            OutputScreen.setText(answer);
        }
    }

    public void buttonClick_decimal(View view) {
        if(errorDisplayed || answerDisplayed) {
            answer = "";
        }
        answer += ((TextView) view).getText().toString();
        OutputScreen.setText(answer);
        answerDisplayed = false;
        errorDisplayed = false;
    }

    public void buttonClick_clear(View view) {
        answer = "0";
        OutputScreen.setText(answer);
        answerDisplayed = true;
        errorDisplayed = false;
    }

    public void buttonClick_Operator(View view) {
        try {
            if (answerDisplayed && !errorDisplayed && !OutputScreen.getText().toString().equals("0")) {
                answerValue = Double.parseDouble(answer);
                answer = "ans";
            } else if (errorDisplayed) {
                answer = "";
            }
            if (OutputScreen.getText().toString().equals("0") && ((TextView) view).getText().toString().equals("-")) {
                answer = "";
            }
            answer += ((TextView) view).getText().toString();
            OutputScreen.setText(answer);
            answerDisplayed = false;
            errorDisplayed = false;
        }catch(Exception e){
            answer = "Err";
            OutputScreen.setText(answer);
            errorDisplayed = true;
            answerDisplayed = true;
        }
    }

    public void buttonClick_number(View view) {
        if(OutputScreen.getText().equals("0") && ((TextView)view).getText().toString().equals("0")) {
            return;
        }
        if (answerDisplayed) {
            answer = "";
        }

        answer += ((TextView)view).getText().toString();
        OutputScreen.setText(answer);
        answerDisplayed = false;
        errorDisplayed = false;
    }

    public boolean isAnOperator(String str) {
        if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
            return true;
        }
        return false;
    }

    public void buttonClick_evaluate(View view) {

        if(!errorDisplayed) {
            Double num1;
            Double num2;
            Double value;
            String ans;

            ArrayList<String> list = new ArrayList<>();
            StringTokenizer num = new StringTokenizer(OutputScreen.getText().toString(), "*/+-", true);
            while (num.hasMoreElements()) {
                list.add(num.nextToken());
            }

            try {
                if(list.get(0).equals("ans")){
                    list.set(0, answerValue.toString());
                }

                for(int i = 1; i<list.size(); i++)
                {
                    if (isAnOperator(list.get(i-1)) && list.get(i).equals("-")) {
                        list.set(i+1, "-" + list.get(i+1));
                        list.remove(i);
                    }
                }
                if (list.size() > 1 && list.get(0).equals("-")) {
                    list.set(1, "-" + list.get(1));
                    list.remove(0);
                }


                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals("*")) {
                        num1 = Double.parseDouble(list.get(i - 1));
                        num2 = Double.parseDouble(list.get(i + 1));
                        value = num1 * num2;
                        ans = value.toString();
                        list.set(i, ans);
                        list.remove(i + 1);
                        list.remove(i - 1);
                        i--;
                    } else if (list.get(i).equals("/")) {
                        num1 = Double.parseDouble(list.get(i - 1));
                        num2 = Double.parseDouble(list.get(i + 1));
                        value = num1 / num2;
                        int test = (int) (double) num1 / (int) (double) num2;
                        ans = value.toString();
                        list.set(i, ans);
                        list.remove(i + 1);
                        list.remove(i - 1);
                        i--;
                    }
                }

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals("+")) {
                        num1 = Double.parseDouble(list.get(i - 1));
                        num2 = Double.parseDouble(list.get(i + 1));
                        value = num1 + num2;
                        ans = value.toString();
                        list.set(i, ans);
                        list.remove(i + 1);
                        list.remove(i - 1);
                        i--;
                    } else if (list.get(i).equals("-")) {
                        num1 = Double.parseDouble(list.get(i - 1));
                        num2 = Double.parseDouble(list.get(i + 1));
                        value = num1 - num2;
                        ans = value.toString();
                        list.set(i, ans);
                        list.remove(i + 1);
                        list.remove(i - 1);
                        i--;
                    }
                }

                Double doublenum = Double.valueOf(list.get(0));
                int intnum = Double.valueOf(list.get(0)).intValue();

                if ((doublenum - intnum) != 0) {
                    //DecimalFormat precision = new DecimalFormat("0.####");
                    //answer = (precision.format(doublenum));
                    answer = list.get(0);
                }
                else {
                    answer = Integer.toString(intnum);
                }

            } catch (ArithmeticException e) {
                answer = "Err: Division by 0";
                errorDisplayed = true;
            } catch (NumberFormatException e) {
                answer = "Err: Op. in a row";
                errorDisplayed = true;
            } catch (IndexOutOfBoundsException e) {
                answer = "Err: End/start w/ op.";
                errorDisplayed = true;
            } catch (Exception e){
                answer = "Err";
                errorDisplayed = true;
            }

            OutputScreen.setText(answer);
            answerDisplayed = true;
        }

    }
}