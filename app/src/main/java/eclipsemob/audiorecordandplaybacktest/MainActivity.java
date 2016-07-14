package eclipsemob.audiorecordandplaybacktest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.*;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import com.example.audiocapture.R;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class MainActivity extends Activity {
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private ExtAudioRecorder recorder = null;

    private boolean isRecording = false;
    private Thread recordingThread = null;

    //String file = Environment.getExternalStorageDirectory().getPath() + "output" + (int) Math.ceil(Math.random() * 1000) + ".wav";
    public String FILE = "/sdcard/output" + (int) Math.ceil(Math.random() * 1000) + ".wav";
//    private double lat = 0.0;
//    private double lon = 0.0;
//    private TextView latitude = (TextView)findViewById(R.id.textView2);
//    private TextView longitude = (TextView)findViewById(R.id.textView3);

    private void onRecord(boolean start, String file) {
        if (start) {
//            _getLocation();
            recorder = new ExtAudioRecorder(false,
                    MediaRecorder.AudioSource.MIC, 27750,
                    AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

            recorder.setOutputFile(file);
            recorder.prepare();
            recorder.start();
        } else {
            recorder.stop();
            recorder.release();
            // update media

        }
    }

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording, FILE);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

//    private void _getLocation() {
//        // Get the location manager
//        LocationManager locationManager = (LocationManager)
//                getSystemService(LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        String bestProvider = locationManager.getBestProvider(criteria, false);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = locationManager.getLastKnownLocation(bestProvider);
//        try {
//            lat = location.getLatitude();
//            lon = location.getLongitude();
//            latitude.setText(String.valueOf(lat));
//            longitude.setText(String.valueOf(lon));
//        } catch (NullPointerException e) {
//            lat = -1.0;
//            lon = -1.0;
//        }
//    }

    public OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.button1:

                    break;


            }
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        Button Rec = (Button) findViewById(R.id.button1);
        Rec.setOnClickListener(listener);
        // doesn't use activity_main layout, all created here:
//        LinearLayout ll = new LinearLayout(this);
//        mRecordButton = new RecordButton(this);
//
//        ll.addView(mRecordButton, new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        0)
//        );
//
//        setContentView(ll);
    }
}