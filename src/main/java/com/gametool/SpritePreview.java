package com.gametool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SpritePreview extends JFrame {
    private BufferedImage sheetImage;
    private List<FrameInfo> frames;
    private int currentIndex = 0;
    private final JPanel canvasPanel;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FrameInfo {
        public String name;
        public int x;
        public int y;
        public int width;
        public int height;
    }

    public static class SheetData {
        public List<FrameInfo> frames;
    }

    public SpritePreview(BufferedImage sheet, List<FrameInfo> frameData) {
        this.sheetImage = sheet;
        this.frames = frameData;

        // 调试打印帧信息
        System.out.println("读取到帧数量：" + frames.size());
        for (FrameInfo f : frames) {
            System.out.printf("帧：%s | x=%d y=%d w=%d h=%d%n", f.name, f.x, f.y, f.width, f.height);
        }

        setTitle("精灵动画预览");
        setSize(600, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        canvasPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (frames == null || sheetImage == null || frames.isEmpty()){
                    g.setColor(Color.RED);
                    g.drawString("没有帧数据", 20, 20);
                    return;
                }

                FrameInfo currentFrame = frames.get(currentIndex);
                int panelW = getWidth();
                int panelH = getHeight();
                int fw = currentFrame.width;
                int fh = currentFrame.height;

                if(fw <=0 || fh <=0){
                    g.setColor(Color.RED);
                    g.drawString("帧宽高异常",20,20);
                    return;
                }

                int drawX = (panelW - fw) / 2;
                int drawY = (panelH - fh) / 2;

                g.drawImage(
                        sheetImage,
                        drawX, drawY, drawX + fw, drawY + fh,
                        currentFrame.x, currentFrame.y,
                        currentFrame.x + fw, currentFrame.y + fh,
                        null
                );
            }
        };
        canvasPanel.setBackground(Color.LIGHT_GRAY);
        add(canvasPanel);

        Timer timer = new Timer(120, e -> {
            currentIndex = (currentIndex + 1) % frames.size();
            canvasPanel.repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        if(args.length <2){
            System.out.println("用法：SpritePreview 图片路径 json路径");
            return;
        }
        String imgPath = args[0];
        String jsonPath = args[1];

        try {
            BufferedImage sheet = javax.imageio.ImageIO.read(new File(imgPath));
            if(sheet == null){
                System.out.println("图片读取失败！");
                return;
            }
            System.out.println("精灵表图片读取成功，宽:"+sheet.getWidth()+" 高:"+sheet.getHeight());

            ObjectMapper mapper = new ObjectMapper();
            SheetData sheetData = mapper.readValue(new File(jsonPath), SheetData.class);

            SwingUtilities.invokeLater(() -> {
                SpritePreview win = new SpritePreview(sheet, sheetData.frames);
                win.setLocationRelativeTo(null);
                win.setVisible(true);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
