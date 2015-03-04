package sample;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*
        ;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes(
  {"sample.Annot"}
)
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class AnnotationProcessor extends AbstractProcessor {
    private static boolean created = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            for(Element e : elements){
                System.out.println("AnnotationProcessor found " + e.toString());
                List<? extends Element> enclosedElements = e.getEnclosedElements();
                for (Element enclosedElement : enclosedElements) {
                    System.out.println("  " + enclosedElement);
                }
            }

        }
        if (!created) {
            createClass();
            created = true;
        }
        return true;
    }

    private void createClass() {
//        createClass("sample.ScalaClass", "code0/target/scala-2.11/classes/sample/ScalaClass.class");
        createClass("sample.ScalaClass$", "code0/target/scala-2.11/classes/sample/ScalaClass$.class");
    }

    private void createClass(String name, String classFilePath) {
        try {
            System.out.println("Annotation processor is adding " + name + " as a generated class for the next processing round");
            JavaFileObject classFile = processingEnv.getFiler().createClassFile(name);
            FileInputStream fileInputStream = new FileInputStream(classFilePath);
            OutputStream os = classFile.openOutputStream();
            try {
                copy(fileInputStream, os);
            } finally {
                fileInputStream.close();
                os.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long copy(InputStream source, OutputStream sink)
            throws IOException {
        long nread = 0L;
        byte[] buf = new byte[1024];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nread += n;
        }
        return nread;
    }
}
