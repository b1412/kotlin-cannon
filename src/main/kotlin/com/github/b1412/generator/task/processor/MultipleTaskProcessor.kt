package com.github.b1412.generator.task.processor

import com.github.b1412.generator.entity.CodeProject
import com.github.b1412.generator.entity.Task
import com.github.b1412.generator.task.service.TaskService
import com.google.common.collect.Lists
import org.apache.commons.beanutils.PropertyUtils

class MultipleTaskProcessor : ITaskProcessor {
    override fun run(codeProject: CodeProject, task: Task, context: MutableMap<String, Any>): List<String> {
        val paths = Lists.newArrayList<String>()
        for (codeEntity in codeProject.entities) {
            val codeEntityMap = PropertyUtils.describe(codeEntity)
 //           codeEntityMap.putAll(task.entityExtProcessor!!.invoke(task, codeEntity))
            task.templateHelper!!.put("entity", codeEntityMap)
            context["entity"] = codeEntityMap
            paths.addAll(TaskService.processTemplate(codeProject, task, context))
        }
        return paths
    }
}