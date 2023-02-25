package com.catworldspace.plugin.system.common

object Strings {
    var ConsolePrefix = "[CWS - System]:"
    var MessagePrefix = "&b[&6&lCWS &7- &eSystem&b]"
    var PluginLoadedMessage = "插件已加载"
    var PluginUnloadedMessage = "插件已卸载"

    // 错误信息
    var UnhandledErrorMessage = "未预见的错误"
    var ResourceNotFoundExceptionMessage = "没有找到资源文件"
    var LanguageResourceNotFoundExceptionMessage = "没有找到语言资源文件"
    var IOExceptionCanNotCreateFolderMessage = "IO 错误, 无法创建文件夹"
    var IOExceptionCanNotReadConfigFileMessage = "IO 错误, 无法读取配置文件"
    var IncorrectConfigurationExceptionMessage = "配置中存在错误"
    var OutdatedConfigurationExceptionMessage = "过期的配置文件"

    object SystemCommand {
        var About = "CatSpaceWorld 服务器的插件"
        var Reloaded = "已重新载入配置文件"
    }

    object LoginPrefixUUIDMatch {
        var BxEUUIDPrefix = "00000000-0000-0000-"
    }
}