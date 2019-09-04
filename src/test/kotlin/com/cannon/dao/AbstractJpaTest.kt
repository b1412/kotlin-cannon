package com.cannon.dao

import com.cannon.DatabaseCleanupService
import com.cannon.config.CustomDateTimeProvider
import com.cannon.config.JpaConfig
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.auditing.config.AuditingConfiguration


@DataJpaTest(
        includeFilters = [ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = [AuditingConfiguration::class, JpaConfig::class, CustomDateTimeProvider::class, DatabaseCleanupService::class]
        )]
)

class AbstractJpaTest {
    @Autowired
    private lateinit var truncateDatabaseService: DatabaseCleanupService


    @AfterEach
    fun cleanupAfterEach() {
        println("after each clean up")
        truncateDatabaseService.truncate()
    }

}