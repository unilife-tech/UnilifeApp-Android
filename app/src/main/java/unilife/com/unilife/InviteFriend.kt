package unilife.com.unilife

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_invite_friend.*

class InviteFriend : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friend)

        btnInvite.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Unilife")
            var shareMessage =
                "Hey, let’s connect on Unilife, Unilife allows you to communicate easily in Uni and gives you access to students discounts and contents.\n\n"
            shareMessage =
                "$shareMessage For Android: https://play.google.com/store/apps/details?id=unilife.com.unilife\n" +
                        "For iPhone: https://apps.apple.com/us/app/id1491474131"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        }
    }
}
