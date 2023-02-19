package id.niteroomcreation.archcomponent.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Septian Adi Wijaya on 05/11/19
 * see - https://stackoverflow.com/a/35827955
 */
public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();
    private static ImageUtils reff;
    private String directoryName = "images";
    private final String prefixFileName = "poster_";
    private final String suffixFileName = ".PNG";
    private String fileName = "";
    private final Context context;
    private boolean external;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public static synchronized ImageUtils init(Context context) {
        if (reff == null) {
            reff = new ImageUtils(context);
        }

        return reff;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public ImageUtils setFileName(String fileName) {
        this.fileName = String.format("%s%s%s", prefixFileName, fileName.replaceAll("\\s+", "_"), suffixFileName);
        return this;
    }

    public ImageUtils setExternal(boolean external) {
        this.external = external;
        return this;
    }

    public ImageUtils setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
        return this;
    }

    @NonNull
    private synchronized File createFile() {
        File directory;
        if (external) {
            directory = getAlbumStorageDir(directoryName);
        } else {
            directory = new File(context.getFilesDir(), "app_images");
        }

        if (!directory.exists())
            directory.mkdirs();

        return new File(directory, fileName);
    }

    private String getPathFile() {
        return createFile().toString();
    }

    public void save(Bitmap b) {
        save(b, null);
    }

    public synchronized void save(Bitmap b, ImageUtilsListener listener) {
        FileOutputStream fos = null;

        if (b != null) {

            if (load() != null)
                listener.success(getPathFile());
            else
                try {
                    fos = new FileOutputStream(createFile());
                    if (b.compress(Bitmap.CompressFormat.PNG, 80, fos)) {
                        if (listener != null)
                            listener.success(getPathFile());
                    } else {
                        if (listener != null)
                            listener.failed("compress not success");
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.failed("Exception 85");
                    }
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        if (listener != null) {
                            listener.failed("IOException 93");
                        }
                        e.printStackTrace();
                    }
                }
        } else {
            listener.failed("bitmap null");
        }
    }

    public synchronized Bitmap load() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private File getAlbumStorageDir(String albumName) {
        return new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
    }

    public interface ImageUtilsListener {
        void success(String fileAbsolutePath);

        void failed(String errMsg);
    }
}
