package com.mindvalley.test.views.fragments


import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.gson.reflect.TypeToken
import com.mindvalley.asyncdownloadlib.interfaces.DataDownloadCallbacks
import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource
import com.mindvalley.asyncdownloadlib.models.DataTypeEnum
import com.mindvalley.asyncdownloadlib.models.DownloadTypeImage
import com.mindvalley.asyncdownloadlib.models.DownloadTypeJson
import com.mindvalley.asyncdownloadlib.utils.DownloadProvider
import com.mindvalley.test.R
import com.mindvalley.test.databinding.TestingFragmentBinding
import com.mindvalley.test.newModels.PinboardModel
import kotlinx.android.synthetic.main.testing_fragment.*
import java.util.*

/**
 * Created by Ankit Kumar Jain on 11/07/2019
 */
class TestingFragment : Fragment() {
    private var mProvider: DownloadProvider? = null
    private var lineIndex = 0
    private var counterForHundler = 0
    private val showSpeed = 20
    private var countImageComeFromNet = 0
    private var countImageComeFromCache = 0
    private var countJsonComeFromNet = 0
    private var countJsonComeFromCache = 0
    private var countCanceled = 0
    private var countFailure = 0
    private val mHandler = Handler()
    private lateinit var testingFragmentBinding: TestingFragmentBinding
    private val urlsImageArray = arrayOf(
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702",
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64&s=ef631d113179b3137f911a05fea56d23",
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128&s=622a88097cf6661f84cd8942d851d9a2",
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64&s=ef631d113179b3137f911a05fea56d23",
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64&s=ef631d113179b3137f911a05fea56d23",
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702"
    )
    private val urlsJsonArray = arrayOf("https://pastebin.com/raw/wgkJgazE")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        testingFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.testing_fragment, container, false)
        return testingFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProvider = DownloadProvider.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


        if (id == R.id.action_refresh) {
            Toast.makeText(context, "Refresh", Toast.LENGTH_SHORT).show()
            txtResult.setText("")
            lineIndex = 0
            counterForHundler = 0
            countImageComeFromCache = 0
            countImageComeFromNet = 0
            countJsonComeFromNet = 0
            countJsonComeFromCache = 0
            countCanceled = 0
            countFailure = 0
            mHandler.postDelayed({ runTest() }, 1000)

            return true
        } else if (id == R.id.action_clear_cache) {
            mProvider?.clearCache()
            Toast.makeText(context, "Cache Cleared", Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun runTest() {
        var i = 0
        // Get Images
        for (str in urlsImageArray) {
            val mDataTypeImage = DownloadTypeImage(str, InterfaceForDataType("Image-" + i++))
            mProvider?.getRequest(mDataTypeImage)
        }
        val mDataTypeImageCancel = DownloadTypeImage(
            "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64&s=ef631d113179b3137f911a05fea56d23",
            InterfaceForDataType("Image-Cancel")
        )
        mProvider?.getRequest(mDataTypeImageCancel)
        mProvider?.cancelRequest(mDataTypeImageCancel)
        i = 0
        // Get JSON
        for (str in urlsJsonArray) {
            val mDataTypeJson = DownloadTypeJson(str, InterfaceForDataType("Json-" + i++))
            mProvider?.getRequest(mDataTypeJson)
        }
    }

    private fun logInEditText(msg: String) {
        counterForHundler++
        mHandler.postDelayed({
            if (lineIndex == 0)
                txtResult.append(lineIndex++.toString() + ": " + msg)
            else
                txtResult.append("\n" + lineIndex++ + ": " + msg)
            txtSummery.setText("Image From Call: $countImageComeFromNet")
            txtSummery.append("\nImage From Cache: $countImageComeFromCache")
            txtSummery.append("\nJson From Call: $countJsonComeFromNet")
            txtSummery.append("\nJson From Cache: $countJsonComeFromCache")
            txtSummery.append("\nCanceled: $countCanceled")
            txtSummery.append("\nFailure: $countFailure")
        }, (counterForHundler * showSpeed).toLong())

    }

    inner class InterfaceForDataType internal constructor(private val name: String) : DataDownloadCallbacks {

        override fun onStart(baseDownloadResource: BaseDownloadResource) {
            logInEditText("--------------------")
            logInEditText("OnStart Call")
            logInEditText("Request: $name")
            logInEditText("Come From: " + baseDownloadResource.comeFrom)
            logInEditText("Data Type: " + baseDownloadResource.getmDataType().toString())
            logInEditText("Key MD5: " + baseDownloadResource.keyMD5)
            logInEditText("--------------------")
            logInEditText("\n")
        }

        override fun onSuccess(baseDownloadResource: BaseDownloadResource) {
            if (baseDownloadResource.getmDataType() === DataTypeEnum.IMAGE)
                if (baseDownloadResource.comeFrom == "Net")
                    countImageComeFromNet++
                else
                    countImageComeFromCache++
            else if (baseDownloadResource.getmDataType() === DataTypeEnum.JSON)
                if (baseDownloadResource.comeFrom == "Net")
                    countJsonComeFromNet++
                else
                    countJsonComeFromCache++
            logInEditText("--------------------")
            logInEditText("onSuccess Call")
            logInEditText("Request: $name")
            logInEditText("Come From: " + baseDownloadResource.comeFrom)
            logInEditText("Data Type: " + baseDownloadResource.getmDataType().toString())
            logInEditText("Data Length: " + baseDownloadResource.data?.size)
            logInEditText("Key MD5: " + baseDownloadResource.keyMD5)


            if (baseDownloadResource.getmDataType() === DataTypeEnum.JSON) {
                val type = object : TypeToken<ArrayList<PinboardModel>>() {

                }.type
                val pinboard = (baseDownloadResource as DownloadTypeJson).getJson(type) as ArrayList<PinboardModel>?
                logInEditText("Profile Image Url: " + pinboard?.get(0)?.user?.profileImage)
            }

            logInEditText("--------------------")
            logInEditText("\n")
        }

        override fun onFailure(
            baseDownloadResource: BaseDownloadResource,
            statusCode: Int,
            errorResponse: ByteArray?,
            e: Throwable?
        ) {
            if (baseDownloadResource.comeFrom == "Canceled")
                countCanceled++
            else
                countFailure++
            logInEditText("--------------------")
            logInEditText("onFailure Call")
            logInEditText("Request: $name")
            logInEditText("Come From: " + baseDownloadResource.comeFrom)
            logInEditText("Data Type: " + baseDownloadResource.getmDataType().toString())
            logInEditText("Key MD5: " + baseDownloadResource.keyMD5)
            logInEditText("--------------------")
            logInEditText("\n")
        }

    }

    companion object {
        fun newInstance(): TestingFragment {
            val args = Bundle()
            val fragment = TestingFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
