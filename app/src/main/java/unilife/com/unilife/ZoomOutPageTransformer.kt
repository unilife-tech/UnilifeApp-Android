
import android.support.v4.view.ViewCompat.setTranslationX
import android.support.v4.view.ViewCompat.setScaleY
import android.support.v4.view.ViewCompat.setScaleX
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.support.v4.view.ViewCompat.setScaleX
import android.support.v4.view.ViewCompat.setScaleY
import android.support.v4.view.ViewCompat.setTranslationX
import android.support.v4.view.ViewPager
import android.view.View

class ZoomOutPageTransformer(isZoomEnable: Boolean) : ViewPager.PageTransformer {


    init {
        if (isZoomEnable) {
            MIN_SCALE = 0.85f
        } else {
            MIN_SCALE = 1f
        }
    }

   override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        var vertMargin = pageHeight * (1 - MIN_SCALE) / 2
        var horzMargin = pageWidth * (1 - MIN_SCALE) / 2
       view.scaleX = MIN_SCALE
       view.scaleY = MIN_SCALE
       when {
           position < -1 -> { // [-Infinity,-1)
               // This page is way off-screen to the left.
               view.alpha = MIN_ALPHA
               view.translationX = horzMargin - vertMargin / 2


           }
           position <= 1 -> { // [-1,1]
               // Modify the default slide transition to shrink the page as well
               val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
               vertMargin = pageHeight * (1 - scaleFactor) / 2
               horzMargin = pageWidth * (1 - scaleFactor) / 2
               if (position < 0) {
                   view.translationX = horzMargin - vertMargin / 2
               } else {
                   view.translationX = -horzMargin + vertMargin / 2
               }

               // Scale the page down (between MIN_SCALE and 1)
               view.setScaleX(scaleFactor)
               view.setScaleY(scaleFactor)

               // Fade the page relative to its size.
               view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)

           }
           else -> { // (1,+Infinity]
               // This page is way off-screen to the right.
               view.setAlpha(MIN_ALPHA)
               view.translationX = -horzMargin + vertMargin / 2

           }
       }
    }

    companion object {
        private var MIN_SCALE = 1f
        private val MIN_ALPHA = 0.7f
    }
}