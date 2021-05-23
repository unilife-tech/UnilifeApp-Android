package wt.com.wt.view.ProductsFragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log



abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        Log.e("OnScrolled", "Executed")

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        Log.e("visibleItemCount", visibleItemCount.toString())
        Log.e("totalItemCount", totalItemCount.toString())


            if (!isLoading() && !isLastPage()) {

                Log.e("NoLoadNoLastPage", "Exec")

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    Log.e("GetMoreAbs", "Exec")
                    getMoreItems()
                }


                }



    }

    abstract fun getMoreItems()

}