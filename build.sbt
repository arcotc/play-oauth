name := "play-oauth"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += jdbc
libraryDependencies += cache
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
libraryDependencies += "com.google.auth" % "google-auth-library-oauth2-http" % "0.6.0"
libraryDependencies += "com.google.api-client" % "google-api-client" % "1.22.0"
libraryDependencies += "com.google.api.client" % "google-api-client-googleapis" % "1.4.1-beta"
libraryDependencies += "com.google.oauth-client" % "google-oauth-client-java6" % "1.11.0-beta"
libraryDependencies += "com.google.oauth-client" % "google-oauth-client-jetty" % "1.11.0-beta"
libraryDependencies += "com.google.apis" % "google-api-services-oauth2" % "v2-rev125-1.22.0"
