package com.github.b1412.generator

import com.github.b1412.generator.entity.CodeProject
import com.github.b1412.generator.entity.Task
import com.github.b1412.generator.entity.scanForCodeEntities
import com.github.b1412.generator.entity.scanForCodeEnum
import com.github.b1412.generator.ext.Utils
import com.github.b1412.generator.template.FreeMarkerHelper
import com.github.b1412.template.apiTasks
import java.util.*


fun generate(): List<Pair<Task, List<String>>> {
    val appProps = Properties()
    appProps.load(Thread.currentThread().contextClassLoader.getResourceAsStream("generator/local.properties"))
    val projectName = appProps.getProperty("projectName")
    val packageName = appProps.getProperty("packageName")
    val entityPackageName = appProps.getProperty("entityLocationPattern")
    val entities = scanForCodeEntities(entityPackageName)
    val enums = scanForCodeEnum()
    return CodeProject(
            name = projectName,
            entities = entities,
            enums = enums,
            utilClasses = listOf(Utils::class.java),
            packageName = packageName,
            tasks = apiTasks(),
            templateEngine = FreeMarkerHelper()
    ).generate()
}


