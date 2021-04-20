# 一. 引入已经配置好的源码
> 已经配置好的码云仓库，下载后切换到对应分支即可直接运行
> 
> </br>jdk11国内镜像：https://mirrors.tuna.tsinghua.edu.cn/AdoptOpenJDK/11/jdk/x64/linux/
> 
> </br>spring版本：5.3.4
> </br>spring码云地址： https://gitee.com/tangjingshan/Spring-Framework
> 
> </br>springboot版本：2.4.3
> </br>springboot码云地址：https://gitee.com/tangjingshan/spring-boot> 
> 
>   </br>切换无更改分支：git checkout nicky5.3.4（只改了配置，没有改动源码）
> 
>   </br>切换学习分支：git checkout  nickyStudy5.3.4 （此分支会加上学习时的注释，偶尔也会改动源码）
>   

 **先build springframework源码,再build  springboot源码**
1. 下载好jdk11到指定目录
2.  获取两个项目的码云代码

```js
git checkout nicky5.3.4
```
3. idea导入两个项目（导入时，如自动build，先结束掉）
4. 更改 idea的gradle的jdk版本
![image.png](https://img-blog.csdnimg.cn/img_convert/bde2f5a34372e186e1bb187f5185a6a9.png)

5. 更改 idea的project的jdk版本，配置使用【gradle-wrapper.properties】的配置去构建项目，否则会有版本问题

![image.png](https://img-blog.csdnimg.cn/img_convert/c2a3f3c32ae39a875a5c505f4e2ac5c5.png)

6.  build project

![image.png](https://img-blog.csdnimg.cn/img_convert/3251f02e1a7b1efa662f7d9270928a34.png)


7. 执行springframework下的publishToMavenLocal,打包到本地仓库

![image.png](https://img-blog.csdnimg.cn/img_convert/1bf5630dd09f7d396431bf2b9167d23c.png)

8.  运行stduy demo
> spring demo路径： org.springframework.study.TestRegisterBean#main

![image.png](https://img-blog.csdnimg.cn/img_convert/e951a386eed4110bc47edebe34cc4ee5.png)

> springboot路径：org.springframework.boot.sutdy.SpringBootStudyApplication#main

![image.png](https://img-blog.csdnimg.cn/img_convert/0ef5a6237720ecaf8bb226ce352c7241.png)

# 二. springframework源码环境搭建
1. fork 源码到码云仓库,然后下载到本地，然后切换到你需要的分支，然后再基于此分支新建一个自己的分支

![image.png](https://img-blog.csdnimg.cn/img_convert/54ba20742aec612b220eef441c627ebf.png)
2. 配置idea, 记住配置使用【gradle-wrapper.properties】的配置去构建项目，否则会有版本问题

![image.png](https://img-blog.csdnimg.cn/img_convert/843260d03e5de004e26e0a7000436b16.png)

3. 导入项目，此时可能会自动build项目，强制结束先。根据spring版本，配置jdk，spring5.4.3需要jdk11

```js
jdk13国内镜像： 
https://mirrors.tuna.tsinghua.edu.cn/AdoptOpenJDK/11/jdk/x64/linux/
```
4. 更改相关镜像为阿里云镜像

```js
- 全局搜索'repositories {'关键词
- 加上下面这段
	mavenLocal()
	maven { url 'https://maven.aliyun.com/nexus/content/groups/public/' }
	maven { url 'https://maven.aliyun.com/nexus/content/repositories/jcenter' }
```
![image.png](https://img-blog.csdnimg.cn/img_convert/6b5e800798eede7a79990ce3e19bcd49.png)

5. 禁用checkStyle ,注释掉checkStyle相关代码即可，避免加注释时执行强制代码检查

```js
文件地址： Spring-Framework/build.gradle
全局搜索，checkstyle 和 javaformat 注释掉相关配置即可（包括.java）
```

![image.png](https://img-blog.csdnimg.cn/img_convert/35606d1895b7e74cd00ba2234f09d504.png)

![image.png](https://img-blog.csdnimg.cn/img_convert/fee262f86cf792a9b1bdf5fecc214e4c.png)

6. 关闭idea自动格式化，否则打包时会全局格式化所有文件

```js
文件地址：Spring-Framework/src/idea/spring-framework.xml
```

![image.png](https://img-blog.csdnimg.cn/img_convert/df63cb048fe89b7edf7e3918ad9685f5.png)

7. 去除单元测试，javadoc

```js
文件地址：/tjs-workspace/spring-boot/build.gradle 
加入如下配置
subprojects {
    tasks.withType(Javadoc).all {
        enabled = false
    }
    tasks.withType(Test).all {
        enabled = false
    }
}

```

8. 新建自己的测试模块，引入需要的包即可.使用AnnotationConfigApplicationContext基于注解加载bean到ioc
- 坑1，新建的模块的.gradle配置文件，必须命名规范。比如新建的模块为spring-study，则.gradle配置文件必须要是spring-study.gradle，否则无法引入依赖！

![image.png](https://img-blog.csdnimg.cn/img_convert/0511b1838b175eb051a0d62c76f8afe1.png)

![image.png](https://img-blog.csdnimg.cn/img_convert/b6203a680372a11524de452f2438b174.png)

9. 执行测试例子

![image.png](https://img-blog.csdnimg.cn/img_convert/f0c66707474ebf41faa6cba545f87ed2.png)

10. 打包到本地仓库，方便后面springboot引入


- 报错：javadoc: 警告.....
  
  未知用处，注释掉相关代码：全局搜索“project.ext.javadocLinks”，注释掉

- 报错： /Spring-Framework/build/docs/javadoc/package-list (没有那个文件或目录)
  
  未知用处，注释掉相关代码：gradle/docs.gradle: externalDocumentationLink
  可能还有其他错误，不影响本次学习的相关的类的，都可以注释掉

![image.png](https://img-blog.csdnimg.cn/img_convert/4b284c09eac342672abb701e5ac7b7fc.png)

# 三. springboot源码环境搭建

基本同上，除了以下这点

8.   去除不相关的项目依赖，加速启动
```js
文件地址：/tjs-workspace/spring-boot/settings.gradle
```

![image.png](https://img-blog.csdnimg.cn/img_convert/834a04fd5bfc1de78437ed30b0f9d68f.png)

# 四. springboot 整合 本地springframework 聚合项目

 - [ ] 聚合项目，更方便整合测试，未完待续。。。

```js
大致思路
1. 合并两个项目的buildSrc，根目录.gradle
2. .gradle文件 更改springboot引入springframework的地方
```
