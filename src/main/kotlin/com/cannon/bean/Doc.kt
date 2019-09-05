package com.cannon.bean

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

@Entity
class Doc(
        var name: String,
        @ManyToOne(fetch = FetchType.LAZY)
        var user: User
) : BaseEntity()