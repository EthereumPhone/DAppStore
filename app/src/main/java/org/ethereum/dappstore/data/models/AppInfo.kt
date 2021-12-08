package org.ethereum.dappstore.data.models

data class AppInfo(
    val id: String,
    val name: String,
    val apkUrl: String,
    val iconUrl: String,
    val description: String,
    val category: String,
)
