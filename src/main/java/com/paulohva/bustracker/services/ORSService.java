package com.paulohva.bustracker.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ORSService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_KEY = "5b3ce3597851110001cf6248f16eab547e1d4b96bbdd20de47d2abe5";
    private final String URL = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";

    public List<double[]> getRoadPath(double startLat, double startLng, double endLat, double endLng) {
        List<double[]> fallbackPath = List.of(
                new double[]{startLat, startLng},
                new double[]{endLat, endLng}
        );
        try {
            Thread.sleep(1500); // 1.5 seconds to avoid ORS rate limits
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // good practice
            System.err.println("❌ Sleep interrupted: " + e.getMessage());
        }
            try {
            System.out.printf("🛰️ Requesting ORS path: FROM (%.6f, %.6f) TO (%.6f, %.6f)%n", startLat, startLng, endLat, endLng);

            // Create JSON body
            JSONObject body = new JSONObject();
            JSONArray coordinates = new JSONArray();
            coordinates.put(new JSONArray(List.of(startLng, startLat))); // ORS needs [lng, lat]
            coordinates.put(new JSONArray(List.of(endLng, endLat)));
            body.put("coordinates", coordinates);

            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", API_KEY);

            HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

            // Send POST request to ORS
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                System.err.printf("❌ ORS API error: %s%n", response.getStatusCode());
                return fallbackPath;
            }

            // Parse coordinates from JSON
            JSONObject json = new JSONObject(response.getBody());
            JSONArray coords = json.getJSONArray("features")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONArray("coordinates");

            if (coords.length() < 2) {
                System.err.println("❌ ORS returned fewer than 2 points. Using fallback path.");
                return fallbackPath;
            }

            List<double[]> result = new ArrayList<>();
            for (int i = 0; i < coords.length(); i++) {
                JSONArray point = coords.getJSONArray(i);
                result.add(new double[]{point.getDouble(1), point.getDouble(0)}); // [lat, lng]
            }

            System.out.printf("✅ ORS returned %d path points.%n", result.size());
            return result;

        } catch (Exception e) {
            System.err.printf("❌ ORS exception: %s%n", e.getMessage());
            System.err.println("⚠️ Falling back to straight-line path.");
            return fallbackPath;
        }
    }

}