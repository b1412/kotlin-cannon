package com.github.b1412.permission.controller

import com.github.b1412.permission.controller.base.BasePermissionController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.github.b1412.permission.service.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import com.github.leon.aci.extenstions.responseEntityOk
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/v1/permission")
class PermissionController(

) : BasePermissionController() {



}