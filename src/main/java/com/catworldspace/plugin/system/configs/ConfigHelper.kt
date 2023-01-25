package com.catworldspace.plugin.system.configs

import com.catworldspace.plugin.system.common.Constants
import com.catworldspace.plugin.system.common.Variables
import com.catworldspace.plugin.system.common.Variables.Strings.ConsolePrefix
import com.catworldspace.plugin.system.models.exception.ResourceNotFoundException
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object ConfigHelper {
    private val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

    fun getConfiguration(pluginFolder: File): Configuration {
        val configFile = File(pluginFolder, Constants.configName)
        //val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

        if (!Constants.IS_DEBUG && configFile.exists() && configFile.length() != 0L) {
            val config = loadConfig(provider, configFile)
            val configVersion = config.getString(Constants.configVersionPath)

            if (isValidVersion(configVersion)) {
                return config
            }
        }

        copyFileFromResourcesByName(Constants.configName, configFile, true)
        return loadConfig(provider, configFile)
    }

    fun getLanguageStrings(pluginFolder: File, language: String = "zh_CN"): Any {
        val languageFolder = getOrCreateFolder(File(pluginFolder, Constants.languageFolderName))
        val stringsFile = File(languageFolder,"$language.yml")
        //val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

        if (!Constants.IS_DEBUG && stringsFile.exists() && stringsFile.length() != 0L) {

            //copyFromResources(getResourceFileByName("${Constants.languageFolderName}/$language.yml"), stringsFile)
            //copyFromResources("$language.yml", stringsFile)
        }

        copyFileFromResourcesByName("${Constants.languageFolderName}/$language.yml", stringsFile, true)
        return loadConfig(provider, stringsFile)
    }

    fun getLanguageCode(config:Configuration): String {
        val language = config.getString(Constants.languagePath)
        if (language.isNullOrEmpty()){
            throw IllegalArgumentException("无法找到指定的语言配置文件")
        }
        return language
    }

    private fun copyFileFromResourcesByName(fileName: String, target: File, replace: Boolean = false) {
        try {
            getResourceAsStream(fileName).use { input ->
                copyFile(input, target, replace)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun getResourceAsStream(fileName: String): InputStream {
        return javaClass.classLoader.getResourceAsStream(fileName)?:throw ResourceNotFoundException("$ConsolePrefix ${Variables.Strings.ResourceNotFoundExceptionMessage} $fileName")
    }
    private fun copyFile(file: File, target: File, replace : Boolean? = false){
        if(replace!!){
            Files.copy(file.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING)
        } else {
            Files.copy(file.toPath(), target.toPath())
        }
    }
    private fun copyFile(input: InputStream, target: File, replace : Boolean? = false){
        if(replace!!){
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