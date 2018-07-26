package com.aline.android.news.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.aline.android.news.R
import com.aline.android.news.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){

            R.id.navigation_locale -> {
                title_toolbar.text = getString(R.string.title_toolbar_locale)
                val localeFrag = LocaleFragment.newInstance()
                openFragment(localeFrag)
               return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_business -> {
                title_toolbar.text = getString(R.string.title_toolbar_business)
                val businessFrag = BusinessFragment.newInstance()
                openFragment(businessFrag)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_tech -> {
                title_toolbar.text = getString(R.string.titletoolbar_tech)
                val techFrag = TechFragment.newInstance()
                openFragment(techFrag)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_sports -> {
                title_toolbar.text = getString(R.string.title_toolbar_sports)
                val sportsFrag = SportsFragment.newInstance()
                openFragment(sportsFrag)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_search ->{
                title_toolbar.text =  getString(R.string.title_toolbar_search)
                val searchFrag = SearchFragment.newInstance()
                openFragment(searchFrag)
                return@OnNavigationItemSelectedListener  true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main as Toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        title_toolbar.text = getString(R.string.title_toolbar_locale)
        val localeFrag = LocaleFragment.newInstance()
        openFragment(localeFrag)

        navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

}
