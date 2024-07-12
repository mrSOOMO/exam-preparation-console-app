ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.4.2"
ThisBuild / organization := "com.soomo"
ThisBuild / organizationName := "soomo"

val zioV = "2.0.22"
val zioConfigV = "4.0.1"
val zioConfigTSV = "4.0.1"
val zioConfigMagV = "4.0.1"
val zioHttpV = "0.0.5"
val zioJsonV = "0.6.2"
val zioStreamsV = "2.0.13"
val zioMock = "1.0.0-RC9"
val zioTest = "2.0.21"
val zioTestSbt = "2.0.13"
val zioTestMagnolia = "2.0.22"
val zioLogV = "2.2.2"
val zioLogSlf4jV = "2.2.2"
val akkaV = "2.8.5"
val akkaTestkitV = "2.8.5"
val akkaStreamV = "2.8.5"
val akkaHttpV = "10.5.3"
val akkaTypedV = "2.8.5"
val quillV = "4.8.0"
val quilljdbcV = "4.8.3"
val flywayV = "10.11.0"
val pgsqlV = "42.7.3"
val jbcryptV = "0.4"
val logbackClassicV = "1.5.6"
val bouncyCastleV = "1.70"
val jwtCoreV = "10.0.0"
val jepV = "4.2.0"
val sttpMillV = "3.9.5"

lazy val root = (project in file("."))
  .settings(
    name := "unified_professional_entrance_test",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioV,
      "dev.zio" %% "zio-config" % zioConfigV,
      "dev.zio" %% "zio-config-typesafe" % zioConfigTSV,
      "dev.zio" %% "zio-config-magnolia" % zioConfigMagV,
      "dev.zio" %% "zio-json" % zioJsonV,
      "dev.zio" %% "zio-http" % zioHttpV,
      "dev.zio" %% "zio-streams" % zioStreamsV,
      "dev.zio" %% "zio-mock" % zioMock,
      "dev.zio" %% "zio-test" % zioTest % Test,
      "dev.zio" %% "zio-test-sbt" % zioTestSbt % Test,
      "dev.zio" %% "zio-test-magnolia" % zioTestMagnolia % Test,
      "dev.zio" %% "zio-logging-slf4j" % zioLogSlf4jV,
      "dev.zio" %% "zio-logging" % zioLogV,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaTypedV,
      "com.typesafe.akka" %% "akka-actor" % akkaV,
      "com.typesafe.akka" %% "akka-stream" % akkaStreamV,
      "com.typesafe.akka" %% "akka-http" % akkaHttpV,
      "com.typesafe.akka" %% "akka-testkit" % akkaTestkitV % Test,
      "io.getquill" %% "quill-jdbc-zio" % quilljdbcV,
      "io.getquill" %% "quill-jasync-postgres" % quillV,
      "org.flywaydb" % "flyway-core" % flywayV,
      "org.postgresql" % "postgresql" % pgsqlV,
      "org.mindrot" % "jbcrypt" % jbcryptV,
      "ch.qos.logback" % "logback-classic" % logbackClassicV,
      "org.bouncycastle" % "bcprov-jdk15on" % bouncyCastleV,
      "com.github.jwt-scala" %% "jwt-core" % jwtCoreV,
      "black.ninia" % "jep" % jepV,
      //"com.softwaremill.sttp.client3" %% "core" % sttpMillV,
      "com.softwaremill.sttp.client3" %% "core" % "3.8.11",
      "com.softwaremill.sttp.client3" %% "circe" % "3.8.11",
      "io.circe" %% "circe-core" % "0.14.1",
      "io.circe" %% "circe-generic" % "0.14.1",
      "io.circe" %% "circe-parser" % "0.14.1",
      "org.scalatest" %% "scalatest" % "3.2.18" % "test",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
      "com.typesafe.akka" %% "akka-http-testkit" % "10.5.3" % Test,
),
    Compile / mainClass := Some("ConsoleApp"),
    Compile / resourceDirectory := baseDirectory.value / "src" / "main" / "resources"
)
enablePlugins(JavaAppPackaging)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
