//package com.potatospy;
//
//import com.potatospy.mcremote.Connection;
//import org.junit.After;
//import org.junit.Before;
//import org.mockftpserver.fake.FakeFtpServer;
//import org.mockftpserver.fake.UserAccount;
//import org.mockftpserver.fake.filesystem.DirectoryEntry;
//import org.mockftpserver.fake.filesystem.FileEntry;
//import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
//
//import java.io.IOException;
//
//public class ConnectionIntegrationTest {
//
//    private FakeFtpServer fakeFtpServer;
//
//    private Connection connection;
//
//    @Before
//    public void setup() throws IOException {
//        fakeFtpServer = new FakeFtpServer();
//        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));
//
//        UnixFakeFileSystem fileSystem = new UnixFakeFileSystem();
//        fileSystem.add(new DirectoryEntry("/data"));
//        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
//        fakeFtpServer.setFileSystem(fileSystem);
//        fakeFtpServer.setServerControlPort(0);
//
//        fakeFtpServer.start();
//
//        connection = new Connection("localhost", fakeFtpServer.getServerControlPort(), "user", "password");
//        connection.open();
//    }
//
//    @After
//    public void teardown() throws IOException {
//        connection.close();
//        fakeFtpServer.stop();
//    }
//}
