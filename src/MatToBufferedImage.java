import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MatToBufferedImage {
    private Mat matrix;
    MatToBufferedImage(Mat matrix){
        this.matrix=matrix;
    }
    public  BufferedImage GetBuffer()throws IOException {
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".png", matrix, mob);
        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }
}
