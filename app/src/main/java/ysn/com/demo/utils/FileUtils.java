package ysn.com.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author yangsanning
 * @ClassName FileUtils
 * @Description 一句话概括作用
 * @Date 2019/8/12
 * @History 2019/8/12 author: description:
 */
public class FileUtils {

    /**
     * 保存图片
     *
     * @param bitmap 图片 bitmap
     * @return true: 保存成功, false: 保存失败
     */
    public static boolean saveImageWithPNG(Context context, Bitmap bitmap) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/doodleview/" + System.currentTimeMillis() + ".png";
        if (!new File(filePath).exists()) {
            new File(filePath).getParentFile().mkdir();
        }
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        sendBroadcast(context, new File(filePath));
        return true;
    }


    /**
     * 保存图片后发送广播通知更新数据库
     */
    private static void sendBroadcast(Context context, File file) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }
}
