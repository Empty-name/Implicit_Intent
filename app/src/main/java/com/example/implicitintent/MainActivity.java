package com.example.implicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public EditText edit_txt;
    public Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_txt = findViewById(R.id.edit_txt);
        btn_submit = findViewById(R.id.submit_btn);

        String input = edit_txt.getText().toString();

        btn_submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        switch (check(input)) {
                            case 1:
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+input));
                                if (callIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(callIntent);
                                }
                                break;
                            case 2:
                                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                                urlIntent.setData(Uri.parse(input));
                                startActivity(urlIntent);
                            case 3:
                                Toast.makeText(MainActivity.this, "It's not phone number or url!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
         );
    }

    public static boolean isUrlValid(String url) {
        String regex = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return  matcher.matches();
    }

    public static boolean isPhoneValid(String phone) {
        String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static int check(String input){
        if(isPhoneValid(input))
            return 1;
        if(isUrlValid(input))
            return 2;
        return 3;
    }
}