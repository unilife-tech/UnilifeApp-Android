package unilife.com.unilife.updated;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retrofit.WebUrls;
import unilife.com.unilife.updated.model.requests.PollRequest;

public class PollActivity extends BaseActivity {

    @BindView(R.id.edtQue)
    EditText edtQue;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtName)
    TextView txtName;

    //    @BindView(R.id.edtOption1)
//    EditText edtOption1;
//    @BindView(R.id.edtOption2)
//    EditText edtOption2;
    @BindView(R.id.line1)
    LinearLayout line1;

    String strQue = "";
    String strOption1 = "";
    String strOption2 = "";

    private int numEdiText = 0;
    private ArrayList<EditText> arrayList = new ArrayList<>();

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        txtName.setText(   PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERNAME));
        try {
            Glide
                    .with(mContext)
                    .load(WebUrls.INSTANCE.getPROFILE_IMAGE_URL() + PreferenceFile.getInstance().getPreferenceData(this, KEY_PROFILE_IMAGE))
                    .apply(options)
                    .into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addEditText();
        addEditText();

        findViewById(R.id.imgAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numEdiText < 10)
                    addEditText();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_poll;
    }

    public void closeView(View view) {
        finish();
    }

    public void addPoll() {
        if (!isNetworkConnected())
            return;

        PollRequest.Options options = new PollRequest.Options();
        options.setOption1(strOption1);
        options.setOption2(strOption2);

        PollRequest pollRequest = new PollRequest();
        pollRequest.setQuestion(strQue);
        pollRequest.setOptions(makeJson());

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.createPoll(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), pollRequest);

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

    public void callService(View view) {
        strQue = edtQue.getText().toString().trim();
//        strOption1 = edtOption1.getText().toString().trim();
//        strOption2 = edtOption2.getText().toString().trim();

        if (valid())
            addPoll();

        makeJson();
    }

    private PollRequest.Options makeJson() {
        PollRequest.Options options = new PollRequest.Options();

        for (int i = 0; i < arrayList.size(); i++) {
            if (i == 0 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption1(arrayList.get(i).getText().toString().trim());
            }
            if (i == 1 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption2(arrayList.get(i).getText().toString().trim());
            }
            if (i == 2 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption3(arrayList.get(i).getText().toString().trim());
            }
            if (i == 3 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption4(arrayList.get(i).getText().toString().trim());
            }
            if (i == 4 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption5(arrayList.get(i).getText().toString().trim());
            }
            if (i == 5 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption6(arrayList.get(i).getText().toString().trim());
            }
            if (i == 6 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption7(arrayList.get(i).getText().toString().trim());
            }
            if (i == 7 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption8(arrayList.get(i).getText().toString().trim());
            }
            if (i == 8 && !arrayList.get(i).getText().toString().trim().equals("")) {
                options.setOption9(arrayList.get(i).getText().toString().trim());
            }
        }


        Log.e("option", options.toString());

        return options;
    }

    boolean valid() {
        boolean isValid = true;
        if (strQue.equals("")) {
            isValid = false;
            edtQue.setHint("Enter question");
            edtQue.setHintTextColor(getResources().getColor(R.color.medium_red));
        }



        /*else if (strOption1.equals("")) {
            isValid = false;
            edtOption1.setHint("Enter option 1");
            edtOption2.setHintTextColor(getResources().getColor(R.color.medium_red));
        } else if (strOption2.equals("")) {
            isValid = false;
            edtOption1.setHint("Enter option 2");
            edtOption2.setHintTextColor(getResources().getColor(R.color.medium_red));
        }*/
        return isValid;
    }

    public void removeEditText(View view) {
        if (numEdiText > 2) {
            numEdiText = numEdiText - 1;
            arrayList.remove(numEdiText);
            line1.removeViewAt(numEdiText);
        }
    }

    public void addEditText() {
        numEdiText = numEdiText + 1;
        EditText et = new EditText(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.topMargin = 40;
        et.setPadding(32, 32, 32, 32);
        et.setLayoutParams(p);
        et.setHint("Option " + numEdiText);
        et.setId(numEdiText);
        et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et.setBackgroundResource(R.drawable.back_edittext);
        line1.addView(et);
        arrayList.add(et);
    }
}
