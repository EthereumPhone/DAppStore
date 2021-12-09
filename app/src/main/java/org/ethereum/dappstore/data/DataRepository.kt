package org.ethereum.dappstore.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.coroutines.toFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.ethereum.dappstore.AppQuery
import org.ethereum.dappstore.AppsQuery
import org.ethereum.dappstore.data.models.AppInfo

object DataRepository {

    val FAKE_DATA: List<AppInfo> = listOf(
        AppInfo(
            "1",
            "AAVE",
            "",
            "https://awesomepolygon.com/wp-content/uploads/2021/04/J1YJtvdI_400x400.jpg",
            "Aave is an open source and non-custodial liquidity protocol for earning interest on deposits and borrowing assets.",
            "AAM"
        ),
        AppInfo(
            "2",
            "SushiSwap",
            "",
            "https://awesomepolygon.com/wp-content/uploads/2021/03/logo.1efce8b3.png",
            "The SushiSwap protocol realigns incentives for network participants by introducing revenue sharing and forum-driven network efforts to the popular AMM model.",
            "Exchange"
        ),
        AppInfo(
            "3",
            "Decentraland",
            "",
            "https://awesomepolygon.com/wp-content/uploads/2021/04/89_fXbxN_400x400.png",
            "Decentraland is a virtual game world run by its users. Every piece of land and every item in the virtual land is a non-fungible token. Some of them sell for thousands of dollars.",
            "Gaming"
        ),
        AppInfo(
            "4",
            "Drakons",
            "",
            "https://awesomepolygon.com/wp-content/uploads/2021/02/Drakons_logo_new.png",
            "Drakons.IO is a strategy game and crypto collectibles site powered by the Ethereum blockchain that lets you collect, breed, sell and battle with digital dragons.",
            "Gaming"
        ),
    )

    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://api.studio.thegraph.com/query/16024/rinkebydappstoretest/0.0.4")
        .build()

    suspend fun fetchApps(): List<AppInfo>? {
        return apolloClient.query(AppsQuery()).await().data?.apps?.map {
            AppInfo(
                it.id, it.appName, "", "", "", "dApp"
            )
        }
    }

    suspend fun fetchAppById(id: String): AppInfo? {
        return apolloClient.query(AppQuery(id)).await().data?.app?.let {
            AppInfo(
                it.id, it.appName, "", "", "", "dApp"
            )
        }
    }
}


