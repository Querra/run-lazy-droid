package de.querra.mobile.runlazydroid.services.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageService{

    private static ImageService INSTANCE;

    private ImageService(){
        // No external instantiation of singleton
    }

    public boolean saveImage(Context context, Bitmap bitmap, String filename){
        // Assume block needs to be inside a Try/Catch block.
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut;
        File file = new File(path, filename); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        try {
            fOut.close(); // do not forget to close the stream
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(),file.getName(),file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Bitmap getImage(String filename){
        String path = String.format("%s/%s", Environment.getExternalStorageDirectory(),filename);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        return BitmapFactory.decodeFile(path, options);
    }

    public static ImageService getInstance(){
        if (INSTANCE == null){
            synchronized (ImageService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageService();
                }
            }
        }
        return INSTANCE;
    }
}
