package com.mindvalley.test.views.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mindvalley.asyncdownloadlib.utils.DownloadProvider
import com.mindvalley.test.R
import com.mindvalley.test.adapters.ImageAdapter
import com.mindvalley.test.constants.AppConstants
import com.mindvalley.test.databinding.FragmentPinboardViewBinding
import com.mindvalley.test.enums.StatusEnum
import com.mindvalley.test.newModels.PinboardModel
import com.mindvalley.test.repositories.PinboardRepository
import com.mindvalley.test.viewmodels.PinboardFragmentViewModel
import com.mindvalley.test.views.activities.FullImageActivity
import kotlinx.android.synthetic.main.fragment_pinboard_view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Ankit Kumar Jain on 3/11/2019.
 */
class PinboardViewFragment : Fragment() {
    private var mProvider: DownloadProvider? = null
    private var mImageAdapter: ImageAdapter? = null
    private lateinit var fragmentPinboardViewBinding: FragmentPinboardViewBinding
    private lateinit var pinboardFragmentViewModel: PinboardFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentPinboardViewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pinboard_view, container, false)
        return fragmentPinboardViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProvider = DownloadProvider.getInstance()
        val imgUrlArray = ArrayList<String>()
        mImageAdapter = context?.let { ImageAdapter(it, imgUrlArray) }
        loadImages()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        pinboardFragmentViewModel = ViewModelProviders.of(this).get(PinboardFragmentViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_refresh) {
            Toast.makeText(context, getString(R.string.action_refresh), Toast.LENGTH_SHORT).show()
            loadImages()
            return true
        } else if (id == R.id.action_clear_cache) {
            mProvider?.clearCache()
            Toast.makeText(context, getString(R.string.action_clear_cache), Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadImages() {
        val imgUrlArray = ArrayList<String>()
        mImageAdapter?.setImgUrlArray(imgUrlArray)
        grid_view.adapter = mImageAdapter
        getJsonResponse()
    }

    private fun getJsonResponse() {
        pinboardFragmentViewModel.downloadJsonType()?.observe(this, object : Observer,
            androidx.lifecycle.Observer<PinboardRepository.Companion.DownloadTypeResponse> {
            override fun update(p0: Observable?, p1: Any?) {

            }

            override fun onChanged(downloadTypeResponse: PinboardRepository.Companion.DownloadTypeResponse?) {
                when {
                    downloadTypeResponse?.mStatus == StatusEnum.START -> progress_bar.visibility = View.VISIBLE
                    downloadTypeResponse?.mStatus == StatusEnum.FAILURE -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, getString(R.string.error_invalid_url), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        val imgUrlArray = ArrayList<String>()
                        val pinboardList = downloadTypeResponse?.mData as ArrayList<PinboardModel>
                        for (b in pinboardList) {
                            b.urls?.thumb?.let { imgUrlArray.add(it) }
                        }
                        mImageAdapter?.setImgUrlArray(imgUrlArray)
                        grid_view.adapter = mImageAdapter

                        grid_view.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                            val i = Intent(context, FullImageActivity::class.java)
                            i.putExtra(AppConstants.FULL_IMAGE_URL, pinboardList[position].urls?.regular)
                            startActivity(i)
                        }
                        progress_bar.visibility = View.GONE
                    }
                }
            }

        })
    }
}
