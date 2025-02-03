ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .settings(
    name := "TitleCrawler"
  )

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % "0.23.30",
  "org.http4s" %% "http4s-blaze-server" % "0.23.17",
  "org.http4s" %% "http4s-blaze-client" % "0.23.17",
  "org.typelevel" %% "cats-effect" % "3.5.7",
  "org.slf4j" % "slf4j-api" % "2.0.12",
  "org.slf4j" % "slf4j-simple" % "2.0.13",
  "com.typesafe" % "config" % "1.4.3",
  "org.http4s" %% "http4s-circe" % "0.23.30",
  "io.circe" %% "circe-generic" % "0.14.9",
  "io.circe" %% "circe-literal" % "0.14.9"
)
