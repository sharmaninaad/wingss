package com.example.android.wingss.Activities;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.wingss.DbPackage.Dbcontract;
import com.example.android.wingss.R;

import java.io.UnsupportedEncodingException;

import static com.example.android.wingss.Activities.Login.mail_user;
import static com.example.android.wingss.R.drawable.mail;


@SuppressWarnings("deprecation")
public class MakePhoto extends AppCompatActivity {

    static Camera camera = null;
    Button photo_le;

    @SuppressWarnings("deprecation")

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_photo);
        photo_le = (Button) findViewById(R.id.take_photo);


        // do we have a camera?
        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId < 0) {
                Toast.makeText(this, "No front facing camera found.",
                        Toast.LENGTH_LONG).show();
            } else {
                camera = Camera.open(cameraId);

            }
        }
        Preview preview = new Preview(this, camera);
        FrameLayout preview_frame = (FrameLayout) findViewById(R.id.preview);
        preview_frame.addView(preview);
        final Camera.PictureCallback mPicture = new Camera.PictureCallback() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override

            public void onPictureTaken(byte[] data, Camera camera) {

//                    File pictureFile = getOutputMediaFile();
//                    if (pictureFile == null) {
//                        Toast.makeText(MakePhoto.this, "No storage found", Toast.LENGTH_SHORT).show();
//
//                        return;
//                    }
//                    try {
//                        FileOutputStream fos = new FileOutputStream(pictureFile);
//                        fos.write(data);
//                        fos.close();
//                    } catch (FileNotFoundException e) {
//                       Log.i("FileNotFoundException: ",e.getMessage());
//                    } catch (IOException e) {
//                        Log.i("Io exception: ",e.getMessage());
//
//                    }
//                }


                //finished saving picture

                try {
                    String byte_img = new String(data, "UTF-8");
                    ContentValues cv = new ContentValues();
                    cv.put(Dbcontract.Dbentry.COLUMN_IMG, byte_img);
                    String[] s_args = {Login.mail_user};
                    Login.database.update(Dbcontract.Dbentry.TABLE_NAME, cv, Dbcontract.Dbentry.COLUMN_MAIL + " like ? ", s_args);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



            }
        };

        photo_le.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //mp.start();

                    camera.takePicture(null, null, mPicture);


                } catch (Exception e) {
                    Log.e("Error", "" + e);
                }
            }
        });
    }

    //  @Nullable
//    private static File getOutputMediaFile() {
//        File mediaStorageDir = new File(
//                Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                "MyCameraApp");
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d("MyCameraApp", "failed to create directory");
//                return null;
//            }
//        }
//        // Create a media file name
//        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
//                .format(new Date());
//        File mediaFile;
//        mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                + "IMG_" + timeStamp + ".jpg");
//
//        return mediaFile;
//    }


    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d("makephoto", "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera = null;
        }
        super.onPause();
    }
}