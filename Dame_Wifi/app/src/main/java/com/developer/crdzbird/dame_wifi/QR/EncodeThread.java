package com.developer.crdzbird.dame_wifi.QR;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.developer.crdzbird.dame_wifi.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

/**
 * Created by crdzbird on 06-23-16.
 */

final class EncodeThread extends Thread {

  private static final String TAG = EncodeThread.class.getSimpleName();

  private final String contents;
  private final Handler handler;
  private final int pixelResolution;
  private final BarcodeFormat format;

  EncodeThread(String contents, Handler handler, int pixelResolution, BarcodeFormat format) {
    this.contents = contents;
    this.handler = handler;
    this.pixelResolution = pixelResolution;
    this.format = format;
  }

  @Override
  public void run() {
    try {
      Bitmap bitmap = QRCodeEncoder.encodeAsBitmap(contents, format, pixelResolution, pixelResolution);
      Message message = Message.obtain(handler, R.id.encode_succeeded);
      message.obj = bitmap;
      message.sendToTarget();
    } catch (WriterException e) {
      Log.e(TAG, "Could not encode barcode", e);
      Message message = Message.obtain(handler, R.id.encode_failed);
      message.sendToTarget();
    } catch (IllegalArgumentException e) {
      Log.e(TAG, "Could not encode barcode", e);
      Message message = Message.obtain(handler, R.id.encode_failed);
      message.sendToTarget();
    }
  }
}
