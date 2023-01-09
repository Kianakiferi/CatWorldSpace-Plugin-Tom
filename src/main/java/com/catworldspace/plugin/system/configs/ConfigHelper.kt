package com.catworldspace.plugin.system.configs

import com.catworldspace.plugin.system.common.Constants
import com.catworldspace.plugin.system.models.exception.ResourceNotFoundException
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object ConfigHelper {

    fun getConfiguration(pluginFolder: File): Configuration {
        val configFile = File(pluginFolder, Constants.configName)

        if (!configFile.exists()) { // 如果不存在, 则从 Resource 中复制
            copyFromResources(Constants.configName, configFile)
        }

        val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)
        val config = loadConfig(provider, configFile)

        val configVersion = config.getString(Constants.configVersionPath)
        if (isOutdatedConfigVersion(configVersion)) { // 如果是过期的配置文件, 则从 Resource 中复制, 并重新载入
            makeACopy(configFile,"_$configVersion")
            copyFromResources(Constants.configName, configFile,true)
            return loadConfig(provider, configFile)
        }

        return config
    }

    fun getLanguageStrings(pluginFolder: File, language: String = "zh_CN"): Any {
        val languageFolder = getOrCreateFolder(File(pluginFolder, Constants.languageFolderName))

        val stringsFile = File(languageFolder,"$language.yml")
        if (!stringsFile.exists()) {
            copyFromResources("${Constants.languageFolderName}/$language.yml", stringsFile)
            //copyFromResources("$language.yml", stringsFile)
        }

        val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)
        return loadConfig(provider, stringsFile)
    }

    private fun makeACopy(file: File, prefix: String? = "_Old") {
        val output  = FileOutputStream(file.path)

        Files.copy(file.toPath(), output)
    }

    fun copyFromResources(fileName: String, target: File, replace: Boolean = false) {
        try {
            getResourceAsStream(fileName).use { input ->
                if(input == null) {
                    throw ResourceNotFoundException("没有找到资源文件 ${fileName}, 请检查文件是否存在")
                }
                if (replace) {
                    Files.copy(
                        input,
                        target.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                } else {
                    Files.copy(
                        input,
                        target.toPath()
                    )
                }

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getResourceAsStream(path: String): InputStream? {
        return javaClass.classLoader.getResourceAsStream(path)
    }

    @JvmStatic
    fun getOrCreateFolder(file: File): File {
        if (!file.exists()) {
            try {
                if (!file.mkdir()) {
                    throw IOException("IO 错误, 无法创建文件夹")
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

        throw IOException("IO 错误, 无法读取配置文件")
    }

    @JvmStatic
    fun isOutdatedConfigVersion(version: String): Boolean {
        // TODO: 更好的时间判断
        return version != Constants.currentConfigVersion
    }
}