package com.aline.android.news.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aline.android.news.R
import com.aline.android.news.adapter.AdapterNews
import com.aline.android.news.models.Locale
import com.aline.android.news.rest.getApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_locale.*

class LocaleFragment : Fragment() {

    val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.recycler_locale) }
    private val LOG: String = LocaleFragment::class.java.simpleName


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_locale, container, false)
    }

    companion object {
        fun newInstance(): LocaleFragment = LocaleFragment()
    }

    override fun onStart() {
        super.onStart()

        progress_locale.visibility = View.VISIBLE

        getApiService().getTopNewsLocale("br")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> handleResult(result) },
                        { error -> handleError(error) }
                )

        recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager

    }

    private fun handleError(error: Throwable?) {
        Log.e(LOG, "Error: " + error?.message)

    }

    private fun handleResult(result: Locale?) {
        progress_locale.visibility = View.GONE
        recyclerView?.adapter = AdapterNews(context!!, result?.articles)
    }


}


