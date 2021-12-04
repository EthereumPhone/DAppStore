package org.ethereum.dappstore.data.models

data class DAppInfo(
    val id: String,
    val name: String,
    val apkUrl: String,
    val iconUrl: String,
    val description: String,
    val category: String,
)
