package com.example.leaguemanager2.test;

import com.example.leaguemanager2.dao.TeamDAO;
import com.example.leaguemanager2.modelDomain.Team;
import com.example.leaguemanager2.utils.Connect;
import com.example.leaguemanager2.utils.ImageStorage;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class Test4 {

        public static void main(String[] args) {
            ImageStorage imageStorage = new ImageStorage();

// Cargar una imagen de un archivo
            Image image = new Image("com/example/leaguemanager2/images/iconoEquipo.png");

// Guardar la imagen en la base de datos para un equipo específico
            String teamName = "Madrid";
            imageStorage.saveImageToDatabase(image,teamName );
        }
    }


