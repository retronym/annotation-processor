import java.io.FileNotFoundException

val proc = project

val code0 = project dependsOn proc

val code = project dependsOn proc settings(
  javacOptions ++= List("-processor", "sample.AnnotationProcessor", "-proc:only", "-XprintRounds"),
  libraryDependencies += "org.bsc.maven" % "maven-processor-plugin" % "2.2.4",
  unmanagedJars in Compile += (
//    Attributed.blank(file("/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/lib/tools.jar")),
    Attributed.blank(file("/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/lib/tools.jar"))
    // /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home
  ),
  fork in run := true
)

scalaVersion in ThisBuild := "2.11.4"
