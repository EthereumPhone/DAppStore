package org.ethereum.dappstore.logic

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import org.ethereum.dappstore.data.models.AppInfo
import java.io.File
import java.io.InputStreamReader

import java.io.BufferedReader
import androidx.core.content.ContextCompat.startActivity
import android.content.pm.PackageInstaller
import android.content.pm.PackageInstaller.SessionParams
import java.io.FileOutputStream
import java.net.URL
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import android.util.Log

import androidx.core.content.ContextCompat.startActivity

import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity


class AppDownloader {

    private var downloadID: Long = 0
    private var appInfo: AppInfo? = null
    private var absoluteFilePath: String? = null


    fun isUserApp(ai: ApplicationInfo): Boolean {
        val mask = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
        return ai.flags and mask == 0
    }

    fun downloadApk(context: Context, info: AppInfo) {
       // installApk(context, info)

        appInfo = info
        val file = File("/sdcard/Download", info.name.replace(" ", "")+".apk")
        if (file.exists()) {
            appInfo!!.let { installApk(context, it) }
        } else {
            context.registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            );
            val url = info.apkUrl
            // TODO figure out why the notification is not showing
            val downloadmanager: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse(url)

            val request = DownloadManager.Request(uri)
            request.setTitle(info.name)
            request.setDescription("Downloading...")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${info.name.replace(" ", "")}.apk")

            downloadID = downloadmanager.enqueue(request)
            queryStatus(context, downloadmanager)
        }
    }
    fun callRequestInstallApp(context: Context, file: File) {
        val promptInstall = Intent(Intent.ACTION_VIEW)
        val apkURI = FileProvider.getUriForFile(
            context, context.applicationContext
                .packageName.toString() + ".provider", file
        )
        promptInstall.setDataAndType(apkURI, "application/vnd.android.package-archive")
        promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(promptInstall)
    }

    fun installApk(context: Context, info: AppInfo) {
        val file = File("/sdcard/Download", info.name.replace(" ", "")+".apk")
        val app: ApplicationInfo =
            context.packageManager.getApplicationInfo(context.packageName, 0)

        if (isUserApp(app)) {
            // Not installed on ethOS. Requires special install
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!context.packageManager.canRequestPackageInstalls()) {
                    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(Uri.parse(String.format("package:%s", context.packageName)))
                    context.startActivity(intent)
                    Toast.makeText(context, "After enabling this setting, press Install App again.", Toast.LENGTH_LONG).show()
                } else {
                    callRequestInstallApp(context, file);
                }
            } else {
                callRequestInstallApp(context, file);
            }
        } else {
            // Is a system-app, probably installed on ethOS.
            val apkLength = file.length()
            val commArray : Array<String> = arrayOf("/bin/sh", "-c", "cat ${file.absolutePath} | pm install -S $apkLength")
            //val command = "cat ${file.absolutePath} | pm install -S $apkLength"
            val pr = Runtime.getRuntime().exec(commArray);
            pr.waitFor()


            val errorString = pr.errorStream.bufferedReader().use { it.readLine() }
            val successString = pr.inputStream.bufferedReader().use { it.readLine() }

            println("Error: $errorString")
            println("Success: $successString")
            if (successString.startsWith("Success")) {
                Toast.makeText(context, "Successfully installed ${info.name}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Something went wrong. Try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            context.unregisterReceiver(this)
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