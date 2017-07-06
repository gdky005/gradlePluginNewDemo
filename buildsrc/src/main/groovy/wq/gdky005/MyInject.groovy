package wq.gdky005

import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor

public class MyInject {
    private static ClassPool pool = ClassPool.getDefault()
    private static String injectStr = "System.out.println(\"I Love WangQing\" ); "

    public static void injectDir(String path, String packageName) {
        pool.appendClassPath(path)
        File dir = new File(path)

        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->

                String filePath = file.absolutePath
                //确保当前文件是class文件，并且不是系统自动生成的class文件
                if (filePath.endsWith(".class")
                        && !filePath.contains('R$')
                        && !filePath.contains('R.class')
                        && !filePath.contains("BuildConfig.class")) {

                    println " 文件路径是：" + filePath


                    // 判断当前目录是否是在我们的应用包里面
                    int index = filePath.indexOf(packageName.replace(".", File.separator))
                    boolean isMyPackage = index != -1;

                    println "包名是：" + packageName + ", index：" + index + ", 文件路径是：" + filePath
                    println "是否包含 app：" + isMyPackage

                    if (isMyPackage) {
                        int end = filePath.length() - 6 // .class = 6
                        String className = filePath.substring(index, end)
                                .replace('\\', '.').replace('/', '.')


                        println "包含 className：" + className
                        //开始修改class文件
                        CtClass c = pool.getCtClass(className)

                        if (c.isFrozen()) {
                            c.defrost()
                        }

                        CtConstructor[] cts = c.getDeclaredConstructors()
                        if (cts == null || cts.length == 0) {
                            println "手动创建一个构造函数：" + className

                            //手动创建一个构造函数
                            CtConstructor constructor = new CtConstructor(new CtClass[0], c)
                            constructor.insertBeforeBody(injectStr)
                            c.addConstructor(constructor)
                        } else {
                            println "直接添加字符串："
                            cts[0].insertBeforeBody(injectStr)
                        }
                        c.writeFile(path)
                        c.detach()
                    }
                }
            }
        }

    }

}