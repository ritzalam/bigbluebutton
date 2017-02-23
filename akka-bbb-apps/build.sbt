enablePlugins(JavaServerAppPackaging)

name := "bbb-apps-akka"

organization := "org.bigbluebutton"

version := "0.0.2"

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "rediscala" at "http://dl.bintray.com/etaty/maven"
)

resolvers += Resolver.sonatypeRepo("releases")

publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/dev/repo/maven-repo/releases")))

// We want to have our jar files in lib_managed dir.
// This way we'll have the right path when we import
// into eclipse.
retrieveManaged := true

testOptions in Test += Tests.Argument(TestFrameworks.Specs2, "html", "console", "junitxml")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/scalatest-reports")

val akkaVersion = "2.4.17"
val scalaTestV = "3.0.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
libraryDependencies += "com.softwaremill.quicklens" %% "quicklens" % "1.4.6"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.13" % "runtime"
libraryDependencies += "com.etaty.rediscala" %% "rediscala" % "1.4.0"
libraryDependencies += "commons-codec" % "commons-codec" % "1.10"
libraryDependencies += "joda-time" % "joda-time" % "2.3"

libraryDependencies += "redis.clients" % "jedis" % "2.7.2"
libraryDependencies += "org.apache.commons" % "commons-pool2" % "2.3"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.5"

libraryDependencies += "com.google.code.gson" % "gson" % "2.5"
libraryDependencies += "io.spray" %% "spray-json" % "1.3.2"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.2"

libraryDependencies += "org.pegdown" % "pegdown" % "1.4.0" % "test"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "org.bigbluebutton" % "bbb-common-message" % "0.0.19-SNAPSHOT"


seq(Revolver.settings: _*)

scalariformSettings


//-----------
// Packaging
//
// Reference:
// https://github.com/muuki88/sbt-native-packager-examples/tree/master/akka-server-app
// http://www.scala-sbt.org/sbt-native-packager/index.html
//-----------
mainClass := Some("org.bigbluebutton.Boot")

maintainer in Linux := "Richard Alam <ritzalam@gmail.com>"

packageSummary in Linux := "BigBlueButton Apps (Akka)"

packageDescription := """BigBlueButton Core Apps in Akka."""

val user = "bigbluebutton"

val group = "bigbluebutton"

// user which will execute the application
daemonUser in Linux := user

// group which will execute the application
daemonGroup in Linux := group

mappings in Universal <+= (packageBin in Compile, sourceDirectory) map { (_, src) =>
  // Move the application.conf so the user can override settings here
  val appConf = src / "main" / "resources" / "application.conf"
  appConf -> "conf/application.conf"
}

mappings in Universal <+= (packageBin in Compile, sourceDirectory) map { (_, src) =>
  // Move logback.xml so the user can override settings here
  val logConf = src / "main" / "resources" / "logback.xml"
  logConf -> "conf/logback.xml"
}

debianPackageDependencies in Debian ++= Seq("java8-runtime-headless", "bash")
