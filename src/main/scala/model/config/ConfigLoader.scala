package model.config

import com.typesafe.config.{Config, ConfigFactory}

object ConfigLoader {
  private val config: Config = ConfigFactory.load()

  object App {
    object Data {
      val questionsDirectory: String = config.getString("app.data.questionsDirectory")
      val structureFile: String = config.getString("app.data.structureFile")
    }
  }
}
