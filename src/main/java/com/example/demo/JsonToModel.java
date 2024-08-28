package com.example.demo;

import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import com.sun.codemodel.*;

public class JsonToModel {

    public void convertJsonToJavaClass(URL inputJsonUrl, File outputJavaClassDirectory, String packageName, String javaClassName)
            throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(jcodeModel, javaClassName, packageName, inputJsonUrl);

        jcodeModel.build(outputJavaClassDirectory);
    }

    public static void main(String[] args) throws IOException {
    	JsonToModel obj = new JsonToModel();
        obj.convertJsonToJavaClass(new File("D:\\PSRE\\demo\\src\\main\\resources\\sample.json").toURI().toURL(),new File("D:\\PSRE\\demo\\src\\main\\java"),"com.example.demo","Book");
    }

}
