# DoodleView
[![](https://jitpack.io/v/yangsanning/DoodleView.svg)](https://jitpack.io/#yangsanning/DoodleView)
[![API](https://img.shields.io/badge/API-19%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=19)

## 效果预览

| [DoodleView]                      |
| ------------------------------- |
| <img src="images/image1.gif" height="512" /> |


## 主要文件
| 名字             | 摘要           |
| ---------------- | -------------- |
| [DoodleView] | 涂鸦View  |
| [PaintType] | 涂鸦画笔类型  |


### 1. 基本用法

#### 1.1 布局中添加
```android
  <ysn.com.view.doodle.DoodleView
        android:id="@+id/main_activity_doodle_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:dv_color="@color/colorPrimary"
        app:dv_stroke="10dp"
        app:dv_type="path" />
```

#### 1.2 设置数据
```android
    // 设置画笔类型
    doodleView.setPaintType(which);
    // 设置画笔颜色
    doodleView.setPaintColor("#000000");
    doodleView.setPaintColor(getResources().getColor(R.color.colorAccent));
    // 设置画笔宽度
    doodleView.setPaintStroke(5);
```

### 2. 配置属性([Attributes])
|name|format|description|
|:---:|:---:|:---:|
| dv_type | integer | 涂鸦画笔类型 |
| dv_color | color | 画笔颜色 |
| dv_stroke | dimension|reference | 画笔宽度 |


### 3.添加方法

#### 3.1 添加仓库

在项目的 `build.gradle` 文件中配置仓库地址。

```android
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

#### 3.2 添加项目依赖

在需要添加依赖的 Module 下添加以下信息，使用方式和普通的远程仓库一样。

```android
    implementation 'com.github.yangsanning:DoodleView:v1.0.0'
```

[DoodleView]:https://github.com/yangsanning/DoodleView/blob/master/doodleview/src/main/java/ysn/com/view/doodle/DoodleView.java
[PaintType]:https://github.com/yangsanning/DoodleView/blob/master/doodleview/src/main/java/ysn/com/view/doodle/type/PaintType.java
[Attributes]:https://github.com/yangsanning/DoodleView/blob/master/doodleview/src/main/res/values/attrs.xml
