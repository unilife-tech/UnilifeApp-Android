package unilife.com.unilife.updated;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.profile.model.responses.UploadImageResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retrofit.WebUrls;
import unilife.com.unilife.updated.model.requests.CreatePostRequest;

public class OpinionActivity extends BaseActivity {
    private static final String VIDEO_DIRECTORY = "/unilife";
    @BindView(R.id.edtComment)
    EditText edtComment;
    @BindView(R.id.imgOpinion)
    ImageView imgOpinion;
    @BindView(R.id.playVideo)
    ImageView playVideo;
    String strOpinion = "";

    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.rootView)
    ConstraintLayout rootView;
    private AlertDialog basic_reg = null;
    private int GALLERY = 1, CAMERA = 2;
    private byte[] imageBytes = null;
    private Uri mImageUri;
    private String strImage = "";
    private String strVideo = "";

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        hideKeyboard();
        txtName.setText(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERNAME));
        try {
            Glide
                    .with(mContext)
                    .load(WebUrls.INSTANCE.getPROFILE_IMAGE_URL() + PreferenceFile.getInstance().getPreferenceData(this, KEY_PROFILE_IMAGE))
                    .apply(options)
                    .into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgOpinion.setVisibility(View.GONE);
                playVideo.setVisibility(View.GONE);
                imgOpinion.setImageDrawable(null);
                findViewById(R.id.imgClose).setVisibility(View.GONE);
                findViewById(R.id.playVideo).setVisibility(View.GONE);
                findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
            }
        });


        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > dpToPx(200)) { // if more than 200 dp, it's probably a keyboard...
                    findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
                    findViewById(R.id.relePhotoVideo).setVisibility(View.VISIBLE);
                } else if (imgOpinion.getDrawable() == null){
                    findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
                    findViewById(R.id.relePhotoVideo).setVisibility(View.GONE);
                }
            }
        });
    }

    public float dpToPx(float valueInDp) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_opinion;
    }

    public void closeView(View view) {
        finish();
    }

    boolean valid() {
        boolean isValid = true;
        if (strOpinion.equals("")) {
            isValid = false;
            edtComment.setHint("Enter your opinion");
            edtComment.setHintTextColor(getResources().getColor(R.color.medium_red));
        }
        return isValid;
    }

    public void addOpinion() {
        if (!isNetworkConnected())
            return;
        CreatePostRequest opinionRequest = new CreatePostRequest();
        CreatePostRequest.EventVideos eventVideos = new CreatePostRequest.EventVideos();

        if (!strImage.equals("")) {
            opinionRequest.setEventImages(strImage);
        }
        if (!strVideo.equals("")) {
            eventVideos.setVideo_1(strVideo);
            opinionRequest.setEventVideos(eventVideos);
        }
        opinionRequest.setCaption(strOpinion);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.addOpinion(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), opinionRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    finish();
                }
                showToast(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void postCall(View view) {
        strOpinion = edtComment.getText().toString().trim();
        if (valid())
            addOpinion();
    }

    public void openVideoImage(View view) {
        hideKeyboard();
        showDialog();
    }

    public void openPoll(View view) {
        start(PollActivity.class);
    }

    public void openEvent(View view) {
        start(EventActivity.class);
    }

    public void openMedia(View view) {
        start(MediaActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == Activity.RESULT_OK) {
                mImageUri = result.getUri();
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(mImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    imageBytes = getBytes(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (imageBytes != null) {
                    uploadImage("image");
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception e = result.getError();
                Toast.makeText(mContext, "Possible error is: " + e, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == GALLERY) {
            Log.d("what", "gale");
            if (data != null) {
                Uri contentURI = data.getData();

                String selectedVideoPath = getPath(contentURI);
                Log.d("path", selectedVideoPath);
                saveVideoToInternalStorage(selectedVideoPath);
                uploadVideo(selectedVideoPath);
            }

        } else if (requestCode == CAMERA) {
            Uri contentURI = data.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr", recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath);
            uploadVideo(recordedVideoPath);
        }
    }

    public void uploadVideo(String mediaPath) {
        if (!isNetworkConnected())
            return;

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("club_image", file.getName(), requestBody);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "video");

        byte[] byteThumb = createThumbnail(mediaPath);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UploadImageResponse> call = apiInterface.uploadVideo(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERID), type, fileToUpload);

        showProgressDialog();
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.body());
                strVideo = response.body().getData();

                imgOpinion.setVisibility(View.VISIBLE);
                playVideo.setVisibility(View.VISIBLE);

                uploadVideoThumb(byteThumb);
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }


    private void saveVideoToInternalStorage(String filePath) {
        File newfile;

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if (currentFile.exists()) {

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v("vii", "Video file saved successfully.");
            } else {
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void uploadImage(String typeName) {
        if (!isNetworkConnected())
            return;

        MultipartBody.Part image = null;
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        image = MultipartBody.Part.createFormData("club_image", "image.jpg", requestFile);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), typeName);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UploadImageResponse> call = apiInterface.upload_post_images(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERID), type, image);

        showProgressDialog();
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                hideProgressDialog();
                strImage = response.body().getData();
                imgOpinion.setVisibility(View.VISIBLE);
                findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
                findViewById(R.id.imgClose).setVisibility(View.VISIBLE);
                imgOpinion.setImageURI(mImageUri);
//                Glide
//                        .with(mContext)
//                        .load(POST_IMAGE_BASE_URL + response.body().getData())
//                        .into(imgOpinion);
                Log.e("response code -->", "" + response.body());

            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void uploadVideoThumb(byte[] bytes) {
        if (!isNetworkConnected())
            return;

        MultipartBody.Part image = null;
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
        image = MultipartBody.Part.createFormData("club_image", "image.jpg", requestFile);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "image");

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UploadImageResponse> call = apiInterface.upload_post_images(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERID), type, image);

        showProgressDialog();
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                hideProgressDialog();
                imgOpinion.setVisibility(View.VISIBLE);
                findViewById(R.id.imgClose).setVisibility(View.VISIBLE);
                findViewById(R.id.playVideo).setVisibility(View.VISIBLE);
                findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
                strVideo = strVideo + "," + response.body().getData();

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgOpinion.setImageBitmap(bitmap);

//                Glide.with(mContext)
//                        .load(POST_IMAGE_BASE_URL + response.body().getData())
//                        .apply(defultImg)
//                        .into(imgOpinion);
                Log.e("response code -->", "" + response.body());

            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    void showDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View v = inflater.inflate(R.layout.layoutcameragallery, null);

        TextView txtCamera = v.findViewById(R.id.txtCamera);
        TextView txtGallery = v.findViewById(R.id.txtGallery);
        TextView txtCancel = v.findViewById(R.id.txtCancel);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basic_reg.dismiss();
            }
        });

        txtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basic_reg.cancel();
                CropImage.activity().start(OpinionActivity.this);
            }
        });

        txtGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.cameravideomenu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        basic_reg.cancel();
                        switch (item.getItemId()) {
                            case R.id.camera:
                                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, 0);
                                startActivityForResult(intent, CAMERA);
                                break;

                            case R.id.gallery:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

                                startActivityForResult(galleryIntent, GALLERY);
                                break;
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        builder.setView(v);
        builder.setCancelable(false);
        builder.create();
        basic_reg = builder.show();
        basic_reg.setCancelable(true);
    }

    public void closeKeyboard(View view) {
        hideKeyboard();
    }
}
