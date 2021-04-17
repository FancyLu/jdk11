pluginManagement {
	repositories {
		mavenLocal()
		maven { url 'https://maven.aliyun.com/nexus/content/groups/public/' }
		maven { url 'https://maven.aliyun.com/nexus/content/repositories/jcenter' }
		maven { url = uri("https://repo.spring.io/milestone") }
		gradlePluginPortal()
	}
}
