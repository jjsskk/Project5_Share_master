package com.example.common;



import com.example.book.BookDAO;

import com.example.book.BookVO;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Component
public class FileUpload {
    @Autowired
    BookDAO bookDAO;
        public BookVO uploadPhoto(HttpServletRequest request){
            String filename = "";
            int sizeLimit = 15*1024*1024;//dd

            String realPath = request.getServletContext().getRealPath("/resources/upload"); // tomcat dont recognize upload folder
            File dir= new File(realPath);
            if(!dir.exists())
                dir.mkdirs();
            BookVO one= null;
            MultipartRequest multipartRequest = null;
            try {
                multipartRequest = new MultipartRequest(request, realPath,sizeLimit,"utf-8",new DefaultFileRenamePolicy());
                System.out.println(realPath);
                filename= multipartRequest.getFilesystemName("photo");
                one = new BookVO();
                String sid = multipartRequest.getParameter("sid");
                if(sid!=null && !"".equals(sid))
                    one.setSid(Integer.parseInt(sid));
                one.setTitle(multipartRequest.getParameter("title"));
                one.setWriter(multipartRequest.getParameter("writer"));

                one.setPublisher(multipartRequest.getParameter("publisher"));
                one.setPrice(multipartRequest.getParameter("price"));
                one.setPages(multipartRequest.getParameter("pages"));
                one.setSizes(multipartRequest.getParameter("sizes"));
                one.setWeight(multipartRequest.getParameter("weight"));
                one.setTopic(multipartRequest.getParameter("topic"));
                one.setContent(multipartRequest.getParameter("content"));


                if (sid != null &&!"".equals(sid)){ //edit 인경우

                    String oldfilename = bookDAO.getPhotoFilename(Integer.parseInt(sid));
                    if(filename != null && oldfilename != null)
                        FileUpload.deleteFile(request, oldfilename);
                    else if(filename==null && oldfilename!=null)
                        filename = oldfilename;

                }
                if(filename == null)
                    filename="";
                one.setPhoto(filename);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return one;
        }
        public static void deleteFile(HttpServletRequest request, String filename){
            String filepath= request.getServletContext().getRealPath("/resources/upload");
            File f =new File(filepath + "/" + filename);
            if(f.exists())
                f.delete();
        }
}
