package qrcodeapi;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.image.BufferedImage;

@Service
public class QRCodeService {

    public BufferedImage generateWhiteSquare(int size) {

        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        var graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size, size);
        return image;
    }
}