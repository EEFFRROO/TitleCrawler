package service

import cats.effect.IO
import cats.implicits._

trait PageCrawler {
  def findTitles(urls: Seq[String]): IO[Map[String, String]]
}

class PageCrawlerImpl extends PageCrawler {
  private val webClient: WebClient = new WebClientImpl

  private def findTitle(url: String): IO[Option[String]] = {
    val (startTag, endTag) = ("<title>", "</title>")
    webClient.get(url).map {
      case Right(page: String) =>
        (page.indexOf(startTag), page.indexOf(endTag)) match {
          case (start, end) if start >= 0 && end >= end => Some(page.substring(start + startTag.length, end).trim)
          case _ => None
        }
      case Left(_) =>
        println("Request finished with error, cannot find title")
        None
    }
  }

  // TODO async
  override def findTitles(urls: Seq[String]): IO[Map[String, String]] = {
    urls
      .map(url =>
        findTitle(url)
          .map(title => url -> title.getOrElse(""))
      )
      .sequence
      .map(_.toMap)
  }

}
