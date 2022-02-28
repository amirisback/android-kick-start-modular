package com.frogobox.movie.mvvm.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.frogobox.base.BuildConfig
import com.frogobox.base.callback.DeleteViewCallback
import com.frogobox.base.callback.SaveViewCallback
import com.frogobox.base.source.model.FavoriteMovie
import com.frogobox.base.source.model.Movie
import com.frogobox.base.util.Helper
import com.frogobox.movie.R
import com.frogobox.movie.util.BaseAppActivity
import com.bumptech.glide.Glide
import com.frogobox.base.databinding.ActivityDetailBinding

class DetailMovieActivity : BaseAppActivity<ActivityDetailBinding>(),
    SaveViewCallback, DeleteViewCallback {

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val EXTRA_FAV_MOVIE = "EXTRA_FAV_MOVIE"
    }

    private lateinit var mViewModel: DetailMovieViewModel
    private lateinit var extraFavoriteMovie: FavoriteMovie
    private lateinit var extraMovie: Movie

    private var isFav = true
    private var menuItem: Menu? = null

    override fun setupViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
        mViewModel = obtainDetailMovieViewModel().apply {

            favoriteMovie.observe(this@DetailMovieActivity, Observer {

            })

            eventIsFavorite.observe(this@DetailMovieActivity, Observer {
                setFavorite(it)
                isFav = it
            })

        }
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupDetailActivity(getString(R.string.title_detail_movie))
        setupExtraData()
    }

    private fun obtainDetailMovieViewModel(): DetailMovieViewModel =
        obtainViewModel(DetailMovieViewModel::class.java)

    private fun stateExtra(listenerMovie: () -> Unit, listenerFavMovie: () -> Unit) {
        if (checkExtra(EXTRA_MOVIE)) {
            listenerMovie()
        } else if (checkExtra(EXTRA_FAV_MOVIE)) {
            listenerFavMovie()
        }
    }


    private fun setupExtraData() {
        binding.apply {
            stateExtra({
                extraMovie = baseGetExtraData(EXTRA_MOVIE)
                val poster = extraMovie.backdrop_path?.let { Helper.Func.removeBackSlash(it) }
                Glide.with(this@DetailMovieActivity).load("${BuildConfig.TMDB_PATH_URL_IMAGE}$poster").into(ivPoster)
                tvTitle.text = extraMovie.title
                tvOverview.text = extraMovie.overview
                extraMovie.id?.let { mViewModel.getFavoriteMovie(it) }
            }) {
                extraFavoriteMovie = baseGetExtraData(EXTRA_FAV_MOVIE)
                val poster =
                    extraFavoriteMovie.backdrop_path?.let { Helper.Func.removeBackSlash(it) }
                Glide.with(this@DetailMovieActivity).load("${BuildConfig.TMDB_PATH_URL_IMAGE}$poster").into(ivPoster)
                tvTitle.text = extraFavoriteMovie.title
                tvOverview.text = extraFavoriteMovie.overview
                extraFavoriteMovie.id?.let { mViewModel.getFavoriteMovie(it) }
            }
        }
    }

    private fun setFavorite(state: Boolean) {
        if (state)
            menuItem?.getItem(0)?.icon = getDrawable(R.drawable.ic_favorite)
        else
            menuItem?.getItem(0)?.icon = getDrawable(R.drawable.ic_un_favorite)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_favorite, menu)
        menuItem = menu
        setFavorite(isFav)
        return true
    }

    private fun handleFavorite() {
        if (isFav) {
            stateExtra({
                extraMovie.id?.let { mViewModel.deleteFavoriteMovie(it, this) }
                extraMovie.id?.let { mViewModel.getFavoriteMovie(it) }
            }) {
                extraFavoriteMovie.id?.let { mViewModel.deleteFavoriteMovie(it, this) }
                extraFavoriteMovie.id?.let { mViewModel.getFavoriteMovie(it) }
            }
        } else {
            stateExtra({
                mViewModel.saveFavoriteMovie(
                    FavoriteMovie(
                        id = extraMovie.id,
                        title = extraMovie.title,
                        backdrop_path = extraMovie.backdrop_path,
                        poster_path = extraMovie.poster_path,
                        overview = extraMovie.overview
                    ), this
                )
                extraMovie.id?.let { mViewModel.getFavoriteMovie(it) }
            }) {
                mViewModel.saveFavoriteMovie(
                    FavoriteMovie(
                        id = extraFavoriteMovie.id,
                        title = extraFavoriteMovie.title,
                        backdrop_path = extraFavoriteMovie.backdrop_path,
                        poster_path = extraFavoriteMovie.poster_path,
                        overview = extraFavoriteMovie.overview
                    ), this
                )
                extraFavoriteMovie.id?.let { mViewModel.getFavoriteMovie(it) }
            }

        }
        setFavorite(isFav)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_menu_fav -> {
                handleFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onShowProgress() {}

    override fun onHideProgress() {}

    override fun onSuccesInsert() {
        showToast(getString(R.string.text_succes_add_favorite))
    }

    override fun onSuccesDelete() {
        showToast(getString(R.string.text_succes_delete_favorite))
    }

    override fun onFailed(message: String) {}

}
