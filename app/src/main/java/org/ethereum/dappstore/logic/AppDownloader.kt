package org.ethereum.dappstore.logic

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import org.ethereum.dappstore.data.models.AppInfo
import java.io.File

class AppDownloader {

    private var downloadID: Long = 0
    private var appInfo: AppInfo? = null

    fun downloadApk(context: Context, info: AppInfo) {
        appInfo = info
        context.registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        );
        val url = info.apkUrl
        // TODO figure out why the notification is not showing
        val request = DownloadManager.Request(Uri.parse(url))
            .setMimeType("application/vnd.android.package-archive")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle(info.name)
            .setDescription("Downloading APK...")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${info.name}.apk")
        val service: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID = service.enqueue(request)
        queryStatus(context, service)
    }

    fun installApk(context: Context, info: AppInfo) {
        val promptInstall = Intent(Intent.ACTION_VIEW)
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            "${info.name}.apk"
        )
        val apkURI = FileProvider.getUriForFile(
            context, context.applicationContext
                .packageName.toString() + ".provider", file
        )
        promptInstall.setDataAndType(apkURI, "application/vnd.android.package-archive")
        promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(promptInstall)
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
                appInfo?.let { installApk(context, it) }
            }
        }
    }

    fun queryStatus(context: Context, service: DownloadManager) {
        // TODO keep querying status and get progress updates
        val c: Cursor? = service.query(DownloadManager.Query().setFilterById(downloadID))
        c?.let {
            it.moveToFirst()
            Toast.makeText(context, statusMessage(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun statusMessage(c: Cursor): String {
        val index = c.getColumnIndex(DownloadManager.COLUMN_STATUS)
        return when (c.getInt(index)) {
            DownloadManager.STATUS_FAILED -> "Download failed"
            DownloadManager.STATUS_PAUSED -> "Download paused"
            DownloadManager.STATUS_PENDING -> "Download started..."
            DownloadManager.STATUS_RUNNING -> "Download in progress"
            DownloadManager.STATUS_SUCCESSFUL -> "Download complete"
            else -> "Download is nowhere in sight"
        }
    }
}