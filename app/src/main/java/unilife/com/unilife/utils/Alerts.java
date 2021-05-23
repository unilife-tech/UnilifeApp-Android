package unilife.com.unilife.utils;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import unilife.com.unilife.R;


import java.io.*;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class Alerts {

    /* Simple snackbar*/
    public static void snackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    /* Snackbar with action*/
    public static void snackBarWithAction(View view, String msg, final Context context, final Class aClass) {
        final Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                context.startActivity(new Intent(context, aClass).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        }).show();
    }

    /* Snackbar for permissions*/
    public static void snackBarForPermissions(View view, String msg, final Activity context, final String arr[], final int requestCode) {
        final Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                ActivityCompat.requestPermissions(context, arr, requestCode);
            }
        }).show();
    }

    /* Alert Dialog with one button*/
    public static void alertDialog(final Context context, String msg) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null, false);

        TextView tvOk = dialogView.findViewById(R.id.tvok);
        TextView tvMsg=dialogView.findViewById(R.id.tvMsg);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setLayout(width, height);

        tvMsg.setText(msg);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

      /*  AlertDialog.Builder dialogBuilder = new
                AlertDialog.Builder(context*//*, R.style.custom_alert_dialog*//*);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.validation_msg, null, false);
        TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
        TextView tvOK = dialogView.findViewById(R.id.tvOK);
        tvMsg.setText(msg);

        dialogBuilder.setView(dialogView);
        final android.support.v7.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setLayout(width, height);

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });


        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();*/

    }

    public static void alertDialogInternet(final Context context, String msg) {

        String  status =null;
        AlertDialog.Builder dialogBuilder=null;
        android.support.v7.app.AlertDialog alertDialog =null;

//        status = NetworkState.getConnectivityStatusString(context);

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();

//        if(status == "Wifi enabled"){
//            alertDialog.dismiss();
//        } else if(status=="Mobile data enabled"){
//            alertDialog.dismiss();
//        }
//        else if(status=="Not connected to Internet"){
//             dialogBuilder = new AlertDialog.Builder(context, R.style.ddialog);
//            View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_nointernet_connection, null, false);
//
//            dialogBuilder.setView(dialogView);
//
////        ivImage.setBackground(context.getResources().getDrawable(R.drawable.login_loader));
//
//            alertDialog = dialogBuilder.create();
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.white)));
//            int width = WindowManager.LayoutParams.MATCH_PARENT;
//            int height = WindowManager.LayoutParams.MATCH_PARENT;
//            alertDialog.getWindow().setLayout(width, height);
//
//            alertDialog.setCancelable(false);
//            alertDialog.setCanceledOnTouchOutside(false);
//            alertDialog.show();
//        }


    }

    public static void alertDialogWithIntent(final Context context, String msg, final Class aClass) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Unilife");
        alertDialogBuilder.setMessage(msg);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg) {


                arg0.dismiss();
                Intent intent=new Intent(context,aClass);
                context.startActivity(intent);
            }
        });

    }

    public static void alertDialogWithIntentClear(final Context context, String msg, final Class aClass) {

        AlertDialog.Builder dialogBuilder = new
                AlertDialog.Builder(context/*, R.style.custom_alert_dialog*/);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null, false);

//        View dialogView = inflater.inflate(R.layout.signout, null);

        TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
        TextView tvOK = dialogView.findViewById(R.id.tvok);

        tvMsg.setText(msg);

        dialogBuilder.setView(dialogView);
        final android.support.v7.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setLayout(width, height);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent=new Intent(context,aClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }



    /* Alert Dialog with one button*/
    public static void alertDialogTwoButtons(Context context, String msg,String firstButton,String secButton) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("FetchMe");
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(firstButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg) {
                arg0.dismiss();
            }
        }); alertDialogBuilder.setPositiveButton(secButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg) {
                arg0.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    /* Alert dialog for intent*/
    public static void alertDialogIntent(final Context context, String msg, final Class to) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Unilife");
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                intent(context, to);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /* Alert dialog for intent*/
    public static void alertDialogIntentClearFlags(final Context context, String msg, final Class to) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("FetchMe");
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                Intent it = new Intent(context, to);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(it);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    /* Start Activity*/
    private static void intent(Context context, Class to) {
        Intent it = new Intent(context, to);
        context.startActivity(it);
    }


    /* Check whether the app is in foreground or in background*/
    public static boolean checkActivation(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;
        if (services.get(0).topActivity.getPackageName().toString().equalsIgnoreCase(context.getPackageName().toString())) {
            isActivityFound = true;
        }
        Log.e(" FCM ", "Activity open: " + isActivityFound);  // true  foreground n false background
        return isActivityFound;
    }


    /* Check Network Availability*/
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        WifiManager wm = (WifiManager)context.getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isConnected()) {
            return true;
        }

        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile != null && mobile.isConnected()) {
            return true;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }



    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }


    public static String getRealPathFromURI(Uri contentURI, AppCompatActivity activity) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }



    // TODO: 3/8/17 get path  for video
    public static String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    public static File compressFile(File file, String name) {
        Bitmap bitmap = shrinkBitmap(file.getPath(), 200, 200);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        file = new File(dir, name);
        if (file.exists()) {
            file.delete();
        }
        file = new File(dir, name);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file.getPath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }



    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);
        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }
        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }


    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }



    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }



    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    public static File makeFile(Bitmap bitmap, String name) {
        File file;
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        file = new File(dir, name);
        if (file.exists()) {
            file.delete();
        }
        file = new File(dir, name);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file.getPath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }





    public static void hideKeyboard(Context context, View view){
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view!=null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

//
//    public static void hideShowPassword(Context context, boolean viewPassword, EditText editText, ImageView imageView){
//        if (viewPassword){
//
//            imageView.setBackground(context.getResources().getDrawable(R.drawable.p_eye));
//            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        }else{
//
//            imageView.setBackground(context.getResources().getDrawable(R.drawable.eye_open));
//            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
////                    tvShow.setHint("Hide");
//        }
//    }


    public static void startActivityClearAllIntent(Context context, Class aClass){
        Intent intent=new Intent(context,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static String makeDirectory(Context context)
    {
        File directory = null;
        String profile_image_path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            directory = new File(Environment.getExternalStorageDirectory() + "/unilife");
            if (!directory.exists())
                directory.mkdirs();
        } else {
            directory = context.getDir("unilife", context.MODE_PRIVATE);
            if (!directory.exists())
                directory.mkdirs();
        }

        if (directory != null) {
            File profile_image = new File(directory + File.separator + "unilife");

            if (!profile_image.exists())
                profile_image.mkdirs();

            profile_image_path = directory + "";

        }
        return profile_image_path;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                DatabaseUtils.dumpCursor(cursor);
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isGoogleDocsUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    private static void copyFileStream(File dest, Uri uri, Context context)
            throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
        }
    }



//    public static String getPath(Context context, Uri uri) {
//        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
//        final String selection;
//        final String[] selectionArgs;
//        // Uri is different in versions after KITKAT (Android 4.4), we need to
//        // deal with different Uris.
//        if (needToCheckUri && DocumentsContract.isDocumentUri(context, uri)) {
//            if ( isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                } else {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//
//            } else if (isDownloadsDocument(uri)) {
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//                Toast.makeText(context, "contentUri"+contentUri, Toast.LENGTH_SHORT).show();
//                Log.e("contentUri", "getPath: "+contentUri );
//                return getDataColumn(context, contentUri, null, null);
//            }
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//
//                selection = "_id=?";
//                selectionArgs = new String[]{
//                        split[1]
//                };
//                return getDataColumn(context, contentUri, selection, selectionArgs);
////                final String docId = DocumentsContract.getDocumentId(uri);
////                final String[] split = docId.split(":");
////                final String type = split[0];
////                if ("image".equals(type)) {
////                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
////                } else if ("video".equals(type)) {
////                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
////                } else if ("audio".equals(type)) {
////                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
////                }
////                selection = "_id=?";
////                selectionArgs = new String[]{ split[1] };
//            }
//
//            /*
//             * check whether the document is selected from google drive
//             * then get the filename and path from the uri
//             *
//             * @Params uri
//             * */
//            else if (isGoogleDocsUri(uri)) {
//                Log.e("contentUri", "getPath: "+"GOOGLE DRIVE" );
//                String filename = "";
//                String filePath = "";
//                String mimeType = context.getContentResolver().getType(uri);
//                if (mimeType == null) {
//                    String path = getPath(context, uri);
//                    if (path == null) {
////                        filename = FilenameUtils.getName(uri.toString());  // comment for Fetchme
//                    } else {
//                        File file = new File(path);
//                        filename = file.getName();
//                    }
//                } else {
//                    Cursor returnCursor = context.getContentResolver().
//                            query(uri, null, null, null, null);
//                    int nameIndex = returnCursor.
//                            getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                    returnCursor.moveToFirst();
//                    filename = returnCursor.getString(nameIndex);
//                }
//                String path = makeDirectory(context);
//                File fileSave = new File(path);
//                filePath = fileSave.getAbsolutePath() + "/" + filename;
//                Log.e("contentUri", "getPath:filePath "+filePath );
//                try {
//                    copyFileStream(new File(filePath), uri, context);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return filePath;
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            // Return the remote address
//            if (isGooglePhotosUri(uri))
//                return uri.getLastPathSegment();
//            return getDataColumn(context, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//        /*if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] projection = { MediaStore.Images.Media.DATA };
//            Cursor cursor = null;
//            try {
//                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                if (cursor.moveToFirst()) {
//                    return cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//            }
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;*/
//    }

}
