package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PythonScriptRunner {
    public static void runTest(int arg1, double arg2) {
        String[] command = {"python", "test.py", Integer.toString(arg1), Double.toString(arg2)};
        runScriptAndReadOutput(command);
    }

    // TODO: functions for all python scripts

    private static void runScript(String[] command, boolean blocking) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            if (blocking) {
                process.waitFor();
            }
        } catch(Exception e) {
            System.out.println("Exception Raised" + e.toString());
        }
    }

    private static void runScriptAndReadOutput(String[] command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStream stdout = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null){
                System.out.println("stdout: "+ line);
            }
        } catch (IOException e) {
            System.out.println("Exception in reading output" + e.toString());
        } catch(Exception e) {
            System.out.println("Exception Raised" + e.toString());
        }
    }
}
