package org.springframework.boot.sutdy.dataFlow.devtools;

import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.devtools.classpath.ClassPathFileSystemWatcher;
import org.springframework.util.ClassUtils;

/**
 * Created by Nicky.Tang on 2021/9/29 3:59 下午
 *
 * @since 02.12.10
 */
@SpringBootApplication
public class DoTestOfDevtools {
    /**
	 * spring-boot-devtools启用了一个新的类加载器去加载工作目录(/target/classes)，其他的类都由默认的加载器加载
	 * 然后启用一个线程去监听文件，当文件发生变更，重新构造一个类加载器去重新调用main方法，即可达到重启的目的
	 * 而此次重启，并不会再次加载第三方jar（第三方jar依然交于默认类加载器加载），所以更快
     *
	 * 1. main方法在哪重新调用
     * 监听springboot启动事件
     * {@link org.springframework.boot.devtools.restart.RestartApplicationListener#onApplicationStartingEvent(org.springframework.boot.context.event.ApplicationStartingEvent)}
     * 新建一个classLoader
     * {@link org.springframework.boot.devtools.restart.Restarter#doStart()}
     *      （ ClassLoader classLoader = new RestartClassLoader fixme 实例化新的类加载器）
     *      {@link org.springframework.boot.devtools.restart.classloader.RestartClassLoader#RestartClassLoader(ClassLoader, java.net.URL[], org.springframework.boot.devtools.restart.classloader.ClassLoaderFileRepository, org.apache.commons.logging.Log)}
     * 新建一个线程回调启动类的main方法
     * {@link org.springframework.boot.devtools.restart.Restarter.LeakSafeThread#callAndWait(java.util.concurrent.Callable)}
     *      (callAndWait,导致首次执行SpringApplication#run方法，执行到listeners.starting后接停止了)
     * {@link org.springframework.boot.devtools.restart.RestartLauncher#run()}
     *      （setContextClassLoader(classLoader);fixme 这里重置当前线程的类加载器）
	 *
	 * 4. 如何监听文件变更
	 * 启动文件监听器线程
     * {@link ClassPathFileSystemWatcher#afterPropertiesSet()}
	 * 		(this.fileSystemWatcher.start();)
     * 文件变更，发布事件
     * {@link org.springframework.boot.devtools.classpath.ClassPathFileChangeListener#onChange(java.util.Set)}
     * 监听文件变更事件
     * {@link org.springframework.boot.devtools.autoconfigure.LocalDevToolsAutoConfiguration.RestartConfiguration#restartingClassPathChangedEventListener(org.springframework.boot.devtools.filewatch.FileSystemWatcherFactory)}
     *
	 * 监听的class文件路径：(仅当前工作目录)
	 * {@link org.springframework.boot.devtools.restart.ChangeableUrls#ChangeableUrls(java.net.URL...)}
	 * 判断两个版本文件是否变化
	 * {@link org.springframework.boot.devtools.filewatch.FileSnapshot#equals(java.lang.Object)}
	 *
	 * spring加载Bean时用的哪个类加载器：
     * springIOC加载bean的时候优先使用Thread.currentThread().getContextClassLoader()
     * {@link AbstractBeanFactory#beanClassLoader}
     * {@link ClassUtils#getDefaultClassLoader()}
	 *
	 * 清理资源
	 * {@link org.springframework.boot.devtools.restart.Restarter#stop()}
     *
     * @param args
     */
    public static void main(String[] args) {
		System.out.println("当前线程名：" + Thread.currentThread().getName()+" id:"+Thread.currentThread().getId());
		System.out.println("当前类加载器：" + DoTestOfDevtools.class.getClassLoader() + "\n");
		SpringApplication.run(DoTestOfDevtools.class, args);
    }
}
