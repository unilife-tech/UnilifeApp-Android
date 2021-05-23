package unilife.com.unilife.updated;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
import unilife.com.unilife.updated.model.requests.CreatePostRequest;

public class MediaActivity extends BaseActivity {

    private static final String VIDEO_DIRECTORY = "/demonuts";
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.img4)
    ImageView img4;
    Uri mImageUri;
    @BindView(R.id.edtCaption)
    EditText edtCaption;
    String strCaption = "";
    private int whichImage = 0;
    private byte[] imageBytes = null;
    private String strImage1 = "";
    private String strImage2 = "";
    private String strImage3 = "";
    private String strImage4 = "";
    private String strVideo1 = "";
    private String strVideo2 = "";
    private String strVideo3 = "";
    private String strVideo4 = "";
    private int GALLERY = 1, CAMERA = 2;

    private android.support.v7.app.AlertDialog basic_reg = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        init();
    }

    private void init() {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichImage = 1;
                openImageOrVideo();
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichImage = 1;
                openImageOrVideo();
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichImage = 2;
                openImageOrVideo();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichImage = 3;
                openImageOrVideo();
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichImage = 4;
                openImageOrVideo();
            }
        });
    }

    void openImageOrVideo() {
        android.support.v7.app.AlertDialog.Builder builder;
        builder = new android.support.v7.app.AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
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
                CropImage.activity().start(MediaActivity.this);
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

    @Override
    protected int getContentView() {
        return R.layout.activity_media;
    }

    public void closeView(View view) {
        finish();
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
                Log.e("response code -->", "" + response.body());

                setUploadedImage(response.body().getData());

            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
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
//        MultipartBody.Part image = null;
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        image = MultipartBody.Part.createFormData("thumbnail", "image.jpg", requestFile);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UploadImageResponse> call = apiInterface.uploadVideo(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERID), type, fileToUpload);

        showProgressDialog();
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.body());
                setUploadedVideo(response.body().getData());
                uploadVideoThumb(byteThumb);
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    void setUploadedImage(String content) {
        String url = POST_IMAGE_BASE_URL + content;

        if (whichImage == 1) {
            strImage1 = content;
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(img1);
        } else if (whichImage == 2) {
            strImage2 = content;
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(img2);
        } else if (whichImage == 3) {
            strImage3 = content;
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(img3);
        } else if (whichImage == 4) {
            strImage4 = content;
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(img4);
        }
    }

    void setUploadedVideo(String content) {
        String url = POST_IMAGE_BASE_URL + content;

        if (whichImage == 1) {
            strVideo1 = content;
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(img1);
        } else if (whichImage == 2) {
            strVideo2 = content;
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(img2);
        } else if (whichImage == 3) {
            strVideo3 = content;
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(img3);
        } else if (whichImage == 4) {
            strVideo4 = content;
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(img4);
        }
    }

    //=================== Video ===========================================

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(intent, CAMERA);
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

    public void postCall(View view) {
        strCaption = edtCaption.getText().toString().trim();

//        if (valid())
            addMediaPost();
    }

    boolean valid() {
        boolean isValid = true;
        if (strCaption.equals("")) {
            isValid = false;
            edtCaption.setHint("Enter your opinion");
            edtCaption.setHintTextColor(getResources().getColor(R.color.medium_red));
        }
        return isValid;
    }

    private String getImages() {
        String imgTotal = "";
        if (!strImage1.equals(""))
            imgTotal = strImage1 + ",";
        if (!strImage2.equals(""))
            imgTotal = strImage2 + ",";
        if (!strImage3.equals(""))
            imgTotal = strImage3 + ",";
        if (!strImage4.equals(""))
            imgTotal = strImage4 + ",";

        if (imgTotal.length() == 0)
            imgTotal = " ";

        return imgTotal.substring(0, imgTotal.length() - 1);
    }

    private CreatePostRequest.EventVideos getVideos() {
        CreatePostRequest.EventVideos eventVideos = new CreatePostRequest.EventVideos();
        if (!strVideo1.equals(""))
            eventVideos.setVideo_1(strVideo1);
        if (!strVideo2.equals(""))
            eventVideos.setVideo_2(strVideo2);
        if (!strVideo3.equals(""))
            eventVideos.setVideo_3(strVideo3);
        if (!strVideo4.equals(""))
            eventVideos.setVideo_4(strVideo4);

        return eventVideos;
    }


    public void addMediaPost() {
        if (!isNetworkConnected())
            return;

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setCaption(strCaption);
        createPostRequest.setEventImages(getImages());
        createPostRequest.setEventVideos(getVideos());

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.createPost(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), createPostRequest);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                String content = response.body().getData();
                String url = POST_IMAGE_BASE_URL + content;

                if (whichImage == 1) {
                    strVideo1 = strVideo1 + "," + content;
                    Glide.with(mContext)
                            .load(url)
                            .apply(options)
                            .into(img1);
                } else if (whichImage == 2) {
                    strVideo2 = strVideo2 + "," + content;
                    Glide.with(mContext)
                            .load(url)
                            .apply(options)
                            .into(img2);
                } else if (whichImage == 3) {
                    strVideo3 = strVideo3 + "," + content;
                    Glide.with(mContext)
                            .load(url)
                            .apply(options)
                            .into(img3);
                } else if (whichImage == 4) {
                    strVideo4 = strVideo4 + "," + content;
                    Glide.with(mContext)
                            .load(url)
                            .apply(options)
                            .into(img4);
                }
                Log.e("response code -->", "" + response.body());

            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }
}
