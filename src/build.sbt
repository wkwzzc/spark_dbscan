name := "spark_dbscan"

organization := "org.alitouka"

version := "0.0.5"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.2.0" % "provided"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.2.0-SNAP10" % "test"

libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"

libraryDependencies += "com.github.scopt" % "scopt_2.11" % "3.7.0"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

resolvers += Resolver.sonatypeRepo("public")

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
