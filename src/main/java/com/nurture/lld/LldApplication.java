package com.nurture.lld;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.nurture.lld.commands.CommandExecutor;
import com.nurture.lld.config.ApplicationConfig;

public class LldApplication {

	public static void main(String[] args) {
		try {
            ApplicationConfig config = new ApplicationConfig();
            CommandExecutor executor = config.getExecutor();

            FileInputStream fis = new FileInputStream("input.txt");
            Scanner sc = new Scanner(fis); 
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line != null && !line.isEmpty()) {
                    List<String> tokens = Arrays.asList(line.split(" "));
                    executor.executeCommand(tokens.get(0),tokens);
                }
            }
            sc.close();
        } catch (IOException e) {
        }
	}

}
