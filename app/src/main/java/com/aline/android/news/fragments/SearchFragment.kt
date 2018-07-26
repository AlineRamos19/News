package com.aline.android.news.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aline.android.news.R
import com.aline.android.news.adapter.AdapterNews
import com.aline.android.news.models.Article
import com.aline.android.news.models.Locale
import com.aline.android.news.rest.getApiService

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.recycler_search) }
    private val LOG : String = SearchFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onStart() {
        super.onStart()


        btn_search.setOnClickListener {

            val searchTextUser = edit_search.text.toString()
            if(searchTextUser.trim().isEmpty()) edit_search.setError("Informe alguma palavra-chave para pesquisar")
            else{
                getDataSearch(searchTextUser)
            }
        }

        edit_search.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(not_found_view.visibility == View.VISIBLE) not_found_view.visibility = View.GONE

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun getDataSearch(searchTextUser: String) {

        progress_search.visibility = View.VISIBLE

        getApiService().getSearchNews(searchTextUser, "pt", "publishedAt")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> handlerResult(result)},
                        { error -> handlerError(error)}
                )

        recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager

    }

    private fun handlerError(error: Throwable?) {
        Log.e(LOG, "Error: " + error?.message)
    }

    private fun handlerResult(result: Locale?) {
        progress_search.visibility = View.GONE
        if(result?.totalResults == 0) {
            not_found_view.visibility = View.VISIBLE
            recyclerView?.adapter =  AdapterNews(context!!, arrayListOf())
            recyclerView?.adapter?.notifyDataSetChanged()
        } else{
            recyclerView?.adapter =  AdapterNews(context!!, result?.articles)
        }
    }

    companion object {
        fun newInstance() : SearchFragment = SearchFragment()
    }
}
