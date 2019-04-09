package com.potatospy.mcremote;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.*;


public class Controller implements Initializable {

    StringBuilder sb = new StringBuilder(); // For compiling the log and any errors

    Connection connection;


    // FX fields and views
    @FXML
    private ListView<RemoteFile> fileListView;  // Files ListView
    @FXML
    private TextArea fileContentsTextArea;      // File content TextArea Todo set this not editable when not connected to a server

    @FXML
    private TextField ipTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPassworldField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // Configure way files are selected in the List
        // One file at a time
        fileListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //fileListView.getSelectionModel().selectFirst();


        /*
         * Clicking a file will select it: The cell will be selected and
         * the contents of the file will be downloaded and shown in the fileContentsTextArea
         *
         * */


    }


    // If the RemoteFile is a File, Get the file contents from the file entry selected in the ListView,
    // download it's contents and present it in the fileContentsTextArea,
    // otherwise it's a directory so open that directory and reload list
    @FXML
    public void handleClickListView() {
        if (!fileListView.getItems().isEmpty()) {

            RemoteFile file = fileListView.getSelectionModel().getSelectedItem();

            if(file.isDirectory()) {

                fileContentsTextArea.setText(connection.getFileContents(file.getFileName()));
            }else{
                getFileEntriesFromDirectory(file.getFileloc()+file.getFileName()); ///////////////////////////////////// Todo hmmmmm
                fileListView.setItems(FileData.getInstance().getRemoteFiles());
            }
        } else {
            System.out.println("No files in list");
        }
    }


    // LOGIN and LOGOUT /////////////////////////////////////////// Todo Move?
    @FXML
    public void login() {

//        connection = new Connection(
//                ipTextField.getText().trim(),
//                Integer.parseInt(portTextField.getText().trim()),
//                usernameTextField.getText().trim(),
//                passwordPassworldField.getText().trim());
        connection = new Connection(
                "192.168.3.7",
                Integer.parseInt("6000"),
                "ftpuser",
                "12345");

        // Get  loggable info and error info from Connection
        sb.append(connection.open());
        System.out.println(sb.toString()); // Todo Pass this to the GUI so user can see

        //
        getFileEntriesFromDirectory(null);
        fileListView.setItems(FileData.getInstance().getRemoteFiles());
        sb.setLength(0);
    }


    @FXML
    public void disconnect() {

        // Get loggable info and error info from Connection
        sb.append(connection.close());
        System.out.println(sb.toString());  // Todo Pass this to the GUI so user can see

        // Remove all from FileData list and ListView
        FileData.getInstance().removeAllFiles();
        fileListView.getItems().clear();
        fileContentsTextArea.setText("");
    }


    // Call deleteFile when key is pressed ////////////////////////////
    public void handleKeyPressed(KeyEvent keyEvent) {

        RemoteFile file = fileListView.getSelectionModel().getSelectedItem();
        if (file != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {            // Todo Call connection handler?
                deleteFile(file);
            }
        }
    }


    // Download file entries from ftp directory and load them into fileListView
    public void getFileEntriesFromDirectory(String directory){

        connection.downloadDirectory(directory); // Get files from initial directory
        fileListView.setItems(FileData.getInstance().getRemoteFiles());
        fileListView.getSelectionModel().selectFirst();

    }


    //  delete File from ListView  //////////////////////////////////
    public void deleteFile(RemoteFile file) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete File");
        alert.setHeaderText("Delete item: " + file);
        alert.setContentText("Are you sure?"); ////////////////////////////////// Todo this aint gonna work anymore
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            FileData.getInstance().deleteRemoteFile(file);
        }
    }


    // EXIT ///////////////////////////////////////////////
    @FXML
    public void handleExit() {
        Platform.exit();
    }
}
