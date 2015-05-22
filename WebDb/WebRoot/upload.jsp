<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8" import="java.io.*,java.util.*"%>
<%@page import="com.oreilly.servlet.multipart.*"%>
<%@page import="com.oreilly.servlet.*"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>JSP Page</h1>
        <%
        try {
            //上传文件最大为10M
            MultipartRequest multi =
                    new MultipartRequest(request, "F:/Flex_File/Tomcat/webapps/WebDb/space/", 10*1024*1024,
                    "ISO-8859-1", new DefaultFileRenamePolicy());
            out.println("PARAMS:");
            Enumeration params = multi.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String)params.nextElement();
                String value = multi.getParameter(name);
                out.println(name + "=" + value);
            }
            out.println();
            out.println("FILES:");
            Enumeration files = multi.getFileNames();
            while (files.hasMoreElements()) {
                String name = (String)files.nextElement();
                String filename = multi.getFilesystemName(name);
                String originalFilename = multi.getOriginalFileName(name);
                String type = multi.getContentType(name);
                File f = multi.getFile(name);
                out.println("name: " + name);
                out.println("filename: " + filename);
                out.println("originalFilename: " + originalFilename);
                out.println("type: " + type);
                if (f != null) {
                    out.println("f.toString(): " + f.toString());
                    out.println("f.getName(): " + f.getName());
                    out.println("f.exists(): " + f.exists());
                    out.println("f.length(): " + f.length());
                }
                out.println();
            }
        } catch (IOException lEx) {
            this.getServletContext().log(lEx, "error reading or saving file");
        }
        %>
    </body>
</html>
