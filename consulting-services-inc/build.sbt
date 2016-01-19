name := """consulting-services-inc"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaCore,
  cache,
  jdbc,
  ws,
  evolutions,
  "org.webjars" % "webjars-play" % "2.4.0-1",
  "org.webjars" % "bootstrap" % "3.3.4",
  "com.adrianhurt" % "play-bootstrap3_2.11" % "0.4.4-P24",
  filters
)

resolvers ++= Seq(
	"Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
	"Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/",
	"Apache" at "http://repol.maven.org/maven2/"
)


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
