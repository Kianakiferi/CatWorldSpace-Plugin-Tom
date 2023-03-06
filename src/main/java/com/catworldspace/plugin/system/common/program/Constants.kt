package com.catworldspace.plugin.system.common.program

object Constants {
    const val IS_DEBUG = false
    const val currentConfigVersion = "2023-0225"

    object FilePaths {
        const val languageFolderName = "localization"
        const val updatesFolderName = "updates"
        const val configName = "config.yml"
    }

    object ConfigPaths {
        const val configVersion ="config-version"
        const val debug = "debug"
        const val language = "language"

        private const val rootPath = "settings"

        object System {
            private const val systemRoot = "$rootPath.system"

            const val systemCommandName = "$systemRoot.command-name"
            const val systemCommandPermission = "$systemRoot.command-permission"
            const val systemCommandAlias = "$systemRoot.command-alias"
        }

        const val serverList = "$rootPath.server-list"
        object ServerElement {
            const val permission = "permission"
            const val redirectPrefix = "redirect-prefix"
        }
        object PrefixUUIDMatch {
            private const val prefixMatchRoot = "$rootPath.prefix-uuid-match"
            const val enabled = "$prefixMatchRoot.enabled"
            const val prefixSeparator = "$prefixMatchRoot.prefix-separator"

            object UUIDMatch {
                private const val uuidMatchRoot = "$prefixMatchRoot.uuid-match"

                const val enabled = "$uuidMatchRoot.enabled"
                const val uuidMatchList = "$uuidMatchRoot.uuid-match-list"
                const val matchPattern = "$uuidMatchRoot.match-pattern"
            }
        }

        object Hub{
            private const val hubRoot = "$rootPath.hub"

            const val enabled = "$hubRoot.enabled"
            const val hubCommandName = "$hubRoot.command-name"
            const val hubCommandPermission = "$hubRoot.command-permission"
            const val hubCommandAlias = "$hubRoot.command-alias"
        }
    }

    object StringsPaths{
        const val configVersion ="config-version"
        const val languageCode = "language-code"

        private const val rootPath = "strings"
        object Plugin {
            private const val pluginPath = "$rootPath.plugin"
            const val consolePrefix = "$pluginPath.console-prefix"
            const val messagePrefix = "$pluginPath.message-prefix"
            const val pluginLoaded = "$pluginPath.plugin-loaded"
            const val pluginUnloaded = "$pluginPath.plugin-unloaded"
        }

        object System {
            private const val systemPath = "$rootPath.system"
            const val about = "$systemPath.about"
            const val reloaded = "$systemPath.reload"
        }

        object Common {
            private const val commonPath = "$rootPath.common"
            const val noPermission = "$commonPath.no-permission"
            const val notExist = "$commonPath.not-exist"
        }
    }

}

