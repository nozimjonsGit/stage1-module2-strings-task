package com.epam.mjc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {

        List<String> result = new ArrayList<>();
        StringBuilder subStrings = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            String character = String.valueOf(source.charAt(i));
            if (delimiters.contains(character)) {
                if (subStrings.length() > 0) {
                    result.add(subStrings.toString());
                    subStrings = new StringBuilder();
                }
            } else {
                if (Character.isAlphabetic(source.charAt(i)) || Character.isDigit(source.charAt(i)))
                    subStrings.append(source.charAt(i));
            }
        }

        if (subStrings.length() > 0)
            result.add(subStrings.toString());



        return result;
    }
}
