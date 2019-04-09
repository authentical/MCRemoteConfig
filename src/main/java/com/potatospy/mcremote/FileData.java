package com.potatospy.mcremote;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.net.ftp.FTPFile;

import java.time.format.DateTimeFormatter;


public class FileData {

    private static FileData instance = new FileData();

    private ObservableList<RemoteFile> remoteFiles = FXCollections.observableArrayList();
    private DateTimeFormatter formatter;


    public static FileData getInstance(){return instance;}

    private FileData(){

        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }
    public ObservableList<RemoteFile> getRemoteFiles(){
        return remoteFiles;
    }








///////////////// Todo
    /// Right now the first for loop is picking up directories as well as files.... so directories are being stored
    /// then directories are also added in the second loop

    // Also removeall files isnt propagating to the list view.


    // ADD and DELETE
    public void loadDirectoryContents(FTPFile[] ftpFiles, FTPFile[] ftpDirectories){

        removeAllFiles();   // Clear remoteFiles list


        // Get
        for(int i =0; i< ftpFiles.length; i++){

            addRemoteFile(new RemoteFile(
                    ftpFiles[i].getName(),
                    ftpFiles[i].getLink(),
                    DateTimeManagement.toLocalDateTime(ftpFiles[i].getTimestamp()),
                    null,
                    false));
        }
        for(int i =0; i< ftpDirectories.length; i++){

            addRemoteFile(new RemoteFile(
                    "./" + ftpDirectories[i].getName(),
                    ftpDirectories[i].getLink(),
                    DateTimeManagement.toLocalDateTime(ftpDirectories[i].getTimestamp()),
                    null,
                    true));
        }

    }
    public void addRemoteFile(RemoteFile remoteFile){

        remoteFiles.add(remoteFile);
    }
    public void deleteRemoteFile(RemoteFile remoteFileToDelete){

        remoteFiles.remove(remoteFileToDelete);
    }
    public void removeAllFiles(){
        remoteFiles.removeAll();
    }
//    public void updateFile...


}
