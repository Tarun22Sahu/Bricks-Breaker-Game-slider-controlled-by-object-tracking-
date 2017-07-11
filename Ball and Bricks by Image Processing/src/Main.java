import javax.swing.JFrame;
import java.awt.AWTException;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_core.CvArr;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

public class Main {



	public static void main(String[] args) throws AWTException{

		
		
		game Game= new game();
		JFrame frame= new JFrame();
		frame.setBounds(10,10,1280,720);
		frame.setTitle("Ball and Bricks");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(Game);
		IplImage img1,imgbinG, imgbinB;
		IplImage imghsv;
		
		
		CvScalar Bminc = cvScalar(95,150,75,0), Bmaxc = cvScalar(145,255,255,0);
		
		
		CvArr mask;
		int w=1280,h=720;
		imghsv = cvCreateImage(cvSize(w,h),8,3);
		imgbinG = cvCreateImage(cvSize(w,h),8,1);
		imgbinB = cvCreateImage(cvSize(w,h),8,1);
		IplImage imgC = cvCreateImage(cvSize(w,h),8,1);
		CvSeq contour1 = new CvSeq(), contour2=null;
		CvMemStorage storage = CvMemStorage.create();
		CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));
		
		CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
		cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_WIDTH,w);
		cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_HEIGHT,h);
		
		while(true)
		{
				
			img1 = cvQueryFrame(capture1);
			if(img1 == null){
				System.err.println("No Image");
				break;
				}
				
			imgbinB = ccmFilter.Filter(img1,imghsv,imgbinB,Bmaxc,Bminc, contour1, contour2, storage,moments,1);
								
			//cvShowImage("Combined",imgC);	
			//cvShowImage("Original",img1);
			char c = (char)cvWaitKey(15);
			if(c=='q') break;
					
		}
		cvReleaseImage(imghsv);
		cvReleaseImage(imgbinG);
		cvReleaseImage(imgbinB);
		cvReleaseImage(imghsv);
		cvReleaseMemStorage(storage);
		cvReleaseCapture(capture1);
	}

}
