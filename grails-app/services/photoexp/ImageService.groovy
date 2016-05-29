package photoexp

import grails.transaction.Transactional

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

@Transactional
class ImageService {
    static scope = "singleton"

    public String getBase64(BufferedImage image) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
        ImageIO.write(image, "jpg", out);
        out.flush()
        byte[] bytes = out.toByteArray();
        out.close();
        String base64bytes = Base64.getEncoder().encodeToString(bytes);
        return URLEncoder.encode(base64bytes, "ISO-8859-1");
    }

    public BufferedImage dropAlphaChannel(BufferedImage src) {
        BufferedImage convertedImg = src;
        if(src.getColorModel().hasAlpha()){
            convertedImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImg.getGraphics().drawImage(src, 0, 0, null);
        }
        return convertedImg;
    }
}
