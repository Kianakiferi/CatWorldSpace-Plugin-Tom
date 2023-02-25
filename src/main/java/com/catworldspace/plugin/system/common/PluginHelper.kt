package com.catworldspace.plugin.system.common

import com.catworldspace.plugin.system.common.Strings.ConsolePrefix
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
    private val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

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
                    throw IOException("$ConsolePrefix ${Strings.IOExceptionCanNotCreateFolderMessage}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }

    @JvmStatic
    fun loadConfig(provider: ConfigurationProvider, file: File): Configuration {
        try {
            return provider.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        throw IOException("$ConsolePrefix ${Strings.IOExceptionCanNotReadConfigFileMessage}")
    }

    @JvmStatic
    fun isValidVersion(version: String): Boolean {
        // TODO: 更好的版本判断
        return version == Constants.currentConfigVersion
    }

    @JvmStatic
    private fun getConfig(folder: File, fileName: String): Configuration {
        val file = File(folder, fileName)
        if (Constants.IS_DEBUG && !file.exists() && file.length() == 0L) {
            copyFileFromResourcesByName(fileName, file, true)
        }

        val result :Configuration = loadConfig(provider, file)
        val configVersion = result.getString(Constants.ConfigPaths.configVersion)

        if (isValidVersion(configVersion)) {
            return result
        } else {
           throw OutdatedConfigurationException("$ConsolePrefix ${Strings.OutdatedConfigurationExceptionMessage}: $configVersion")
        }
    }

    @JvmStatic
    fun setVariables(config: Configuration) {
        Variables.isDebug = config.getBoolean(Constants.ConfigPaths.debug)

        Variables.serverList  =
            ConfigHelper.getServerList((config.getList("${Constants.ConfigPaths.serverList}")).toList())

        Variables.PrefixUUIDMatch.isEnabled = config.getBoolean("${Constants.ConfigPaths.PrefixUUIDMatch.enabled}")
        Variables.PrefixUUIDMatch.prefixSeparator = config.getString("${Constants.ConfigPaths.PrefixUUIDMatch.prefixSeparator}")
        Variables.PrefixUUIDMatch.UUIDMatch.isEnabled = config.getBoolean("${Constants.ConfigPaths.PrefixUUIDMatch.UUIDMatch.enabled}")

    }

    @JvmStatic
    fun setStrings(config: Configuration) {
        Strings.ConsolePrefix = config.getString("${Constants.StringsPath.}")

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
            ?: throw ResourceNotFoundException("$ConsolePrefix ${Strings.ResourceNotFoundExceptionMessage}: $fileName")
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