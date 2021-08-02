package bg.sofia.uni.fmi.mjt.foodanalyzer;

import bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions.BarcodeRetrieverException;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.UPCAReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarcodeRetriever {

    protected BarcodeRetriever() {
    }

    protected static String getBarcodeFromPath(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            BinaryBitmap bitmap = convertImageToBinaryBitmap(image);
            UPCAReader reader = new UPCAReader();
            Result result = reader.decode(bitmap);
            return result.getText();
        } catch (IOException e) {
            throw new BarcodeRetrieverException("ImageIo reading error!", e);
        } catch (NotFoundException | FormatException e) {
            throw new BarcodeRetrieverException("Reader decoding error!", e);
        }
    }

    private static BinaryBitmap convertImageToBinaryBitmap(BufferedImage image) {
        int[] pixels = image.getRGB(0, 0,
                image.getWidth(), image.getHeight(),
                null, 0, image.getWidth());

        RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);

        return new BinaryBitmap(new HybridBinarizer(source));
    }

}
