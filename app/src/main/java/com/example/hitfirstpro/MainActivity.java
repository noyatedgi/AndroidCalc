package com.example.hitfirstpro;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView eq;
    private TextView result;
    private double firstNumber;
    private double secondNumber;
    private char ch;
    private boolean resetOnNextInput = false;
    private boolean isOperatorPressed = false;
    private boolean isEqualPressed = false;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        result = findViewById(R.id.textViewResult);
        eq = findViewById(R.id.textVieweq);
        result.setText("");
        eq.setText("");
    }

    public void numfunction(View view) {
        if (resetOnNextInput) {
            eq.setText("");
            resetOnNextInput = false;
        }

        Button button = (Button) view;
        eq.append(button.getText().toString());
        result.setText("");
    }

    public void cfunction(View view) {
        String currentText = eq.getText().toString();
        if (!currentText.isEmpty()) {
            eq.setText(currentText.substring(0, currentText.length() - 1));
        }
        result.setText("");
    }

    public void pointfunction(View view) {
        Button button = (Button) view;
        String currentText = eq.getText().toString();

        if (!currentText.isEmpty()) {
            String[] parts = currentText.split("[+\\-*/]");
            String currentNumber = parts[parts.length - 1];

            if (!currentNumber.contains(".")) {
                eq.append(button.getText().toString());
            }
        } else {
            eq.append("0.");
        }
        result.setText("");
    }

    public void funcaction(View view) {
        if (isOperatorPressed) {
            Toast.makeText(view.getContext(), "כבר בחרת פעולה", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isValidNumber(eq.getText().toString())) {
            ch = ((Button) view).getText().toString().charAt(0);
            firstNumber = Double.parseDouble(eq.getText().toString());
            eq.setText("");
            result.setText("");
            isOperatorPressed=true;
        } else {
            showError("חייב להכניס מספר קודם!");
        }
    }

    public void sumfunction(View view) {
        if (isEqualPressed) {
            Toast.makeText(view.getContext(), "כבר ביצעת חישוב, מתבצעת אתחול (AC)", Toast.LENGTH_SHORT).show();
            resetDisplay();
            return;
        }

        String eqText = eq.getText().toString();
        if (eqText.isEmpty()) {
            Toast.makeText(view.getContext(), "תרשום מספר", Toast.LENGTH_SHORT).show();
            return;
        }

        secondNumber = Double.parseDouble(eqText);
        double reso = 0;

        if (ch == '/' && secondNumber == 0) {
            Toast.makeText(view.getContext(), "אי אפשר לחלק ב 0", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (ch) {
            case '+':
                reso = firstNumber + secondNumber;
                break;
            case '-':
                reso = firstNumber - secondNumber;
                break;
            case 'x':
                reso = firstNumber * secondNumber;
                break;
            case '/':
                reso = firstNumber / secondNumber;
                break;
            default:
                firstNumber = Double.parseDouble(eqText);
                reso = firstNumber;
                break;
        }

        displayResult(reso);
        eq.setText("");
        isEqualPressed = true;
        isOperatorPressed = false;
        firstNumber = 0;
        secondNumber = 0;
        ch = '\0';
    }

    public void resetfunc(View view) {
        resetDisplay();
        isOperatorPressed=false;
    }

    private void displayResult(double res) {
        // אם התוצאה היא עגולה, נציג רק את החלק השלם
        if (res == (int) res) {
            result.setText(String.valueOf((int) res));  // הצגת תוצאה כשלם
            eq.setText(String.valueOf((int) res));      // הצגת תוצאה כשלם
        } else {
            result.setText(String.valueOf(res));       // הצגת תוצאה עם חלק עשרוני
            eq.setText(String.valueOf(res));           // הצגת תוצאה עם חלק עשרוני
        }
        resetOnNextInput = true;
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void resetDisplay() {
        eq.setText("");
        result.setText("");
        isOperatorPressed = false;
        isEqualPressed = false;
        firstNumber = 0;
        secondNumber = 0;
        ch = '\0';
    }

    private boolean isValidNumber(String text) {
        return !text.isEmpty(); // מוודא שהקלט לא ריק
    }
}
