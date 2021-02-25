package com.example.tapisirisi.UI.utilUiOpenCv;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.UserMotif.FindByImageService;
import com.example.tapisirisi.model.Propriete;
import com.example.tapisirisi.model.UserMotif;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenCVCameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = OpenCVCameraActivity.class.getName();
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final boolean LOG_MEM_USAGE = true;
    private static final boolean DETECT_RED_OBJECTS_ONLY = false;
    private static final Scalar HSV_LOW_RED1 = new Scalar(0, 100, 100);
    private static final Scalar HSV_LOW_RED2 = new Scalar(10, 255, 255);
    private static final Scalar HSV_HIGH_RED1 = new Scalar(160, 100, 100);
    private static final Scalar HSV_HIGH_RED2 = new Scalar(179, 255, 255);
    private static final Scalar RGB_RED = new Scalar(255, 0, 0);
    private static final int FRAME_SIZE_WIDTH = 640;
    private static final int FRAME_SIZE_HEIGHT = 680;
    private static final boolean FIXED_FRAME_SIZE = true;
    private static final boolean DISPLAY_IMAGES = false;

    private Mat bw;
    private Mat hsv;
    private Mat lowerRedRange;
    private Mat upperRedRange;
    private Mat downscaled;
    private Mat upscaled;
    private Mat contourImage;
    private ActivityManager activityManager;
    private ActivityManager.MemoryInfo mi;
    private Mat hierarchyOutputVector;
    private MatOfPoint2f approxCurve;
    private CameraBridgeViewBase mOpenCvCameraView;
    private Toast toast;
    private OpenCVOverlayView overlayView;
    private LinearLayout bottomSheetLayout;
    private LinearLayout gestureLayout;
    private BottomSheetBehavior sheetBehavior;
    protected ImageView bottomSheetArrowImageView;
    private ImageView recognitionImageView;
    private TextView recognitionTextViewLibelle;
    private TextView recognitionTextViewDescription;
    private ImageView recognitionImageView1;
    private TextView recognitionTextViewLibelle1;
    private TextView recognitionTextViewDescription1;
    private ImageView recognitionImageView2;
    private TextView recognitionTextViewLibelle2;
    private TextView recognitionTextViewDescription2;
    private boolean dataIsAvailable = false;
    private boolean waitingForFrameResult = false;
    private Bitmap motifBitmap;
    private RelativeLayout mRelativeZaplon;
    private RelativeLayout mRelativeToSlide;
    private boolean isVisible = false;
    private RelativeLayout mRelativeZaplon1;
    private RelativeLayout mRelativeToSlide1;
    private boolean isVisible1 = false;
    private RelativeLayout mRelativeZaplon2;
    private RelativeLayout mRelativeToSlide2;
    private boolean isVisible2 = false;
    private TextView proprieteTextView, proprieteTextView1, proprieteTextView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_open_c_v_camera);
        setTitle("Détecter un motif");

        // get the OverlayView responsible for displaying images on top of the camera
        overlayView = findViewById(R.id.overlay_view);
        mOpenCvCameraView = findViewById(R.id.opencv_camera_view);

        if (FIXED_FRAME_SIZE) {
            mOpenCvCameraView.setMaxFrameSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        }
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);

        mi = new ActivityManager.MemoryInfo();
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        // bottom sheet layout elements initialisation
        bottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        gestureLayout = findViewById(R.id.gesture_layout);
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetArrowImageView = findViewById(R.id.bottom_sheet_arrow);

        sheetBehavior.setBottomSheetCallback(
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_HIDDEN:
                                break;
                            case BottomSheetBehavior.STATE_EXPANDED: {
                                bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_down);
                            }
                            break;
                            case BottomSheetBehavior.STATE_COLLAPSED: {
                                bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_up);
                            }
                            break;
                            case BottomSheetBehavior.STATE_DRAGGING:
                                break;
                            case BottomSheetBehavior.STATE_SETTLING:
                                bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_up);
                                break;
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });

        recognitionImageView = findViewById(R.id.detected_motif_image);
        recognitionTextViewLibelle = findViewById(R.id.detected_motif_libelle);
        recognitionTextViewDescription = findViewById(R.id.detcted_motif_description);
        recognitionImageView1 = findViewById(R.id.detected_motif_image1);
        recognitionTextViewLibelle1 = findViewById(R.id.detected_motif_libelle1);
        recognitionTextViewDescription1 = findViewById(R.id.detcted_motif_description1);
        recognitionImageView2 = findViewById(R.id.detected_motif_image2);
        recognitionTextViewLibelle2 = findViewById(R.id.detected_motif_libelle2);
        recognitionTextViewDescription2 = findViewById(R.id.detcted_motif_description2);

        mRelativeZaplon = findViewById(R.id.relativeZaplon);
        mRelativeToSlide = findViewById(R.id.relativevToSlide);
        mRelativeZaplon1 = findViewById(R.id.relativeZaplon1);
        mRelativeToSlide1 = findViewById(R.id.relativevToSlide1);
        mRelativeZaplon2 = findViewById(R.id.relativeZaplon2);
        mRelativeToSlide2 = findViewById(R.id.relativevToSlide2);
        proprieteTextView = findViewById(R.id.proprieteTextView);
        proprieteTextView1 = findViewById(R.id.proprieteTextView1);
        proprieteTextView2 = findViewById(R.id.proprieteTextView2);

        mRelativeToSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    collapse(mRelativeZaplon, 1000, 0);
                    isVisible = false;
                } else if (!isVisible) {
                    expand(mRelativeZaplon, 1000, 200);
                    isVisible = true;
                }

            }
        });

        mRelativeToSlide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible1) {
                    collapse(mRelativeZaplon1, 1000, 0);
                    isVisible1 = false;
                } else if (!isVisible1) {
                    expand(mRelativeZaplon1, 1000, 200);
                    isVisible1 = true;
                }

            }
        });

        mRelativeToSlide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible2) {
                    collapse(mRelativeZaplon2, 1000, 0);
                    isVisible2 = false;
                } else if (!isVisible2) {
                    expand(mRelativeZaplon2, 1000, 200);
                    isVisible2 = true;
                }

            }
        });
    }

    private final BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");

                    bw = new Mat();
                    hsv = new Mat();
                    lowerRedRange = new Mat();
                    upperRedRange = new Mat();
                    downscaled = new Mat();
                    upscaled = new Mat();
                    contourImage = new Mat();

                    hierarchyOutputVector = new Mat();
                    approxCurve = new MatOfPoint2f();

                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            List<UserMotif> userMotifs;
            userMotifs = (List<UserMotif>) bundle.getSerializable("motifs");
            Log.i(TAG, "onReceive: " + userMotifs.size());
            dataIsAvailable = true;
            waitingForFrameResult = false;
            if (userMotifs != null)
                showResultsInBottomSheet(userMotifs);
        }
    };

    public OpenCVCameraActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
        if (toast != null)
            toast.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.intent.action.CustomReciever");
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        if (LOG_MEM_USAGE) {
            activityManager.getMemoryInfo(mi);
            long availableMegs = mi.availMem / 1048576L; // 1024 x 1024
            //Percentage can be calculated for API 16+
            //long percentAvail = mi.availMem / mi.totalMem;
        }

        // get the camera frame as gray scale image
        Mat gray = null;

        if (DETECT_RED_OBJECTS_ONLY) {
            gray = inputFrame.rgba();
        } else {
            gray = inputFrame.gray();
        }

        // the image to output on the screen in the end
        // -> get the unchanged color image
        Mat dst = inputFrame.rgba();

        // down-scale and upscale the image to filter out the noise
        Imgproc.pyrDown(gray, downscaled, new Size(gray.cols() / 2, gray.rows() / 2));
        Imgproc.pyrUp(downscaled, upscaled, gray.size());

        if (DETECT_RED_OBJECTS_ONLY) {
            // convert the image from RGBA to HSV
            Imgproc.cvtColor(upscaled, hsv, Imgproc.COLOR_RGB2HSV);

            // threshold the image for the lower and upper HSV red range
            Core.inRange(hsv, HSV_LOW_RED1, HSV_LOW_RED2, lowerRedRange);
            Core.inRange(hsv, HSV_HIGH_RED1, HSV_HIGH_RED2, upperRedRange);
            // put the two thresholded images together
            Core.addWeighted(lowerRedRange, 1.0, upperRedRange, 1.0, 0.0, bw);
            // apply canny to get edges only
            Imgproc.Canny(bw, bw, 0, 255);
        } else {
            // Use Canny instead of threshold to catch squares with gradient shading
            Imgproc.Canny(upscaled, bw, 0, 255);
        }


        // dilate canny output to remove potential
        // holes between edge segments
        Imgproc.dilate(bw, bw, new Mat(), new Point(-1, 1), 1);

        // find contours and store them all as a list
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        contourImage = bw.clone();
        Imgproc.findContours(
                contourImage,
                contours,
                hierarchyOutputVector,
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE
        );
        //Drawing the Contours
        Scalar color = new Scalar(0, 0, 255);
        Imgproc.drawContours(dst, contours, -1, color, 2, Imgproc.LINE_8,
                hierarchyOutputVector, 2, new Point());

        // loop over all found contours
        for (MatOfPoint cnt : contours) {
            MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

            // approximates a polygonal curve with the specified precision
            Imgproc.approxPolyDP(
                    curve,
                    approxCurve,
                    0.02 * Imgproc.arcLength(curve, true),
                    true
            );

            double contourArea = Imgproc.contourArea(cnt);

            // ignore to small areas
            if (Math.abs(contourArea) < 100) {
                continue;
            }
        }

        if (!waitingForFrameResult) {
            Bitmap bitmap = Bitmap.createBitmap(gray.cols(), gray.rows(), Bitmap.Config.ARGB_8888);
            motifBitmap = bitmap;
            getResult(bitmap);
            waitingForFrameResult = true;
        }
        // return the matrix / image to show on the screen
        return dst;

    }

    /**
     * Helper function to find a cosine of angle between vectors
     * from pt0->pt1 and pt0->pt2
     */
    private static double angle(Point pt1, Point pt2, Point pt0) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1 * dx2 + dy1 * dy2)
                / Math.sqrt(
                (dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10
        );
    }

    /**
     * display a label in the center of the given contur (in the given image)
     *
     * @param im      the image to which the label is applied
     * @param label   the label / text to display
     * @param contour the contour to which the label should apply
     */
    private void setLabel(Mat im, String label, MatOfPoint contour) {
        int fontface = Core.FONT_HERSHEY_SIMPLEX;
        double scale = 3;//0.4;
        int thickness = 3;//1;
        int[] baseline = new int[1];

        Size text = Imgproc.getTextSize(label, fontface, scale, thickness, baseline);
        Rect r = Imgproc.boundingRect(contour);

        Point pt = new Point(
                r.x + ((r.width - text.width) / 2),
                r.y + ((r.height + text.height) / 2)
        );
        Imgproc.putText(im, label, pt, fontface, scale, RGB_RED, thickness);
    }

    /**
     * makes an logcat/console output with the string detected
     * displays also a TOAST message and finally sends the command to the overlay
     *
     * @param content the content of the detected barcode
     */
    private void doSomethingWithContent(String content) {
        Log.d(TAG, "content: " + content); // for debugging in console

        final String command = content;

        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            public void run() {
                overlayView.changeCanvas(command);
            }
        });
    }

    private void getResult(Bitmap bitmap) {
//        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] bytes = stream.toByteArray();
        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (pictureFile != null) {
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(bytes);

                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(OpenCVCameraActivity.this, FindByImageService.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("picture", pictureFile);
            intent.putExtras(bundle);
            startService(intent);

            deleteFile(pictureFile.getName());
        }
    }

    @UiThread
    protected void showResultsInBottomSheet(List<UserMotif> results) {
        int numberOfResults = results.size();
        if (numberOfResults > 0) {
            UserMotif userMotif = results.get(0);

            if (userMotif != null) {
                Picasso.get().load(userMotif.getFileUrl()).into(recognitionImageView);
                String doubleString = String.valueOf(userMotif.getMotif().getPourcentage());
                if (userMotif.getMotif().getLibelle() != null)
                    recognitionTextViewLibelle.setText(userMotif.getMotif().getLibelle() + "(" + doubleString.substring(0, doubleString.indexOf(',') + 3) + "%)");
                if (userMotif.getMotif().getDescription() != null)
                    recognitionTextViewDescription.setText(userMotif.getMotif().getDescription());
                if (userMotif.getMotif().getProprietes() != null && userMotif.getMotif().getProprietes().size() > 0) {
                    for (Propriete propriete : userMotif.getMotif().getProprietes()) {
                        String text = propriete.getLibelle() + ": " + propriete.getDescription() + "\n";
                        proprieteTextView.setText(text);
                    }
                }
            }

            if (numberOfResults > 1) {
                UserMotif userMotif1 = results.get(1);

                if (userMotif1 != null) {
                    Picasso.get().load(userMotif.getFileUrl()).into(recognitionImageView1);
                    String doubleString = String.valueOf(userMotif1.getMotif().getPourcentage());
                    if (userMotif1.getMotif().getLibelle() != null)
                        recognitionTextViewLibelle1.setText(userMotif.getMotif().getLibelle() + "(" + doubleString.substring(0, doubleString.indexOf(',') + 3) + "%)");
                    if (userMotif1.getMotif().getDescription() != null)
                        recognitionTextViewDescription1.setText(userMotif.getMotif().getDescription());
                    if (userMotif1.getMotif().getProprietes() != null && userMotif1.getMotif().getProprietes().size() > 0) {
                        for (Propriete propriete : userMotif1.getMotif().getProprietes()) {
                            String text = propriete.getLibelle() + ": " + propriete.getDescription() + "\n";
                            proprieteTextView1.setText(text);
                        }
                    }
                }

                if (numberOfResults > 2) {
                    UserMotif userMotif2 = results.get(2);

                    if (userMotif2 != null) {
                        Picasso.get().load(userMotif.getFileUrl()).into(recognitionImageView2);
                        String doubleString = String.valueOf(userMotif2.getMotif().getPourcentage());
                        if (userMotif2.getMotif().getLibelle() != null)
                            recognitionTextViewLibelle2.setText(userMotif2.getMotif().getLibelle() + "(" + doubleString.substring(0, doubleString.indexOf(',') + 3) + "%)");
                        if (userMotif2.getMotif().getDescription() != null)
                            recognitionTextViewDescription2.setText(userMotif2.getMotif().getDescription());
                        if (userMotif2.getMotif().getProprietes() != null && userMotif2.getMotif().getProprietes().size() > 0) {
                            for (Propriete propriete : userMotif2.getMotif().getProprietes()) {
                                String text = propriete.getLibelle() + ": " + propriete.getDescription() + "\n";
                                proprieteTextView2.setText(text);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static void expand(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
}