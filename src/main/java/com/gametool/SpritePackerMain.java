package com.gametool;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class SpritePackerMain {
    public static void main(String[] args) {
        if (args.length <4 || !args[0].equals("-i") || !args[2].equals("-o")){
            System.out.println("用法：java -jar SpritePacker.jar -i 输入文件夹 -o 输出文件夹");
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

    // 获取文件夹png文件，并按文件名排序
    public static List<File> getPngFiles(File dir){
        File[] files = dir.listFiles((file, name)-> name.endsWith(".png"));
        if(files == null) return new ArrayList<>();
        Arrays.sort(files);
        return Arrays.asList(files);
    }
}
