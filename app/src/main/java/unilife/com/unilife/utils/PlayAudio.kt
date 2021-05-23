package unilife.com.unilife.utils

import android.media.MediaPlayer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import java.util.*

class PlayAudio {

    companion object {
        var mediaPlayer: MediaPlayer? = null

        fun setMediaPlayer(
            seekBarProgress: SeekBar,
            playImage: ImageView,
            stopImage: ImageView,
            source: String,
            textView: TextView,
//            arrayList: ArrayList<ChatDatum>,
            position: Int
        ) {


            mediaPlayer = MediaPlayer()

            mediaPlayer!!.setOnBufferingUpdateListener { mp, percent ->
                seekBarProgress.secondaryProgress = percent
            }


            mediaPlayer!!.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: MediaPlayer?) {
                    var d = mp!!.duration
//                    Log.e("DuratrionDD", d.toString())
                }
            })

            mediaPlayer!!.setOnCompletionListener {
                seekBarProgress.progress = 0
                playImage.visibility = View.VISIBLE
                stopImage.visibility = View.GONE
            }


            mediaPlayer!!.setOnPreparedListener { mp ->
                var output = Common.getTimeFromMillis(mp!!.duration.toLong())
//                arrayList.get(position).audioDuration = output
                textView.text = output
            }

            try {
                mediaPlayer!!.setDataSource(source) // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                mediaPlayer!!.prepareAsync() // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun primarySeekBarProgressUpdater(seekBarProgress: SeekBar) {
            if (mediaPlayer != null) {
//                Log.e("MediaPlayerosition", (mediaPlayer!!.currentPosition.toString()))

//                Log.e("MediaFileLength", mediaFileLengthInMilliseconds.toString())
                var cp = mediaPlayer!!.currentPosition

//                Log.e("CurrentPOs", cp.toString())

                var total = mediaPlayer!!.duration
                if (mediaPlayer!!.isPlaying && cp < total) {

                    //seekBarProgress.progress = cp

                    if (total > 0) {
                        var progress =
                            ((mediaPlayer!!.currentPosition) * 100) / total
                        if (progress != 100) {
                            seekBarProgress.progress = progress
                        }
                    }
                }

//                Log.e("PrimarytProgress", seekBarProgress.progress.toString())

                if (mediaPlayer!!.isPlaying) {
                    val notification = Runnable { primarySeekBarProgressUpdater(seekBarProgress) }
                    Handler().postDelayed(notification, 1000)
                }
            }
        }

        fun playSound(
            source: String,
            playSoundImage: ImageView,
            stopImage: ImageView,
            seekBar: SeekBar
        ) {

//            Log.e("AudioDuration",mediaPlayer!!.duration.toString())

            if (!mediaPlayer!!.isPlaying) {

                playSoundImage.visibility = View.INVISIBLE
                stopImage.visibility = View.VISIBLE
                mediaPlayer!!.start()
            } else {
                mediaPlayer!!.pause()
                playSoundImage.visibility = View.VISIBLE
                stopImage.visibility = View.GONE

            }

            primarySeekBarProgressUpdater(seekBar)
        }

    }
}
