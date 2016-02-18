# HttpRequestTagEngine

#### 1. 总体框架
* http-request-tag-engine  
  * http-request-tag-engine-core
  * http-request-tag-engine-plugin-core
  * http-request-tag-engine-plugin-template
    * http-reqeust-tag-engine-plugin-atom-id
  * http-request-tag-engine-publisher
    * config
      * core.xml
      * plugins.xml
    * engine
      * http-request-tag-engine-core-0.0.1-full.jar
    * plugins
      * http-request-tag-engine-plugin-atom-id-0.0.1-full.jar
  * dist
    * http-request-tag-engine-0.0.1-dist.jar
  * repository
    * com.jstelecom.it.http-request-tag-engine
      * http-request-tag-engine-plugin-core
        * 0.0.1
          * http-request-tag-engine-plugin-core-0.0.1-full.jar

##### 2. 模块说明

* http-request-tag-engine-core  
标签引擎的核心模块  
负责加载配置、动态加载插件、调用插件
* http-request-tag-engine-plugin-core  
标签引擎的插件核心  
负责指定帮助函数和标准接口
* http-request-tag-engine-plugin-template  
标签引擎的插件模板  
负责管理插件模块
* http-request-tag-engine-plugin-atom-id  
标签引擎的插件（举例）  
插件必须从插件模板继承，必须实现插件模板定义的接口和功能
* http-request-tag-engine-publisher  
标签引擎的发布模块  
收集各个模块的jar包，并生成Release包  
将多个jar包合并为一个，并且压缩了体积

#### 3. Release包

* 位置  
/dist/http-request-tag-engine-${project.version}-dist.jar
* 包含  
＋**config**  
　　｜－core.xml  
　　｜－plugins.xml  
＋**engine**  
　　｜－http-request-tag-engine-${project.version}-full.jar  
＋**plugins**  
　　｜－http-request-tag-engine-plugin-atom-id-${project.version}-full.jar

#### 4. 调用举例

* 反射调用  
推荐使用JarClassLoader进行加载  
    *<dependency>  
    　<groupId>org.xeustechnologies</groupId>  
    　<artifactId>jcl-core</artifactId>  
    　<version>2.7</version>  
    </dependency>*  
举例  
    **JarClassLoader jcl = new JarClassLoader();  
    jcl.add(url);  
    Class<?> clazz = Class.forName(entry, true, jcl);**

* 嵌入调用