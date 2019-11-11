package com.github.leon.generator.entity

data class CodeToolbar(
        val showReset: Boolean = true,
        val showAdd: Boolean = true,
        val showExcelImport: Boolean = false,
        val showExcelExport: Boolean = false,
        val toolbarItems: List<CodeToolbarItem> = mutableListOf()
)




