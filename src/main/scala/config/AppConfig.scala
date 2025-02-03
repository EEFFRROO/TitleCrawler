package config

import com.typesafe.config.ConfigFactory

trait Config {
  def port: Int
  def host: String
}

class AppConfig extends Config {
  private val conf = ConfigFactory.load()

  val port: Int = conf.getInt("port")
  val host: String = conf.getString("host")
}
