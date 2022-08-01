package com.xingkong.spingboot.reflection;

/**
 * * @className: Test6
 * * @description: 类加载器
 * * @author: fan xiaoping
 * * @date: 2022/8/1 0001 22:49
 **/
public class Test6 {

    public static void main(String[] args) throws ClassNotFoundException {
        //获取系统类的加载器 AppClassLoader
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        //获取系统类加载器的父类加载器 ---> 扩展类加载器 ExtClassLoader
        ClassLoader parent = systemClassLoader.getParent();
        System.out.println(parent);
        //获取扩展类加载器的父类加载器 ---> (根加载器 C/C++)
        ClassLoader parent1 = parent.getParent();
        System.out.println(parent1);

        //测试当前类是哪个加载器加载的
        ClassLoader classLoader = Class.forName("com.xingkong.spingboot.reflection.Test6").getClassLoader();
        System.out.println(classLoader);
        //测试JDK内置类是谁加载的
        classLoader = Class.forName("java.lang.Object").getClassLoader();
        System.out.println(classLoader);
        //如何获得系统类加载器可以加载的路径
        System.out.println(System.getProperty("java.class.path"));
        /**
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\charsets.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\deploy.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\access-bridge-64.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\cldrdata.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\dnsns.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\jaccess.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\jfxrt.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\localedata.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\nashorn.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\sunec.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\sunjce_provider.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\sunmscapi.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\sunpkcs11.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\zipfs.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\javaws.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\jce.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\jfr.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\jfxswt.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\jsse.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\management-agent.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\plugin.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\resources.jar;
         * C:\Program Files\Java\jdk1.8.0_144\jre\lib\rt.jar;
         * E:\workspace\fanxiaoping\basics\target\classes;
         * E:\m2\org\springframework\boot\spring-boot-starter\2.0.4.RELEASE\spring-boot-starter-2.0.4.RELEASE.jar;
         * E:\m2\org\springframework\boot\spring-boot\2.0.4.RELEASE\spring-boot-2.0.4.RELEASE.jar;
         * E:\m2\org\springframework\boot\spring-boot-autoconfigure\2.0.4.RELEASE\spring-boot-autoconfigure-2.0.4.RELEASE.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-logging\2.0.4.RELEASE\spring-boot-starter-logging-2.0.4.RELEASE.jar;
         * E:\m2\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;
         * E:\m2\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;
         * E:\m2\org\apache\logging\log4j\log4j-to-slf4j\2.10.0\log4j-to-slf4j-2.10.0.jar;
         * E:\m2\org\apache\logging\log4j\log4j-api\2.10.0\log4j-api-2.10.0.jar;
         * E:\m2\org\slf4j\jul-to-slf4j\1.7.25\jul-to-slf4j-1.7.25.jar;
         * E:\m2\javax\annotation\javax.annotation-api\1.3.2\javax.annotation-api-1.3.2.jar;
         * E:\m2\org\springframework\spring-core\5.0.8.RELEASE\spring-core-5.0.8.RELEASE.jar;
         * E:\m2\org\springframework\spring-jcl\5.0.8.RELEASE\spring-jcl-5.0.8.RELEASE.jar;
         * E:\m2\org\yaml\snakeyaml\1.19\snakeyaml-1.19.jar;
         * E:\m2\com\rabbitmq\amqp-client\5.1.2\amqp-client-5.1.2.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-amqp\2.0.4.RELEASE\spring-boot-starter-amqp-2.0.4.RELEASE.jar;
         * E:\m2\org\springframework\spring-messaging\5.0.8.RELEASE\spring-messaging-5.0.8.RELEASE.jar;
         * E:\m2\org\springframework\spring-beans\5.0.8.RELEASE\spring-beans-5.0.8.RELEASE.jar;
         * E:\m2\org\springframework\amqp\spring-rabbit\2.0.5.RELEASE\spring-rabbit-2.0.5.RELEASE.jar;
         * E:\m2\org\springframework\amqp\spring-amqp\2.0.5.RELEASE\spring-amqp-2.0.5.RELEASE.jar;
         * E:\m2\com\alipay\sdk\alipay-sdk-java\3.7.26.ALL\alipay-sdk-java-3.7.26.ALL.jar;
         * E:\m2\commons-logging\commons-logging\1.1.1\commons-logging-1.1.1.jar;
         * E:\m2\cn\hutool\hutool-all\5.3.2\hutool-all-5.3.2.jar;
         * E:\m2\log4j\log4j\1.2.17\log4j-1.2.17.jar;
         * E:\m2\org\slf4j\slf4j-api\1.8.0-beta0\slf4j-api-1.8.0-beta0.jar;
         * E:\m2\org\slf4j\slf4j-simple\1.8.0-beta0\slf4j-simple-1.8.0-beta0.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-jersey\2.0.4.RELEASE\spring-boot-starter-jersey-2.0.4.RELEASE.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-json\2.0.4.RELEASE\spring-boot-starter-json-2.0.4.RELEASE.jar;
         * E:\m2\com\fasterxml\jackson\core\jackson-databind\2.9.6\jackson-databind-2.9.6.jar;
         * E:\m2\com\fasterxml\jackson\core\jackson-core\2.9.6\jackson-core-2.9.6.jar;
         * E:\m2\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.9.6\jackson-datatype-jdk8-2.9.6.jar;
         * E:\m2\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.9.6\jackson-datatype-jsr310-2.9.6.jar;
         * E:\m2\com\fasterxml\jackson\module\jackson-module-parameter-names\2.9.6\jackson-module-parameter-names-2.9.6.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-tomcat\2.0.4.RELEASE\spring-boot-starter-tomcat-2.0.4.RELEASE.jar;
         * E:\m2\org\apache\tomcat\embed\tomcat-embed-core\8.5.32\tomcat-embed-core-8.5.32.jar;
         * E:\m2\org\apache\tomcat\embed\tomcat-embed-el\8.5.32\tomcat-embed-el-8.5.32.jar;
         * E:\m2\org\apache\tomcat\embed\tomcat-embed-websocket\8.5.32\tomcat-embed-websocket-8.5.32.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-validation\2.0.4.RELEASE\spring-boot-starter-validation-2.0.4.RELEASE.jar;
         * E:\m2\org\springframework\spring-web\5.0.8.RELEASE\spring-web-5.0.8.RELEASE.jar;
         * E:\m2\org\glassfish\jersey\core\jersey-server\2.26\jersey-server-2.26.jar;
         * E:\m2\org\glassfish\jersey\core\jersey-common\2.26\jersey-common-2.26.jar;
         * E:\m2\org\glassfish\hk2\osgi-resource-locator\1.0.1\osgi-resource-locator-1.0.1.jar;
         * E:\m2\org\glassfish\jersey\core\jersey-client\2.26\jersey-client-2.26.jar;
         * E:\m2\javax\ws\rs\javax.ws.rs-api\2.1\javax.ws.rs-api-2.1.jar;
         * E:\m2\org\glassfish\jersey\media\jersey-media-jaxb\2.26\jersey-media-jaxb-2.26.jar;
         * E:\m2\javax\validation\validation-api\2.0.1.Final\validation-api-2.0.1.Final.jar;
         * E:\m2\org\glassfish\jersey\containers\jersey-container-servlet-core\2.26\jersey-container-servlet-core-2.26.jar;
         * E:\m2\org\glassfish\jersey\containers\jersey-container-servlet\2.26\jersey-container-servlet-2.26.jar;
         * E:\m2\org\glassfish\jersey\ext\jersey-bean-validation\2.26\jersey-bean-validation-2.26.jar;
         * E:\m2\org\glassfish\jersey\ext\jersey-spring4\2.26\jersey-spring4-2.26.jar;
         * E:\m2\org\glassfish\jersey\inject\jersey-hk2\2.26\jersey-hk2-2.26.jar;
         * E:\m2\org\glassfish\hk2\hk2-locator\2.5.0-b42\hk2-locator-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\external\aopalliance-repackaged\2.5.0-b42\aopalliance-repackaged-2.5.0-b42.jar;
         * E:\m2\org\javassist\javassist\3.22.0-CR2\javassist-3.22.0-CR2.jar;
         * E:\m2\org\glassfish\hk2\hk2\2.5.0-b42\hk2-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\hk2-utils\2.5.0-b42\hk2-utils-2.5.0-b42.jar;
         * E:\m2\javax\inject\javax.inject\1\javax.inject-1.jar;
         * E:\m2\org\glassfish\hk2\hk2-api\2.5.0-b42\hk2-api-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\config-types\2.5.0-b42\config-types-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\hk2-core\2.5.0-b42\hk2-core-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\hk2-config\2.5.0-b42\hk2-config-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\hk2-runlevel\2.5.0-b42\hk2-runlevel-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\class-model\2.5.0-b42\class-model-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\external\asm-all-repackaged\2.5.0-b42\asm-all-repackaged-2.5.0-b42.jar;
         * E:\m2\org\glassfish\hk2\spring-bridge\2.5.0-b42\spring-bridge-2.5.0-b42.jar;
         * E:\m2\org\springframework\spring-aop\5.0.8.RELEASE\spring-aop-5.0.8.RELEASE.jar;
         * E:\m2\org\glassfish\jersey\media\jersey-media-json-jackson\2.26\jersey-media-json-jackson-2.26.jar;
         * E:\m2\org\glassfish\jersey\ext\jersey-entity-filtering\2.26\jersey-entity-filtering-2.26.jar;
         * E:\m2\com\fasterxml\jackson\core\jackson-annotations\2.9.0\jackson-annotations-2.9.0.jar;
         * E:\m2\com\fasterxml\jackson\module\jackson-module-jaxb-annotations\2.9.6\jackson-module-jaxb-annotations-2.9.6.jar;
         * E:\m2\org\apache\httpcomponents\httpclient\4.5.13\httpclient-4.5.13.jar;
         * E:\m2\org\apache\httpcomponents\httpcore\4.4.10\httpcore-4.4.10.jar;
         * E:\m2\commons-codec\commons-codec\1.11\commons-codec-1.11.jar;
         * E:\m2\commons-collections\commons-collections\3.2.2\commons-collections-3.2.2.jar;
         * E:\m2\org\apache\poi\poi\4.1.0\poi-4.1.0.jar;
         * E:\m2\org\apache\commons\commons-collections4\4.3\commons-collections4-4.3.jar;
         * E:\m2\org\apache\commons\commons-math3\3.6.1\commons-math3-3.6.1.jar;
         * E:\m2\org\apache\poi\poi-ooxml\4.1.0\poi-ooxml-4.1.0.jar;
         * E:\m2\org\apache\commons\commons-compress\1.18\commons-compress-1.18.jar;
         * E:\m2\com\github\virtuald\curvesapi\1.06\curvesapi-1.06.jar;
         * E:\m2\org\apache\xmlbeans\xmlbeans\3.1.0\xmlbeans-3.1.0.jar;
         * E:\m2\org\apache\poi\poi-ooxml-schemas\4.1.0\poi-ooxml-schemas-4.1.0.jar;
         * E:\m2\mysql\mysql-connector-java\5.1.46\mysql-connector-java-5.1.46.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-jdbc\2.0.4.RELEASE\spring-boot-starter-jdbc-2.0.4.RELEASE.jar;
         * E:\m2\com\zaxxer\HikariCP\2.7.9\HikariCP-2.7.9.jar;
         * E:\m2\org\springframework\spring-jdbc\5.0.8.RELEASE\spring-jdbc-5.0.8.RELEASE.jar;
         * E:\m2\org\mybatis\spring\boot\mybatis-spring-boot-starter\1.3.2\mybatis-spring-boot-starter-1.3.2.jar;
         * E:\m2\org\mybatis\spring\boot\mybatis-spring-boot-autoconfigure\1.3.2\mybatis-spring-boot-autoconfigure-1.3.2.jar;
         * E:\m2\org\mybatis\mybatis\3.4.6\mybatis-3.4.6.jar;
         * E:\m2\org\mybatis\mybatis-spring\1.3.2\mybatis-spring-1.3.2.jar;
         * E:\m2\com\alibaba\fastjson\1.2.58\fastjson-1.2.58.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-web\2.0.4.RELEASE\spring-boot-starter-web-2.0.4.RELEASE.jar;
         * E:\m2\org\hibernate\validator\hibernate-validator\6.0.11.Final\hibernate-validator-6.0.11.Final.jar;
         * E:\m2\org\jboss\logging\jboss-logging\3.3.2.Final\jboss-logging-3.3.2.Final.jar;
         * E:\m2\com\fasterxml\classmate\1.3.4\classmate-1.3.4.jar;
         * E:\m2\org\openjfx\javafx.base\11.0.0-SNAPSHOT\javafx.base-11.0.0-20180702.224858-3.jar;
         * E:\m2\org\openjfx\javafx.base\11.0.0-SNAPSHOT\javafx.base-11.0.0-20180702.223831-2-linux.jar;
         * E:\m2\org\openjfx\javafx.base\11.0.0-SNAPSHOT\javafx.base-11.0.0-20180702.224858-3-mac.jar;
         * E:\m2\org\openjfx\javafx.base\11.0.0-SNAPSHOT\javafx.base-11.0.0-20180629.175051-1-win.jar;
         * E:\m2\org\springframework\spring-webmvc\5.0.8.RELEASE\spring-webmvc-5.0.8.RELEASE.jar;
         * E:\m2\org\springframework\spring-expression\5.0.8.RELEASE\spring-expression-5.0.8.RELEASE.jar;
         * E:\m2\org\apache\commons\commons-lang3\3.9\commons-lang3-3.9.jar;
         * E:\m2\com\alibaba\easyexcel\2.0.5\easyexcel-2.0.5.jar;
         * E:\m2\cglib\cglib\3.1\cglib-3.1.jar;
         * E:\m2\org\ow2\asm\asm\4.2\asm-4.2.jar;
         * E:\m2\org\ehcache\ehcache\3.5.2\ehcache-3.5.2.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-data-redis\2.0.4.RELEASE\spring-boot-starter-data-redis-2.0.4.RELEASE.jar;
         * E:\m2\org\springframework\data\spring-data-redis\2.0.9.RELEASE\spring-data-redis-2.0.9.RELEASE.jar;
         * E:\m2\org\springframework\data\spring-data-keyvalue\2.0.9.RELEASE\spring-data-keyvalue-2.0.9.RELEASE.jar;
         * E:\m2\org\springframework\spring-oxm\5.0.8.RELEASE\spring-oxm-5.0.8.RELEASE.jar;
         * E:\m2\io\lettuce\lettuce-core\5.0.4.RELEASE\lettuce-core-5.0.4.RELEASE.jar;
         * E:\m2\io\projectreactor\reactor-core\3.1.8.RELEASE\reactor-core-3.1.8.RELEASE.jar;
         * E:\m2\org\reactivestreams\reactive-streams\1.0.2\reactive-streams-1.0.2.jar;
         * E:\m2\io\netty\netty-common\4.1.27.Final\netty-common-4.1.27.Final.jar;
         * E:\m2\io\netty\netty-transport\4.1.27.Final\netty-transport-4.1.27.Final.jar;
         * E:\m2\io\netty\netty-buffer\4.1.27.Final\netty-buffer-4.1.27.Final.jar;
         * E:\m2\io\netty\netty-resolver\4.1.27.Final\netty-resolver-4.1.27.Final.jar;
         * E:\m2\io\netty\netty-handler\4.1.27.Final\netty-handler-4.1.27.Final.jar;
         * E:\m2\io\netty\netty-codec\4.1.27.Final\netty-codec-4.1.27.Final.jar;
         * E:\m2\redis\clients\jedis\2.9.0\jedis-2.9.0.jar;
         * E:\m2\org\apache\commons\commons-pool2\2.5.0\commons-pool2-2.5.0.jar;
         * E:\m2\org\springframework\kafka\spring-kafka\2.1.7.RELEASE\spring-kafka-2.1.7.RELEASE.jar;
         * E:\m2\org\springframework\spring-context\5.0.8.RELEASE\spring-context-5.0.8.RELEASE.jar;
         * E:\m2\org\springframework\spring-tx\5.0.8.RELEASE\spring-tx-5.0.8.RELEASE.jar;
         * E:\m2\org\springframework\retry\spring-retry\1.2.2.RELEASE\spring-retry-1.2.2.RELEASE.jar;
         * E:\m2\org\apache\kafka\kafka-clients\2.0.0\kafka-clients-2.0.0.jar;
         * E:\m2\org\lz4\lz4-java\1.4.1\lz4-java-1.4.1.jar;
         * E:\m2\org\xerial\snappy\snappy-java\1.1.7.1\snappy-java-1.1.7.1.jar;
         * E:\m2\org\gavaghan\geodesy\1.1.3\geodesy-1.1.3.jar;
         * E:\m2\org\zeromq\jeromq\0.5.1\jeromq-0.5.1.jar;
         * E:\m2\eu\neilalexander\jnacl\1.0.0\jnacl-1.0.0.jar;
         * E:\m2\org\aspectj\aspectjrt\1.9.5\aspectjrt-1.9.5.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-data-mongodb\2.0.4.RELEASE\spring-boot-starter-data-mongodb-2.0.4.RELEASE.jar;
         * E:\m2\org\mongodb\mongodb-driver\3.6.4\mongodb-driver-3.6.4.jar;
         * E:\m2\org\mongodb\bson\3.6.4\bson-3.6.4.jar;
         * E:\m2\org\mongodb\mongodb-driver-core\3.6.4\mongodb-driver-core-3.6.4.jar;
         * E:\m2\org\springframework\data\spring-data-mongodb\2.0.9.RELEASE\spring-data-mongodb-2.0.9.RELEASE.jar;
         * E:\m2\org\springframework\data\spring-data-commons\2.0.9.RELEASE\spring-data-commons-2.0.9.RELEASE.jar;
         * E:\m2\org\springframework\boot\spring-boot-starter-mail\2.0.4.RELEASE\spring-boot-starter-mail-2.0.4.RELEASE.jar;
         * E:\m2\org\springframework\spring-context-support\5.0.8.RELEASE\spring-context-support-5.0.8.RELEASE.jar;
         * E:\m2\com\sun\mail\javax.mail\1.6.1\javax.mail-1.6.1.jar;
         * E:\m2\javax\activation\activation\1.1\activation-1.1.jar;
         * E:\m2\org\springframework\boot\spring-boot-configuration-processor\2.0.4.RELEASE\spring-boot-configuration-processor-2.0.4.RELEASE.jar;
         * D:\IntelliJ IDEA 2020.3.4\lib\idea_rt.jar
         */
    }
}
