# HttpRequestTagEngine

#### 1. 总体框架
* http-request-tag-engine  
  * http-request-tag-engine-core
  * http-request-tag-engine-plugin-core
  * http-request-tag-engine-plugin-template
    * http-reqeust-tag-engine-plugin-atom-id
  * http-request-tag-engine-examples
    * BasicExample
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
生成最终的Release包，并且压缩体积
* http-request-tag-engine-plugin-core  
标签引擎的插件核心  
负责指定帮助函数和标准接口
* http-request-tag-engine-plugin-template  
标签引擎的插件模板  
负责管理插件模块
* http-request-tag-engine-plugin-atom-id  
标签引擎的插件（举例）  
插件必须从插件模板继承，必须实现插件模板定义的接口和功能
* http-request-tag-engine-examples  
使用样例库

#### 3. Release包

* 位置  
/dist/http-request-tag-engine-${project.version}-dist.jar
* 包含  
＋**HttpRequestTagEngine**  
＋**config**  
　　｜－core.xml  
　　｜－plugins.xml  
＋**plugins**  
　　｜－http-request-tag-engine-plugin-atom-id-${project.version}-full.jar

#### 4. 调用举例

		String strDistJar = "../dist/http-request-tag-engine-0.0.1-dist.jar";
	String strHttpRequestTagEngine = "HttpRequestTagEngine";
	File fileDistJar = new File(strDistJar);
	URL urlDistJar = fileDistJar.toURI().toURL();
	URLClassLoader classLoader = new URLClassLoader(new URL[]{ urlDistJar });
	Class<?> classHttpRequestTagEngine = classLoader.loadClass(strHttpRequestTagEngine);
	Object engine = classHttpRequestTagEngine.newInstance();
	Method methodSetDpi = classHttpRequestTagEngine.getMethod("setDpi", String.class);
	methodSetDpi.invoke(engine, "GW");
	Method methodLoadPlugin = classHttpRequestTagEngine.getMethod("loadPlugin", String.class);
	methodLoadPlugin.invoke(engine, "atom-id");
	Method methodTagging = classHttpRequestTagEngine.getMethod("tagging", String.class);
	methodTagging.invoke(engine