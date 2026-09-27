```
# SpritePacker 像素动画帧打包 & 预览工具（Java Maven）
> 当前版本：v0.2
✨ 一款简单的精灵表打包工具，适合游戏开发，批量把多张像素动画帧合并为精灵表，自带动画预览窗口。

## ✨功能
- 将多张png精灵帧横向合并成一张精灵表（SpriteSheet）
- 自动生成JSON描述文件，记录每一张小图在大图里的坐标、宽高、文件名
- 内置Swing预览窗口，读取精灵表+JSON循环播放动画，画面自动居中

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

## 📁项目结构

```
sprite-packer
├── src/main/java/com/gametool
│   ├── SpritePackerMain.java  # 主入口，打包/预览双模式
│   └── SpritePreview.java     # Swing动画预览窗口
├── pom.xml
├── .gitignore
└── README.md
```

## 📌说明

精灵表（Sprite Sheet）常用于 2D 像素游戏，把零散动画帧合并成一张大图，减少游戏资源读取开销。
本项目用 Java Swing 实现预览，适合学习和小游戏开发使用。

## 📜版本记录

- v0.1：基础精灵打包功能，支持横向拼接 png 帧，输出精灵表 sheet.png 和坐标 JSON
- v0.2：新增 Swing 动画预览窗口，支持精灵表循环播放、画面居中；整合打包 / 预览双入口；补充 README 与.gitignore

## License

MIT License