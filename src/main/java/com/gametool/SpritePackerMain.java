package com.gametool;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpritePackerMain {

    public static class SpriteFrame {
        public String name;
        public int x;
        public int y;
        public int width;
        public int height;

        public SpriteFrame(String name, int x, int y, int w, int h) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        }
    }

    public static class SpriteData {
        public List<SpriteFrame> frames;
    }

    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("用法：");
            System.out.println("打包：-i 输入文件夹 -o 输出文件夹");
            System.out.println("预览：-p sheet.png sheet.json");
            return;
        }

        // 预览模式
        if(args[0].equals("-p")){
            String imgPath = args[1];
            String jsonPath = args[2];
            SpritePreview.main(new String[]{imgPath, jsonPath});
            return;
        }

        //打包模式
        if (args.length <4 || !args[0].equals("-i") || !args[2].equals("-o")){
            System.out.println("打包用法：java -jar SpritePacker.jar -i 输入文件夹 -o 输出文件夹");
            return;
        }
        String inputDirPath = args[1];
        String outputDirPath = args[3];
        File inputDir = new File(inputDirPath);
        File outputDir = new File(outputDirPath);
        if(!outputDir.exists()) outputDir.mkdirs();

        try {
            List<File> pngFiles = getPngFiles(inputDir);
            if(pngFiles.isEmpty()){
                System.out.println("文件夹没有png图片");
                return;
            }
            List<BufferedImage> imageList = new ArrayList<>();
            List<SpriteFrame> frameList = new ArrayList<>();

            int offsetX = 0;
            int frameHeight = 0;
            for(File f : pngFiles){
                BufferedImage img = ImageIO.read(f);
                imageList.add(img);
                int w = img.getWidth();
                int h = img.getHeight();
                frameHeight = h;
                frameList.add(new SpriteFrame(f.getName(), offsetX,0,w,h));
                offsetX += w;
            }

            // 创建大图
            BufferedImage sheet = new BufferedImage(offsetX, frameHeight, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = sheet.createGraphics();
            int drawX =0;
            for(int i=0;i<imageList.size();i++){
                BufferedImage img = imageList.get(i);
                g2d.drawImage(img,drawX,0,null);
                drawX += img.getWidth();
            }
            g2d.dispose();

            // 保存图片
            File sheetFile = new File(outputDir, "sheet.png");
            ImageIO.write(sheet,"png",sheetFile);

            // 保存json
            SpriteData data = new SpriteData();
            data.frames = frameList;
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(outputDir,"sheet.json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile,data);

            System.out.println("打包完成！");
            System.out.println("输出："+sheetFile.getAbsolutePath());
            System.out.println("输出："+jsonFile.getAbsolutePath());

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 获取文件夹内所有png文件
    private static List<File> getPngFiles(File dir){
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if(files == null) return list;
        for(File f : files){
            String name = f.getName().toLowerCase();
            if(name.endsWith(".png")){
                list.add(f);
            }
        }
        return list;
    }
}
