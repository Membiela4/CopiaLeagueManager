package com.example.leaguemanager2.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.*;

public class ImageStorage {
    public void saveImageToDatabase(Image image, String teamName ) {
        BufferedImage bufferedImage = convertToBufferedImage(image);

        try (
                // Connect database
                Connection connection = Connect.getConnect();
                //Statement
                PreparedStatement statement = connection.prepareStatement("UPDATE Team SET icon = ? WHERE team_name = ?")
        ) {
            // Set BufferedImage to bytes
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            //set statement
            statement.setBytes(1, imageBytes);
            statement.setString(2, teamName);

            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage convertToBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        WritablePixelFormat<ByteBuffer> pixelFormat = WritablePixelFormat.getByteBgraInstance();
        PixelReader pixelReader = image.getPixelReader();

        // Obtener los datos de los píxeles uno por uno y copiarlos al BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = pixelReader.getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }

        return bufferedImage;
    }
}
