package com.example.aura;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageGenerator {
    private final String url = "https://api.openai.com/v1/images/generations";
    private final Context context;
    private final String apiKey = "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"; // Replace with your actual OpenAI API key

    public ImageGenerator(Context context) {
        this.context = context;
    }

    public void generate(String prompt, int width, int height, int count, OnLoaded onLoaded) {
        // Validate image size before making the request
        if (!isValidImageSize(width, height)) {
            Toast.makeText(context, "Invalid size. Use 256x256, 512x512, or 1024x1024.", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> arrayList = new ArrayList<>();
        JSONObject js = new JSONObject();

        try {
            js.put("prompt", prompt);
            js.put("n", count);
            js.put("size", width + "x" + height); // Valid sizes: 256x256, 512x512, 1024x1024
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("ImageGenerator", "Request Payload: " + js.toString()); // Log payload for debugging

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, js, response -> {
            if (response != null) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject item = dataArray.getJSONObject(i);
                        arrayList.add(item.getString("url")); // Collect image URLs
                    }
                    onLoaded.loaded(arrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            // Handle error and log detailed information
            Log.e("ImageGenerator", "Error: " + error.getMessage());
            if (error.networkResponse != null) {
                Log.e("ImageGenerator", "Response Code: " + error.networkResponse.statusCode);
                Log.e("ImageGenerator", "Response Data: " + new String(error.networkResponse.data));
            }
            Toast.makeText(context, "Error while generating images", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + apiKey);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                30 * 1000, // Timeout in ms
                3, // Retry attempts
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private boolean isValidImageSize(int width, int height) {
        String size = width + "x" + height;
        return size.equals("256x256") || size.equals("512x512") || size.equals("1024x1024");
    }
}
