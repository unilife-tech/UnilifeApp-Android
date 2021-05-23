//package unilife.com.unilife.datahelper;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.ng.compmanagement.module.ItemCompany;
//import com.ng.compmanagement.module.ItemEntry;
//
//import java.util.ArrayList;
//
//public class DataHelper {
//
//    final String TAG = this.getClass().toString();
//    SQLitHelper sqLitHelper;
//    SQLiteDatabase sqLiteDatabase;
//    Context context;
//
//    public DataHelper(Context context) {
//        sqLitHelper = new SQLitHelper(context);
//        this.context = context;
//    }
//
//    //==================================  Pump Database Operations =======================
//
//    public void insertCOMP(ItemCompany itemCompany) {
//        try {
//            open();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("USER_ID", itemCompany.getUserId());
//            contentValues.put("COMP_NAME", itemCompany.getCompName());
//            contentValues.put("COMP_AREA", itemCompany.getCompArea());
//            contentValues.put("COMP_CONTACT", itemCompany.getCompContact());
//            contentValues.put("COMP_MANAGER", itemCompany.getCompManager());
//
//            sqLiteDatabase.insert(SQLitHelper.TbLCOMPANY, null, contentValues);
//            Log.e(TAG, "insertCOMP : Sus");
//
//        } catch (Exception e) {
//            Log.e(TAG, "insertCOMP : " + e.toString());
//        } finally {
//            close();
//        }
//    }
//
//
//    public void insertEntry(ItemEntry itemEntry) {
//        try {
//            open();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("COMP_ID", itemEntry.getCompId());
//            contentValues.put("COMP_NAME", itemEntry.getCompName());
//            contentValues.put("DATE", itemEntry.getDate());
//            contentValues.put("AMOUNT", itemEntry.getTotal());
//            contentValues.put("PAID", itemEntry.getPaid());
//            contentValues.put("REMAIN", itemEntry.getRemain());
//            contentValues.put("NOTE", itemEntry.getNote());
//
//            sqLiteDatabase.insert(SQLitHelper.TbLEntry, null, contentValues);
//            Log.e(TAG, "insertEntry : Sus");
//
//        } catch (Exception e) {
//            Log.e(TAG, "insertEntry : " + e.toString());
//        } finally {
//            close();
//        }
//    }
//
//    public void updateEntry(ItemEntry itemEntry) {
//        try {
//            open();
//            ContentValues contentValues = new ContentValues();
////            contentValues.put("COMP_ID", itemEntry.getCompId());
////            contentValues.put("COMP_NAME", itemEntry.getCompName());
//            contentValues.put("DATE", itemEntry.getDate());
//            contentValues.put("AMOUNT", itemEntry.getTotal());
//            contentValues.put("PAID", itemEntry.getPaid());
//            contentValues.put("REMAIN", itemEntry.getRemain());
//            contentValues.put("NOTE", itemEntry.getNote());
//
//            sqLiteDatabase.update(SQLitHelper.TbLEntry, contentValues, "id=" + itemEntry.getId(), null);
//            Log.e(TAG, "updateEntry : Sus");
//
//        } catch (Exception e) {
//            Log.e(TAG, "updateEntry : " + e.toString());
//        } finally {
//            close();
//        }
//    }
//
//    public void updateToPaidEntry(String total, String id) {
//        try {
//            open();
//            ContentValues contentValues = new ContentValues();
////            contentValues.put("COMP_ID", itemEntry.getCompId());
////            contentValues.put("COMP_NAME", itemEntry.getCompName());
////            contentValues.put("DATE", itemEntry.getDate());
////            contentValues.put("AMOUNT", itemEntry.getTotal());
//            contentValues.put("PAID", total);
//            contentValues.put("REMAIN", 0);
////            contentValues.put("NOTE", itemEntry.getNote());
//
//            sqLiteDatabase.update(SQLitHelper.TbLEntry, contentValues, "id=" + id, null);
//            Log.e(TAG, "updateEntry : Sus");
//
//        } catch (Exception e) {
//            Log.e(TAG, "updateEntry : " + e.toString());
//        } finally {
//            close();
//        }
//    }
//
//
////    public boolean updatePumpRegistration(int farm, String QrCode, String Mobile, String Farm_name, String Lps, String Phase) {
////        try {
////            open();
////
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("QrCode", QrCode);
////            contentValues.put("Mobile", Mobile);
////            contentValues.put("FarmName", Farm_name);
////            contentValues.put("PumpStatus","0");
////            contentValues.put("Lps", Lps);
////            contentValues.put("Phase", Phase);
////            contentValues.put("Fault", "0");
////            sqLiteDatabase.update(SQLitHelper.TblPump, contentValues, "id=" + farm, null);
////
////            Log.e(TAG, "updatePumpRegistration : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "updatePumpRegistration : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
//
//
//    public ArrayList<ItemCompany> getCompList() {
//        ArrayList<ItemCompany> arrayList = new ArrayList<ItemCompany>();
//        Cursor cursor = null;
//        try {
//            open();
//            cursor = sqLiteDatabase.rawQuery("Select * from " + SQLitHelper.TbLCOMPANY, null);
//            if (cursor.getColumnCount() > 0) {
//                while (cursor.moveToNext()) {
//                    ItemCompany itemCompany = new ItemCompany();
//                    itemCompany.setId(cursor.getInt(cursor.getColumnIndex("id")));
//                    itemCompany.setUserId(cursor.getString(cursor.getColumnIndex("USER_ID")));
//                    itemCompany.setCompName(cursor.getString(cursor.getColumnIndex("COMP_NAME")));
//                    itemCompany.setCompArea(cursor.getString(cursor.getColumnIndex("COMP_AREA")));
//                    itemCompany.setCompContact(cursor.getString(cursor.getColumnIndex("COMP_CONTACT")));
//                    itemCompany.setCompManager(cursor.getString(cursor.getColumnIndex("COMP_MANAGER")));
//
//                    arrayList.add(itemCompany);
//                }
//            }
//
//        } catch (Exception e) {
//            Log.e(TAG, "getCompList : " + e.toString());
//        } finally {
//            close();
//            if (cursor != null)
//                if (!cursor.isClosed())
//                    cursor.close();
//        }
//        return arrayList;
//    }
//
//
//    public ArrayList<ItemEntry> getEntryList(String compId) {
//        ArrayList<ItemEntry> arrayList = new ArrayList<ItemEntry>();
//        Cursor cursor = null;
//        try {
//            open();
//            String Query = "Select * from " + SQLitHelper.TbLEntry + " WHERE COMP_ID=?";
//            cursor = sqLiteDatabase.rawQuery(Query, new String[]{String.valueOf(compId)});
//            if (cursor.getColumnCount() > 0) {
//                while (cursor.moveToNext()) {
//                    ItemEntry itemEntry = new ItemEntry();
//                    itemEntry.setId(cursor.getInt(cursor.getColumnIndex("id")));
//                    itemEntry.setCompId(cursor.getInt(cursor.getColumnIndex("COMP_ID")));
//                    itemEntry.setCompName(cursor.getString(cursor.getColumnIndex("COMP_NAME")));
//                    itemEntry.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
//                    itemEntry.setTotal(cursor.getInt(cursor.getColumnIndex("AMOUNT")));
//                    itemEntry.setPaid(cursor.getInt(cursor.getColumnIndex("PAID")));
//                    itemEntry.setRemain(cursor.getInt(cursor.getColumnIndex("REMAIN")));
//                    itemEntry.setNote(cursor.getString(cursor.getColumnIndex("NOTE")));
//
//                    arrayList.add(itemEntry);
//                }
//            }
//
//        } catch (Exception e) {
//            Log.e(TAG, "getEntryList : " + e.toString());
//        } finally {
//            close();
//            if (cursor != null)
//                if (!cursor.isClosed())
//                    cursor.close();
//        }
//        return arrayList;
//    }
//
//
//    public ArrayList<ItemEntry> getEntryListDateWise(String date) {
//        ArrayList<ItemEntry> arrayList = new ArrayList<ItemEntry>();
//        Cursor cursor = null;
//        try {
//            open();
//            String Query = "Select * from " + SQLitHelper.TbLEntry + " WHERE DATE=?";
//            cursor = sqLiteDatabase.rawQuery(Query, new String[]{String.valueOf(date)});
//            if (cursor.getColumnCount() > 0) {
//                while (cursor.moveToNext()) {
//                    ItemEntry itemEntry = new ItemEntry();
//                    itemEntry.setId(cursor.getInt(cursor.getColumnIndex("id")));
//                    itemEntry.setCompId(cursor.getInt(cursor.getColumnIndex("COMP_ID")));
//                    itemEntry.setCompName(cursor.getString(cursor.getColumnIndex("COMP_NAME")));
//                    itemEntry.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
//                    itemEntry.setTotal(cursor.getInt(cursor.getColumnIndex("AMOUNT")));
//                    itemEntry.setPaid(cursor.getInt(cursor.getColumnIndex("PAID")));
//                    itemEntry.setRemain(cursor.getInt(cursor.getColumnIndex("REMAIN")));
//                    itemEntry.setNote(cursor.getString(cursor.getColumnIndex("NOTE")));
//
//                    arrayList.add(itemEntry);
//                }
//            }
//
//        } catch (Exception e) {
//            Log.e(TAG, "getEntryList : " + e.toString());
//        } finally {
//            close();
//            if (cursor != null)
//                if (!cursor.isClosed())
//                    cursor.close();
//        }
//        return arrayList;
//    }
//
//    public void deleteOneEntry(String id) {
//        try {
//            open();
//            sqLiteDatabase.delete(SQLitHelper.TbLEntry, "id" + "=?", new String[]{id});
//
//            // Drop older table if existed
//        } catch (Exception e) {
//            Log.e(TAG, "deleteOneEntry : " + e.toString());
//        } finally {
//            close();
//        }
//    }
//
////    public ArrayList<PumpList_Items> getPumpListWithId(int pumpid) {
////        ArrayList<PumpList_Items> arr_pumplist = new ArrayList<>();
////        Cursor cursor = null;
////        try {
////            open();
////            String Query = "Select * from " + SQLitHelper.TblPump + " WHERE id=?";
////            cursor = sqLiteDatabase.rawQuery(Query, new String[]{String.valueOf(pumpid)});
////            if (cursor.getColumnCount() > 0) {
////                while (cursor.moveToNext()) {
////                    PumpList_Items pumpList_items = new PumpList_Items();
////                    pumpList_items.PumpId = cursor.getInt(cursor.getColumnIndex("id"));
////                    pumpList_items.QrCode = cursor.getString(cursor.getColumnIndex("QrCode"));
////                    pumpList_items.Mobile = cursor.getString(cursor.getColumnIndex("Mobile"));
////                    pumpList_items.PumpHP = cursor.getString(cursor.getColumnIndex("PumpHP"));
////                    pumpList_items.ImagePath = cursor.getString(cursor.getColumnIndex("ImagePath"));
////                    pumpList_items.IsVerified = cursor.getString(cursor.getColumnIndex("IsVerified"));
////                    pumpList_items.Fault = cursor.getString(cursor.getColumnIndex("Fault"));
////                    pumpList_items.PumpStatus = cursor.getString(cursor.getColumnIndex("PumpStatus"));
////                    pumpList_items.SerialNo = cursor.getString(cursor.getColumnIndex("SerialNo"));
////                    pumpList_items.FarmName = cursor.getString(cursor.getColumnIndex("FarmName"));
////                    pumpList_items.Lps = cursor.getString(cursor.getColumnIndex("Lps"));
////                    pumpList_items.RegType = cursor.getString(cursor.getColumnIndex("RegType"));
////                    pumpList_items.Phase = cursor.getString(cursor.getColumnIndex("Phase"));
////
////                    arr_pumplist.add(pumpList_items);
////                }
////            }
////
////        } catch (Exception e) {
////            Log.e(TAG, "getPump : " + e.toString());
////        } finally {
////            close();
////            if (cursor != null)
////                if (!cursor.isClosed())
////                    cursor.close();
////        }
////        return arr_pumplist;
////    }
////
////
////    //====================================== Schedule Database =====================================
////
////    public boolean insertSchedule(int PumpId, String StartTime, String EndTime, String RepeatDays, String givenid, String Duration) {
////        try {
////            open();
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("PumpId", PumpId);
////            contentValues.put("StartTime", StartTime);
////            contentValues.put("EndTime", EndTime);
////            contentValues.put("RepeatDays", RepeatDays);
////            contentValues.put("GivenId", givenid);
////            contentValues.put("Duration", Duration);
////            contentValues.put("IsVerified", "No");
////
////            sqLiteDatabase.insert(SQLitHelper.TblSchedule, null, contentValues);
////
////            Log.e(TAG, "insertSchedule : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "insertSchedule : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
////
////        public boolean insertSubSchedule(int PumpId, String StartTime, String EndTime, String RepeatDays, String givenid, String Duration) {
////            try {
////                open();
////                ContentValues contentValues = new ContentValues();
////                contentValues.put("PumpId", PumpId);
////                contentValues.put("StartTime", StartTime);
////                contentValues.put("EndTime", EndTime);
////                contentValues.put("RepeatDays", RepeatDays);
////                contentValues.put("GivenId", givenid);
////                contentValues.put("Duration", Duration);
////
////                sqLiteDatabase.insert(SQLitHelper.TblSubSchedule, null, contentValues);
////
////                Log.e(TAG, "insertSchedule : Sus");
////                return true;
////            } catch (Exception e) {
////                Log.e(TAG, "insertSchedule : " + e.toString());
////            } finally {
////                close();
////            }
////            return false;
////        }
////
////    public boolean updateSchedule(String sch_id, String StartTime, String EndTime, String RepeatDays, String Duration) {
////        try {
////            open();
////
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("StartTime", StartTime);
////            contentValues.put("EndTime", EndTime);
////            contentValues.put("RepeatDays", RepeatDays);
////            contentValues.put("Duration", Duration);
////            sqLiteDatabase.update(SQLitHelper.TblSchedule, contentValues, "id=" + sch_id, null);
////
////            Log.e(TAG, "updateSchedule : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "updateSchedule : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
////
////
////
////    public ArrayList<Schedule_Items> getScheduleList(int pumpid) {
////        ArrayList<Schedule_Items> arr_schedule_list = new ArrayList<>();
////        Cursor cursor = null;
////        try {
////            open();
////            String QUERY = "SELECT * FROM pump a INNER JOIN schedule b ON a.id=b.PumpId WHERE b.PumpId=?";
////            cursor = sqLiteDatabase.rawQuery(QUERY, new String[]{String.valueOf(pumpid)});
////            if (cursor.getColumnCount() > 0) {
////                while (cursor.moveToNext()) {
////                    Schedule_Items schedule_items = new Schedule_Items();
////                    schedule_items.ScheduleId = cursor.getInt(cursor.getColumnIndex("id"));
////                    schedule_items.PumpId = cursor.getInt(cursor.getColumnIndex("PumpId"));
////                    schedule_items.StartTime = cursor.getString(cursor.getColumnIndex("StartTime"));
////                    schedule_items.EndTime = cursor.getString(cursor.getColumnIndex("EndTime"));
////                    schedule_items.RepeatDays = cursor.getString(cursor.getColumnIndex("RepeatDays"));
////                    schedule_items.GivenId = cursor.getString(cursor.getColumnIndex("GivenId"));
////                    schedule_items.FarmName = cursor.getString(cursor.getColumnIndex("FarmName"));
////                    schedule_items.Duration = cursor.getString(cursor.getColumnIndex("Duration"));
////                    schedule_items.IsVerified = cursor.getString(cursor.getColumnIndex("IsVerified"));
////
////                    arr_schedule_list.add(schedule_items);
////                }
////            }
////        } catch (Exception e) {
////            Log.e(TAG, "getScheduleList : " + e.toString());
////        } finally {
////            close();
////            if (cursor != null)
////                if (!cursor.isClosed())
////                    cursor.close();
////        }
////        return arr_schedule_list;
////    }
////
////
////    public ArrayList<Schedule_Items> getSubScheduleList(int pumpid) {
////        ArrayList<Schedule_Items> arr_schedule_list = new ArrayList<>();
////        Cursor cursor = null;
////        try {
////            open();
////            String QUERY = "SELECT * FROM pump a INNER JOIN subschedule b ON a.id=b.PumpId WHERE b.PumpId=?";
////            cursor = sqLiteDatabase.rawQuery(QUERY, new String[]{String.valueOf(pumpid)});
////            if (cursor.getColumnCount() > 0) {
////                while (cursor.moveToNext()) {
////                    Schedule_Items schedule_items = new Schedule_Items();
////                    schedule_items.ScheduleId = cursor.getInt(cursor.getColumnIndex("id"));
////                    schedule_items.PumpId = cursor.getInt(cursor.getColumnIndex("PumpId"));
////                    schedule_items.StartTime = cursor.getString(cursor.getColumnIndex("StartTime"));
////                    schedule_items.EndTime = cursor.getString(cursor.getColumnIndex("EndTime"));
////                    schedule_items.RepeatDays = cursor.getString(cursor.getColumnIndex("RepeatDays"));
////                    schedule_items.GivenId = cursor.getString(cursor.getColumnIndex("GivenId"));
////                    schedule_items.FarmName = cursor.getString(cursor.getColumnIndex("FarmName"));
////                    schedule_items.Duration = cursor.getString(cursor.getColumnIndex("Duration"));
////
////                    arr_schedule_list.add(schedule_items);
////                }
////            }
////
////        } catch (Exception e) {
////            Log.e(TAG, "getScheduleList : " + e.toString());
////        } finally {
////            close();
////            if (cursor != null)
////                if (!cursor.isClosed())
////                    cursor.close();
////        }
////        return arr_schedule_list;
////    }
////
////
////    //=================================== Notification Database ================================================
////
////    public boolean insertNotification(String Message, String Time, String Mobile) {
////        try {
////            open();
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("Message", Message);
////            contentValues.put("Time", Time);
////            contentValues.put("Status", "No");
////            contentValues.put("Mobile", Mobile);
////
////            sqLiteDatabase.insert(SQLitHelper.TblNotification, null, contentValues);
////
////            Log.e(TAG, "insertNotification : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "insertNotification : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
////
////
////    public boolean updateNotification(String id) {
////        try {
////            open();
////
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("Status", "Yes");
////            sqLiteDatabase.update(SQLitHelper.TblNotification, contentValues, "id=" + id, null);
////
////            Log.e(TAG, "updateNotification : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "updateNotification : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
////
////
////    public ArrayList<NotificationItems> getNotificationList(String mobile) {
////        ArrayList<NotificationItems> arr_notification_list = new ArrayList<>();
////        Cursor cursor = null;
////        try {
////            open();
////            String QUERY = "SELECT * FROM " + SQLitHelper.TblNotification + " WHERE Mobile=?";
////            cursor = sqLiteDatabase.rawQuery(QUERY, new String[]{mobile});
////            if (cursor.getColumnCount() > 0) {
////                while (cursor.moveToNext()) {
////                    NotificationItems notificationItems = new NotificationItems();
////                    notificationItems.Id = cursor.getInt(cursor.getColumnIndex("id"));
////                    notificationItems.Message = cursor.getString(cursor.getColumnIndex("Message"));
////                    notificationItems.Time = cursor.getString(cursor.getColumnIndex("Time"));
////                    notificationItems.Status = cursor.getString(cursor.getColumnIndex("Status"));
////                    notificationItems.Mobile = cursor.getString(cursor.getColumnIndex("Mobile"));
////
////                    arr_notification_list.add(notificationItems);
////                }
////            }
////
////        } catch (Exception e) {
////            Log.e(TAG, "getNotificationList : " + e.toString());
////        } finally {
////            close();
////            if (cursor != null)
////                if (!cursor.isClosed())
////                    cursor.close();
////        }
////        return arr_notification_list;
////    }
////
////    //=================================== Other Database ================================================
////
////    public boolean updatePumpDetail(String mobile, String status) {
////        try {
////            open();
////
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("PumpStatus", status);
////            sqLiteDatabase.update(SQLitHelper.TblPump, contentValues, "Mobile=" + mobile, null);
////
////            Log.e(TAG, "updatePumpDetail : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "updatePumpDetail : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
////
////
////    public boolean updateVerified(String mobile, String IsVerified) {
////        try {
////            open();
////
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("IsVerified", IsVerified);
////            sqLiteDatabase.update(SQLitHelper.TblPump, contentValues, "Mobile=" + mobile, null);
////
////            Log.e(TAG, "updateVerified : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "updateVerified : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
////
////    public int getDatabaseId(String mobile) {
////        open();
////        Cursor cursor = null;
////        int empName = 0;
////        try {
////            cursor = sqLiteDatabase.rawQuery("SELECT id FROM pump WHERE Mobile=?", new String[]{mobile + ""});
////            if (cursor.getCount() > 0) {
////                cursor.moveToFirst();
////                empName = cursor.getInt(cursor.getColumnIndex("id"));
////            }
////
////            return empName;
////
////        } catch (Exception e) {
////            Log.e(TAG, "getPump : " + e.toString());
////        } finally {
////            close();
////            if (cursor != null)
////                if (!cursor.isClosed())
////                    cursor.close();
////        }
////
////        return empName;
////    }
////
////    public String getVerified(String mobile) {
////        open();
////        Cursor cursor = null;
////        String empName = "";
////        try {
////            cursor = sqLiteDatabase.rawQuery("SELECT IsVerified FROM pump WHERE Mobile=?", new String[]{mobile + ""});
////            if (cursor.getCount() > 0) {
////                cursor.moveToFirst();
////                empName = cursor.getString(cursor.getColumnIndex("IsVerified"));
////            }
////
////            return empName;
////
////        } catch (Exception e) {
////            Log.e(TAG, "getVerified : " + e.toString());
////        } finally {
////            close();
////            if (cursor != null)
////                if (!cursor.isClosed())
////                    cursor.close();
////        }
////
////        return empName;
////    }
////
////
////    public boolean updateFault(String mobile, String fault) {
////        try {
////            open();
////
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("Fault", fault);
////            sqLiteDatabase.update(SQLitHelper.TblPump, contentValues, "Mobile=" + mobile, null);
////
////            Log.e(TAG, "updateFault : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "updateFault : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
////
////    public boolean updateVerifiedSchedule(String pump_id, String given_id, String IsVerified) {
////        try {
////            open();
////
////            ContentValues contentValues = new ContentValues();
////            contentValues.put("IsVerified", IsVerified);
////            sqLiteDatabase.update(SQLitHelper.TblSchedule, contentValues, "PumpId=" + pump_id + " AND " + "GivenId=" + given_id, null);
////
////            Log.e(TAG, "updateVerifiedSchedule : Sus");
////            return true;
////        } catch (Exception e) {
////            Log.e(TAG, "updateVerifiedSchedule : " + e.toString());
////        } finally {
////            close();
////        }
////        return false;
////    }
////
////    //======================== Delete Table or Row database =================================================
////
////    public void deleteOnePump(String mobile) {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblPump, "Mobile" + "=" + mobile, null);
////            Log.e(TAG, "deleteOnePump : " +"Success");
////            // Drop older table if existed
////        } catch (Exception e) {
////            Log.e(TAG, "deleted : " + e.toString());
////        } finally {
////            close();
////        }
////    }
////
////    public void deletePumpTable() {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblPump, null, null);
////            // Drop older table if existed
////            Log.e(TAG, "deletePumpTable : " +"Success");
////        } catch (Exception e) {
////            Log.e(TAG, "deleted : " + e.toString());
////        } finally {
////            close();
////        }
////    }
////
////    public void deleteOneSchedule(String pumpid, String scheduleid) {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblSchedule, "PumpId" + "=? AND " + "GivenId" + "=?", new String[]{pumpid, scheduleid});
////            // Drop older table if existed
////        } catch (Exception e) {
////            Log.e(TAG, "deleteOneSchedule : " + e.toString());
////        } finally {
////            close();
////        }
////    }
////
////    public void deleteSubSchedule(String pumpid, String scheduleid) {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblSubSchedule, "PumpId" + "=? AND " + "GivenId" + "=?", new String[]{pumpid, scheduleid});
////            // Drop older table if existed
////        } catch (Exception e) {
////            Log.e(TAG, "deleteOneSchedule : " + e.toString());
////        } finally {
////            close();
////        }
////    }
////
////    public void deletePumpSchedules(String pumpid) {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblSchedule, "PumpId" + "=?", new String[]{pumpid});
////            sqLiteDatabase.delete(SQLitHelper.TblSubSchedule, "PumpId" + "=?", new String[]{pumpid});
////            // Drop older table if existed
////        } catch (Exception e) {
////            Log.e(TAG, "deleteOneSchedule : " + e.toString());
////        } finally {
////            close();
////        }
////    }
////
////    public void deleteScheduleTable() {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblSchedule, null, null);
////            sqLiteDatabase.delete(SQLitHelper.TblSubSchedule, null, null);
////            // Drop older table if existed
////        } catch (Exception e) {
////            Log.e(TAG, "deleted : " + e.toString());
////        } finally {
////            close();
////        }
////    }
////
////
////    public void deleteNotificationTable() {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblNotification, null, null);
////            // Drop older table if existed
////        } catch (Exception e) {
////            Log.e(TAG, "deleted : " + e.toString());
////        } finally {
////            close();
////        }
////    }
////
////    public void deleteOneNotification(String id, String mobile) {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblNotification, "id" + "=? AND " + "Mobile" + "=?", new String[]{id, mobile});
////
////            // Drop older table if existed
////        } catch (Exception e) {
////            Log.e(TAG, "deleteOneNotification : " + e.toString());
////        } finally {
////            close();
////        }
////    }
////
////    public void deleteOneNotificationMobile(String mobile) {
////        try {
////            open();
////            sqLiteDatabase.delete(SQLitHelper.TblNotification, "Mobile" + "=" + mobile, null);
////
////            // Drop older table if existed
////        } catch (Exception e) {
////            Log.e(TAG, "deleteOneNotification : " + e.toString());
////        } finally {
////            close();
////        }
////    }
//
//    //======================== Open and close database =========================================================
//
//    public void open() {
//        try {
//            sqLiteDatabase = sqLitHelper.getWritableDatabase();
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//    }
//
//    public void close() {
//        try {
//            sqLiteDatabase.close();
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//    }
//
//}
