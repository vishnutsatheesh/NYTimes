import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.nytimes.R
import com.nytimes.util.ProgressDialog
import kotlinx.android.synthetic.main.content_article_detailview.*


class WebViewFragment : Fragment() {


    var progressDialog: ProgressDialog? = null

    companion object {
        val KEY_URL = "key:url"
        fun newInstance(url: String): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString(KEY_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    interface OnWebViewCreatedListener {
        fun onWebViewCreated()
    }

    private var mUrl: String? = null
    private var mOnWebViewCreatedListener: OnWebViewCreatedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.content_article_detailview, container, false)


        progressDialog = ProgressDialog(this!!.context!!)
        progressDialog!!.showDialog()

        /**
         * Making sure the dialog is not showing for much time
         */
        Handler().postDelayed(Runnable { progressDialog!!.dismissDialog() }, 8000)

        /**
         * Fetching url
         */
        mUrl = arguments!!.getString(KEY_URL)

        if (TextUtils.isEmpty(mUrl)) {
            throw IllegalArgumentException("Empty URL passed to WebViewFragment!")
        }

        // enable remote debugging
        if (0 != (ApplicationInfo.FLAG_DEBUGGABLE and (activity?.applicationInfo?.flags ?: 0))
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        return view
    }


    @SuppressLint("JavascriptInterface", "AddJavascriptInterface", "SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settings = webview.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        webview.webViewClient = DefaultWebViewClient(this!!.progressDialog!!)
        webview.loadUrl(mUrl)

        mOnWebViewCreatedListener?.onWebViewCreated()
    }

    override fun onResume() {
        super.onResume()
        webview.onResume()
    }

    override fun onPause() {
        super.onPause()
        webview.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // don't hold on to the listener (which could potentially be an Activity)
        mOnWebViewCreatedListener = null
        // destroy the WebView completely
        if (webview != null) {
            // the WebView must be removed from the view hierarchy before calling destroy
            // to prevent a memory leak (#75)
            // See https://developer.android.com/reference/android/webkit/WebView.html#destroy%28%29
            (webview.parent as ViewGroup).removeView(webview)
            webview.removeAllViews()
            webview.destroy()
        }
    }

    // our custom methods
    val currentUrl: String?
        get() {
            if (webview == null) {
                return null
            }
            var currentLoadedUrl: String? = webview.originalUrl
            if (currentLoadedUrl == null) {
                currentLoadedUrl = mUrl
            }
            return currentLoadedUrl
        }

    val currentTitle: String?
        get() {
            if (webview == null) {
                return null
            }
            return webview.title
        }

    fun evaluateJavascript(javascript: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.evaluateJavascript(javascript, null)
        } else {
            webview.loadUrl("javascript:" + javascript)
        }
    }

    @SuppressLint("JavascriptInterface", "AddJavascriptInterface")
    fun setJSInterface(jsInterface: Any, name: String) {
        webview.addJavascriptInterface(jsInterface, name)
    }

    fun setWebViewClient(webViewClient: DefaultWebViewClient) {
        webview.webViewClient = webViewClient
    }

    fun setWebChromeClient(webChromeClient: DefaultWebChromeClient) {
        webview.webChromeClient = webChromeClient
    }

    fun onBackPressed(): Boolean {
        if (webview.canGoBack()) {
            webview.goBack()
            return true
        }
        return false
    }

    open class DefaultWebViewClient(var dialog: ProgressDialog) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            // do your stuff here
            dialog!!.dismissDialog()

        }

    }

    class DefaultWebChromeClient : WebChromeClient()    // no-op
}