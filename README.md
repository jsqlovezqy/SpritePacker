```
# SpritePacker
像素动画帧打包 & 预览工具（Java Maven）

## ✨功能
- 将多张png精灵帧横向合并成一张精灵表（SpriteSheet）
- 自动生成JSON描述文件，记录每一张小图在大图里的坐标
- 内置预览窗口，读取精灵表+JSON循环播放动画

## 📋环境要求
JDK 17+（推荐JDK23），Maven

## 🚀编译打包
```bash
mvn package
```

## 使用方法

### 1. 打包精灵帧

```
java -jar target/sprite-packer-1.0-SNAPSHOT.jar -i frames -o output
```

- `-i`：存放 png 图片的文件夹
- `-o`：输出精灵表 sheet.png 和 sheet.json 的目录

### 2. 动画预览

```
java -jar target/sprite-packer-1.0-SNAPSHOT.jar -p output/sheet.png output/sheet.json
```

## 📁目录说明

- frames：放置待打包的 png 图片
- output：打包输出目录，生成 sheet.png、sheet.json

## 📌开发计划

- 支持多行排版（不只是横向拼接）
- 拖拽选择图片
- 可视化调整帧播放速度

```

### 关键点说明
- 代码块必须是 ```bash 开头，单独占一行；结束 ``` 也要单独一行，不能贴在命令同一行
- 去掉多余的`'''`，你截图里出现了多余三引号，github markdown不识别这个

替换保存好之后，执行这组命令上传：
```cmd
git add README.md
git commit -m "添加项目README文档"
git push
```