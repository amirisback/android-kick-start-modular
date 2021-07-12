package com.frogobox.movie.mvvm.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.frogobox.base.util.Constant.Constant.SEARCH_MOVIE
import com.frogobox.base.util.Constant.Constant.SEARCH_TV_SHOW
import com.frogobox.movie.R
import com.frogobox.movie.mvvm.movie.SearchMovieActivity
import com.frogobox.movie.mvvm.tv.SearchTvShowActivity
import com.frogobox.movie.mvvm.favorite.FavoriteFragment
import com.frogobox.movie.mvvm.movie.MovieFragment
import com.frogobox.movie.mvvm.tv.TvShowFragment
import com.frogobox.movie.util.BaseAppActivity
import com.frogobox.movie.mvvm.favorite.FavoriteMovieViewModel
import com.frogobox.movie.mvvm.favorite.FavoriteTvShowViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAppActivity() {

    private lateinit var mViewModel: MainActivityViewModel

    private var menuItem: Menu? = null
    private var optionSearch = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNav(R.id.frameLayout)
        setupViewModel()
        setupOptionReminder()
        setupFragment(savedInstanceState)
    }

    fun obtainMainActivityViewModel(): MainActivityViewModel =
        obtainViewModel(MainActivityViewModel::class.java)

    fun obtainMainViewModel(): MainViewModel =
        obtainViewModel(MainViewModel::class.java)

    fun obtainFavoriteMovieViewModel(): FavoriteMovieViewModel =
        obtainViewModel(FavoriteMovieViewModel::class.java)

    fun obtainFavoriteTvShowViewModel(): FavoriteTvShowViewModel =
        obtainViewModel(FavoriteTvShowViewModel::class.java)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        menuItem = menu
        return true
    }

    private fun setupSearchActivity(option: String) {
        if (option.equals(SEARCH_MOVIE)) {
            baseStartActivity<SearchMovieActivity>(this)
        } else if (option.equals(SEARCH_TV_SHOW)) {
            baseStartActivity<SearchTvShowActivity>(this)
        }
    }

    private fun setupViewModel() {
        mViewModel = obtainMainActivityViewModel().apply {
            eventStateDailyReminder.observe(this@MainActivity, Observer {
            })

            eventStateReleaseReminder.observe(this@MainActivity, Observer {
            })
        }
    }

    private fun setupOptionReminder() {
        mViewModel.getOprionReminder()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_menu_settings -> {
                baseStartActivity<SettingActivity>(this)
                true
            }

            R.id.toolbar_menu_search -> {
                setupSearchActivity(optionSearch)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.bottom_menu_movie
        }
    }

    private fun setupBottomNav(frameLayout: Int) {
        bottomNavigationView.clearAnimation()
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.bottom_menu_movie -> {
                    optionSearch =
                        SEARCH_MOVIE
                    menuItem?.getItem(0)?.setVisible(true)
                    setupChildFragment(
                        frameLayout,
                        MovieFragment()
                    )
                }

                R.id.bottom_menu_tv -> {
                    optionSearch =
                        SEARCH_TV_SHOW
                    menuItem?.getItem(0)?.setVisible(true)
                    setupChildFragment(
                        frameLayout,
                        TvShowFragment()
                    )
                }

                R.id.bottom_menu_fav -> {
                    menuItem?.getItem(0)?.setVisible(false)
                    setupChildFragment(
                        frameLayout,
                        FavoriteFragment()
                    )
                }

            }

            true
        }

    }

}