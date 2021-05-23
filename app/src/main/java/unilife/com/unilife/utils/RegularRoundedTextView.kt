package unilife.com.unilife.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class RegularRoundedTextView : TextView {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    fun init() {
        //            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "AvenirNextLTPro-Bold.otf");
        //            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "AvenirNextLTPro-DemiCnIt.otf");
        val tf = Typeface.createFromAsset(context.assets, "Arcon-Rounded-Regular.otf")
        setTypeface(tf, 1)

    }
}
