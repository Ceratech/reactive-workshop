name := "reactive-workshop"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  guice,

  "com.h2database" % "h2" % "1.4.194",
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.slick" %% "slick" % "3.2.0",

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test,
  "org.mockito" % "mockito-core" % "2.8.47" % Test,
  "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.3" % Test

)
