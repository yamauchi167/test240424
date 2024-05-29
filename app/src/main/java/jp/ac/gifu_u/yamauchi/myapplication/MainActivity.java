package jp.ac.gifu_u.yamauchi.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        CameraView camview = new CameraView(this);
        this.setContentView(camview);
    }
    @Override
    protected  void onResume(){
        super.onResume();
        final int FREQUENCY = 8000;
        int bufsize = AudioRecord.getMinBufferSize(FREQUENCY,AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT
        )
        ;
        short[] buf = new  short[bufsize];

        AudioRecord rec = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
                FREQUENCY,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufsize);
        rec.startRecording();

        rec.stop();
        rec.release();
        rec.read(buf, 0, bufsize);

        int datasize = rec.read(buf,0,bufsize), max = 0;
        for(int i = 0; i < datasize; i++){
            if((buf[i] > 0) && (buf[i] > max)){ max = buf[i];}
            if((buf[i] < 0) && (buf[i] < -max)){ max = -buf[i];}
        }
        String str = Integer.toString(max);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}