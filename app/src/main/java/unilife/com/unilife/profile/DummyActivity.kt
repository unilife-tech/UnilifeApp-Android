package unilife.com.unilife.profile

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import unilife.com.unilife.R
import android.util.Log
import android.widget.EditText
import android.text.Spannable
import android.text.style.ImageSpan
import android.text.SpannableString





class DummyActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy)

        val textView = findViewById<EditText>(R.id.text)
        var string = textView.text.toString()

        var substring: ArrayList<String> = string.split("\n") as ArrayList<String>

        for (i in 0 until substring.size) {
            var lengthSub = substring[i].length
            Log.e("ddfsfefef",""+lengthSub)
            val span = SpannableString(substring.toString())
            val android = resources.getDrawable(R.drawable.answer_icon_eight)
            android.setBounds(0, 0, 32, 32)
            val image = ImageSpan(android, ImageSpan.ALIGN_BASELINE)
            span.setSpan(image, lengthSub ,lengthSub+1 , Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            textView.setText(span)
        }
    }


    }

