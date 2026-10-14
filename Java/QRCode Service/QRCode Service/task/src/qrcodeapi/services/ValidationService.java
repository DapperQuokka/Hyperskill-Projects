package qrcodeapi.services;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;

import javax.print.attribute.standard.Media;
import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationService {

    public Integer validateSize(Integer size) {
        if (size == null) {
            return 250;
        }
        if (size>= 150 && size <= 350) {
            return size;
        }
        return -1;
    }

    public ErrorCorrectionLevel validateCorrection(Character correction) {
       return switch(correction) {
           case 'L' -> ErrorCorrectionLevel.L;
           case 'M' -> ErrorCorrectionLevel.M;
           case 'Q' -> ErrorCorrectionLevel.Q;
           case 'H' -> ErrorCorrectionLevel.H;
           case null -> ErrorCorrectionLevel.L;
           default -> null;
       };
    }

    public Tuple<String, MediaType> validateType(String type) {
        Tuple<String, MediaType> result = null;

        return switch(type) {
            case "png" -> new Tuple<>("png", MediaType.IMAGE_PNG);
            case "jpeg" -> new Tuple<>("jpeg", MediaType.IMAGE_JPEG);
            case "gif" -> new Tuple<>("gif", MediaType.IMAGE_GIF);
            case null -> new Tuple<>("png", MediaType.IMAGE_PNG);
            default -> result;
        };
    }



    public ResponseEntity<Map<String, String>> getErrorResponse(Integer size, String type, String contents, Character correction) {

        Map<String, String> errorResponseBody =  new HashMap<>();

        if (contents.isBlank()) {
            errorResponseBody.put("error", "Contents cannot be null or blank");
        }
        else if (validateSize(size) < 0) {
            errorResponseBody.put("error", "Image size must be between 150 and 350 pixels");
        }
        else if (validateCorrection(correction) == null) {
            errorResponseBody.put("error", "Permitted error correction levels are L, M, Q, H");
        }
        else if (validateType(type) == null) {
            errorResponseBody.put("error", "Only png, jpeg and gif image types are supported");
        }
        else {
            return null;
        }
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponseBody);
    }
}
