name := "akka-restdoc"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.4",
  "com.typesafe.akka" %% "akka-stream" % "2.4.4",
  "com.typesafe.akka" %% "akka-http-core" % "2.4.4",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.4",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.4",
  "commons-io" % "commons-io" % "2.4",
  "com.typesafe.akka" %% "akka-http-testkit" % "2.4.4" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.4" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)

enablePlugins(AsciidoctorPlugin)
