package com.github.b1412.cannon.service.rule

import arrow.core.*
import arrow.core.extensions.list.foldable.find
import arrow.core.extensions.list.foldable.firstOption
import com.github.b1412.cannon.entity.Rule
import com.github.b1412.cannon.entity.User
import com.github.b1412.cannon.service.SecurityFilter
import com.github.b1412.cannon.service.rule.access.AccessRule
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.text.MessageFormat
import java.util.regex.Pattern

@Component
class SecurityFilterImpl : SecurityFilter {


    @Autowired
    lateinit var accessRules: List<AccessRule>

    fun findAccessRules(ruleName: String): Option<AccessRule> {

        return accessRules.firstOption { it.ruleName == ruleName }
    }

    override fun currentUser(): User {
        return SecurityContextHolder.getContext().authentication.principal as User

    }

    override fun query(method: String, requestURI: String): Map<String, String> {
        val role = currentUser().role!!
        val permissions = role
                .rolePermissions
                .map { it.permission }
        val permissionOpt = permissions
                .find {
                    it!!.authUris.split(";").any { uriPatten ->
                        Pattern.matches(uriPatten, requestURI)
                    }
                }.filter { it!!.httpMethod == method }

        return when (permissionOpt) {
            is None -> throw AccessDeniedException(MessageFormat.format("No permission {0} {1}", method, requestURI))
            is Some -> {
                val permission = permissionOpt.t!!
                val rules = role.rolePermissions
                        .firstOption { it.permission!!.id == permission.id }
                        .map { it.rules }
                        .getOrElse { listOf<Rule>() }
                if (rules.isEmpty()) {
                    logger.warn("no rule found")
                    throw AccessDeniedException(MessageFormat.format("No permission {0} {1}", method, requestURI))
                }
                rules.map {
                    findAccessRules(it.name).map { it.exec(permission) }.getOrElse { mapOf() }
                }.reduce { acc, map -> acc + map }
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SecurityFilterImpl::class.java)
    }

}
