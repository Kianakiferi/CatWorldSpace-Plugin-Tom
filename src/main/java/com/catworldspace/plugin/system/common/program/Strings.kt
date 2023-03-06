package com.catworldspace.plugin.system.common.program

object Strings {
    var consolePrefix = "[CWS - System]:"
    var messagePrefix = "&b[&6&lCWS &7- &eSystem&b]"
    var pluginLoadedMessage = "插件已加载"
    var pluginUnloadedMessage = "插件已卸载"

    // 错误信息
    var unhandledErrorMessage = "未预见的错误"
    var resourceNotFoundExceptionMessage = "未找到资源文件"
    var languageResourceNotFoundExceptionMessage = "没有找到语言资源文件"
    var canNotCreateFolderExceptionMessage = "IO 错误, 无法创建文件夹"
    var canNotReadConfigFileExceptionMessage = "IO 错误, 无法读取配置文件"
    var incorrectConfigurationExceptionMessage = "配置中存在错误"
    var outdatedConfigurationExceptionMessage = "过期的配置文件"

    object SystemCommand {
        var about = "CatSpaceWorld 服务器的插件"
        var reloaded = "已重新载入配置文件"
    }

    object LoginPrefixUUIDMatch {
        var BxEUUIDPrefix = "00000000-0000-0000-"
    }
}