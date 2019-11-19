package com.github.b1412.cannon.controller

import com.github.b1412.cannon.controller.base.BaseBlogController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.github.b1412.cannon.service.BlogService
import org.springframework.beans.factory.annotation.Autowired
import com.github.b1412.cannon.extenstions.responseEntityOk
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/v1/blog")
class BlogController(

) : BaseBlogController() {



}