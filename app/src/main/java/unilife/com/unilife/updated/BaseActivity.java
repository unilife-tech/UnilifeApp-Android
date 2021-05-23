package unilife.com.unilife.updated;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.utils.SharePref;

public abstract class BaseActivity extends AppCompatActivity {

    public static final String POST_IMAGE_BASE_URL = "http://15.206.103.14/public/post_imgs/";
    public static final String CHAT_MEDIA_URL = "http://15.206.103.14/public/chatdata/";
    public RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_account_circle_black_24dp)
            .error(R.drawable.ic_account_circle_black_24dp)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH);

    public RequestOptions defultImg = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH);

    public RequestOptions defChatback = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.chat_background)
            .error(R.drawable.chat_background)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH);


    public Context mContext = BaseActivity.this;
    public Intent intent;
    public String KEY_USERID = "userid";
    public String KEY_PROFILE_IMAGE = "profile_image";
    public String KEY_USERNAME = "" +
            "";
    public SharePref sharePref;
    //    public SharePrefOfferRide sharePrefOfferRide;
//    public SharePrefFindRide sharePrefFindRide;
    protected TextView txtHeading;
    protected ImageView imgBack;
    protected ImageView imgRight;
    ArrayList<String> arrayList1 = new ArrayList<>();
    ArrayList<Integer> arrayList2 = new ArrayList<>();
    //    ArrayList<ItemNavigation> arrayList = new ArrayList<>();
    private Dialog dialog = null;

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

//    public void showDrawer() {
//        Dialog dialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.layout_drawer);
//
//        ImageView imgProfile = dialog.findViewById(R.id.imgProfile);
//        TextView txtName = dialog.findViewById(R.id.txtName);
//        TextView txtMobile = dialog.findViewById(R.id.txtMobile);
//        ImageView imgClose = dialog.findViewById(R.id.imgClose);
//
//        txtName.setText(sharePref.getName());
//        txtMobile.setText("+91 " + sharePref.getPhone());
//        Glide.with(mContext)
//                .load(sharePref.getImage())
//                .placeholder(R.drawable.profile_user)
//                .error(R.drawable.profile_user)
//                .into(imgProfile);
//
//        imgProfile.setOnClickListener(view -> {
//            start(UserDriverActivity.class);
//            dialog.dismiss();
//        });
//
//        RecyclerView recyclerDrawer = dialog.findViewById(R.id.recyclerDrawer);
//        recyclerDrawer.setHasFixedSize(true);
//        recyclerDrawer.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerDrawer.setAdapter(new NavigationAdapter(mContext, arrayList));
//
//        recyclerDrawer.addOnItemTouchListener(
//                new RecyclerItemClickListener(mContext, (view, position) -> {
//                    Log.e("@@@@@", "" + position);
//                    sharePref.setSelectedDrawer(position);
//                    recyclerDrawer.getAdapter().notifyDataSetChanged();
//
//                    switch (position) {
//                        case 0:
//                            start(HomeActivity.class);
//                            break;
//                        case 1:
//                            start(AvailableRidesActivity.class);
//                            break;
//                        case 2:
//                            start(ChatListingActivity.class);
//                            break;
//                        case 3:
//                            start(MyBookedRidesActivity.class);
//                            break;
//                        case 4:
//                            start(PostedRidesActivity.class);
//                            break;
//                        case 5:
//                            start(UserPostedActivity.class);
//                            break;
//                        case 6:
//                            start(WalletActivity.class);
//                            break;
//                        case 7:
//                            logout();
//
//                            break;
//                    }
//
//                    dialog.dismiss();
//                    if (!this.getClass().getSimpleName().equalsIgnoreCase("HomeActivity")) {
//                        finish();
//                    }
//                })
//        );
//
//        imgClose.setOnClickListener(v -> dialog.dismiss());
//        dialog.show();
//    }

    public byte[] createThumbnail(String filePath) {
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MINI_KIND);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getContentView());
        sharePref = new SharePref(mContext);
//        sharePrefOfferRide = new SharePrefOfferRide(mContext);
//        sharePrefFindRide = new SharePrefFindRide(mContext);
        ButterKnife.bind(this);
//        setUpView();
        onViewReady(savedInstanceState, getIntent());

    }

//    public void invalidAuthentication(String msg) {
//        if (msg.equals("Invalid authentication.")) {
//            Intent intent = new Intent(mContext, LoginActivity.class);
//            // set the new task and clear flags
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }

//    private void setUpView() {
//        arrayList1.add("Home");
//        arrayList1.add("Available Rides");
////        arrayList1.add("Offer a Ride");
//        arrayList1.add("Chat");
//        arrayList1.add("My Booked Ride");
//        arrayList1.add("Posted Rides");
//        arrayList1.add("My Requests");
//        arrayList1.add("Wallet");
//        arrayList1.add("Logout");
//
//        arrayList2.add(R.drawable.ic_home_black_24dp);
//        arrayList2.add(R.drawable.ic_search_black_24dp);
////        arrayList2.add(R.drawable.ic_directions_car_black_24dp);
//        arrayList2.add(R.drawable.ic_forum_black_24dp);
//        arrayList2.add(R.drawable.ic_bookmark_border_black_24dp);
//        arrayList2.add(R.drawable.ic_drawer_request);
//        arrayList2.add(R.drawable.ic_live_help_black_24dp);
//        arrayList2.add(R.drawable.ic_drawer_wallet);
//        arrayList2.add(R.drawable.ic_exit_to_app_black_24dp);
//
//        for (int i = 0; i < arrayList1.size(); i++) {
//            ItemNavigation itemNavigation = new ItemNavigation();
//            itemNavigation.setName(arrayList1.get(i));
//            itemNavigation.setImage(arrayList2.get(i));
//            arrayList.add(itemNavigation);
//        }
//
//        openDrawer();
//        goBack();
//    }

    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        //To be used by child activities
    }

    @Override
    protected void onDestroy() {
        ButterKnife.bind(this);
        super.onDestroy();
    }

    protected void hideKeyboard() {
        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }

//    protected void showBackArrow() {
//        ActionBar supportActionBar = getSupportActionBar();
//        if (supportActionBar != null) {
//            supportActionBar.setDisplayHomeAsUpEnabled(true);
//            supportActionBar.setDisplayShowHomeEnabled(true);
//        }
//    }

    public void showProgressDialog() {
        if (dialog == null) {
            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.layout_progress);
            dialog.show();
        } else if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

//    protected void checkInvalidAuth(String mToastMsg) {
//        if (mToastMsg.equalsIgnoreCase("Invalid authentication.")) {
//            Intent intent = new Intent(mContext, LoginActivity.class);
//            // set the new task and clear flags
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }


    protected boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean d = cm.getActiveNetworkInfo() != null;
        if (!d) {
            showToast("Check your internet connection");
        }
        return cm.getActiveNetworkInfo() != null;
    }

    protected void start(Class<? extends BaseActivity> activity) {
        startActivity(new Intent(this, activity));
    }

    protected abstract int getContentView();

    public String convertDate(String inputDate) {
        String dt = inputDate;
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date;
        try {
            date = originalFormat.parse(inputDate);
            System.out.println("Old Format :   " + originalFormat.format(date));
            System.out.println("New Format :   " + targetFormat.format(date));
            dt = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return dt;
    }

    public String convertMsgTime(String inputDate) {
        String dt = inputDate;
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM HH:mm a");
        Date date;
        try {
            date = originalFormat.parse(inputDate);
            System.out.println("Old Format :   " + originalFormat.format(date));
            System.out.println("New Format :   " + targetFormat.format(date));
            dt = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return dt;
    }

    public void shareQr(Uri imageUri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/jpg");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Select one"));
    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = timeStamp + "_";
        File mFileTemp = null;
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "Fulibooked");
//        String root = this.getDir("Fulibooked", Context.MODE_PRIVATE).getAbsolutePath();
//        File myDir = new File(root + "/Qr");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            mFileTemp = File.createTempFile(imageFileName, ".jpg", directory.getAbsoluteFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return mFileTemp;
    }

    public Uri convertUri(Bitmap bitmap) {

        Uri uri = null;
        try {
            File file = createImageFile();
            if (file != null) {
                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fout);
                    fout.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                uri = Uri.fromFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uri;
    }

    public boolean checkWriteExternalPermission() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = mContext.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkCameraPermission() {
        String permission = Manifest.permission.CAMERA;
        int res = mContext.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public String getStringValue(int str) {
        return Objects.requireNonNull(mContext).getResources().getString(str);
    }
//
//    protected void openDrawer() {
//        if (imgRight == null)
//            imgRight = findViewById(R.id.imgRight);
//        if (imgRight != null) {
//            imgRight.setOnClickListener(view -> showDrawer());
//        }
//    }
//
//    protected void goBack() {
//        if (imgBack == null)
//            imgBack = findViewById(R.id.imgBack);
//        if (imgBack != null) {
//            imgBack.setOnClickListener(view -> finish());
//        }
//    }
//
//    public void setHeading(String resId) {
//        if (txtHeading == null)
//            txtHeading = findViewById(R.id.txtHeader);
//        if (txtHeading != null)
//            txtHeading.setText(resId);
//    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public String time24To12(String time) {
//        String t = "";
//        try {
//            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//            final Date dateObj = sdf.parse(time);
//            System.out.println(dateObj);
//            t = new SimpleDateFormat("hh:mm a").format(dateObj);
//        } catch (final ParseException e) {
//            e.printStackTrace();
//        }
        return time;
    }

    public String getHtmlConverted(String data) {
        Spanned title = Html.fromHtml(data);
        return title.toString();
    }

    public void inviteFriends() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Unilife");
            String shareMessage = "Hey, let’s connect on Unilife, Unilife allows you to communicate easily in Uni and gives you access to students discounts and contents\n\n";
            shareMessage = shareMessage + " https://play.google.com/store/apps/details?id=unilife.com.unilife";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void logout() {
////        showProgressDialog();
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<CommonResponse> call = apiInterface.logout(sharePref.getToken());
//        call.enqueue(new Callback<CommonResponse>() {
//            @Override
//            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
//                Log.e("response status code --", "" + response.code());
////                if (response.isSuccessful() && response.body().getStatus()) {
////                hideProgressDialog();
//
////                }
//            }
//
//            @Override
//            public void onFailure(Call<CommonResponse> call, Throwable t) {
////                hideProgressDialog();
////                sharePref.clearPreferences();
////                sharePrefFindRide.clearPreferences();
////                sharePrefOfferRide.clearPreferences();
////
////                Intent intent = new Intent(mContext, LoginActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                startActivity(intent);
//
//            }
//        });
//
//        sharePref.clearPreferences();
//        sharePrefFindRide.clearPreferences();
//        sharePrefOfferRide.clearPreferences();
//
//        Intent intent = new Intent(mContext, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//
//    }
}


