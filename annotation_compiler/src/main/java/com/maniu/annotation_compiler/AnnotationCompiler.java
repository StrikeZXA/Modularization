package com.maniu.annotation_compiler;

import com.google.auto.service.AutoService;
import com.maniu.annotation.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * 替我们生成这些工具类，同时写代码
 * @SupportedAnnotationTypes({"com..BindPath.class.getCanonicalName()"})
 * @SupportedSourceVersion(SourceVersion.RELEASE_8)
 */
@AutoService(Processor.class) //注册注解处理器
public class AnnotationCompiler extends AbstractProcessor {

    /**
     * 生成文件的对象
     */
    Filer filer;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //获取到当前模块用到的BindPath节点
        //TypeElement 类节点
        //ExecutableElement 方法节点
        //VariableElement 成员变量节点
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        Map<String,String> map = new HashMap<>();
        for(Element element:elements){
            TypeElement typeElement = (TypeElement) element;
            //获取到Activity上面的BindPath的注解
            BindPath annotation = typeElement.getAnnotation(BindPath.class);
            //获取注解BindPath的值
            String key = annotation.value();
            //获取到包名加类名
            Name activityName = typeElement.getQualifiedName();
            map.put(key,activityName+".class");
        }

        if(map.size()>0){
            Writer writer = null;
            String activityName =  "ActivityUtil"+System.currentTimeMillis();
            //生成一个Java文件
            try {
                JavaFileObject sourceFile = filer.createSourceFile("com.maniu.util."+activityName);
                writer = sourceFile.openWriter();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("package com.maniu.util;\n");
                stringBuffer.append("import com.maniu.arouter.ARouter;\n" +
                        "import com.maniu.arouter.IRouter;\n" +
                        "\n" +
                        "public class "+activityName+" implements IRouter {\n\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n" );
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    String className = map.get(key);
                    stringBuffer.append("      ARouter.getInstance().addActivity(\""+key+"\","+
                            className+");");
                }
                stringBuffer.append("\n    }\n}");
                writer.write(stringBuffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 声明注解处理器要处理的注解
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        //获取包名+类名
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 声明处理注解起床支持的java源版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

}
