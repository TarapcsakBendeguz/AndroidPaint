package com.example.drawinghw;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.drawinghw.model.DrawObject;
import com.example.drawinghw.model.Point;
import com.example.drawinghw.model.Circle;
import com.example.drawinghw.model.Image;
import com.example.drawinghw.model.Line;
import com.example.drawinghw.model.Square;
import com.example.drawinghw.model.Text;
import com.example.drawinghw.sqlite.PersistentDataHelper;
import com.example.drawinghw.view.DrawingView;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DrawingActivity extends AppCompatActivity {

    private DrawingView canvas;
    private MenuItem styleMenu;
    private MenuItem strokeMenu;

    private PersistentDataHelper dataHelper;

    private Bitmap image;
    private List<String> projects;
    private String current;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        canvas = findViewById(R.id.canvas);
        projects = new ArrayList<>();
        dataHelper = new PersistentDataHelper(this);
        dataHelper.open();
        checkSavedData();
    }

    private void checkSavedData() {
        //dataHelper.getDbHelper().resetTables(dataHelper.getDatabase());
        projects = dataHelper.restoreProjects();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataHelper.open();
    }

    @Override
    protected void onPause() {
        dataHelper.close();
        super.onPause();
    }

    private void restorePersistedObjects(String proj) {
        onResume();
        current = proj;
        try {
            List<DrawObject> drawObjects = new ArrayList<>();

            List<Point> points = dataHelper.restorePoints(proj);
            List<Line> lines = dataHelper.restoreLines(proj);
            List<Circle> circles = dataHelper.restoreCircles(proj);
            List<Square> squares = dataHelper.restoreSquares(proj);
            List<Image> images = dataHelper.restoreImages(proj);
            List<Text> texts = dataHelper.restoreTexts(proj);

            drawObjects.addAll(points);
            drawObjects.addAll(lines);
            drawObjects.addAll(circles);
            drawObjects.addAll(squares);
            drawObjects.addAll(images);
            drawObjects.addAll(texts);

            //dataHelper.getDbHelper().resetTables(dataHelper.getDatabase());
            canvas.restoreObjects(drawObjects);
        }
        catch (SQLiteException sqle){
            Log.e("Error", "Error in SQLite: " + sqle.toString());
            if(sqle.toString().contains("points") || sqle.toString().contains("lines") || sqle.toString().contains("squares") || sqle.toString().contains("circles")){
                dataHelper.getDbHelper().resetTables(dataHelper.getDatabase());
            }
            if(sqle.toString().contains("no such column")){
                dataHelper.getDbHelper().simpleUpgrade(dataHelper.getDatabase());
                dataHelper.getDbHelper().resetTables(dataHelper.getDatabase());
            }
        }
        catch (Exception genereale){
            Log.e("Error", "Error: " + genereale.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        final Menu toolbarMenu = toolbar.getMenu();


        getMenuInflater().inflate(R.menu.menu_toolbar, toolbarMenu);
        for (int i = 0; i < toolbarMenu.size(); i++) {
            final MenuItem menuItem = toolbarMenu.getItem(i);
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(final MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
            if (menuItem.getItemId() == R.id.menu_style){
                styleMenu = menuItem;//így egyszerűbb volt, mint kikeresni a menuItem ősét
            }
            if (menuItem.getItemId() == R.id.stroke_width){
                strokeMenu = menuItem;
            }
            if (menuItem.hasSubMenu()) {
                final SubMenu subMenu = menuItem.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    subMenu.getItem(j).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem item) {
                            return onOptionsItemSelected(item);
                        }
                    });
                }
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_style_line:
                canvas.setDrawingStyle(DrawingView.DrawingStyle.LINE);
                styleMenu.setIcon(R.drawable.baseline_remove_white_24);
                item.setChecked(true);
                return true;
            case R.id.menu_style_point:
                canvas.setDrawingStyle(DrawingView.DrawingStyle.POINT);
                styleMenu.setIcon(R.drawable.baseline_filter_center_focus_white_24);
                item.setChecked(true);
                return true;
            case R.id.menu_style_circle:
                canvas.setDrawingStyle(DrawingView.DrawingStyle.CIRCLE);
                styleMenu.setIcon(R.drawable.baseline_panorama_fish_eye_white_24);
                item.setChecked(true);
                return true;
            case R.id.menu_style_square:
                canvas.setDrawingStyle(DrawingView.DrawingStyle.SQUARE);
                styleMenu.setIcon(R.drawable.baseline_crop_square_white_24);
                item.setChecked(true);
                return true;
            case R.id.menu_style_image:
                canvas.setDrawingStyle(DrawingView.DrawingStyle.IMAGE);
                styleMenu.setIcon(R.drawable.baseline_collections_white_24);
                item.setChecked(true);
                selectImage();
                return true;
            case R.id.menu_style_text:
                canvas.setDrawingStyle(DrawingView.DrawingStyle.TEXT);
                styleMenu.setIcon(R.drawable.baseline_edit_white_24);
                item.setChecked(true);
                startActivityForResult(new Intent(DrawingActivity.this,TextPicker.class), 2);
                return true;
            case R.id.menu_save:
                Intent intent = new Intent(DrawingActivity.this,ProjectPicker.class);
                String s;
                if(projects.size() == 0){
                    s = "";
                }
                else{
                    s = projects.get(0);
                    for (int i = 1; i < projects.size(); i++){
                        s += ","+projects.get(i);
                    }
                }
                intent.putExtra("PROJECT", s);
                intent.putExtra("TYPE", 1);
                startActivityForResult(intent, 4);
                //onSave();
                return true;
            case R.id.menu_load:
                Intent lintent = new Intent(DrawingActivity.this,ProjectPicker.class);
                String ls;
                if(projects.size() == 0){
                    ls = "";
                }
                else{
                    ls = projects.get(0);
                    for (int i = 1; i < projects.size(); i++){
                        ls += ","+projects.get(i);
                    }
                }
                lintent.putExtra("PROJECT", ls);
                lintent.putExtra("TYPE", 0);
                startActivityForResult(lintent, 4);
                //restorePersistedObjects();
                return true;
            case R.id.menu_delete:
                canvas.clearCanvas();
                return true;
            case R.id.color_other:
                item.setChecked(true);
                startActivityForResult(new Intent(DrawingActivity.this,ColorPicker.class), 0);
                return true;
            case R.id.color_red:
                item.setChecked(true);
                canvas.setPaint(Color.rgb(255,0,0));
                return true;
            case R.id.color_green:
                item.setChecked(true);
                canvas.setPaint(Color.rgb(0,255,0));
                return true;
            case R.id.color_blue:
                item.setChecked(true);
                canvas.setPaint(Color.rgb(0,0,255));
                return true;
            case R.id.stroke_small:
                item.setChecked(true);
                canvas.setPenSize(5);
                strokeMenu.setIcon(R.drawable.ic_strk_width_small);
                return true;
            case R.id.stroke_med:
                item.setChecked(true);
                canvas.setPenSize(20);
                strokeMenu.setIcon(R.drawable.ic_strk_width_med);
                return true;
            case R.id.stroke_big:
                item.setChecked(true);
                canvas.setPenSize(35);
                strokeMenu.setIcon(R.drawable.ic_strk_width_big);
                return true;
            case R.id.stroke_spec:
                item.setChecked(true);
                strokeMenu.setIcon(R.drawable.ic_strk_width_spec);
                startActivityForResult(new Intent(DrawingActivity.this,StrokeWidthPicker.class), 5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectImage(){
        Intent pickPhoto = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        switch (reqCode) {
            case 0: {
                if (resCode == RESULT_OK) {
                    int red = data.getIntExtra("red", 0);
                    int green = data.getIntExtra("green", 0);
                    int blue = data.getIntExtra("blue", 0);
                    canvas.setPaint(Color.rgb(red, green, blue));
                }
                break;
            }
            case 1:
                if (data != null) {// e.g. "back" pressed"
                    try {
                        Uri contentURI = Uri.parse(data.getDataString());
                        ContentResolver cr = getContentResolver();
                        InputStream in = null;
                        in = cr.openInputStream(contentURI);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        image = BitmapFactory.decodeStream(in, null, options);
                        Bitmap mute_bitmap = image.copy(image.getConfig(),true);

                        canvas.setTempImg(mute_bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    canvas.setTempImg(null);
                }
                break;
            case 2: {
                if (resCode == RESULT_OK) {
                    List<String> textData = new ArrayList<>();
                    textData.add(data.getStringExtra("text"));
                    textData.add(data.getStringExtra("type"));
                    textData.add(data.getStringExtra("size"));
                    textData.add(data.getStringExtra("b"));
                    textData.add(data.getStringExtra("i"));

                    canvas.setTextData(textData);
                }
                else{
                    canvas.setTextData(null);
                }
                break;
            }
            case 4: {
                if (resCode == RESULT_OK) {
                    String proj = data.getStringExtra("choosen");
                    int type = data.getIntExtra("s_l", -1);

                    if (type == 0){
                        restorePersistedObjects(proj);
                    }
                    else if (type == 1){
                        onSave(proj);
                    }
                }
                break;
            }
            case 5: {
                if (resCode == RESULT_OK) {
                    int width = data.getIntExtra("size", -1);
                    canvas.setPenSize(width);
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.are_you_sure_want_to_exit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        onExit();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private  void onSave(String proj){
        onResume();
        current = proj;
        Log.d("onSave", "to: "+proj);
        if(!projects.contains(proj)){
            projects.add(proj);
        }
        dataHelper.persistPoints(canvas.getPoints(),proj);
        dataHelper.persistLines(canvas.getLines(),proj);
        dataHelper.persistCircles(canvas.getCircles(),proj);
        dataHelper.persistSquares(canvas.getSquares(),proj);
        dataHelper.persistImages(canvas.getImages(),proj);
        dataHelper.persistTexts(canvas.getText(),proj);
        dataHelper.persistProjects(projects);
    }

    private void onExit() {
        dataHelper.close();
        finish();
    }
}

