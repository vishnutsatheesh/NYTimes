package com.nytimes.fragment

import WebViewFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.newyorktimes.model.Response
import com.nytimes.R
import com.nytimes.interfaces.InternetListener
import com.nytimes.interfaces.ReacyclerListener
import com.nytimes.model.MainViewModel
import com.nytimes.util.LogsUtils
import com.nytimes.util.ProgressDialog
import com.twobasetechnologies.workit.adapters.ArticlesAdapter
import kotlinx.android.synthetic.main.article_fragment.*


class ArticleFragment : Fragment(), ReacyclerListener, InternetListener {


    lateinit var adapter: ArticlesAdapter

    lateinit var content: ArrayList<Response.Result>

    var progressDialog: ProgressDialog? = null

    val TAG = javaClass.simpleName

    companion object {
        fun newInstance() = ArticleFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.article_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressDialog = ProgressDialog(this!!.context!!)
        progressDialog!!.showDialog()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initRecyclerview()
        viewModel.setListener(this)
        viewModel.setContext(this!!.context!!)
        viewModel.getUsers().observe(this, Observer<ArrayList<Response.Result>> { contents ->
            // update UI

            progressDialog!!.dismissDialog()

            if (contents != null) {
                LogsUtils.makeLogE(TAG, "onActivityCreated model_Result Size>>${contents.size}")
            }

            if (contents != null) {
                content.addAll(contents)
                adapter.notifyDataSetChanged()
            }

        })


    }

    fun initRecyclerview() {
        content = ArrayList()
        var linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ArticlesAdapter(this!!.context!!, this, content)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onItemClicked(positon: Int) {

//        val intent = Intent(activity, ArticleDetailview::class.java)
//        intent.putExtra(Constants.CONSTANT_URL,content[positon].url)
//        startActivity(intent)

        // Begin the transaction
        val fm = fragmentManager

        val ft = fm!!.beginTransaction()

        var fragment = WebViewFragment.newInstance(content[positon].url)
        // FragmentManager.BackStackEntry backEntry = (FragmentManager.BackStackEntry) getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount());
        // Replace the contents of the container with the new fragment
        ft.add(R.id.container, fragment)
        // String str = fragment.getClass().getName();
        ft.addToBackStack("" + System.currentTimeMillis())
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit()

    }

    override fun isConnectionAvailable(available: Boolean) {
        if (!available)
            Toast.makeText(context, "No internet available, Please check your network connection.",
                    Toast.LENGTH_LONG).show()
    }


}
