// enablePlugins(JavaServerAppPackaging)
enablePlugins(JettyPlugin)

name := "bbbapps"

organization := "org.bigbluebutton"

version := "0.0.1"

scalaVersion  := "2.11.7"

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
  "rediscala" at "http://dl.bintray.com/etaty/maven",
  "blindside-repos" at "http://blindside.googlecode.com/svn/repository/"
)

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/dev/repo/maven-repo/releases" )) )

// We want to have our jar files in lib_managed dir.
// This way we'll have the right path when we import
// into eclipse.
retrieveManaged := true

libraryDependencies ++= {
  val akkaVersion  = "2.4.2"
  val springVersion = "4.2.5.RELEASE"
  val minaVersion = "2.0.13"
  val slf4jVersion = "1.7.21"
  val red5Version = "1.0.7-M10"
  Seq(
    "com.typesafe.akka"        %%  "akka-actor"            % akkaVersion,
    "com.typesafe.akka"        %%  "akka-testkit"          % akkaVersion    % "test",
    "com.typesafe.akka"        %%  "akka-slf4j"            % akkaVersion,
    "com.typesafe"              %  "config"                % "1.3.0",

    // Servlet
    "javax.servlet"             %  "servlet-api"           % "2.5",

    // Mina
    "org.apache.mina"           % "mina-core"              % minaVersion,
    "org.apache.mina"           % "mina-integration-jmx"   % minaVersion,
    "org.apache.mina"           % "mina-integration-beans" % minaVersion,

    // Spring
    "org.springframework"       %  "spring-web"            % springVersion,
    "org.springframework"       %  "spring-beans"          % springVersion,
    "org.springframework"       %  "spring-context"        % springVersion,
    "org.springframework"       %  "spring-core"           % springVersion,
    "org.springframework"       %  "spring-webmvc"         % springVersion,
    "org.springframework"       %  "spring-aop"            % springVersion,

    // Red5
    "org.red5"                  %  "red5-server"           % red5Version,
    "org.red5"                  %  "red5-server-common"    % red5Version,

    // Logging
    "ch.qos.logback"            %  "logback-classic"       % "1.1.6"        % "runtime",
    "ch.qos.logback"            %  "logback-core"          % "1.1.6"        % "runtime",
    "org.slf4j"                 %  "log4j-over-slf4j"      % slf4jVersion,
    "org.slf4j"                 %  "jcl-over-slf4j"        % slf4jVersion,
    "org.slf4j"                 %  "jul-to-slf4j"          % slf4jVersion,
    "org.slf4j"                 %  "slf4j-api"             % slf4jVersion,

    // Testing
    "org.easymock"              %  "easymock"              % "2.4",

    //redis
    "redis.clients"             %  "jedis"                 % "2.7.2",
    "org.apache.commons"        %  "commons-pool2"         % "2.3",

    "com.google.code.gson"      %  "gson"                  % "2.5",
    // "org.apache.commons"        %  "commons-lang3"     % "3.2", // might not be needed
    "org.bigbluebutton"         %  "bbb-common-message"    % "0.0.19-SNAPSHOT",
    "io.fastjson"               %  "boon"                  % "0.33"

  )}

//seq(Revolver.settings: _*)
//
//scalariformSettings


//-----------
// Packaging
//
// Reference:
// https://github.com/muuki88/sbt-native-packager-examples/tree/master/akka-server-app
// http://www.scala-sbt.org/sbt-native-packager/index.html
//-----------
//mainClass := Some("org.bigbluebutton.deskshare.Boot")

maintainer in Linux := "Richard Alam <ritzalam@gmail.com>"

packageSummary in Linux := "BigBlueButton Red5 Apps (Akka)"

packageDescription := """BigBlueButton Red5 Apps in Akka."""

val user = "bigbluebutton"

val group = "bigbluebutton"

// user which will execute the application
daemonUser in Linux := user

// group which will execute the application
daemonGroup in Linux := group

