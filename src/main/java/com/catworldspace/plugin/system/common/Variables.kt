package com.catworldspace.plugin.system.common

import com.catworldspace.plugin.system.models.exception.ResourceNotFoundException

object Variables {

    var IsDebugEnabled : Boolean = false
    object PrefixUUIDMatch {
        var IsEnabled: Boolean = false
        var PrefixDivider: String = "."
        var IsMatchUUIDEnabled: Boolean = false
        var redirectList: List<Any?> = listOf()
    }



    object Strings {
        var ConsolePrefix = "[CWS - System]:"

        var PluginLoadedMessage = "插件已加载"
        var PluginUnloadedMessage = "插件已卸载"

        var ResourceNotFoundExceptionMessage = "无法找到资源文件"
        var IOExceptionCanNotCreateFolderMessage = "IO 错误, 无法创建文件夹"
        var IOExceptionCanNotReadConfigFileMessage = "IO 错误, 无法读取配置文件"
        var UnhandledErrorMessage = "未预见的错误"


        object SystemCommand {
            var Greeting = "你好,"
            var PluginReloadedMessage = "已重新载入配置, 部分功能需重启服务器后生效!"
        }

        object LoginPrefixUUIDMatch {
            var BxEUUIDPrefix = "00000000-0000-0000-"
        }
    }
}

