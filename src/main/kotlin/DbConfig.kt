

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import repository.InventoryTable
import javax.sql.DataSource




object DbConnection {

    val dataSource = createDataSource()
    val dataBase = provideDatabase(dataSource)

    fun createDataSource(): DataSource{
        val config = HikariConfig()

        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/store"
        config.username = "user"
        config.password = "password"

        config.validate()

        return HikariDataSource(config)
    }

    fun provideDatabase( ds: DataSource ): Database{
        return Database.connect(ds)
    }


}

fun Application.initDB(){

    val database = DbConnection.dataBase

    transaction(database) {
        SchemaUtils.create(InventoryTable)
    }

}