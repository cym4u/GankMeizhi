/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package cn.chenyuanming.gankmeizhi.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.ExecutionException

/**
 * 简单重构了下，并且修复了重复插入图片问题
 * Created by drakeet on 8/10/15.
 */
object RxMeizhi {

    fun saveImageAndGetPathObservable(context: Context, url: String, title: String?): Observable<Uri> {
        return Observable.create(ObservableOnSubscribe<Bitmap> { emitter ->
            var bitmap: Bitmap? = null
            try {
                bitmap = Glide.with(context).asBitmap().load(Uri.parse(url)).submit().get()
                emitter.onNext(bitmap)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }

            if (bitmap == null) {
                emitter.onError(Exception("无法下载到图片"))
            }
            emitter.onComplete()
        }).flatMap { bitmap ->
            val appDir = File(Environment.getExternalStorageDirectory(), "Meizhi")
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val fileName = title?.replace('/', '-') + ".jpg"
            val file = File(appDir, fileName)
            try {
                val fos = FileOutputStream(file)
                assert(bitmap != null)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val uri = Uri.fromFile(file)
            // 通知图库更新
            val scannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
            context.sendBroadcast(scannerIntent)
            Observable.just(uri)
        }.subscribeOn(Schedulers.io())
    }
}
