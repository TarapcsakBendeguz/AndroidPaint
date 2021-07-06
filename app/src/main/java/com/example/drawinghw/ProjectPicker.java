package com.example.drawinghw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectPicker extends AppCompatActivity {


    private LinearLayout ow, np;
    private Button ok;
    private CheckBox cn,co;
    private Spinner spinner;
    private EditText editText;
    private TextView warn;
    private String projectName;
    private String existing;
    private List<String> projects;
    private int s_l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_picker);
        projects = new ArrayList<>();
        ow = findViewById(R.id.ow);
        np = findViewById(R.id.np);
        ok = findViewById(R.id.okbutton);
        cn = findViewById(R.id.proj_new);
        co = findViewById(R.id.proj_ow);
        warn = findViewById(R.id.warn);
        spinner = findViewById(R.id.project);
        editText = findViewById(R.id.new_proj);

        Intent intent = getIntent();
        existing = intent.getStringExtra("PROJECT");
        s_l = intent.getIntExtra("TYPE", -1);

        if (existing != null && existing != "") {
            String[] arr = existing.split(",");
            for (int i = 0; i < arr.length; i++) {
                projects.add(arr[i]);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, projects);
            spinner.setAdapter(adapter);
        }


        if (s_l == 0){
            warn.setSaveEnabled(false);
            warn.setVisibility(View.GONE);
            co.setSaveEnabled(false);
            co.setVisibility(View.GONE);
            cn.setSaveEnabled(false);
            cn.setVisibility(View.GONE);
            ow.setSaveEnabled(true);
            np.setSaveEnabled(false);
            ow.setVisibility(View.VISIBLE);
            np.setVisibility(View.GONE);
            ok.setSaveEnabled(true);
            ok.setVisibility(View.VISIBLE);
        }
        else{
            cn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        co.setChecked(false);
                        ow.setSaveEnabled(false);
                        np.setSaveEnabled(true);
                        ow.setVisibility(View.GONE);
                        np.setVisibility(View.VISIBLE);
                        ok.setSaveEnabled(true);
                        ok.setVisibility(View.VISIBLE);
                    }
                    else{
                        if (!co.isChecked()){
                            co.setChecked(true);
                        }
                    }
                }
            });

            co.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        cn.setChecked(false);
                        ow.setSaveEnabled(true);
                        np.setSaveEnabled(false);
                        ow.setVisibility(View.VISIBLE);
                        np.setVisibility(View.GONE);
                        ok.setSaveEnabled(true);
                        ok.setVisibility(View.VISIBLE);
                    }
                    else{
                        if (!cn.isChecked()){
                            cn.setChecked(true);
                        }
                    }
                }
            });
        }
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (s_l == 1) {
                    if (cn.isChecked()) {
                        projectName = editText.getText().toString();
                    } else if (co.isChecked()) {
                        projectName = spinner.getSelectedItem().toString();
                    }
                }
                else{
                    projectName = spinner.getSelectedItem().toString();
                }
                Intent intent = new Intent();
                intent.putExtra("choosen", projectName);
                intent.putExtra("s_l", s_l);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
