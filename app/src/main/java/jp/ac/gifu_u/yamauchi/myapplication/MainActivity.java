package jp.ac.gifu_u.yamauchi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new MyView(this));
        //Button button4 = findViewById(R.id.button4);
        //button4.setOnClickListener(new View.OnClickListener() {

            /*public void onClick(View v) {
                // button1 がクリックされたときの処理
                Log.d("ButtonClick", "OK");
            }*/
    }
}
