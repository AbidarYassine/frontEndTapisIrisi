package com.example.tapisirisi.UI.utilUiOpenCv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tapisirisi.R;

public class CameraStartupActivity extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_READ_EXTERNAL_STORAGE_REQUEST_CODE = 101;
    private static final int MY_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 102;

    private Button startCameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_startup);
        this.getSupportActionBar().hide();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        startCameraButton = findViewById(R.id.button_start_camera);
        startCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraStartupActivity.this, OpenCVCameraActivity.class);
                startActivity(intent);

//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cornedebelier1);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//                byte[] bytes = stream.toByteArray();
//                File pictureFile = OpenCVCameraActivity.getOutputMediaFile(OpenCVCameraActivity.MEDIA_TYPE_IMAGE);
//                Log.i("TAG", "onCameraFrame: " + pictureFile.toString());
//                try {
//                    FileOutputStream fos = new FileOutputStream(pictureFile);
//                    fos.write(bytes);
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Intent intent = new Intent(CameraStartupActivity.this, MotifServiceImpl.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("picture", (Serializable) pictureFile);
//                intent.putExtras(bundle);
//                startService(intent);
            }
        });
    }
}