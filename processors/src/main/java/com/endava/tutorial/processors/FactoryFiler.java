package com.endava.tutorial.processors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FactoryFiler {

    private Filer filer;
    private Map<String, List<FactoryElementInfo>> input;
    private Messager messager;

    public FactoryFiler(Filer filer, Map<String, List<FactoryElementInfo>> input, Messager messager) {
        this.filer = filer;
        this.input = input;
        this.messager = messager;
    }

    public void generate() {
        for (Map.Entry<String, List<FactoryElementInfo>> entry : input.entrySet()) {
            if (null == entry.getValue() || entry.getValue().isEmpty()) {
                continue;
            }
            Class parentClazz = new Class(entry.getKey());
            JavaFileObject builderClass = null;
            BufferedWriter bufferedWriter = null;
            try {
                String factoryName = parentClazz.getClazzName() + "Factory";
                builderClass = filer.createSourceFile(factoryName);
                bufferedWriter = new BufferedWriter(builderClass.openWriter());
                bufferedWriter.append("package ");
                bufferedWriter.append(parentClazz.getPackagz());
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                bufferedWriter.append("public class ");
                bufferedWriter.append(factoryName);
                bufferedWriter.append(" {\n\n");
                bufferedWriter.append(String.format("\tprivate %s() {}", factoryName));
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                bufferedWriter.append(String.format("\tpublic static %s create(String key) {", entry.getKey()));
                bufferedWriter.newLine();
                bufferedWriter.append("\t\tswitch(key) {");
                bufferedWriter.newLine();

                for (FactoryElementInfo factoryElement : entry.getValue()) {
                    bufferedWriter.append(String.format("\t\t\tcase \"%s\": ", factoryElement.getTagValue()));
                    bufferedWriter.newLine();
                    bufferedWriter.append(String.format("\t\t\t\treturn new %s();", factoryElement.getClassElement().getQualifiedName()));
                    bufferedWriter.newLine();
                }

                bufferedWriter.append("\t\t\tdefault: throw new UnsupportedOperationException();");
                bufferedWriter.newLine();
                bufferedWriter.append("\t\t}\n\t}\n}");

                bufferedWriter.close();
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
            }
        }
    }

    private class Class {
        private String packagz;
        private String clazzName;

        public Class(String input) {
            extractInfo(input);
        }

        private void extractInfo(String input) {
            this.clazzName = input.substring(input.lastIndexOf('.') + 1);
            this.packagz = input.substring(0, input.lastIndexOf('.')) + ';';
        }

        public String getPackagz() {
            return packagz;
        }

        public String getClazzName() {
            return clazzName;
        }
    }
}
