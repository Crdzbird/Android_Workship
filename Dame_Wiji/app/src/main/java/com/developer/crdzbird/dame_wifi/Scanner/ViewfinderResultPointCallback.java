package com.developer.crdzbird.dame_wifi.Scanner;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/**
 * Created by crdzbird on 06-23-16.
 */

final class ViewfinderResultPointCallback implements ResultPointCallback {

  private final ViewfinderView viewfinderView;

  ViewfinderResultPointCallback(ViewfinderView viewfinderView) {
    this.viewfinderView = viewfinderView;
  }

  public void foundPossibleResultPoint(ResultPoint point) {
    viewfinderView.addPossibleResultPoint(point);
  }

}
