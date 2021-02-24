package com.example.tapisirisi.UI.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.tapisirisi.R;

import com.example.tapisirisi.ServiceImpl.UserMotif.UserMotifServiceImpl;
import com.example.tapisirisi.Common.database.DatabaseHelper;
import com.squareup.picasso.Picasso;


import android.widget.ImageView;
import android.widget.Toast;


public class HomeFragment extends Fragment {
    Button cabture_btn;
    Button chercher, admin;
    FrameLayout frameLayout;
    ImageView image_capturer;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.home_fragment, container, false);
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        image_capturer = rootView.findViewById(R.id.image_capturer);
        cabture_btn = rootView.findViewById(R.id.button_start_camera);
        chercher = rootView.findViewById(R.id.chercher);
        frameLayout = getActivity().findViewById(R.id.fragment_content);
        admin = rootView.findViewById(R.id.goToAdmin);


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserMotifServiceImpl.class);
                Long id = new DatabaseHelper(getContext()).getCurrentUser().getId();
                i.putExtra("idUser",String.valueOf(id));
                getContext().startService(i);
            }
        });
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
        }
        cabture_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 100);
        });
        chercher.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Cherche Button", Toast.LENGTH_SHORT).show();
        });
//        UserMotifServiceImpl.getAllUserMotif();
//        Intent i = new Intent(getContext(), UserMotifServiceImpl.class);
//        getContext().startService(i); // 80
        Picasso.get().load("http://192.168.1.103:7900/api/tapis-irisi/user-motif/images/50").into(image_capturer);
        return rootView;
        // Create ListView start
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                chercher.setVisibility(View.VISIBLE);
                cabture_btn.setVisibility(View.GONE);
                Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");
                Bitmap resultSize = getResizedBitmap(bitmapImage, 1080, 1080);
                image_capturer.setImageBitmap(resultSize);
            }
        }
    }

    private void setupView() {
        image_capturer = rootView.findViewById(R.id.image_capturer);
        cabture_btn = rootView.findViewById(R.id.button_start_camera);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}
