import com.sun.xml.internal.ws.api.config.management.policy.ManagedServiceAssertion;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;

public class face_detect {
    public Mat cover_hat;

    face_detect(Mat cover_hat){
        this.cover_hat=cover_hat;
    }


    public Mat returnMat(){
        Mat gray = new Mat();
        Mat redhat = Imgcodecs.imread("./hat.png",-1);
        Imgproc.cvtColor(cover_hat,gray,COLOR_BGR2GRAY);
        equalizeHist(gray,gray);
        CascadeClassifier faceDetector = new CascadeClassifier("./lbpcascade_frontalface.xml"); //opencv人脸训练集
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(gray, faceDetections );

       for (Rect rect : faceDetections.toArray()) {
//           System.out.println("face++");

           Imgproc.resize(redhat,redhat,new Size(rect.width,rect.height),0,0,0); //根据人脸rect大小来设置帽子大小

//           System.out.println(redhat.channels());

           Mat dst = new Mat();
           List<Mat> planes = new ArrayList<Mat>();
           List<Mat> result = new ArrayList<Mat>();
           Mat result1 = new Mat();
           Mat result2 = new Mat();
           Mat result3 = new Mat();
           Mat result4 = new Mat();

           Core.split(redhat,planes);
           result1 = planes.get(0);
           result2 = planes.get(1);
           result3 = planes.get(2);
           result4 = planes.get(3);

           result.add(result1);
           result.add(result2);
           result.add(result3);
           result.add(result4);

           Core.merge(result,dst); //分离通道值


        Rect roi = new Rect(rect.x,new Double(rect.y-0.9*rect.height).intValue(),redhat.width(),redhat.height());
        //越界处理
        if(!(0 <= roi.x && 0 <= roi.width && roi.x + roi.width <= cover_hat.cols() && 0 <= roi.y && 0 <= roi.height && roi.y + roi.height <= cover_hat.rows())){
            if(!(roi.x + roi.width <= cover_hat.cols())) roi.width=cover_hat.cols()-roi.x;
            if(!(roi.y + roi.height <= cover_hat.rows())) roi.height=cover_hat.rows()-roi.y;
            if(!(roi.x>=0) ) roi.x=0;  if(!(roi.width>=0) ) roi.width=0; if(!(roi.y>=0) ) roi.y=0;
        }

        Mat destinationROI = cover_hat.submat(roi);
           for (int j = 0; j < dst.rows(); j++)
           {
               for (int i = 0; i < dst.cols(); i++)
               {
                   double[] temp = dst.get(j,i);
                   if(temp[3]<1) continue; //apha值为0则不用覆盖
                   destinationROI.put(j,i,temp[0],temp[1],temp[2]);
               }
           }
       }
        return cover_hat;
    }


}
