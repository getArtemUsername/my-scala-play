name := """play-scala-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  javaHome := Some(file("/usr/java/jdk-1.8.0-u121"))
)

scalaVersion := "2.11.11"

pipelineStages := Seq(digest)

libraryDependencies ++= Seq(
  ws,
  cache,
  jdbc,
  evolutions,
  "org.scalikejdbc" %% "scalikejdbc" % "2.3.5",
  "org.scalikejdbc" %% "scalikejdbc-config" % "2.3.5",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.postgresql" % "postgresql" % "9.4.1207.jre7",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
  "org.mockito" % "mockito-core" % "2.0.45-beta",
  "com.softwaremill.macwire" %% "macros" % "2.2.0" % "provided",
  "com.softwaremill.macwire" %% "util" % "2.2.0",
  "de.svenkubiak" % "jBCrypt" % "0.4.1"
)
