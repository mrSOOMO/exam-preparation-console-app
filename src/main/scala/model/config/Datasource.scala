package model.config

import io.getquill.jdbczio.Quill
import io.getquill.{PostgresJAsyncContext, SnakeCase}
import model.DbConfigPath
import zio.{ZIO, ZLayer}

import javax.sql.DataSource

object Datasource:

  final private val quillPath: DbConfigPath  = "omniview"
  final private val flywayPath: DbConfigPath = "flyway"

  val dataSourceLayer: ZLayer[Any, Throwable, DataSource] = Quill.DataSource.fromPrefix(flywayPath)
  val quillCtxLayer: ZLayer[Any, Throwable, PostgresJAsyncContext[SnakeCase]] = ZLayer(
    ZIO.attempt(new PostgresJAsyncContext[SnakeCase](SnakeCase, quillPath))
  )
