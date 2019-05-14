package com.potatospy.mcremote.ui.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveLoad {

    private static String filename = "lastHost.txt";
    //lasthost
    //lastip
    //lastuser


    //constructor


    // FILE store and load

    public static void saveLastHost() throws IOException {

        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try{

            // bw.write host

            bw.newLine();

            // bw.write port

            bw.newLine();

            // bw.write user



        } finally {
            if(bw!=null){
                bw.close();
            }
        }
    }

    public static void loadLastHost() throws IOException{

        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;


        try{




        } finally {
            if(br!=null){
                br.close();
            }
        }
    }
}
