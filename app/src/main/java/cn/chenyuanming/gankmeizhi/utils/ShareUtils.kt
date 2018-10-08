package cn.chenyuanming.gankmeizhi.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

import cn.chenyuanming.gankmeizhi.R

/**
 * Created by drakeet on 8/17/15.
 */
object ShareUtils {


    fun shareImage(context: Context, uri: Uri, title: String?) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/jpeg"
        context.startActivity(Intent.createChooser(shareIntent, title))
    }


    @JvmOverloads
    fun share(context: Context, extraText: String = context.getString(R.string.share_text)) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share))
        intent.putExtra(Intent.EXTRA_TEXT, extraText)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.action_share)))
    }
}