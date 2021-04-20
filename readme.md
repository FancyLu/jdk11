# 一. 引入已经配置好的源码
> 已经配置好的码云仓库，下载后切换到对应分支即可直接运行
> 
> </br>jdk11国内镜像：https://mirrors.tuna.tsinghua.edu.cn/AdoptOpenJDK/11/jdk/x64/linux/
> 
> </br>spring版本：5.3.4  spring码云地址： https://gitee.com/tangjingshan/Spring-Framework
> 
> </br>springboot版本：2.4.3   springboot码云地址：https://gitee.com/tangjingshan/spring-boot
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

![first_img](https://gitee.com/tangjingshan/spring-boot/blob/nickyStudy5.3.4/src/img/first.png)
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
