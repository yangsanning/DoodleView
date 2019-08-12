package ysn.com.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ysn.com.demo.utils.FileUtils;
import ysn.com.view.doodle.DoodleView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DoodleView doodleView;
    private AlertDialog typeDialog, colorDialog, strokeWidthDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setClick(R.id.main_activity_type);
        setClick(R.id.main_activity_color);
        setClick(R.id.main_activity_stroke_width);
        setClick(R.id.main_activity_back);
        setClick(R.id.main_activity_reset);
        setClick(R.id.main_activity_save);

        doodleView = findViewById(R.id.main_activity_doodle_view);
    }

    private void setClick(int id) {
        findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            switch (view.getId()) {
                case R.id.main_activity_type:
                    showTypeDialog();
                    break;
                case R.id.main_activity_color:
                    showColorDialog();
                    break;
                case R.id.main_activity_stroke_width:
                    showStrokeWidthDialog();
                    break;
                case R.id.main_activity_back:
                    doodleView.back();
                    break;
                case R.id.main_activity_reset:
                    doodleView.reset();
                    break;
                case R.id.main_activity_save:
                    saveImage();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 画笔类型对话框
     */
    private void showTypeDialog() {
        if (typeDialog == null) {
            typeDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.text_paint_type)
                    .setSingleChoiceItems(new String[]{"路径", "直线", "矩形", "圆形", "实心矩形", "实心圆"}, (0),
                            (dialog, which) -> {
                                doodleView.setPaintType(which);
                                dialog.dismiss();
                            }).create();
        }
        typeDialog.show();
    }

    /**
     * 画笔颜色对话框
     */
    private void showColorDialog() {
        if (colorDialog == null) {
            colorDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.text_paint_color)
                    .setSingleChoiceItems(new String[]{"黑色", "红色", "蓝色"}, 0,
                            (dialog, which) -> {
                                switch (which) {
                                    case 0:
                                        doodleView.setPaintColor("#000000");
                                        break;
                                    case 1:
                                        doodleView.setPaintColor(getResources().getColor(R.color.colorAccent));
                                        break;
                                    case 2:
                                        doodleView.setPaintColor("#0000ff");
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }).create();
        }
        colorDialog.show();
    }


    /**
     * 画笔宽度对话框
     */
    private void showStrokeWidthDialog() {
        if (strokeWidthDialog == null) {
            strokeWidthDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.text_paint_stroke_width)
                    .setSingleChoiceItems(new String[]{"细", "中", "粗"}, 0,
                            (dialog, position) -> {
                                switch (position) {
                                    case 0:
                                        doodleView.setPaintStroke(5);
                                        break;
                                    case 1:
                                        doodleView.setPaintStroke(10);
                                        break;
                                    case 2:
                                        doodleView.setPaintStroke(15);
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }).create();
        }
        strokeWidthDialog.show();
    }

    /**
     * 保存涂鸦
     */
    @SuppressLint("CheckResult")
    private void saveImage() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(granted -> {
                    if (granted) {
                        if (FileUtils.saveImageWithPNG((MainActivity.this), doodleView.getDoodleBitmap())) {
                            showMessage("保存成功");
                        } else {
                            showMessage("保存失败");
                        }
                    } else {
                        showMessage("权限不足");
                    }
                });
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}