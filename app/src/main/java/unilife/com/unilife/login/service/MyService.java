package unilife.com.unilife.login.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class MyService extends Service {

    private ArrayList<ItemContact> arrayList1 = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void doAsynks() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
//                showProgressDialog();
            }// End of onPreExecute method

            @Override
            protected Void doInBackground(Void... params) {
                getContactList();
                return null;
            }// End of doInBackground method

            @Override
            protected void onPostExecute(Void result) {
//                hideProgressDialog();


            }//End of onPostExecute method
        }.execute((Void[]) null);
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

                        arrayList1.add(new ItemContact(name,
                                phoneNo.replace(" ", "").replace("+91", "").replace("+971", "")));
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

    }

    public class ItemContact {
        String name;
        String contact;

        public ItemContact(String name, String contact) {
            this.name = name;
            this.contact = contact;
        }

        public String getName() {
            return name;
        }

        public String getContact() {
            return contact;
        }
    }
}