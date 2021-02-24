package com.example.tapisirisi.UI.utilUiOpenCv;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BitmapToFile {
    private static final String TAG = "BitmapToFile";

    public static File persistImage(Bitmap bitmap, String name, Context context) {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(TAG, "Error writing bitmap", e);
        }
        return imageFile;
    }
}
