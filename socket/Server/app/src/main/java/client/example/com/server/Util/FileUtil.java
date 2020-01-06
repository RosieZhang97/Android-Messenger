package client.example.com.server.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    public FileUtil() {
    }

    public static boolean isSDCardMounted() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String getSDCardRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static Bitmap getBitmap(String picturePath) {
        FileInputStream fis = null;
        Bitmap bitmap = null;

        try {
            fis = new FileInputStream(picturePath);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            Log.e("tag", e.getMessage());
        } catch (OutOfMemoryError e) {
            Log.e("tag", e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                return null;
            }
        }

        return bitmap;
    }

    public static void saveBitmap(String picturePath, Bitmap bitmap) {
        if (bitmap == null) return;

        File file = new File(picturePath);
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picturePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, getQuality(bitmap.getByteCount()), out);
        } catch (FileNotFoundException e) {
            Log.e("tag", e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }
        }
    }

    private static int getQuality(int length) {
        int quality = 30;

        if (length > 2097152 && length <= 3145728) {    //2-3M
            quality = 30;
        } else if (length > 1048576 && length <= 2097152) {//1-2M
            quality = 50;
        } else if (length < 1048576) {  //少于1M
            quality = 60;
        }

        return quality;
    }
}
