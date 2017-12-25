import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import sun.applet.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame{
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    Frame opencv;
    private FileDialog openFile;
    Frame fileframe;
    JButton an1,an2;

    JPanel mb1;//南部
    Graphics gs;
    //中部

    JPanel mb2;

    JButton bq2;

    JTextField wbk;

    BufferedImage in;
    BufferedImage out;
    String outurl;
    String inrul;

    public static void main(String[] args){
        MainFrame lg=new MainFrame();

    }

    public MainFrame(){

        bq2=new JButton("upload");

        bq2.setFont(new Font("隶书",Font.PLAIN,16));


        wbk=new JTextField();

        mb1=new JPanel();//南部

        an1=new JButton("确定");


        mb2=new JPanel();

        mb1.add(an1);


        mb2.setLayout(null);

        bq2.setBounds(10, 10, 100, 20);

        mb2.add(bq2);

        wbk.setBounds(130, 10, 90, 20);

        mb2.add(wbk);


        this.add(mb1,BorderLayout.SOUTH);


        this.add(mb2,BorderLayout.CENTER);


        this.setTitle("欢迎使用");

        this.setSize(250,200);

        this.setLocationRelativeTo(null);

        //this.setResizable(false);//?

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);

       bq2.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               openFile=new FileDialog(fileframe, "打开文件", FileDialog.LOAD);
               openFile.setVisible(true);
               String dirName=openFile.getDirectory();
               String fileName=openFile.getFile();
               if(!fileName.endsWith(".jpg")&&!fileName.endsWith(".png")&&!fileName.endsWith(".PNG")&&!fileName.endsWith(".JPG")){
                   JOptionPane.showMessageDialog(null, "请上传jpg或者png");
                   return;
               }
               wbk.setText(dirName+fileName);
               System.out.println(dirName);

               inrul=dirName+fileName;

           }
       });


        an1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inrul == null) {
                    JOptionPane.showMessageDialog(null, "上传照片不能为空");
                    return ;
                } else {

                    File file = new File(inrul);
                    try {
                        InputStream is = new FileInputStream(file);
                        BufferedImage bi = ImageIO.read(is);
                        BufferedImageToMat bf = new BufferedImageToMat(bi);
                        Mat input = bf.getMat();

                        face_detect fc = new face_detect(input);
                        Mat result = null;
                        result = fc.returnMat();
                        MatToBufferedImage mt = new MatToBufferedImage(result);

                        out = mt.GetBuffer();

                 ImageIO.write(out, "png", new File("d:/" + "戴帽子" + ".png"));
                    JOptionPane.showMessageDialog(null,"图片已生成并保存到D:");
//                   System.exit(0);
//                   System.out.print(bi);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


    }

}