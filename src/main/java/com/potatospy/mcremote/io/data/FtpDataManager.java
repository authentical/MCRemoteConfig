package com.potatospy.mcremote.io.data;

import com.potatospy.mcremote.util.DateTimeManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.net.ftp.FTPFile;

import java.time.format.DateTimeFormatter;


public class FtpDataManager {

    private static FtpDataManager instance = new FtpDataManager();

    private ObservableList<RemoteFile> remoteFiles = FXCollections.observableArrayList();

    private DateTimeFormatter formatter;


    public static FtpDataManager getInstance(){return instance;}

    private FtpDataManager(){

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
    public void loadDirectoryContents(FTPFile[] ftpFiles, FTPFile[] ftpDirectories, String parentDirectory){

        removeAllFiles();   // Clear remoteFiles list


        // Add ../ for every directory Todo what about root directory...
        addRemoteFile(new RemoteFile(
                    "../",
                    "../",
                null,
                null,
                true
        ));

        for(int i =0; i< ftpFiles.length; i++){

            addRemoteFile(new RemoteFile(
                    ftpFiles[i].getName(),
                    ftpFiles[i].getLink(), // TODO this is symbolic link
                    DateTimeManagement.toLocalDateTime(ftpFiles[i].getTimestamp()),
                    null,
                    false));
        }
        for(int i =0; i< ftpDirectories.length; i++){

            addRemoteFile(new RemoteFile(
                    "./" + ftpDirectories[i].getName(),
                    ftpDirectories[i].getName() + "/",
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

    // Todo might want to create members for these but for now i dont know
    public int getCurrentDirectoryFileCount(){


        int fileCount = 0;
        for(RemoteFile file: remoteFiles){

            if(!file.isDirectory()){
                fileCount++;
            }
        }
        return fileCount;
    }
    public int getCurrentDirectoryDirectoryCount(){ // Todo nice name


        int dirCount = 0;
        for(RemoteFile file: remoteFiles){

            if(file.isDirectory()){
                dirCount++;
            }
        }
        return dirCount;
    }


}
