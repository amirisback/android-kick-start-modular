package com.frogobox.movie.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.frogobox.base.BuildConfig
import com.frogobox.base.source.model.FavoriteMovie
import com.frogobox.base.source.DataSource
import com.frogobox.base.util.AppExecutors
import com.frogobox.base.util.Constant
import com.frogobox.base.util.Constant.RoomDatabase.Movie.setupCursor
import com.frogobox.movie.R
import java.io.IOException

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * movie
 * Copyright (C) 16/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.movie.ui.widget
 *
 */
class StackRemoteViewFactory(private val mContext: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory, DataSource.GetLocalCallBack<Cursor> {

    private val LOADER_CHEESES = 1

    private val mWidgetItems = mutableListOf<FavoriteMovie>()
    private val mAppWidgetId: Int = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
    )

    private val mLoaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            when (id) {
                LOADER_CHEESES -> return CursorLoader(
                    mContext,
                    Constant.RoomDatabase.Movie.CONTENT_URI,
                    Constant.RoomDatabase.Movie.PROJECTION, null, null, null
                )
                else -> throw IllegalArgumentException()
            }
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
            when (loader.id) {
                LOADER_CHEESES -> getMovieData(data)
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            when (loader.id) {
                LOADER_CHEESES -> getMovieData(null)
            }
        }

    }

    private fun getMovieData(data: Cursor?) {
        mWidgetItems.clear()
        data?.let { setupCursor(it) }?.let { mWidgetItems.addAll(it) }
    }

    private fun getCursorMovie() {
        AppExecutors().diskIO.execute {
            onShowProgressDialog()
            val cursor = mContext.contentResolver.query(
                Constant.RoomDatabase.Movie.CONTENT_URI,
                Constant.RoomDatabase.Movie.PROJECTION,
                null, null, null
            )

            if (cursor != null) {
                if (cursor.count == 0) {
                    onHideProgressDialog()
                    onEmpty()
                } else {
                    onHideProgressDialog()
                    cursor.let { onSuccess(it) }
                }
            }
            cursor?.close()
        }
    }

    override fun onCreate() {
        getCursorMovie()
    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        Binder.restoreCallingIdentity(identityToken)
        getCursorMovie()
    }

    override fun onDestroy() {}

    override fun getCount(): Int {
        return mWidgetItems.size
    }


    override fun getViewAt(position: Int): RemoteViews {

        val mRemoteViews = RemoteViews(mContext.packageName, R.layout.widget_content_item)
        // -----------------------------------------------------------------------------------------
        mRemoteViews.setTextViewText(R.id.textViewWidget, mWidgetItems[position].title)
        try {
            Glide.with(mContext).asBitmap().load(BuildConfig.TMDB_PATH_URL_IMAGE + mWidgetItems[position].backdrop_path).into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    mRemoteViews.setImageViewBitmap(R.id.imageViewWidget, resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // -----------------------------------------------------------------------------------------
        val extras = Bundle()
        extras.putInt(FavoriteBannerWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        // -----------------------------------------------------------------------------------------
        mRemoteViews.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent)
        // -----------------------------------------------------------------------------------------
        return mRemoteViews
    }

    override fun onShowProgressDialog() {}

    override fun onHideProgressDialog() {}

    override fun onSuccess(data: Cursor) {
        mWidgetItems.clear()
        mWidgetItems.addAll(setupCursor(data))
    }

    override fun onFinish() {}

    override fun onEmpty() {}

    override fun onFailed(statusCode: Int, errorMessage: String?) {}

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}