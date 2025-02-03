import cats.effect.{ExitCode, IO, IOApp}
import config.AppConfig
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import service.{PageCrawler, PageCrawlerImpl}

object Server extends IOApp with Routes {

  override val pageCrawler: PageCrawler = new PageCrawlerImpl

  override def run(args: List[String]): IO[ExitCode] = {
    val config = new AppConfig

    val app = Router(
      "/" -> uris
    ).orNotFound

    BlazeServerBuilder[IO]
      .bindHttp(config.port, config.host)
      .withHttpApp(app)
      .resource
      .useForever
      .as(ExitCode.Success)
  }
}
