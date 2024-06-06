package com.example.ISA2023.back.controllers;

import com.example.ISA2023.back.dtos.CoordinatesDto;
import com.example.ISA2023.back.models.Company;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/map")
public class MapController {
    public MapController() {
    }
    @PostMapping("/")
    public CoordinatesDto runPython(@RequestBody CoordinatesDto list)
    {
        try {
            // Specify the path to your Python script
            String pythonScriptPath = "E:\\4. godina\\ISA\\projekat\\backend\\locator.py";

            String[] command = {"python", pythonScriptPath, list.toString()};

            // Create ProcessBuilder with the Python command and script path
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Redirect error stream to the output stream
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);
            return list;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return list;
    }

}
