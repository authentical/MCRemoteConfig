package com.potatospy.mcremote;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.SocketException;

import static com.potatospy.mcremote.ConsoleColours.*;

//TOdo need to test for connection statuses when open, closed or intermediate
// Todo clean up the way error/logging is being printed and sent up to Controller

//https://www.codejava.net/java-se/networking/ftp/java-ftp-file-upload-tutorial-and-example
public class Connection {

    private String serverIP;
    private int serverPort;
    private String username;
    private String password;
    private FTPClient client;


    // constructor

    public Connection(String serverIP, int serverPort, String username, String password) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.username = username;
        this.password = password;
    }

    public String open() {

        client = new FTPClient();
        StringBuilder error = new StringBuilder();  // I want to pass useful errors up to the username


        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));



        System.out.println("Running client.connect(serverIP, serverPort);");
        try {
            client.connect(serverIP, serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
            return error.append(
                    ANSI_RED + "Got SocketException while connecting. " +
                            "Connection refused or serverIP/serverPort is incorrect.").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return error.append("Got IOException while connecting.").toString();
        }

        int reply = client.getReplyCode();
        System.out.println(ANSI_BLUE_BACKGROUND + ANSI_WHITE + "FTP Reply " + reply + ANSI_RESET);


        System.out.println("Running if (!FTPReply.isPositiveCompletion(reply)) {");
        if (!FTPReply.isPositiveCompletion(reply)) {
            close();    // Will this lock up?
            return error.append(ANSI_RED + "Error connecting to FTP Server: FTP Reply " + reply).toString();
        }

        System.out.println("Connected. Running client.login(username, password)");
        try {
            client.login(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            close();
            return error.append("Connection interrupted.").toString();
        }


        return ANSI_GREEN_BACKGROUND + "Password Accepted. Logged in." + ANSI_RESET;
    }


    // Download FTPFile[] of files and FTPFile[] of directories
    // and give them to FileData for parsing and storage
    public void downloadDirectory(String directory) {

        try {

            client.setFileType(FTP.BINARY_FILE_TYPE);

            // Copy the files into the FileData list. If directory null, its assumed to be ftp user root Todo really?
            FileData.getInstance().loadDirectoryContents(
                    client.listFiles(directory /*, !FTPFileFilters.DIRECTORIES   Todo doesnt work*/),
                    client.listDirectories());

        } catch (IOException e) {// Handle Todo

        }
    }

    public String getFileContents(String fileName) {

        String fileContent = "";


        try(ByteArrayOutputStream  byteArrayStream = new ByteArrayOutputStream()){



            client.setFileType(FTP.BINARY_FILE_TYPE);

            client.retrieveFile(fileName, byteArrayStream);    // Returns boolean

            System.out.println("Got file " + fileName + " with contents: " + byteArrayStream.toString());


            return byteArrayStream.toString();

        } catch (IOException ex) {
            /* Todo */
        }

        return null;
    }


    public String close() {

        StringBuilder error = new StringBuilder();  // I want to pass useful errors up to the username

        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return error.append("IOException during close connection").toString();
        } finally {
            return error.append(ANSI_RESET + "\nConnection closed.").toString();
        }
    }

}
