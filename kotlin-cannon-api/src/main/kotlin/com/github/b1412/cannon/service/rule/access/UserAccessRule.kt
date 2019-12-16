package com.github.b1412.cannon.service.rule.access

import com.github.b1412.cannon.entity.Permission
import com.github.b1412.cannon.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserAccessRule : AccessRule {
    override val ruleName: String
        get() = "user"

    override fun exec(permission: Permission): Map<String, String> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return mapOf("f_user.id" to user.id.toString(), "f_user.id_op" to "=")
    }
}