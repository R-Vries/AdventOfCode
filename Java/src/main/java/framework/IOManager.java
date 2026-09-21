package main.java.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IOManager {
    public final int year;
    public final int day;
    private final String dayString;

    public IOManager(int year, int day) {
        this.year = year;
        this.day = day;
        this.dayString = String.format("%02d", day);
    }

    public List<String> readInput() throws IOException, InterruptedException {
        Path inputFile = Path.of("src/main/resources/" + year + "/inputs/day" + dayString + ".txt");

        if (!Files.exists(inputFile)) {
            System.out.println("Input file not found");
            fetchInput();
        }

        return Files.readAllLines(inputFile);
    }
    
    public List<String> readTest() throws IOException {
        Path inputFile = Path.of("src/main/resources/" + year + "/tests/day" + dayString + ".txt");
        
        if (!Files.exists(inputFile)) {
            fetchTest();
        }
        return Files.readAllLines(inputFile);
    }

    private void fetchTest() throws IOException {
        System.out.println("Paste teste input below. End with two empty lines:");

        List<String> lines = new ArrayList<>();
        int emptyCount = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line = br.readLine();

            if (line == null) {
                break;
            }

            if (line.isEmpty()) {
                emptyCount++;
                if (emptyCount == 2) {
                    break;
                }
            } else {
                emptyCount = 0;
                lines.add(line);
            }
        }
        Path targetFile = Path.of("src/main/resources/" + year + "/tests/day" + dayString + ".txt");

        Files.createDirectories(targetFile.getParent());
        Files.writeString(targetFile, String.join("\n", lines));

        System.out.println("Input saved to " + targetFile);
    }

    private void fetchInput() throws IOException, InterruptedException {
        Path cookieFile = Path.of("../cookie.txt");

        if (!Files.exists(cookieFile)) {
            throw new IllegalStateException(
                    "Missing cookie.txt in project root. Place your AoC session cookie inside."
            );
        }

        String sessionCookie = Files.readString(cookieFile).trim();

        if (sessionCookie.isEmpty()) {
            throw new IllegalStateException("cookie.txt is empty.");
        }

        URI uri = URI.create(
                "https://adventofcode.com/" + year + "/day/" + day + "/input"
        );

        HttpRequest request = HttpRequest.newBuilder(uri)
                .GET()
                .header("Cookie", "session=" + sessionCookie)
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException(
                    "Failed to download input. HTTP status: " + response.statusCode()
            );
        }

        Path targetFile = Path.of(
                "src/main/resources/" + year + "/inputs/day" + dayString + ".txt"
        );

        Files.createDirectories(targetFile.getParent());
        Files.writeString(targetFile, response.body());

        System.out.println("Downloaded input to " + targetFile);

    }
}
