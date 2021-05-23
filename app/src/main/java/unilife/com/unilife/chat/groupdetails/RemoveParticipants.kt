package unilife.com.unilife.chat.groupdetails

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.drawer_toolbar.*
import kotlinx.android.synthetic.main.remove_participants_layout.*
import org.json.JSONArray
import org.json.JSONObject
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class RemoveParticipants : AppDrawer(), RemoveParticipantsListAdapter.SelectMembers,
    RemoveSelectedParticipantsAdapter.ClickSelected, View.OnClickListener, RetrofitResponse {
    private var removeParticipantsListAdapter: RemoveParticipantsListAdapter? = null
    private var removeSelectedParticipantsAdapter: RemoveSelectedParticipantsAdapter? = null
    private var groupId: String = ""
    private var membersList: ArrayList<GroupDetail.GroupUserModel>? = null
    private var selectedMembersList = ArrayList<GroupDetail.GroupUserModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remove_participants_layout)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        ivBack.visibility = View.VISIBLE
        ivProfileImg.visibility = View.GONE
        ivNoti.visibility = View.GONE
        llToolBar.background = resources.getDrawable(R.color.colorPrimary)
        tvMainTitle.text = getString(R.string.Remove_Participant)
        selectedMembersList!!.clear()
        getIntentData()
        setClickCallBacks()
        setParticipantsListAdapter()
        setRemoveParticipantsListAdapter()
    }

    private fun getIntentData() {
        try {
            if (intent.hasExtra("groupId")) {

                groupId = intent.getStringExtra("groupId")
            }
            if (intent.hasExtra("groupMemberList")) {
                membersList =
                    intent!!.getSerializableExtra("groupMemberList") as ArrayList<GroupDetail.GroupUserModel>
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llRemoveMembers -> {
                if (selectedMembersList.size == 0) {
                    Alerts.alertDialog(this, "Please select atleast one group member to remove")
                } else {
                    callRemoveParticipantsApi()
                }
            }
            R.id.ivBack -> {
                super.onBackPressed()
            }
        }
    }

    private fun setClickCallBacks() {
        try {
            llRemoveMembers.setOnClickListener(this)
            ivBack.setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setParticipantsListAdapter() {
        try {
            removeParticipantsListAdapter = RemoveParticipantsListAdapter(this, membersList)
            rvParticipantsList.adapter = removeParticipantsListAdapter
            removeParticipantsListAdapter!!.setOnItemClickListener(this)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setRemoveParticipantsListAdapter() {
        try {
            removeSelectedParticipantsAdapter =
                RemoveSelectedParticipantsAdapter(this, selectedMembersList!!)
            rvSelectedParticipants.adapter = removeSelectedParticipantsAdapter
            removeSelectedParticipantsAdapter!!.initClickSelected(this)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPerformAction(position: Int) {
        try {
            if (membersList!![position].check_req_status == "0") {
                membersList!![position].check_req_status = "1"
                selectedMembersList!!.add(membersList!![position])
            } else if (membersList!![position].check_req_status == "1") {
                membersList!![position].check_req_status = "0"
                if (selectedMembersList!!.contains(membersList!![position])) {
                    var indexIs = selectedMembersList!!.indexOf(membersList!![position])
                    selectedMembersList!!.removeAt(indexIs)
                }
            }
            removeSelectedParticipantsAdapter!!.notifyDataSetChanged()
            removeParticipantsListAdapter!!.notifyItemChanged(position)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun removeSelected(position: Int) {
        try {

            if (membersList!!.contains(selectedMembersList[position])) {
                var indexIs = membersList!!.indexOf(selectedMembersList[position])
                membersList!![indexIs].check_req_status = "0"
                removeParticipantsListAdapter!!.notifyDataSetChanged()
            }
            selectedMembersList!!.removeAt(position)
            removeSelectedParticipantsAdapter!!.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callRemoveParticipantsApi() {
        try {
            var jsonObject = JSONObject()
            jsonObject.put("group_id", groupId)
            var jsonArray = JSONArray()
            for (i in 0 until selectedMembersList.size) {
                jsonArray.put(selectedMembersList[i].user_id)
            }
            jsonObject.put("user_id", jsonArray)

            RetrofitService(
                this,
                this,
                WebUrls.REMOVE_PARTCIPANT,
                WebUrls.REMOVE_PARTCIPANT_CODE,
                3,
                jsonObject
            ).callService(true)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        when (requestCode) {
            WebUrls.REMOVE_PARTCIPANT_CODE -> {
                try {
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        super.onBackPressed()
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_LONG)
                            .show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
