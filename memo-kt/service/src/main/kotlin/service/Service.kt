package service

import org.komapper.r2dbc.R2dbcDatabase
import query.Query
import utils.IdWorker

abstract class Service(val query: Query, val database: R2dbcDatabase){
    companion object{
        fun generateId() = IdWorker.DEFAULT.nextId()
    }
}
