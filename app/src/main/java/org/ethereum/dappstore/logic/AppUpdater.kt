package org.ethereum.dappstore.logic

import android.content.Context
import android.widget.Toast
import org.ethereum.dappstore.BuildConfig
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import org.ethereum.dappstore.DappStoresQuery
import org.ethereum.dappstore.data.models.AppInfo

class AppUpdater {
    private val ipfs = "https://gateway.pinata.cloud/ipfs/"
    private val updaterClient = ApolloClient.builder()
        .serverUrl("https://api.thegraph.com/subgraphs/name/markusbug/dappstore-rinkeby")
        .build()
    suspend fun checkForUpdate(context: Context) {
        val currentVersionCode = BuildConfig.VERSION_CODE
        updaterClient.query(DappStoresQuery()).await().data?.dappStores?.get(0).let {
            if (it != null) {
                if (currentVersionCode<it.id.toInt()) {
                    //Update
                    Toast.makeText(context, "New update found, updating now.", Toast.LENGTH_LONG).show()
                    val appInfo: AppInfo = AppInfo(
                        name = "DAppStore",
                        apkUrl = ipfs+it.ipfsHash,
                        category = "Store",
                        id = "-1",
                        description = "",
                        iconUrl = ""
                    )
                    AppDownloader().downloadApk(context, appInfo)
                }
            }
        }
    }
}