package unilife.com.unilife.chat;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUploadManager {
    private static final String TAG = "FileUploadManager";
    private long mFileSize;
    private File mFile;
    private BufferedInputStream stream;
    private String mData;
    private int mBytesRead;

    public boolean prepare(String fullFilePath) {
        mFile = new File(fullFilePath);
        mFileSize = mFile.length();

        Log.e("FSize", String.valueOf(mFileSize));
        try {
            Log.e(TAG, "qqqqq" + "qqqqq");

            FileInputStream fileInputStream = new FileInputStream(mFile);
            stream = new BufferedInputStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getFileName() {
        return mFile.getName();
    }

    public long getFileSize() {
        return mFileSize;
    }

    public long getBytesRead() {
        return mBytesRead;
    }

    public String getData() {
        return mData;
    }

    public void read(int byteOffset) {
        try {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//            byte[] buffer = new byte[4096];
            byte[] buffer = new byte[1000000];
            mBytesRead = stream.read(buffer);
            byteBuffer.write(buffer, 0, mBytesRead);

            Log.e(TAG, "Read :" + mBytesRead);
            mData = Base64.encodeToString(byteBuffer.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (stream != null) {
            try {
                Log.e("CHECK", "CHECK");
                mFile.deleteOnExit();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stream = null;
        }
    }
}
