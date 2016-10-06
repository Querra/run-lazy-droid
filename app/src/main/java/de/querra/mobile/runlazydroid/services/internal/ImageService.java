package de.querra.mobile.runlazydroid.services.internal;

import android.graphics.Bitmap;

public interface ImageService {

    boolean saveImage(Bitmap bitmap, String filename);

    Bitmap getImage(String filename);
}
