name := "Elevator"

version := "1.0"

scalaVersion := "2.12.3"


libraryDependencies ++= Seq(
  "com.lihaoyi" %% "utest" % "0.5.3" % "test"
)

testFrameworks += new TestFramework("utest.runner.Framework")