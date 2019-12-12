# Android_frame
## 使用方式
### 1.在项目根build.gradle中添加：
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
### 2.在app的build.gradle中添加：
```
dependencies {
	implementation 'com.github.yanhaozeng:Android_frame:v1.0'
}
```
## 特别工具类使用注意
### 3.使用项目中SpUtils时注意在自己的Application中初始化：其中YourName为缓存保存时的文件名称
```
SpUtils.init("YourName");
```
### 4.使用项目中CrashHandler时请在自己的Application中初始化：
```
CrashHandler.getInstance().init(getApplicationContext());
```
