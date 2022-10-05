package com.epam.servlet;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

@WebServlet(name = "PosterImages", value = "/posterImages/*")
public class PosterImages extends HttpServlet {
    String path;

    @Override
    public void init() throws ServletException {

        try (InputStream inputStream = PosterImages.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            path = properties.getProperty("image.directory");
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String img = request.getPathInfo().substring(1);
        BufferedImage bufferedImage = ImageIO.read(new File(path + File.separator + img));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
        byte[] image = byteArrayOutputStream.toByteArray();
        response.setContentType(getServletContext().getMimeType(img));
        response.setContentLength(image.length);
        response.getOutputStream().write(image);


    }

}
