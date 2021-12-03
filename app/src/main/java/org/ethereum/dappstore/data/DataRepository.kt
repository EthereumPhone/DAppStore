package org.ethereum.dappstore.data

import kotlinx.coroutines.delay
import org.ethereum.dappstore.data.models.DAppInfo

class DataRepository {

    companion object {
        val FAKE_DATA: List<DAppInfo> = listOf(
            DAppInfo(
                "1",
                "AAVE",
                "",
                "https://awesomepolygon.com/wp-content/uploads/2021/04/J1YJtvdI_400x400.jpg",
                "Aave is an open source and non-custodial liquidity protocol for earning interest on deposits and borrowing assets."
            ),
            DAppInfo(
                "1",
                "SushiSwap",
                "",
                "https://awesomepolygon.com/wp-content/uploads/2021/03/logo.1efce8b3.png",
                "The SushiSwap protocol realigns incentives for network participants by introducing revenue sharing and forum-driven network efforts to the popular AMM model."
            ),
            DAppInfo(
                "1",
                "Decentraland",
                "",
                "https://awesomepolygon.com/wp-content/uploads/2021/04/89_fXbxN_400x400.png",
                "Decentraland is a virtual game world run by its users. Every piece of land and every item in the virtual land is a non-fungible token. Some of them sell for thousands of dollars."
            ),
            DAppInfo(
                "1",
                "Drakons",
                "",
                "https://awesomepolygon.com/wp-content/uploads/2021/02/Drakons_logo_new.png",
                "Drakons.IO is a strategy game and crypto collectibles site powered by the Ethereum blockchain that lets you collect, breed, sell and battle with digital dragons."
            ),
        )
    }

    suspend fun fetchApps(): List<DAppInfo> {
        delay(1000)
        return FAKE_DATA;
    }
}


