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
        String classPath = MosaicTools.class.getResource("/").getFile().substring(1);
        Pic pic1 = new Pic();
        pic1.setSourceFile(classPath + "企业借款及居间协议_9.png");
        pic1.setToSaveDir(classPath + "tmp/");
        pic1.setToSaveName("test1");
        pic1.setSuffix("png");

        Position pos1 = new Position();
        pos1.setX1(597);
        pos1.setX2(996);
        pos1.setY1(1958);
        pos1.setY2(2132);

        MosaicTools.mosaic(pic1, pos1);

        Pic pic2 = new Pic();
        pic2.setSourceFile(classPath + "企业借款及居间协议_1.png");
        pic2.setToSaveDir(classPath + "tmp/");
        pic2.setToSaveName("test2");
        pic2.setSuffix("png");

        Position pos2 = new Position();
        pos2.setX1(492);
        pos2.setX2(2088);
        pos2.setY1(1100);
        pos2.setY2(1420);

        MosaicTools.mosaic(pic2, pos2);
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
        int size = 1;
        BufferedImage bi = ImageIO.read(file); // 读取该图片
        BufferedImage spinImage = new BufferedImage(bi.getWidth(), bi.getHeight(),
            BufferedImage.TYPE_INT_RGB);
        if (bi.getWidth() < size || bi.getHeight() < size || size <= 0) { // 马赛克格尺寸太大或太小
            return false;
        }

        int xcount = 0; // x方向绘制个数
        int ycount = 0; // y方向绘制个数
        if (bi.getWidth() % size == 0) {
            xcount = bi.getWidth() / size;
        } else {
            xcount = bi.getWidth() / size + 1;
        }
        if (bi.getHeight() % size == 0) {
            ycount = bi.getHeight() / size;
        } else {
            ycount = bi.getHeight() / size + 1;
        }
        int x = 0; //坐标
        int y = 0;
        // 绘制马赛克(绘制矩形并填充颜色)
        Graphics gs = spinImage.getGraphics();
        for (int i = 0; i < xcount; i++) {
            for (int j = 0; j < ycount; j++) {
                // 马赛克矩形格大小
                int mwidth = size;
                int mheight = size;
                if (i == xcount - 1) { //横向最后一个比較特殊，可能不够一个size
                    mwidth = bi.getWidth() - x;
                }
                if (j == ycount - 1) { //同理
                    mheight = bi.getHeight() - y;
                }
                // 矩形颜色取中心像素点RGB值
                int centerX = x;
                int centerY = y;
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
                    Random random = new Random(0);
                    gs.setColor(new Color(
                        bi.getRGB(centerX - random.nextInt(30), centerY - random.nextInt(30))));
                } else {
                    gs.setColor(new Color(bi.getRGB(centerX, centerY)));
                }
                gs.fillRect(x, y, mwidth, mheight);
                y = y + size;// 计算下一个矩形的y坐标
            }
            y = 0;// 还原y坐标
            x = x + size;// 计算x坐标
        }
        gs.dispose();
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
