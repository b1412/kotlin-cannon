package ${project.packageName}.dao

import ${project.packageName}.dao.base.BaseDao
import ${project.packageName}.entity.${entity.name}
import org.springframework.stereotype.Repository

@Repository
interface ${entity.name}Dao : BaseDao<${entity.name}, Long>