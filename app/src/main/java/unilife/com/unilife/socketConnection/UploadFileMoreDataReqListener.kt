package bubblebud.com.bubblebud.view.chat

import org.json.JSONException

interface UploadFileMoreDataReqListener {

    abstract fun uploadChunck(place: Int, percent: Int)
    abstract fun err(e: JSONException)
}