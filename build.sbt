name := "alsystem"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-language:postfixOps")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.0" % "test",
  "junit" % "junit" % "4.10" % "test"
)

fork in Test := false

lazy val alsystem = project in file(".")

lazy val alweb = (
  Project("alweb", file("alweb"))
    settings(libraryDependencies ++= Seq("org.webjars" %% "webjars-play" % "2.3.0",
    "com.typesafe.play" %% "play-slick" % "0.8.0"))
  ).enablePlugins(PlayScala)