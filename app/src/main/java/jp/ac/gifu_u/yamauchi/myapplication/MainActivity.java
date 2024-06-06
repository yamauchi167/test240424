package jp.ac.gifu_u.yamauchi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        CameraView camview = new CameraView(this);
        this.setContentView(camview);

        // パーミッションをリクエスト
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) {
            Toast.makeText(this, "オーディオ録音の権限が拒否されました", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionToRecordAccepted) {
            startAudioRecording();
        } else {
            Toast.makeText(this, "オーディオ録音の権限が必要です", Toast.LENGTH_SHORT).show();
        }
    }

    private void startAudioRecording() {
        final int FREQUENCY = 8000;
        int bufsize = AudioRecord.getMinBufferSize(FREQUENCY, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        short[] buf = new short[bufsize];

        AudioRecord rec = null;
        try {
            rec = new AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    FREQUENCY,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufsize);

            rec.startRecording();
            int datasize = rec.read(buf, 0, bufsize);

            int max = 0;
            for (int i = 0; i < datasize; i++) {
                if (buf[i] > max) {
                    max = buf[i];
                }
            }
            String str = Integer.toString(max);
            runOnUiThread(() -> Toast.makeText(this, str, Toast.LENGTH_SHORT).show());

        } catch (SecurityException e) {
            Toast.makeText(this, "録音権限がありません", Toast.LENGTH_SHORT).show();
        } finally {
            if (rec != null) {
                rec.stop();
                rec.release();
            }
        }
    }
}
