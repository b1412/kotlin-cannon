package com.github.b1412.permission.service

import arrow.core.*
import com.github.b1412.api.service.BaseService
import com.github.b1412.permission.dao.UserDao
import com.github.b1412.permission.entity.Role
import com.github.b1412.permission.entity.RolePermission
import com.github.b1412.permission.entity.User
import com.github.b1412.security.PermissionProperties
import com.github.b1412.security.TokenBasedAuthentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@EnableConfigurationProperties(value = [PermissionProperties::class])
class UserService(
    @Autowired
    val userDao: UserDao,
    @Value("\${spring.application.name}")
    val application: String,
    @Autowired
    val permissionProperties: PermissionProperties
) : BaseService<User, Long>(dao = userDao) {

    @Transactional
    fun getUserWithPermissions(username: String, clientId: String): User {
        val id = userDao.findByUsernameAndClientId(username, clientId).toOption().map { it.id }.getOrElse { 0L }
        entityManager.clear()
        val graph = this.entityManager.createEntityGraph(User::class.java)

        val roleSubGraph = graph.addSubgraph<Role>("role")
        val rolePermissionSubGraph = roleSubGraph.addSubgraph<List<RolePermission>>("rolePermissions")
        rolePermissionSubGraph.addAttributeNodes("permission")
        rolePermissionSubGraph.addAttributeNodes("rules")
        graph.addSubgraph<User>("branch")

        val hints = HashMap<String, Any>()
        hints["javax.persistence.fetchgraph"] = graph
        val userOpt = this.entityManager.find(User::class.java, id, hints).toOption().filter {
            when (permissionProperties.user.needVerify) {
                true -> it.active!!
                false -> true
            }
        }
        val user = when (userOpt) {
            is Some -> userOpt.t
            None -> throw AccessDeniedException("invalid user information or user is not verified: $username")
        }

        val permissions = user.role!!.rolePermissions
            //TODO
            //   .filter { it.rules.isEmpty().not() }
            .map { it.permission }
        val grantedAuthorities =
            permissions.map { SimpleGrantedAuthority(it?.authKey) }.toMutableList()
        user.grantedAuthorities = grantedAuthorities
        return user
    }

    @Transactional
    fun loadAuthenticationByClientId(clientId: String): Option<Authentication> {
        return userDao.findByIdOrNull(clientId.toLong()).toOption()
            .map {
                val user = cacheClient.get("$application-${it.username}-$clientId".toLowerCase()) {
                    getUserWithPermissions(
                        it.username!!,
                        clientId
                    )
                }!!
                TokenBasedAuthentication(user as UserDetails)
            }
    }
}
