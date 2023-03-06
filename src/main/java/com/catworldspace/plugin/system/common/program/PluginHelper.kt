package com.catworldspace.plugin.system.common.program

import com.catworldspace.plugin.system.common.configs.ConfigHelper
import com.catworldspace.plugin.system.exception.OutdatedConfigurationException
import com.catworldspace.plugin.system.exception.ResourceNotFoundException
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object PluginHelper {
    @JvmStatic
    fun setVariables(config: Configuration) {
        Variables.isDebug = config.getBoolean(Constants.ConfigPaths.debug)

        Variables.serverList =
            ConfigHelper.getServerList((config.getList(Constants.ConfigPaths.serverList)).toList())

        Variables.PrefixUUIDMatch.isEnabled = config.getBoolean(Constants.ConfigPaths.PrefixUUIDMatch.enabled)
        Variables.PrefixUUIDMatch.prefixSeparator = config.getString(Constants.ConfigPaths.PrefixUUIDMatch.prefixSeparator)

        Variables.PrefixUUIDMatch.UUIDMatch.isEnabled = config.getBoolean(Constants.ConfigPaths.PrefixUUIDMatch.UUIDMatch.enabled)
        Variables.PrefixUUIDMatch.UUIDMatch.matchPattern = config.getString(Constants.ConfigPaths.PrefixUUIDMatch.UUIDMatch.matchPattern)


    }

    @JvmStatic
    fun setStrings(config: Configuration) {
        Strings.consolePrefix = config.getString(Constants.StringsPaths.Plugin.consolePrefix)
        Strings.messagePrefix = config.getString(Constants.StringsPaths.Plugin.messagePrefix)
        Strings.pluginLoadedMessage = config.getString(Constants.StringsPaths.Plugin.pluginLoaded)
        Strings.pluginUnloadedMessage = config.getString(Constants.StringsPaths.Plugin.pluginUnloaded)

        Strings.SystemCommand.about = config.getString(Constants.StringsPaths.System.about)
        Strings.SystemCommand.reloaded = config.getString(Constants.StringsPaths.System.reloaded)
    }

    @JvmStatic
    fun getConfiguration(pluginFolder: File): Configuration {
        return getConfig(pluginFolder, Constants.FilePaths.configName)
    }

    @JvmStatic
    fun getLocalizedStrings(pluginFolder: File, languageCode: String = "zh_CN"): Configuration {
        return getConfig(pluginFolder, "${Constants.FilePaths.languageFolderName}/$languageCode.yml")

        /* val languageFolder = getOrCreateFolder(File(pluginFolder, Constants.languageFolderName))
        val stringsFile = File(languageFolder,"$language.yml")
        //val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

        if (!Constants.IS_DEBUG && stringsFile.exists() && stringsFile.length() != 0L) {

            //copyFromResources(getResourceFileByName("${Constants.languageFolderName}/$language.yml"), stringsFile)
            //copyFromResources("$language.yml", stringsFile)
        }

        copyFileFromResourcesByName("${Constants.languageFolderName}/$language.yml", stringsFile, true)
        return loadConfig(provider, stringsFile)
        */
    }

    @JvmStatic
    fun getOrCreateFolder(file: File): File {
        if (!file.exists()) {
            try {
                if (!file.mkdir()) {
                    throw IOException("${Strings.consolePrefix} ${Strings.canNotCreateFolderExceptionMessage}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }


    @JvmStatic
    private fun loadConfig(provider: ConfigurationProvider, file: File): Configuration {
        try {
            return provider.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        throw IOException("${Strings.consolePrefix} ${Strings.canNotReadConfigFileExceptionMessage}")
    }

    @JvmStatic
    private fun isValidVersion(version: String): Boolean {
        // TODO: 更好的版本判断
        return version == Constants.currentConfigVersion
    }

    @JvmStatic
    private fun getConfig(folder: File, fileName: String): Configuration {
        val file = File(folder, fileName)
        if (Constants.IS_DEBUG && !file.exists() && file.length() == 0L) {
            copyFileFromResourcesByName(fileName, file, true)
        }

        val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

        val result :Configuration = loadConfig(provider, file)
        val configVersion = result.getString(Constants.ConfigPaths.configVersion)

        if (isValidVersion(configVersion)) {
            return result
        } else {
            backupAndCopyConfig(fileName, file,true)


            throw OutdatedConfigurationException("${Strings.consolePrefix} ${Strings.outdatedConfigurationExceptionMessage}: $configVersion")
        }
    }

    private fun backupAndCopyConfig(fileName: String, file: File, replace: Boolean = false) {
        var oldFile :File = file



    }


    @JvmStatic
    private fun copyFileFromResourcesByName(fileName: String, target: File, replace: Boolean = false) {
        try {
            getResourceAsStream(fileName).use { input ->
                copyFile(input, target, replace)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    private fun getResourceAsStream(fileName: String): InputStream {
        return javaClass.classLoader.getResourceAsStream(fileName)
            ?: throw ResourceNotFoundException("${Strings.consolePrefix} ${Strings.resourceNotFoundExceptionMessage}: $fileName")
    }

    @JvmStatic
    private fun copyFile(input: InputStream, target: File, replace: Boolean? = false) {
        if (replace!!) {
            Files.copy(input, target.toPath(), StandardCopyOption.REPLACE_EXISTING)
        } else {
            Files.copy(input, target.toPath())
        }
    }
}
