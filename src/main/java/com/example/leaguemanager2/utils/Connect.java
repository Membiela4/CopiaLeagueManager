package com.example.leaguemanager2.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Connect {
    private final String file ="conexion.xml";
    private static Connect _newInstance;
    private Connect() {
        ConnectionData cd = loadXML();

        try {
            con = DriverManager.getConnection(cd.getServer()+"/" +cd.getDatabase(), cd.getUsername(), cd.getPassword());
        } catch (SQLException e) {
            con = null;
            e.printStackTrace();
        }
    }

    public static Connection getConnect() {
        if(_newInstance == null) {
            _newInstance = new Connect();
        }
        return con;
    }

    private static Connection con;

    /**
     *
     * Metodo que carga la conexion XML
     * @return conexion establecida
     */
    public  ConnectionData loadXML() {
        ConnectionData con = new ConnectionData();
        JAXBContext jaxbContext;
        try{
            jaxbContext = JAXBContext.newInstance(ConnectionData.class);
            Unmarshaller jaxunmarshaller = jaxbContext.createUnmarshaller();
            con = (ConnectionData) jaxunmarshaller.unmarshal(new File(file));
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }
}