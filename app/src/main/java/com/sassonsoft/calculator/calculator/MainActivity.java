package com.sassonsoft.calculator.calculator;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView math;
    private ImageButton animal;

    private float result = 0;
    private int conditionFlag = 0;

    private int number=0;
    String targil;
    private List<String> targilTemp=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();
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

    private void initializeVariables() {
        math = (TextView) findViewById(R.id.tvMath);
        animal = (ImageButton) findViewById(R.id.ibAnimal);
        animal.setTag(R.drawable.dog);

        animal.setOnClickListener(this);
    }

    public void btnNumClick(View view) {
        /*
        conditionFlag=0 ==> EXPECTING num1
        conditionFlag=1 ==> There is num1 EXPECTING num1 to change OR operation sign
        conditionFlag=2 ==> There is operation sign EXPECTING num2
        conditionFlag=3 ==> There is num2 EXPECTING num2 to change OR operation '='
         */
        Button btn = (Button) view;
        int btnNum = Integer.parseInt(btn.getText().toString());

        if (btnNum == 3)
            Log.w("You clicked: ", btn.getText().toString());

        switch (conditionFlag) {
            case 0:
                conditionFlag = 1;
                math.setText(btn.getText().toString());
                number=number*10+btnNum;
                break;
            case 1:
                math.append(btn.getText().toString());
                number=number*10+btnNum;
                break;
            case 2:
                conditionFlag = 3;
                number=number*10+btnNum;
                math.append(btn.getText().toString());
                break;
            case 3:
                math.append(btn.getText().toString());
                number=number*10+btnNum;
                break;
        }
    }

    public void btnOpClick(View view) {
        /*
        conditionFlag=0 ==> EXPECTING num1
        conditionFlag=1 ==> There is num1 EXPECTING num1 to change OR operation sign
        conditionFlag=2 ==> There is operation sign EXPECTING num2
        conditionFlag=3 ==> There is num2 EXPECTING num2 to change OR operation '='
         */
        Button btn = (Button) view;

        if(btn.getText().toString().equals("AC")) {
            conditionFlag=0;
            result=0;
            math.setText("0");
            conditionFlag = 0;
            number=0;
            targilTemp.clear();
        }


        switch (conditionFlag) {
            case 0:
                if (btn.getText().toString().equals("+")) {
                    conditionFlag = 2;
                    targilTemp.add(Float.toString(result));
                    targilTemp.add("+");
                    number=0;
                    if(Math.floor(result)!=0)
                        math.setText(Float.toString(result) + "+");
                    else
                        math.setText(Integer.toString((int)result) + "+");
                }
                if (btn.getText().toString().equals("-")) {
                    conditionFlag = 2;
                    targilTemp.add(Float.toString(result));
                    targilTemp.add("-");
                    number=0;
                    math.setText(Float.toString(result) + "-");
                }
                if (btn.getText().toString().equals("*")) {
                    conditionFlag = 2;
                    targilTemp.add(Float.toString(result));
                    targilTemp.add("*");
                    number=0;
                    if(Math.floor(result)!=0)
                        math.setText(Float.toString(result) + "*");
                    else
                        math.setText(Integer.toString((int)result) + "*");
                }
                if (btn.getText().toString().equals("/")) {
                    conditionFlag = 2;
                    targilTemp.add(Float.toString(result));
                    targilTemp.add("/");
                    number=0;
                    if(Math.floor(result)!=0)
                        math.setText(Float.toString(result) + "/");
                    else
                        math.setText(Integer.toString((int)result) + "/");
                }
                break;
            case 1:
                if (btn.getText().toString().equals("+")) {
                    conditionFlag = 2;
                    targilTemp.add(Float.toString((float)number));
                    targilTemp.add("+");
                    number=0;
                    math.append(btn.getText().toString());
                }
                if (btn.getText().toString().equals("-")) {
                    conditionFlag = 2;
                    targilTemp.add(Float.toString((float)number));
                    targilTemp.add("-");
                    number=0;
                    math.append(btn.getText().toString());
                }
                if (btn.getText().toString().equals("*")) {
                    conditionFlag = 2;
                    targilTemp.add(Float.toString((float)number));
                    targilTemp.add("*");
                    number=0;
                    math.append(btn.getText().toString());
                }
                if (btn.getText().toString().equals("/")) {
                    conditionFlag = 2;
                    targilTemp.add(Float.toString((float)number));
                    targilTemp.add("/");
                    number=0;
                    math.append(btn.getText().toString());
                }
                break;
            case 3:
                if (btn.getText().toString().equals("=")) {
                    targilTemp.add(Float.toString((float) number));
                    calcResult();
                    conditionFlag = 0;
                    number=0;
                    result=Float.parseFloat(targil);
                    targilTemp.clear();
                }
                else if (btn.getText().toString().equals("+")){
                    conditionFlag=2;
                    targilTemp.add(Float.toString((float)number));
                    targilTemp.add("+");
                    number=0;
                    math.append(btn.getText().toString());
                }
                else if (btn.getText().toString().equals("-")){
                    conditionFlag=2;
                    targilTemp.add(Float.toString((float)number));
                    targilTemp.add("-");
                    number=0;
                    math.append(btn.getText().toString());
                }
                else if (btn.getText().toString().equals("*")){
                    conditionFlag=2;
                    targilTemp.add(Float.toString((float)number));
                    targilTemp.add("*");
                    number=0;
                    math.append(btn.getText().toString());
                }
                else if (btn.getText().toString().equals("/")){
                    conditionFlag=2;
                    targilTemp.add(Float.toString((float)number));
                    targilTemp.add("/");
                    number=0;
                    math.append(btn.getText().toString());
                }
                break;

        }
    }

    private void calcResult() {
        targil=targilTemp.toString();
        targil =targil.replaceAll(",", "");
        targil=targil.replaceAll(" ", "");
        targil= targil.replaceAll("\\[", "");
        targil=targil.replaceAll("]","");


        math.setText(targil.toString());
        ///////////////////////////////////////////////////////////////////////////////
        int opIndex;
        int nowIndex, startIndex, endIndex;
        float num1=0, num2=0,answer;
        int i=0;
        while(i<targil.length()){
            if(targil.charAt(i)=='*'||targil.charAt(i)=='/') {
                opIndex=i;
                int x=i;
                while(true) {
                    x--;
                    if(targil.charAt(x)=='*'||targil.charAt(x)=='/'||targil.charAt(x)=='+'||targil.charAt(x)=='-'||x==0) {
                        startIndex=x;
                        x=opIndex;
                        break;
                    }
                }
                while(true) {
                    x++;
                    if(targil.charAt(x)=='*'||targil.charAt(x)=='/'||targil.charAt(x)=='+'||targil.charAt(x)=='-'||x==targil.length()-1) {
                        endIndex=x;
                        x=opIndex+1;
                        break;
                    }
                }

                num1=Float.parseFloat(targil.substring(startIndex,opIndex));
                num2=Float.parseFloat(targil.substring(opIndex+1,endIndex));

                if(targil.charAt(opIndex)=='*') {
                    answer=num1*num2;
                }
                else {
                    answer=num1/num2;
                }
                String str=Float.toString(num1)+targil.charAt(opIndex)+Float.toString(num2);
                math.setText(targil.replaceFirst(str, Float.toString(answer)));
                targil =targil.replace(str, Float.toString(answer));
                num1=num2=0;
                i=0;
            }
            else i++;
        }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
        i=0;
        while(i<targil.length()){
            if((targil.charAt(i)=='+'||targil.charAt(i)=='-')&&i!=0) {
                opIndex=i;
                int x=i;
                while(true) {
                    x--;
                    if(targil.charAt(x)=='+'||targil.charAt(x)=='-'||x==0) {
                        startIndex=x;
                        x=opIndex;
                        break;
                    }
                }
                while(true) {
                    x++;
                    if(targil.charAt(x)=='+'||targil.charAt(x)=='-'||x==targil.length()-1) {
                        endIndex=x;
                        x=opIndex+1;
                        break;
                    }
                }

                num1=Float.parseFloat(targil.substring(startIndex,opIndex));
                num2=Float.parseFloat(targil.substring(opIndex+1,endIndex));

                if(targil.charAt(opIndex)=='+') {
                    answer=num1+num2;
                }
                else {
                    answer=num1-num2;
                }
                String str=Float.toString(num1)+targil.charAt(opIndex)+Float.toString(num2);
                math.setText(targil.replaceFirst(str, Float.toString(answer)));
                targil =targil.replace(str, Float.toString(answer));
                num1=num2=0;
                i=0;
            }
            else i++;
        }
        math.setText(targil);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibAnimal:
                setImage();
                break;
        }

    }

    private void setImage() {
        if(animal.getTag().equals(R.drawable.dog)){
            animal.setImageResource(R.drawable.cat);
            animal.setTag(R.drawable.cat);
        }
        else {
            animal.setImageResource(R.drawable.dog);
            animal.setTag(R.drawable.dog);
        }

    }
}
