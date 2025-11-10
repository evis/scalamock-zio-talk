ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "dev.zio" %% "zio" % "2.1.21",
    "dev.zio" %% "zio-macros" % "2.1.21",
    "dev.zio" %% "zio-test" % "2.1.21" % Test,
    "dev.zio" %% "zio-test-sbt" % "2.1.21" % Test
  )
)

lazy val macros = (project in file("macros"))
  .settings(
    scalaVersion := "3.3.6",
    name := "macros",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.21",
      "org.scalamock" %% "scalamock" % "7.5.0"
    )
  )

lazy val scala2 = (project in file("scala2"))
  .settings(
    scalaVersion := "2.13.16",
    name := "scala2",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scalamock" %% "scalamock-zio" % "7.5.0" % Test,
      "dev.zio" %% "zio-mock" % "1.0.0-RC12" % Test
    ),
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-language:experimental.macros"
    )
  )

lazy val scala3 = (project in file("scala3"))
  .dependsOn(macros)
  .settings(
    scalaVersion := "3.3.6",
    name := "scala3",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scalamock" %% "scalamock-zio" % "7.5.0" % Test
    )
  )

lazy val root = (project in file("."))
  .aggregate(macros, scala2, scala3)
  .settings(
    name := "scalamock-zio-talk"
  )
