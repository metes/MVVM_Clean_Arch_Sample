package com.example.mvvmkotlintest.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.*
import android.os.Build
import android.provider.MediaStore
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.ParameterizedType
import java.net.Inet4Address
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KClass


object CommonUtils {

    const val DATE_FORMAT = "dd.MM.yyyy"
    const val DATE_Z_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    fun <T> getTClass(): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

    fun <T : Any> getTClassKotlin(): KClass<T> {
        val tClass = getTClass<T>()
        return tClass.kotlin
    }

    @Suppress("UNCHECKED_CAST")
    fun <ViewModelType : Any> viewModelClass(): KClass<ViewModelType> {
        // dirty hack to get generic type https://stackoverflow.com/a/1901275/719212
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<ViewModelType>).kotlin
    }


    fun getDeviceInfo(): String {
        return Build.MANUFACTURER + ", " + Build.MODEL + " - API:" + Build.VERSION.SDK_INT
    }

    fun getIpv4HostAddress(): String {
        NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
            networkInterface.inetAddresses?.toList()?.find {
                !it.isLoopbackAddress && it is Inet4Address
            }?.let { return it.hostAddress }
        }
        return ""
    }

    /**
     * Dogru calismiyor
     */
    fun <T> String.convertToObject(java: Class<T>): T? {
        return try {
            Gson().fromJson(this, java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = am.runningAppProcesses
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == context.packageName) {
                        isInBackground = false
                    }
                }
            }
        }
        return isInBackground
    }

    fun getNowDate(): String {
        return SimpleDateFormat(DATE_FORMAT, Locale.US).format(Date(System.currentTimeMillis()))
    }

    fun getNowZDate(): String {
        return SimpleDateFormat(DATE_Z_FORMAT, Locale.US).format(Date(System.currentTimeMillis()))
    }

    fun getAppVersionName(context: Context?): String {
        return context?.packageManager?.getPackageInfo(context.packageName, 0)?.versionName ?: ""
    }

    fun dpToPx(dip: Float, context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            context.resources.displayMetrics
        )
    }

    fun <T> ArrayList<T>.changeData(newData: ArrayList<T>): ArrayList<T> {
        clear()
        addAll(newData)
        return this
    }

    fun pxToDP(px: Int, context: Context): Float {
        return px / context.resources.displayMetrics.density
    }

    fun <T> List<T>.toArrayList(): ArrayList<T> {
        ArrayList<T>().apply {
            addAll(this@toArrayList)
            return this
        }
    }

    fun File.deleteFile(contentResolver: ContentResolver) {
        // Set up the projection (we only need the ID)

        // Set up the projection (we only need the ID)
        val projection = arrayOf(MediaStore.Images.Media._ID)

        // Match on the file path

        // Match on the file path
        val selection = MediaStore.Images.Media.DATA + " = ?"
        val selectionArgs = arrayOf<String>(absolutePath)

        // Query for the ID of the media matching the file path

        // Query for the ID of the media matching the file path
        val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val contentResolver: ContentResolver = app.contentResolver
        val c: Cursor? = contentResolver.query(queryUri, projection, selection, selectionArgs, null)

        if (c != null) {
            if (c.moveToFirst()) {
                // We found the ID. Deleting the item via the content provider will also remove the file
                val id: Long =
                    c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val deleteUri = ContentUris.withAppendedId(queryUri, id)
                contentResolver.delete(deleteUri, null, null)
            } else {
                // File not found in media store DB
            }
            c.close()
        }
    }

    fun setLocale(context: Context, locale: Locale) {
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        configuration.setLocale(locale)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            context.createConfigurationContext(configuration)
        } else {
            resources.updateConfiguration(configuration, displayMetrics)
        }
    }


    fun isFullNumber(text: String): Boolean {
        text.toCharArray().forEach {
            if (!Character.isDigit(it)) {
                return false
            }
        }
        return true
    }

    fun isFullLetter(text: String): Boolean {
        text.toCharArray().forEach {
            if (Character.isDigit(it)) {
                return false
            }
        }
        return true
    }

    @Throws(Exception::class)
    fun getValueKotlin(pName: String, obj: Any): String {
        val objClass = obj.javaClass
        val fields = objClass.declaredFields
        for (field in fields) {
            field.isAccessible = true
            val name = field.name
            if (pName.equals(name, ignoreCase = true)) {
                var value = field.get(obj)
                if (value == null) {
                    value = "0.0"
                }
                println("getValue:: $name: $value")
                return value.toString()
            }
        }
        return ""
    }

    fun convertHTMLToSpanned(htmlText: String): Spanned? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(htmlText)
        }
    }


    fun convertObjToString(clsObj: Any?): String? {
        //convert object  to string json
        return Gson().toJson(clsObj, object : TypeToken<Any?>() {}.type)
    }

    fun <T> convertStringToObj(strObj: String?, classOfT: Class<T>?): T {
        //convert string json to object
        return Gson().fromJson(strObj, classOfT)
    }

    fun resizeImage(file: File, scaleTo: Int = 1920) {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / scaleTo, photoH / scaleTo)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        val resized = BitmapFactory.decodeFile(file.absolutePath, bmOptions) ?: return
        file.outputStream().use {
            resized.compress(Bitmap.CompressFormat.JPEG, 75, it)
            resized.recycle()
        }
    }

    fun saveImage(finalBitmap: Bitmap, name: Uri): File {
        /*
        val root: String = Environment.getExternalStorageDirectory().absolutePath
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val fileName = "Image-$name.jpg"

         */
        val file = File(name.path)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return file
    }

    fun calculateScreenSize(activity: Activity): Int {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }

    private fun getScreenMetrics(context: Context): DisplayMetrics {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)

        return metrics
    }

    fun getScreenHeight(context: Context?): Float {
        return if (context != null) {
            val metrics = getScreenMetrics(context)
            val height = metrics.heightPixels
            height.toFloat()
        } else {
            0f
        }
    }

    fun getScreenWidth(context: Context?): Float {
        return if (context != null) {
            val metrics = getScreenMetrics(context)
            val width = metrics.widthPixels
            width.toFloat()
        } else {
            0f
        }
    }


    fun postDelay(function: () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(250)
            function()
        }
    }

    fun isOnline(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.let { networkCapabilities ->
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        }
        return false
    }


    fun View.visibleIf(visible: Boolean) {
        if (visible) visibility = VISIBLE else GONE
    }

}