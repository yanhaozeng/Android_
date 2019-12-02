# Android_frame
yhzAndroid基本框架和工具库
在项目根build.gradle中添加
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
在app的build.gradle中添加
dependencies {
	        implementation 'com.github.yanhaozeng:Android_frame:Tag'
}
