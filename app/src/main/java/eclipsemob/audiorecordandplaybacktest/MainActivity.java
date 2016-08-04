package eclipsemob.audiorecordandplaybacktest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.*;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.format.Time;
import android.webkit.WebView;
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
import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
//    private RecordButton mRecordButton = null; // not used
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private ExtAudioRecorder recorder = null;
    private Button rec;
    //GPS testing VV
    private Button gpsTime;
    private TextView gpsLoc;
    private LocationManager locationManager;
    private LocationListener locationListener;
    //GPS testing ^^
    //Timer tester VV
    private TextView time;
//  private Button timeRec;// Use same button as gpsLoc
    //Timer tester ^^
    //WebView tester VV
    private Button web;
    private WebView webView = (WebView) findViewById(R.id.webview1);
    //WebView tester ^^
    private boolean isRecording = false;
    private Thread recordingThread = null;

    //String file = Environment.getExternalStorageDirectory().getPath() + "output" + (int) Math.ceil(Math.random() * 1000) + ".wav";
    public String FILE = "/sdcard/output" + (int) Math.ceil(Math.random() * 1000) + ".wav";

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
//NOT USED
//    class RecordButton extends Button {
//        boolean mStartRecording = true;
//
//        OnClickListener clicker = new OnClickListener() {
//            public void onClick(View v) {
//                onRecord(mStartRecording, FILE);
//                if (mStartRecording) {
//                    setText("Stop recording");
//                } else {
//                    setText("Start recording");
//                }
//                mStartRecording = !mStartRecording;
//            }
//        };
//
//        public RecordButton(Context ctx) {
//            super(ctx);
//            setText("Start recording");
//            setOnClickListener(clicker);
//        }
//    }
//NOT USED
//NOT USED
//    public OnClickListener listener = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.button1:
//
//                    break;
//
//
//            }
//        }
//    };
//NOT USED

    @TargetApi(Build.VERSION_CODES.M)//required for api 23+
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        gpsTime = (Button) findViewById(R.id.button1); // GPS testing
        gpsLoc = (TextView) findViewById(R.id.textView2);// GPS testing
        time = (TextView) findViewById(R.id.textView3);// TIME testing
        web = (Button) findViewById(R.id.button2); // Webview testing
        // OPEN WEBPAGE VV
        web.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //open webpage code here
            }
        });
        // OPEN WEBPAGE ^^
        // audio recording VV
        rec.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                isRecording = !isRecording;
                onRecord(isRecording,FILE);
            }
        });
        // audio recording ^^
        // LOCATION CODE VV
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                gpsLoc.append("\n" + location.getLatitude() + " " + location.getLongitude());
                time.append("\n" + DateFormat.getDateTimeInstance().format(new Date()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10 );
            }

            return;
        }else{
            configureButton();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }
    private void configureButton() {
        gpsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 600000, 15, locationListener);//provider, minimum time till next call(milliseconds), distance till upadte is required(meters), Location Listener
            }
        });
    }
    //LOCATION CODE ^^
}