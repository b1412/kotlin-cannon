package com.github.b1412.cannon.controller.base

import com.github.b1412.cannon.controller.BaseController
import com.github.b1412.cannon.controller.base.BaseBranchController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.github.b1412.cannon.entity.Branch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestParam


abstract class BaseBranchController(

) : BaseController<Branch, Long>() {

    @GetMapping
    override fun page(request: HttpServletRequest, @RequestParam filter: Map<String, String>): List<Branch> {
        return super.page(request, filter)

    }

    @GetMapping("{id}")
    override fun findOne(@PathVariable id: Long, request: HttpServletRequest): Branch {
        return super.findOne(id, request)
    }

    @Transactional
    @PostMapping
    override fun saveOne(@Validated @RequestBody input: Branch, request: HttpServletRequest): ResponseEntity<*> {
        return super.saveOne(input, request)

    }

    @Transactional
    @PutMapping("{id}")
    override fun updateOne(@PathVariable id: Long, @Validated @RequestBody input: Branch, request: HttpServletRequest): ResponseEntity<*> {
        return super.updateOne(id, input, request)
    }

    @DeleteMapping("{id}")
    override fun deleteOne(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<*> {
        return super.deleteOne(id,request)

    }
}