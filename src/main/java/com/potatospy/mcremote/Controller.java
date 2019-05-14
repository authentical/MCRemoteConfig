package com.potatospy.mcremote;


import com.potatospy.mcremote.ClientManager;
import com.potatospy.mcremote.io.data.FtpDataManager;
import com.potatospy.mcremote.io.data.RemoteFile;
import com.potatospy.mcremote.io.connection.ConnectionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;


public class Controller implements Initializable {

    private ClientManager clientMgr = new ClientManager();


    // FX fields and views
    @FXML
    private ListView<RemoteFile> fileListView;  // Files ListView Todo when directory is big... the list runs down below window
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

    @FXML
    private Text fileCountText;
    @FXML
    private Text directoryCountText;

    @FXML
    private Text logText;


//    public Controller(ClientManager clientMgr) {
//        this.clientMgr = new ClientManager();
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Configure the way files are selected in the List: One file at a time
        fileListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fileListView.getSelectionModel().selectFirst(); // Todo test there is always a first item to select

    }


    // If the RemoteFile is a File, Get the file contents from the file entry selected in the ListView,
    // download it's contents and present it in the fileContentsTextArea,
    // otherwise it's a directory so openConnection that directory and reload list
    @FXML
    public void handleFileListViewClick() {
        // Todo add log text

        // If the file list isn't empty, check which item has been clicked
        if (!fileListView.getItems().isEmpty()) {

            RemoteFile selectedFile = fileListView.getSelectionModel().getSelectedItem();

            // Load the file content if it's a file,
            // Load the directory list if it's a directory
            if(!selectedFile.isDirectory()){

                // Load file contents
                fileContentsTextArea.setText(selectedFile.getContent());

            }else{

                // Clear UI directory list and file contents text area
                fileListView.getItems().clear();
                fileContentsTextArea.setText("");

                // Download directory list and update UI
                downloadAndUpdateDirectoryList(selectedFile);
            }
        } else {
            System.out.println("No files in list");
        }
    }


    // Call deleteFile when key is pressed ////////////////////////////
    public void handleKeyPressed(KeyEvent keyEvent) {
        // Todo add log text
        // New windows "Are you sure?"

        RemoteFile file = fileListView.getSelectionModel().getSelectedItem();
        if (file != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {            // Todo Call connectionManager handler?
                deleteFile(file);
            }
        }
    }


    //  delete File from ListView  //////////////////////////////////
    public void deleteFile(RemoteFile file) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete File");
        alert.setHeaderText("Delete item: " + file);
        alert.setContentText("Are you sure?"); ////////////////////////////////// Todo this aint gonna work anymore
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            FtpDataManager.getInstance().deleteRemoteFile(file);
        }
    }


    // LOGIN and LOGOUT ///////////////////////////////////////////
    @FXML
    public void login() {
        // Todo add log text

        clientMgr.login(ipTextField.getText().trim(),
                portTextField.getText().trim(),
                usernameTextField.getText().trim(),
                passwordPassworldField.getText().trim());


        downloadAndUpdateDirectoryList(null);
    }


    @FXML
    public void disconnect() {
        // Todo add log text update

        clientMgr.disconnect();

        // Clear UI directory list and file contents area
        fileListView.getItems().clear();
        fileContentsTextArea.setText("");
    }


    public void downloadAndUpdateDirectoryList(RemoteFile remoteFile){


        fileListView.setItems(
                clientMgr.getDirectory(remoteFile)
        );
        fileListView.getSelectionModel().selectFirst();

        // Update UI file and directory count
        fileCountText.setText("Files: " + clientMgr.getFileCount() + " ");
        directoryCountText.setText("Directories: " + clientMgr.getDirectoryCount());
    }




    // EXIT ///////////////////////////////////////////////
    @FXML
    public void handleExit() {
        Platform.exit();
    }
}
