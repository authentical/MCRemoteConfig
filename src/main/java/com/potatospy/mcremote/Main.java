package com.potatospy.mcremote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Main extends Application {

    // Main Window dimensions
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 400;


    @Override
    public void start(Stage primaryStage) throws Exception{

        // Set min width and height Todo
        // Todo where can I do the caching of downloaded files



        // == MAIN WINDOW ==

        String mainwindow = "fxml/mainwindow.fxml";

        Parent root = null;
        URL mainwindowUrl = null;

        try {

            mainwindowUrl = getClass().getClassLoader().getResource(mainwindow);
            root = FXMLLoader.load(mainwindowUrl);
            System.out.println(" mainwindow = " + mainwindow);

        }catch (Exception ex){
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + mainwindowUrl );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            throw ex;
        }


        primaryStage.setTitle("MCRemoteConfig");

        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }




    /////////////////////////////////////////////////////////
    // Save last IP/Port/User to file

    @Override
    public void stop() throws Exception {
        try {

            SaveLoad.saveLastHost();

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /////////////////////////////////////////////////////////
    // Read previous config from file
    @Override
    public void init() throws Exception {
        try {

            SaveLoad.loadLastHost();

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
