package com.paganini.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * <p>马赛克工具</p>
 * @author paganini
 * @version $Id: MosaicTools.java, v 0.1 2018年10月9日 上午11:17:55 qiuzhongtian Exp $
 */
abstract public class MosaicTools {

    public static void main(String[] args) throws Exception {
        Pic pic1 = new Pic();
        pic1.setSourceFile("E:/pdftest/企业借款及居间协议/企业借款及居间协议_1.png");
        pic1.setToSaveDir("E:/pdftest/企业借款及居间协议/");
        pic1.setToSaveName("test");
        pic1.setSuffix("png");

        Position pos = new Position();
        pos.setX1(597);
        pos.setX2(996);
        pos.setY1(1958);
        pos.setY2(2132);

        MosaicTools.mosaic(pic1, pos);
    }

    /**
     * @param pic
     * @param pos
     * @return
     * @throws Exception
     */
    public static boolean mosaic(final Pic pic, final Position pos) throws Exception {
        File file = new File(pic.getSourceFile());
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }
        BufferedImage bi = ImageIO.read(file); // 读取该图片
        BufferedImage spinImage = new BufferedImage(bi.getWidth(), bi.getHeight(),
            BufferedImage.TYPE_INT_RGB);

        int xcount = 0; // x方向绘制个数
        int ycount = 0; // y方向绘制个数
        if (bi.getWidth() == 0) {
            xcount = bi.getWidth() / 1;
        } else {
            xcount = bi.getWidth() / 2;
        }
        if (bi.getHeight() == 0) {
            ycount = bi.getHeight() / 1;
        } else {
            ycount = bi.getHeight() / 2;
        }
        int x = 0, y = 0; //坐标
        // 绘制马赛克(绘制矩形并填充颜色)
        Graphics gs = spinImage.getGraphics();
        for (int i = 0; i < xcount; i++) {
            for (int j = 0; j < ycount; j++) {
                // 马赛克矩形格大小
                int mwidth = 1, mheight = 1;
                if (i == xcount - 1) { //横向最后一个比較特殊，可能不够一个size
                    mwidth = bi.getWidth() - x;
                }
                if (j == ycount - 1) { //同理
                    mheight = bi.getHeight() - y;
                }
                // 矩形颜色取中心像素点RGB值
                int centerX = x, centerY = y;
                if (mwidth % 2 == 0) {
                    centerX += mwidth / 2;
                } else {
                    centerX += (mwidth - 1) / 2;
                }
                if (mheight % 2 == 0) {
                    centerY += mheight / 2;
                } else {
                    centerY += (mheight - 1) / 2;
                }
                if (x > pos.x1 && x < pos.x2 && y > pos.y1 && y < pos.y2) {
                    gs.setColor(new Color(bi.getRGB(centerX - new Random().nextInt(30),
                        centerY - new Random().nextInt(30))));
                } else {
                    gs.setColor(new Color(bi.getRGB(centerX, centerY)));
                }
                gs.fillRect(x, y, mwidth, mheight);
                y = y + 1;// 计算下一个矩形的y坐标
            }
            y = 0;// 还原y坐标
            x = x + 1;// 计算x坐标
        }
        gs.dispose();
        File sf = new File(pic.toSaveDir, pic.toSaveName + "." + pic.suffix);
        ImageIO.write(spinImage, pic.suffix, sf); // 保存图片
        return true;

    }

    /**
     * <p>图片文件</p>
     * @author paganini
     * @version $Id: MosaicTools.java, v 0.1 2018年10月9日 上午11:46:16 qiuzhongtian Exp $
     */
    public static class Pic {

        private String sourceFile, toSaveDir, toSaveName, suffix;

        public String getSourceFile() {
            return sourceFile;
        }

        public void setSourceFile(String sourceFile) {
            this.sourceFile = sourceFile;
        }

        public String getToSaveDir() {
            return toSaveDir;
        }

        public void setToSaveDir(String toSaveDir) {
            this.toSaveDir = toSaveDir;
        }

        public String getToSaveName() {
            return toSaveName;
        }

        public void setToSaveName(String toSaveName) {
            this.toSaveName = toSaveName;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

    }

    /**
     * <p>坐标方位</p>
     * @author paganini
     * @version $Id: MosaicTools.Position.java, v 0.1 2018年10月9日 上午11:40:51 qiuzhongtian Exp $
     */
    public static class Position {

        private int x1, x2, y1, y2;

        public int getX1() {
            return x1;
        }

        public void setX1(int x1) {
            this.x1 = x1;
        }

        public int getX2() {
            return x2;
        }

        public void setX2(int x2) {
            this.x2 = x2;
        }

        public int getY1() {
            return y1;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public int getY2() {
            return y2;
        }

        public void setY2(int y2) {
            this.y2 = y2;
        }

    }

}
