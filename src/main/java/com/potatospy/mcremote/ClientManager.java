package com.potatospy.mcremote;


import com.potatospy.mcremote.io.connection.ConnectionManager;
import com.potatospy.mcremote.io.connection.messages.ConnectionMessageManager;
import com.potatospy.mcremote.io.data.FtpDataManager;
import com.potatospy.mcremote.io.data.RemoteFile;
import javafx.collections.ObservableList;

// ClientManager is being used to remove some of the tight coupling I saw in my Controller
// The Controller should only have to use ClientManager in order to get everything done
public class ClientManager {

    private ConnectionManager connMgr;


    public void login(String serverIP, String serverPort, String username, String password){


//        connMgr = new ConnectionManager(
//                ipTextField.getText().trim(),
//                Integer.parseInt(portTextField.getText().trim()),
//                usernameTextField.getText().trim(),
//                passwordPassworldField.getText().trim());
        connMgr = new ConnectionManager(
                "192.168.3.7",
                Integer.parseInt("6000"),
                "ftpuser",
                "12345");   // Todo If connectionManager dies and user doesn't logout... the list view doesnt get wiped

//        connMgr = new ConnectionManager(
//                "198.143.191.178",
//                Integer.parseInt("21"),
//                "cvspencer@gmail.com.124126",
//                "Av)7@2Pi4tN7uS");   // Todo If connectionManager dies and user doesn't logout... the list view doesnt get wiped



        // Open the connection and
        connMgr.openConnection();
    }


    public void disconnect(){

        // Get loggable info and error info from ConnectionManager
        connMgr.closeConnection();

        // Remove all from FtpDataManager list and ListView
        FtpDataManager.getInstance().removeAllFiles();
    }


    // Download entries from ftp directory and load them into fileListView
    public ObservableList<RemoteFile> getDirectory(RemoteFile remoteFile){


        FtpDataManager.getInstance().removeAllFiles();


        // Get file entries from directory and load them into fileListView from FtpDataManager list

        String directory = remoteFile==null?null:remoteFile.getFilePath(); // The root directory's filepath is null.

        connMgr.downloadDirectoryEntries(directory);

        return FtpDataManager.getInstance().getRemoteFiles();
    }


    public String getFileCount(){

        return String.valueOf(FtpDataManager.getInstance().getCurrentDirectoryFileCount());
    }

    public String getDirectoryCount(){

        return String.valueOf(FtpDataManager.getInstance().getCurrentDirectoryDirectoryCount());

    }


}
