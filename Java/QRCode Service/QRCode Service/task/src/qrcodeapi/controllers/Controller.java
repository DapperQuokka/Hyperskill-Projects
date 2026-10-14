package qrcodeapi.controllers;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.Tuple;
import qrcodeapi.services.QRCodeService;
import qrcodeapi.services.ValidationService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private ValidationService validationService;

    @GetMapping("/api/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity
                .ok()
                .body("Healthy");
    }

    @GetMapping("/api/qrcode")
    public ResponseEntity<?> getQRCode(
            @RequestParam String contents,
            @RequestParam(required = false) Character correction,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String type
    ) {

        ResponseEntity<Map<String, String>> errorResponse = validationService.getErrorResponse(size, type, contents, correction);

        if (errorResponse != null) {
            return errorResponse;
        }

        Tuple<String, MediaType> mediaTuple = validationService.validateType(type);
        ErrorCorrectionLevel correctionLevel = validationService.validateCorrection(correction);
        Integer mediaSize = validationService.validateSize(size);

        BufferedImage bufferedImage = qrCodeService.generateQRCode(contents, mediaSize, correctionLevel);

        try (var outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, mediaTuple._1(), outputStream);
            byte[] byteArray = outputStream.toByteArray();
            return ResponseEntity
                    .ok()
                    .contentType(mediaTuple._2())
                    .body(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
