package web.servlet;

import dao.C3P0XmlSimplify;
import dao.DatabaseUntil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.UploadManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {

    private List<String> legalExt = new ArrayList<>(Arrays.asList("jpg", "png", "gif", "jpeg"));

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String savePath = this.getServletContext().getRealPath("/img/userHead");
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
        System.out.println(savePath);
        System.out.println(tempPath);
        File tmpFile = new File(tempPath);
        File saveDir = new File(savePath);
        Cookie[] cookies = request.getCookies();
        String userName = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("UserName")) {
                userName = cookie.getValue();
            }
        }
        if (!tmpFile.exists()) {
            //创建临时目录
            tmpFile.mkdirs();
        }

        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        //消息提示
        String message = "";
        try{
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024*100);
            factory.setRepository(tmpFile);
            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setProgressListener((pBytesRead, pContentLength, arg2) -> System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead));


            upload.setHeaderEncoding("UTF-8");
            if(!ServletFileUpload.isMultipartContent(request)) {
                System.out.println("form");
                return;
            }
            upload.setFileSizeMax(1024*1024*2);
            upload.setSizeMax(1024*1024*2);

            List<FileItem> list = upload.parseRequest(request);
            System.out.println("5");
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                }else{
                    String filename = item.getName();
                    System.out.println(filename);
                    if(filename == null || filename.trim().equals("")){
                        continue;
                    }

                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
                    if (!legalExt.contains(fileExtName)) {
                        response.getWriter().write("false");
                        return;
                    }
                    System.out.println("上传的文件的扩展名是："+ fileExtName);

                    List<Map<String, Object>> data = UploadManager.uploadUserHead(item, filename, savePath, userName);
                    request.getSession().setAttribute("userHeadPic", data);

                    item.delete();
                    message = "文件上传成功！";
                }
            }
        }catch (FileUploadBase.FileSizeLimitExceededException e) {
            e.printStackTrace();
            request.setAttribute("message", "单个文件超出最大值！！！");
            response.getWriter().write("false");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }catch (FileUploadBase.SizeLimitExceededException e) {
            e.printStackTrace();
            response.getWriter().write("false");
            request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }catch (Exception e) {
            message= "文件上传失败！";
            response.getWriter().write("false");
            e.printStackTrace();

        }
        response.getWriter().write("true");
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
