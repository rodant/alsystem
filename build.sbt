name := "alsystem"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-language:postfixOps")

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "com.typesafe.play" %% "play-ws" % "2.3.1"
)

fork in Test := false

lazy val logic = (Project("logic", file("logic"))
  settings(
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.1",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.0" % "test",
    "junit" % "junit" % "4.10" % "test")
  )
  )

lazy val alsystem = (project in file(".")).enablePlugins(PlayScala).dependsOn(logic)
