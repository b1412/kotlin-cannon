package com.github.b1412.generator

import com.github.b1412.generator.entity.*
import com.github.b1412.generator.ext.Utils
import com.github.b1412.generator.script.DefaultScriptHelper
import com.github.b1412.generator.template.FreeMarkerHelper
import com.github.b1412.template.apiTasks
import com.github.b1412.template.uiTasks
import com.github.b1412.template.uiTemplateTasks
import java.util.*


fun generate(codeEnv: CodeEnv? = null): List<Pair<Task, List<String>>> {
    val appProps = Properties()
    appProps.load(Thread.currentThread().contextClassLoader.getResourceAsStream("generator/local.properties"))
    val projectName = appProps.getProperty("projectName")
    val packageName = appProps.getProperty("packageName")
    val entityPackageName = appProps.getProperty("entityLocationPattern")
    val apiTargetPath = System.getProperty("user.dir")

    val uiTargetPath: String
    val testTargetPath: String
    val uiTemplateTargetPath: String

    uiTargetPath = appProps.getProperty("uiTargetPath")
    testTargetPath = appProps.getProperty("testTargetPath")
    uiTemplateTargetPath = appProps.getProperty("uiTemplateTargetPath")

    val entities = scanForCodeEntities(entityPackageName)
    val enums = scanForCodeEnum()
    return CodeProject(
            name = projectName,
            entities = entities,
            enums = enums,
            utilClasses = listOf(Utils::class.java),
            packageName = packageName,
            uiTemplateTargetPath = uiTemplateTargetPath,
            testTasks = listOf(),
            uiTasks = uiTasks(),
            apiTasks = apiTasks(),
            uiTemplateTasks = uiTemplateTasks(),
            uiTargetPath = uiTargetPath,
            apiTargetPath = apiTargetPath,
            testTargetPath = testTargetPath,
            scriptHelper = DefaultScriptHelper("groovy"),
            templateEngine = FreeMarkerHelper()
    ).generate(codeEnv)
}


