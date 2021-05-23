package unilife.com.unilife.login;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.R;
import unilife.com.unilife.home.Home;
import unilife.com.unilife.login.adapter.ContactAdapter;
import unilife.com.unilife.login.model.FriendRequest;
import unilife.com.unilife.login.response.UserPhonesResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class AddFromContactActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    ContactAdapter contactAdapter;
    private String TAG = "Contact";

    private ArrayList<UserPhonesResponse.Datum> arrUsersPhone = new ArrayList<UserPhonesResponse.Datum>();
    private ArrayList<AddFromContactActivity.ItemContact> arrayList1 = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_add_from_contact;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        hideKeyboard();

        getUsersList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doAsynks();
                    }
                }, 1000);
            }
        }

        contactAdapter = new ContactAdapter(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(contactAdapter);

        edtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<AddFromContactActivity.ItemContact> arrTemp = new ArrayList<>();
                if (editable.toString().length() > 0) {
                    for (int i = 0; i < arrayList1.size(); i++) {
                        if (arrayList1.get(i).getName().toLowerCase().contains(editable.toString().trim().toLowerCase())) {
                            arrTemp.add(arrayList1.get(i));
                        }

                        contactAdapter.updateData(arrTemp);
                    }
                } else {
                    contactAdapter.updateData(arrayList1);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doAsynks();
                } else {
                    Toast.makeText(this, "Permission denied to read your Contacts", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        arrayList1.add(new ItemContact(name, phoneNo.replace(" ", "").replace("+91", "").replace("+971", ""), false));
                    }

                    pCur.close();
                }
            }
        }

        if (cur != null) {
            cur.close();
        }

//        ArrayList<ItemContact> allEvents = // fill with your events.
        ArrayList<ItemContact> noRepeat = new ArrayList<ItemContact>();

        for (ItemContact event : arrayList1) {
            boolean isFound = false;
            // check if the event name exists in noRepeat
            for (ItemContact e : noRepeat) {
                if (e.getName().equals(event.getName()) || (e.equals(event))) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) noRepeat.add(event);
        }

        arrayList1.clear();
        arrayList1.addAll(noRepeat);
    }

    public void goBack(View view) {
        finish();
    }

    public void getUsersList() {
        if (!isNetworkConnected())
            return;

//        UsersPhoneRequest usersPhoneRequest = new UsersPhoneRequest();
//        usersPhoneRequest.setUniversity_id(sharePref.getUniversityId());
//        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserPhonesResponse> call = apiInterface.phone_number_get_univ_wise(sharePref.getUserId());

        call.enqueue(new Callback<UserPhonesResponse>() {
            @Override

            public void onResponse(Call<UserPhonesResponse> call, Response<UserPhonesResponse> response) {
//                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    arrUsersPhone.addAll(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<UserPhonesResponse> call, Throwable t) {
//                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    private int getItemPos(String nm) {
        int pos = 0;
        for (int i = 0; i < arrUsersPhone.size(); i++) {
            if (arrUsersPhone.get(i).getPhone().contains(nm)) {
                pos = i;
            }
        }
        return pos;
    }

//    private int getItemPos(String key ,  String item) {
//        int i = 0;
//        for (i=0; i<mArrayList.size(); i++)
//        {
//            if(mArrayList.get(i).get(key).equalsIgnoreCase(item))
//            {
//                return i;
//            }
//        }
//        return -1;
//    }

    private void doAsynks() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                showProgressDialog();
            }// End of onPreExecute method

            @Override
            protected Void doInBackground(Void... params) {
                getContactList();
                return null;
            }// End of doInBackground method

            @Override
            protected void onPostExecute(Void result) {
                hideProgressDialog();

                ArrayList<String> usersContact = new ArrayList<>();
                ArrayList<String> usersContactStatus = new ArrayList<>();
                for (int i = 0; i < arrUsersPhone.size(); i++) {
                    usersContact.add(arrUsersPhone.get(i).getPhone().replace(" ", "").replace("+91", "").replace("+971", ""));
                    usersContactStatus.add(arrUsersPhone.get(i).getPhone());
                }

                //LinkedHashSet preserves the order of the original list
                Set<ItemContact> unique = new LinkedHashSet<ItemContact>(arrayList1);
                arrayList1 = new ArrayList<ItemContact>(unique);

                Collections.sort(arrayList1, new CustomComparator());

                for (int i = 0; i < arrayList1.size(); i++) {

//                    Log.e("names",arrayList1.get(i));
//                    Log.e("contact",arrayList1.get(i));

                    if (usersContact.contains(arrayList1.get(i).getContact())) {
//                        Log.e("Checking contact", "Match found");

                        int ind = getItemPos(arrayList1.get(i).getContact());

                        if (arrUsersPhone.get(ind).getSchool().equalsIgnoreCase("same")) {
                            arrayList1.get(i).setAction("Add Friend");
                        } else {
                            arrayList1.get(i).setAction("Not in your School");
                        }

                        arrayList1.get(i).setInContact(true);
                        arrayList1.get(i).setUserId(arrUsersPhone.get(ind).getId());
                    }


                }
                contactAdapter.updateData(arrayList1);

            }//End of onPostExecute method
        }.execute((Void[]) null);
    }

    public void sendRequest(String status, String id) {
        if (!isNetworkConnected())
            return;

        FriendRequest friendRequest = new FriendRequest();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<CommonResponse> call;
        if (status.equals("send")) {
            friendRequest.setUser_id(sharePref.getUserId());
            friendRequest.setRequest_id(id);
            call = apiInterface.friendRequest(sharePref.getUserId(), friendRequest);
        } else {
            friendRequest.setReject_id(sharePref.getUserId());
            friendRequest.setUser_id(id);
            call = apiInterface.cancelRequest(sharePref.getUserId(), friendRequest);
        }
        showProgressDialog();
        call.enqueue(new Callback<CommonResponse>() {
            @Override

            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void callNext(View view) {
        intent = new Intent(mContext, Home.class);
        startActivity(intent);
    }

    public class CustomComparator implements Comparator<ItemContact> {
        @Override
        public int compare(ItemContact o1, ItemContact o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public class ItemContact {
        String name;
        String contact;
        String userId;
        String action;
        String school;
        boolean isInContact = false;
        boolean Select = false;

        public ItemContact(String name, String contact, boolean isInContact) {
            this.name = name;
            this.contact = contact;
            this.isInContact = isInContact;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isInContact() {
            return isInContact;
        }

        public void setInContact(boolean inContact) {
            isInContact = inContact;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public boolean isSelect() {
            return Select;
        }

        public void setSelect(boolean select) {
            Select = select;
        }
    }

    public class UsersPhoneRequest {
        String university_id;

        public void setUniversity_id(String university_id) {
            this.university_id = university_id;
        }
    }
}