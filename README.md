# Android_frame
## 使用方式
### 在项目根build.gradle中添加：
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
### 在app的build.gradle中添加：
```
dependencies {
	implementation 'com.github.yanhaozeng:Android_frame:v1.0'
}
```
## 特别工具类使用注意
### 使用项目中SpUtils时注意在自己的Application中初始化：
```
SpUtils.init("YourName");
```
#### 其中YourName为缓存保存时的文件名称

### 使用项目中CrashHandler时请在自己的Application中初始化：
```
CrashHandler.getInstance().init(getApplicationContext());
或者
CrashHandler.getInstance().init(getApplicationContext(),"你要将异常文件提交到的Url");
```
#### 如果只添加了Context 没有添加异常文件上传路径，不会在出现异常时上传异常信息。请开发者自行决定异常文件的使用。
#### 请使用 CrashHandler.getLOG_DIR()方法获取异常文件路径，本路径下是项目所有异常文件路径，开发者使用全局异常捕获器时，请及时清理。
```
CrashHandler.getLOG_DIR();
```
#### 如果添加了异常文件上传Url请使用File进行接收,参数名"file"，上传成功后会自动删除异常文件。

