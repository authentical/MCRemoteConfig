package com.potatospy.mcremote.io.connection;

import com.potatospy.mcremote.io.data.FtpDataManager;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.SocketException;

import static com.potatospy.mcremote.ui.configuration.ConsoleColours.*;

//TOdo need to test for connection statuses when openConnection, closed or intermediate
// Todo clean up the way error/logging is being printed and sent up to Controller
// TOdo connection isn't staying alive
// TOdo currently theres no way to check the connection is alive and ready. It just dies and GUI doesnt know
//https://www.codejava.net/java-se/networking/ftp/java-ftp-file-upload-tutorial-and-example
public class ConnectionManager {

    private String serverIP;
    private int serverPort;
    private String username;
    private String password;
    private FTPClient ftpClient;
    private boolean connected = false;


    // ==CONSTRUCTOR==

    public ConnectionManager(String serverIP, int serverPort, String username, String password) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.username = username;
        this.password = password;
    }


    // ==PUBLIC METHODS==

    public String openConnection() {

        ftpClient = new FTPClient();
        StringBuilder error = new StringBuilder();  // I want to pass useful errors up to the username


        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));



        System.out.println("Running ftpClient.connect(serverIP, serverPort);");
        try {
            ftpClient.connect(serverIP, serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
            return error.append(
                    ANSI_RED + "Got SocketException while connecting. " +
                            "ConnectionManager refused or serverIP/serverPort is incorrect.").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return error.append("Got IOException while connecting.").toString();
        }

        int reply = ftpClient.getReplyCode();
        System.out.println(ANSI_BLUE_BACKGROUND + ANSI_WHITE + "FTP Reply " + reply + ANSI_RESET);


        System.out.println("Running if (!FTPReply.isPositiveCompletion(reply)) {");
        if (!FTPReply.isPositiveCompletion(reply)) {
            closeConnection();    // Will this lock up?
            return error.append(ANSI_RED + "Error connecting to FTP Server: FTP Reply " + reply).toString();
        }

        System.out.println("Connected. Running ftpClient.login(username, password)");
        try {
            ftpClient.login(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
            return error.append("ConnectionManager interrupted.").toString();
        }

        connected = true;

        return ANSI_GREEN_BACKGROUND + "Password Accepted. Logged in." + ANSI_RESET;
    }


    // Download FTPFile[] of files and FTPFile[] of directories
    // and give them to FtpDataManager for parsing and storage
    public void downloadDirectoryEntries(String directory) {

        try {

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // Todo doesn't download all directories
            // Copy the files into the FtpDataManager list. If directory null, its assumed to be ftp user root
            FtpDataManager.getInstance().loadDirectoryContents(
                    // Get files and directorie, filter for files only
                    ftpClient.listFiles(directory, new FTPFileFilter() {
                        @Override
                        public boolean accept(FTPFile file) {
                            return file.isFile();
                        }
                    }),
                    // Get directories
                    ftpClient.listDirectories(directory),
                    // Pass parent directory to child directory's ../ entry
                    directory);

        } catch (IOException e) {// Handle Todo

        }
    }

    public String getFileContents(String fileName) {

        String fileContent = "";


        try(ByteArrayOutputStream  byteArrayStream = new ByteArrayOutputStream()){



            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpClient.retrieveFile(fileName, byteArrayStream);    // Returns boolean

            System.out.println("Got file " + fileName + " with contents: " + byteArrayStream.toString());


            return byteArrayStream.toString();

        } catch (IOException ex) {
            /* Todo */
        }

        return null;
    }


    public String closeConnection() {

        StringBuilder error = new StringBuilder();  // I want to pass useful errors up to the username

        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return error.append("IOException during closeConnection connection").toString();
        } finally {

            connected=false;
            return error.append(ANSI_RESET + "\nConnectionManager closed.").toString();
        }
    }


    public boolean isConnected(){

        return connected;
    }

}
