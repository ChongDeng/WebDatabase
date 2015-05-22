
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
*
* @author crystal
*/
public class FileUploaded extends HttpServlet {

    /**
      * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
      * @param request servlet request
      * @param response servlet response
      */
    // 定义文件的上传路径

    private String uploadPath = "F:\\Flex_File\\Tomcat\\webapps\\WebDb\\space\\";
	//private String uploadPath = "F:\\";
// 限制文件的上传大小

    private int maxPostSize = 100 * 1024 * 1024;

    public FileUploaded() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Access !");
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

//保存文件到服务器中

         DiskFileItemFactory factory = new DiskFileItemFactory();
         factory.setSizeThreshold(4096);
         ServletFileUpload upload = new ServletFileUpload(factory);
         upload.setSizeMax(maxPostSize);
        try {
            List fileItems = upload.parseRequest(request);
            Iterator iter = fileItems.iterator();
            while (iter.hasNext()) {
                 FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    String name = item.getName();
                    System.out.println(name);
                    try {
                        item.write(new File(uploadPath + name));
                       // SaveFile s = new SaveFile();

                       // s.saveFile(name);

                    } catch (Exception e) {
                         e.printStackTrace();
                    }
                }
            }
        } catch (FileUploadException e) {
             e.printStackTrace();
            System.out.println(e.getMessage() + "结束");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
      * Handles the HTTP <code>GET</code> method.
      * @param request servlet request
      * @param response servlet response
      */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         processRequest(request, response);
    }

    /**
      * Handles the HTTP <code>POST</code> method.
      * @param request servlet request
      * @param response servlet response
      */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         processRequest(request, response);
    }

    /**
      * Returns a short description of the servlet.
      */
    public String getServletInfo() {
        return "Short description";
        }
}

