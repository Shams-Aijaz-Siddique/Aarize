import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import java.io.FileReader;
import java.math.BigInteger;
import java.util.Map;

public class Program {

    // Method to decode values based on the base
    public static long decodeValue(String value, int base) {
        return new BigInteger(value, base).longValue();
    }

    // Lagrange interpolation function
    public static double lagrangeInterpolation(int[] x, long[] y, int n) {
        double result = 0;

        for (int i = 0; i < n; i++) {
            double term = y[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    term *= (0 - x[j]) / (double) (x[i] - x[j]);
                }
            }
            result += term;
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            // Read JSON file
            FileReader reader = new FileReader("input.json");
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Extract n and k from "keys"
            JsonObject keys = jsonObject.getAsJsonObject("keys");
            int n = keys.get("n").getAsInt();
            int k = keys.get("k").getAsInt();

            int[] x = new int[n];
            long[] y = new long[n];

            // Iterate through the JSON object to extract base and value pairs
            int index = 0;
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                if (!entry.getKey().equals("keys")) {
                    int xValue = Integer.parseInt(entry.getKey());
                    JsonObject valueObject = entry.getValue().getAsJsonObject();
                    String base = valueObject.get("base").getAsString();
                    String value = valueObject.get("value").getAsString();

                    x[index] = xValue; // The key is the x value
                    y[index] = decodeValue(value, Integer.parseInt(base)); // Decode the value based on the base
                    index++;
                }
            }

            // Perform Lagrange interpolation
            double c = lagrangeInterpolation(x, y, k);

            // Output result
            System.out.println("The constant term (c) is: " + c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
