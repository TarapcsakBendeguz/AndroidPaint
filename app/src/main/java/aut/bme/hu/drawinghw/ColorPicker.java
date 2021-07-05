package aut.bme.hu.drawinghw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColorPicker extends AppCompatActivity {

    View colorPrev;
    TextView code;
    SeekBar red, green, blue;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        colorPrev = findViewById(R.id.color_preview);
        red = findViewById(R.id.color_red);
        green = findViewById(R.id.color_green);
        blue = findViewById(R.id.color_blue);
        code = findViewById(R.id.color_code);
        ok = findViewById(R.id.btnOk);

        red.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        green.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        blue.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("red", red.getProgress());
                intent.putExtra("green", green.getProgress());
                intent.putExtra("blue", blue.getProgress());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                changeColor();
            }
        });
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                changeColor();
            }
        });
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                changeColor();
            }
        });
    }

    public void changeColor(){
        int color = Color.rgb(red.getProgress(),green.getProgress(),blue.getProgress());
        colorPrev.setBackgroundColor(color);

        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        code.setText(hexColor);
    }
}

