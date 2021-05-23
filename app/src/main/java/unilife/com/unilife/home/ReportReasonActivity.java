package unilife.com.unilife.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class ReportReasonActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;

    public static String strReportReason="";
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        strReportReason="";
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("spam");
        arrayList.add("it's inappropriate");
        arrayList.add("violence");
        arrayList.add("nudity");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                strReportReason=arrayList.get(i);
                finish();
            }
        });
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_report_reson;
    }

    public void goBack(View view) {
        finish();
    }
}