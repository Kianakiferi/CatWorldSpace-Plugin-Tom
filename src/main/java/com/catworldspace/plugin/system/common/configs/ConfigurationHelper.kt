package com.catworldspace.plugin.system.common.configs

import com.catworldspace.plugin.system.common.Constants
import com.catworldspace.plugin.system.common.Constants.permissionPath
import com.catworldspace.plugin.system.common.Constants.redirectPrefixPath
import com.catworldspace.plugin.system.common.Variables
import com.catworldspace.plugin.system.common.Variables.Strings.ConsolePrefix
import com.catworldspace.plugin.system.common.connection.ServerHelper.getServerByName
import com.catworldspace.plugin.system.models.CWSServer
import com.catworldspace.plugin.system.models.exception.IncorrectConfigurationException
import com.catworldspace.plugin.system.models.exception.ResourceNotFoundException
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object ConfigurationHelper {
    private val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

    @JvmStatic
    fun getConfiguration(pluginFolder: File): Configuration {
        return getConfig(pluginFolder, Constants.configName)
    }

    @JvmStatic
    fun getLanguageStrings(pluginFolder: File, languageCode: String = "zh_CN"): Any {
        return getConfig(pluginFolder, "${Constants.languageFolderName}/$languageCode.yml")

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
    fun getLanguageCode(config: Configuration): String {
        val language = config.getString(Constants.languagePath)
        if (language.isNullOrEmpty()) {
            throw ResourceNotFoundException("$ConsolePrefix ${Variables.Strings.LanguageResourceNotFoundExceptionMessage}")
        }
        return language
    }


    @JvmStatic
    fun getServerList(rawList: List<Any?>): List<CWSServer> {
        val result = mutableListOf<CWSServer>()

        for (element in rawList) {
            if (element is LinkedHashMap<*, *>) {
                val key = element.keys.first().toString()
                val value = element.values.first() as LinkedHashMap<*, *>

                if (value.containsKey(permissionPath) && value.containsKey(redirectPrefixPath)) {
                    val permission = value[permissionPath] as String?
                    val redirectPrefix = value[redirectPrefixPath] as String?

                    result.add(CWSServer(key, permission, redirectPrefix, getServerByName(key)))
                } else {
                    throw IncorrectConfigurationException("$ConsolePrefix ${Variables.Strings.IncorrectConfigurationExceptionMessage} at $key")
                }
            }
        }
        return result
    }

    @JvmStatic
    private fun getConfig(folder: File, fileName: String): Configuration {
        val file = File(folder, fileName)

        if (!Constants.IS_DEBUG && file.exists() && file.length() != 0L) {
            val config = loadConfig(provider, file)
            val configVersion = config.getString(Constants.configVersionPath)

            if (isValidVersion(configVersion)) {
                return config
            }
        }

        copyFileFromResourcesByName(fileName, file, true)
        return loadConfig(provider, file)
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
            ?: throw ResourceNotFoundException("$ConsolePrefix ${Variables.Strings.ResourceNotFoundExceptionMessage} $fileName")
    }

// --注释掉检查 START (2023/1/28 20:41):
//    @JvmStatic
//    private fun copyFile(file: File, target: File, replace: Boolean? = false) {
//        if (replace!!) {
//            Files.copy(file.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING)
//        } else {
//            Files.copy(file.toPath(), target.toPath())
//        }
//    }
// --注释掉检查 STOP (2023/1/28 20:41)

    @JvmStatic
    private fun copyFile(input: InputStream, target: File, replace: Boolean? = false) {
        if (replace!!) {
            Files.copy(input, target.toPath(), StandardCopyOption.REPLACE_EXISTING)
        } else {
            Files.copy(input, target.toPath())
        }
    }

    @JvmStatic
    fun getOrCreateFolder(file: File): File {
        if (!file.exists()) {
            try {
                if (!file.mkdir()) {
                    throw IOException("$ConsolePrefix ${Variables.Strings.IOExceptionCanNotCreateFolderMessage}")
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

        throw IOException("$ConsolePrefix ${Variables.Strings.IOExceptionCanNotReadConfigFileMessage}")
    }

    @JvmStatic
    fun isValidVersion(version: String): Boolean {
        // TODO: 更好的版本判断
        return version == Constants.currentConfigVersion
    }
}