import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.jsonOf
import org.http4s.dsl.io._
import org.http4s.{EntityDecoder, HttpRoutes}
import service.PageCrawler



trait Routes {
  import requestBodies._

  protected val pageCrawler: PageCrawler

  protected def uris: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case r @ POST -> Root / "findTitles" => Ok {
      r.as[Urls].flatMap(entity => pageCrawler.findTitles(entity.urls))
    }
  }


}

object requestBodies {
  case class Urls(urls: Seq[String])

  implicit val urlsDecoder: EntityDecoder[IO, Urls] = jsonOf[IO, Urls]
}
