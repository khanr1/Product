name := """Products"""
organization := "com.products"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.7"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

//webjars Bootstrap
libraryDependencies += "org.webjars" % "bootstrap" % "2.1.1"



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.products.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.products.binders._"
