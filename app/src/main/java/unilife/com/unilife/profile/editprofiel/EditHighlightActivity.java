package unilife.com.unilife.profile.editprofiel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.model.UserHighlight;
import unilife.com.unilife.profile.model.requests.HighlightsRequest;
import unilife.com.unilife.profile.model.responses.HighlightResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditHighlightActivity extends BaseActivity {

    String strCurrentWorking;
    String strCurrentStuding;
    String strGraduated;
    String strSchooling;
    String strLivesin;
    String strFrom;

    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.edit2)
    EditText edit2;
    @BindView(R.id.edit3)
    EditText edit3;
    @BindView(R.id.edit4)
    EditText edit4;
    @BindView(R.id.edit5)
    EditText edit5;
    @BindView(R.id.edit6)
    EditText edit6;

    UserHighlight userHighlight;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        if (getIntent().getParcelableExtra("item") != null) {
            userHighlight = getIntent().getParcelableExtra("item");
            edit1.setText(userHighlight.getCurrentlyWorking());
            edit2.setText(userHighlight.getCurrentlyStudying());
            edit3.setText(userHighlight.getGraduatedFrom());
            edit4.setText(userHighlight.getCompleteHighschoolAt());
            edit5.setText(userHighlight.getLivesIn());
            edit6.setText(userHighlight.getFrom());

            edit1.setSelection(userHighlight.getCurrentlyWorking().length());
            edit1.requestFocus();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_highlight;
    }

    public void callService(View view) {
        strCurrentWorking = edit1.getText().toString().trim();
        strCurrentStuding = edit2.getText().toString().trim();
        strGraduated = edit3.getText().toString().trim();
        strSchooling = edit4.getText().toString().trim();
        strLivesin = edit5.getText().toString().trim();
        strFrom = edit6.getText().toString().trim();

        doCall();
    }


    public void doCall() {
        if (!isNetworkConnected())
            return;

        HighlightsRequest highlightsRequest = new HighlightsRequest();
        highlightsRequest.setCurrently_working(strCurrentWorking);
        highlightsRequest.setCurrently_studying(strCurrentStuding);
        highlightsRequest.setGraduated_from(strGraduated);
        highlightsRequest.setComplete_highschool_at(strSchooling);
        highlightsRequest.setLives_in(strLivesin);
        highlightsRequest.setFrom(strFrom);
        highlightsRequest.setPersonal_information("-");

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<HighlightResponse> call = apiInterface.user_highlights(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), highlightsRequest);

        call.enqueue(new Callback<HighlightResponse>() {
            @Override
            public void onResponse(Call<HighlightResponse> call, Response<HighlightResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    showToast(response.body().getMessage());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<HighlightResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}
