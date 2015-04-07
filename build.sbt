name := "alsystem"

//playScalaSettings

fork in Test := false

fork in run := true

lazy val logic = project
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq()
  )

lazy val alsystem = (project in file("."))
  .dependsOn(logic)
  .aggregate(logic)
  .settings(commonSettings: _*)
  .settings(commonPlaySettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.webjars" % "angularjs" % "1.2.18" exclude("org.webjars", "jquery"),
      "org.webjars" % "requirejs" % "2.1.11-1"
    )
  ).enablePlugins(PlayScala)

lazy val commonPlaySettings: Seq[Setting[_]] = Seq(
  libraryDependencies ++= Seq(
    "org.webjars" %% "webjars-play" % "2.3.0",
    "com.typesafe.play" %% "play-slick" % "0.8.0",
    "com.typesafe.play" %% "play-ws" % "2.3.1"
  )
)

lazy val commonSettings: Seq[Setting[_]] = Seq(
  name <<= name("alsystem-" + _),
  version := "0.2-SNAPSHOT",
  scalaVersion := "2.11.1",
  scalacOptions ++= Seq("-deprecation", "-feature", "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.0" % "test",
    "junit" % "junit" % "4.10" % "test")
)
