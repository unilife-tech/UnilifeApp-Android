package unilife.com.unilife.brands

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.saved_brands_coupans.*
import unilife.com.unilife.R

class SavedBrandsCoupans : AppCompatActivity() {

    //    @BindView(R.id.rycCoupans)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saved_brands_coupans)

        setAdapter()
    }


    private fun setAdapter() {


        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rycCoupans?.layoutManager = staggeredGridLayoutManager
        val savedBrandsCoupans = SavedCoupansAdapter()
        rycCoupans?.adapter = savedBrandsCoupans

    }
}
