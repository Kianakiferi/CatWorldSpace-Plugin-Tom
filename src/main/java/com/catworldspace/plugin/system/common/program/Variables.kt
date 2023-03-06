package com.catworldspace.plugin.system.common.program

import com.catworldspace.plugin.system.models.CWSServer

object Variables {
    var isDebug = false
    var language = "zh_CN"

    var serverList: List<CWSServer> = listOf()
    object PrefixUUIDMatch {
        var isEnabled = false
        var prefixSeparator = "."

        object UUIDMatch {
            var isEnabled = false

            var matchPattern = "[a-zA-Z0-9]"
        }
    }

    object Hub {
        var isEnabled = false
    }
}

