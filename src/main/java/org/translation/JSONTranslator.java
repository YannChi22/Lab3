package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    public static final String CODE = "alpha3";
    private final JSONArray countries = new JSONArray();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */

    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                countries.put(jsonObject);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> languages = new ArrayList<>();

        for (int i = 0; i < countries.length(); i++) {
            JSONObject obj = countries.getJSONObject(i);
            if (obj.getString(CODE).equals(country)) {
                for (String key: obj.keySet()) {
                    if (!"alpha3".equals(key) && !"alpha2".equals(key) && !"id".equals(key)) {
                        languages.add(obj.getString(key));
                    }
                }
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> country = new ArrayList<>();

        for (int i = 0; i < countries.length(); i++) {
            JSONObject obj = countries.getJSONObject(i);
            country.add(obj.getString(CODE));
        }
        return country;
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        for (int i = 0; i < countries.length(); i++) {
            JSONObject obj = countries.getJSONObject(i);
            if (obj.getString(CODE).equals(country)) {
                return obj.getString(language);
            }
        }
        return null;
    }
}
