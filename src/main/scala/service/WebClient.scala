package service

import cats.effect.IO
import org.http4s.Status
import org.http4s.blaze.client.BlazeClientBuilder

trait WebClient {
  def get(url: String): IO[Either[String, String]]
}

class WebClientImpl extends WebClient {
  override def get(url: String): IO[Either[String, String]] = {
    BlazeClientBuilder[IO].resource.use { client =>
      client.get(url) {
        case Status.Successful(r) => r.attemptAs[String].leftMap(_.message).value
        case r => r.as[String]
          .map(b => Left(s"Request failed with status ${r.status.code} and body $b"))
      }.attempt.map {
        case Left(throwable: Throwable) => Left(throwable.getMessage)
        case Right(x) => x
      }
    }
  }
}


