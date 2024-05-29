package jp.ac.gifu_u.yamauchi.myapplication;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera cam;
    private SurfaceHolder holder;

    public CameraView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        cam = Camera.open(0);
        try {
            cam.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (cam != null) {
            cam.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (cam != null) {
            cam.setPreviewCallback(null);
            cam.stopPreview();
            cam.release();
            cam = null;
        }
    }
}
