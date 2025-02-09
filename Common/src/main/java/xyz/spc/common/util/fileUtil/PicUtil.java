package xyz.spc.common.util.fileUtil;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import xyz.spc.common.funcpack.exception.ServiceException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片工具类
 */
public final class PicUtil {


    /**
     * 获取图像文件后缀
     *
     * @param photoByte 图像数据
     * @return 后缀名
     */
    public static String getPicExtendName(byte[] photoByte) {
        String strFileExtendName = "jpg";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56) && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97)) {
            strFileExtendName = "gif";
        } else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70)) {
            strFileExtendName = "jpg";
        } else if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
            strFileExtendName = "bmp";
        } else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
            strFileExtendName = "png";
        }
        return strFileExtendName;
    }


    /**
     * 读取图片的全部信息
     */
    public static Map<String, String> readImgAll(File file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Map<String, String> map = new HashMap<>();
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                map.put(tag.getTagName(), tag.getDescription());
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    throw new ServiceException("图片读取失败: " + error);
                }
            }
        }
        return map;
    }

    /**
     * 读取图片的拍摄时间
     */
    public static Date readImgCaptrueTime(File file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (directory != null) {
            Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            return date;
        }
        return null;
    }


    // 读取图片的常用信息,并将信息封装到集合里面
    public static Map readImgNormal(File file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Map<String, String> map = new HashMap<>();
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                String desc = tag.getDescription(); //标签信息
                switch (tagName) {
                    case "Image Height" -> map.put("imgHeigh", desc);
                    case "Image Width" -> map.put("imgWeith", desc);
                    case "Date/Time Original" -> map.put("time", desc);
                }
            }
        }
        return map;
    }

    /**
     * 生成图片缩略图
     *
     * @param imageInputStream 图片输入流，在该方法内<strong>不会</strong>关闭该流
     * @param maxThumbSize     最大的长宽值，压缩生成的缩略图长或宽必有一个小于该值
     * @param outputStream     接收缩略图的输出流，在该方法内<strong>不会</strong>关闭该流
     * @throws IOException IO异常
     */
    public static void generateThumbnail(InputStream imageInputStream, int maxThumbSize, OutputStream outputStream) throws IOException {
        final BufferedImage image = ImageIO.read(imageInputStream);
        final int originWidth = image.getWidth(null);
        final int originHeight = image.getHeight(null);

        final int rate = Math.max(
                originHeight / maxThumbSize,
                originWidth / maxThumbSize
        );
        int newWidth;
        int newHeight;
        if (rate <= 0) {
            newHeight = originHeight;
            newWidth = originWidth;
        } else {
            newHeight = originHeight / rate;
            newWidth = originWidth / rate;
        }
        final BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        final Image scaledImage = image.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        newImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        ImageIO.write(newImage, "jpg", outputStream);
    }
}
