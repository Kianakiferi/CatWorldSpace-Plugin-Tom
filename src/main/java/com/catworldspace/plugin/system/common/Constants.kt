package com.catworldspace.plugin.system.common

object Constants {
    const val IS_DEBUG = false
    const val currentConfigVersion = "2023-0124"

    const val languageFolderName = "localization"
    const val updatesFolderName = "updates"
    const val configName = "config.yml"

    // Config Path
    const val configVersionPath ="config-version"
    const val debugPath = "debug"
    const val languagePath = "language"

    private const val rootPath = "settings"

    const val serverListPath = "$rootPath.server-list"
    const val permissionPath = "permission"
    const val redirectPrefixPath = "redirect-prefix"

    const val commandSystemPath = "$rootPath.system"

    const val loginPrefixUUIDMatchPath = "$rootPath.prefix-uuid-match"
    const val loginPrefixUUIDMatch_MatchUUIDPath = "$loginPrefixUUIDMatchPath.match-uuid"

}

