<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>JSP Page</h1>
        <%
    String filename = "";
    if (request.getParameter("file") != null) {
        filename =     request.getParameter("file");
    }
    response.setContentType("application/x-msdownload");
    response.setHeader("Content-disposition","attachment; filename="+filename);
    
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
        bis = new BufferedInputStream(new FileInputStream(getServletContext().getRealPath("" + filename)));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff,0,bytesRead);
        }
        out.clear();
        out=pageContext.pushBody();
    } catch(final IOException e) {
        System.out.println ( "Exception." + e );
    } finally {
        if (bis != null)
            bis.close();
        if (bos != null)
            bos.close();
    }
   %>
    </body>
</html>
