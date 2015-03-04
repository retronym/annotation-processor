## Interop Test: Scala class files in Java annotation processors

### Contents

  - A Java annotation processor [sample.AnnotationProcessor](proc/src/main/java/sample/AnnotationProcessor.java), that
    processes files annotations with `@sample.Annot`. This processor:
      - prints the members of the matched class
      - dynamically adds the class files assocuated with [scala.ScalaClass](code0/src/main/scala/ScalaClass.scala)
        to the next annotation processing round. This should cause the annotation processing tool to call this
        processor again with the new class, as it is suitably annotated.

This was a (failed) attempt to reproduce the error reported here:

  http://scala-programming-language.1934581.n4.nabble.com/NPE-during-annotation-processing-Maven-source-jar-target-td4639229.html

### Running

```
% sbt clean code0/compile code1/compile
...
[info] Compiling 1 Java source to /Users/jason/code/annotation-processor/code/target/scala-2.11/classes...
[warn] Round 1:
[warn] 	input files: {sample.JavaClass}
[warn] 	annotations: [sample.Annot]
[warn] 	last round: false
[info] AnnotationProcessor found sample.JavaClass
[info]   JavaClass()
[info] Annotation processor is adding sample.ScalaClass$ as a generated class for the next processing round
[warn] warning: Supported source version 'RELEASE_6' from annotation processor 'sample.AnnotationProcessor' less than -source '1.7'
[warn] Round 2:
[warn] 	input files: {sample.ScalaClass$}
[warn] 	annotations: [sample.Annot]
[warn] 	last round: false
[info] AnnotationProcessor found sample.ScalaClass$
[info]   MODULE$
[info]   _locale
[info]   <clinit>()
[info]   _locale()
[info]   defaultLocale()
[info]   ScalaClass$()
[warn] Round 3:
[warn] 	input files: {}
[warn] 	annotations: []
[warn] 	last round: true
[warn] 1 warning
[warn] Could not determine source for class sample.ScalaClass$
```
