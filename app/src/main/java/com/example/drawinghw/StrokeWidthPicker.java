package com.example.drawinghw;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.drawinghw.view.DrawingView;

public class StrokeWidthPicker extends AppCompatActivity {

    private DrawingView canvas;
    private EditText stroke_size;
    private Button ok;
    private int curr_strk_width;
    //private Line line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroke_width_picker);

        canvas = findViewById(R.id.canvas);
        canvas.clearCanvas();
        stroke_size = findViewById(R.id.stroke_size_val);
        ok = findViewById(R.id.okbutton);
        curr_strk_width = (int)Float.parseFloat(stroke_size.getText().toString());
        canvas.setPenSize(curr_strk_width);
        canvas.setDrawingStyle(DrawingView.DrawingStyle.LINE);

        stroke_size.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String t = s.toString();
                if (t.length() < 1)
                    return;
                float f = Float.parseFloat(t);
                if (f < 1) {
                    f = 1;
                    stroke_size.setText(String.valueOf(f));
                }
                if (f > 50) {
                    f = 50;
                    stroke_size.setText(String.valueOf(f));
                }

                curr_strk_width = (int)f;
                canvas.clearCanvas();
                canvas.setPenSize(curr_strk_width);
                canvas.setDrawingStyle(DrawingView.DrawingStyle.LINE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("size", curr_strk_width);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


}
