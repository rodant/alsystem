name := "alsystem"

version := "0.1"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-language:postfixOps")

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test",
  "junit" % "junit" % "4.10" % "test"
)
    