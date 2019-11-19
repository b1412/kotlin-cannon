package com.github.b1412.cannon.service

import com.github.b1412.cannon.service.base.BaseService
import com.github.b1412.cannon.dao.BlogDao
import com.github.b1412.cannon.entity.Blog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class BlogService(
    @Autowired
    val dao: BlogDao
) : BaseService<Blog, Long>(dao = dao)



