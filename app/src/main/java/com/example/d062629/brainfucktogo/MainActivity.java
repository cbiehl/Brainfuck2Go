package com.example.d062629.brainfucktogo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    private void errorMessage(String s){
        Toast t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
        t.show();
    }

    private void execute(){
        EditText et = (EditText) findViewById(R.id.edittext);
        String brainfuckCode = et.getText().toString();

        if (brainfuckCode == null || brainfuckCode.equals("")) {
            errorMessage(getString(R.string.errornocode));
            return;
        }

        if (!brainfuckCode.matches("[<>\\+-\\[\\]]+")) {
            errorMessage(getString(R.string.invalidchars));
            return;
        }

        BrainfuckEngine bfe = new BrainfuckEngine(1000);
        String result = getString(R.string.emptyoutput);
        try {
            result = result.isEmpty() ? result : bfe.interpret(brainfuckCode);
        } catch (Exception e) {
            Log.e("EXCEPTION!!!", e.getMessage());
        }

        TextView tv = (TextView) findViewById(R.id.textview);
        tv.setText(result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edittext);
        editText.setText(R.string.helloworld);

        Button btn = (Button) findViewById(R.id.buttonExecute);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute();
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        //TODO: Event Listeners
        Button btn_forward = (Button) findViewById(R.id.buttonforward);
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + ">");
            }
        });

        Button btn_backward = (Button) findViewById(R.id.buttonbackward);
        btn_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "<");
            }
        });

        Button btn_comma = (Button) findViewById(R.id.buttonopenbracket);
        btn_comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + ",");
            }
        });

        Button btn_dot = (Button) findViewById(R.id.buttondot);
        btn_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + ".");
            }
        });

        Button btn_minus = (Button) findViewById(R.id.buttonminus);
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "-");
            }
        });

        Button btn_plus = (Button) findViewById(R.id.buttonplus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "+");
            }
        });

        Button btn_bracketopen = (Button) findViewById(R.id.buttonopenbracket);
        btn_bracketopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "[");
            }
        });

        Button btn_bracketclose = (Button) findViewById(R.id.buttonclosebracket);
        btn_bracketclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "]");
            }
        });

        Button btn_del = (Button) findViewById(R.id.delete);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = editText.getText().toString();
                editText.setText(txt.substring(0, txt.length() - 1));
            }
        });

        Button btn_clear = (Button) findViewById(R.id.clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }
}
