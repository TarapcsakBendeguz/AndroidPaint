package aut.bme.hu.drawinghw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

public class TextPicker extends AppCompatActivity{

    private EditText text, text_size;
    private Spinner text_type;
    private CheckBox cb,ci;
    private Button ok;
    private String st = "DEFAULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_picker);

        text = findViewById(R.id.text_preview);
        text_size = findViewById(R.id.font_size);
        text_type = findViewById(R.id.font_type);
        cb = findViewById(R.id.font_bold);
        ci = findViewById(R.id.font_italic);
        ok = findViewById(R.id.okbutton);
        String[] items = new String[]{"Normal", "Sans", "Serif", "Monospace"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        text_type.setAdapter(adapter);
        text_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        text.setTypeface(Typeface.DEFAULT);
                        st = "DEFAULT";
                        break;
                    case 1:
                        text.setTypeface(Typeface.SANS_SERIF);
                        st = "SANS_SERIF";
                        break;
                    case 2:
                        text.setTypeface(Typeface.SERIF);
                        st = "SERIF";
                        break;
                    case 3:
                        text.setTypeface(Typeface.MONOSPACE);
                        st = "MONOSPACE";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                text.setTypeface(Typeface.DEFAULT);
                st = "DEFAULT";
                if (cb.isChecked())
                    text.setTypeface(text.getTypeface(), Typeface.BOLD);
                if(ci.isChecked())
                    text.setTypeface(text.getTypeface(), Typeface.ITALIC);
            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if(ci.isChecked())
                        text.setTypeface(text.getTypeface(), Typeface.BOLD_ITALIC);
                    else
                        text.setTypeface(text.getTypeface(), Typeface.BOLD);
                }
                else{
                    text.setTypeface(text.getTypeface(), Typeface.NORMAL);
                    if(ci.isChecked())
                        text.setTypeface(text.getTypeface(), Typeface.ITALIC);
                }
            }
        });

        ci.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if(cb.isChecked())
                        text.setTypeface(text.getTypeface(), Typeface.BOLD_ITALIC);
                    else
                        text.setTypeface(text.getTypeface(), Typeface.ITALIC);
                }
                else{
                    text.setTypeface(text.getTypeface(), Typeface.NORMAL);
                    if(cb.isChecked())
                        text.setTypeface(text.getTypeface(), Typeface.BOLD);
                }
            }
        });

        text_size.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String t = s.toString();
                if (t.length() < 1)
                    return;
                float f = Float.parseFloat(t);
                if (f <= 0)
                    return;
                text.setTextSize(f);
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
                intent.putExtra("text", text.getText().toString());
                intent.putExtra("type", st);
                intent.putExtra("size", text_size.getText().toString());
                intent.putExtra("b", cb.isChecked()? "1" : "0");
                intent.putExtra("i", ci.isChecked()? "1" : "0");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
