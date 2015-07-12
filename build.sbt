name := "akka-restdoc"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0-M5",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0-M5",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-M5",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0-M5",
  "commons-io" % "commons-io" % "2.4",
  "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0-M5" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.9" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)

site.settings

site.asciidoctorSupport()
