package com.guigarage.lessfx.converters.mathematics;

import com.guigarage.lessfx.converters.LessStyleConverter;
import javafx.css.ParsedValue;
import javafx.scene.text.Font;

import java.text.DecimalFormat;
import java.util.regex.Matcher;

/**
 * @author Robin Küster
 * @version 1.0-SNAPSHOT
 * @since 2015-01-11
 */
public class PercentageConverter extends LessStyleConverter<String, String> {
    /**
     * Regular Expression to parse the function call.
     */
    private final static String REGEX = "^percentage\\((-?[0-9]+\\.?[0-9]*)\\)$";

    /**
     * Initialization-on-demand holder
     */
    private static class Holder {
        static final PercentageConverter INSTANCE = new PercentageConverter();
    }

    /**
     * Instance of the converter that got initialize in the Holder.
     *
     * @return Instance of the converter.
     */
    public static PercentageConverter getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Converts the given CSS function call to a Java object.
     *
     * @param value ParsedValue containing the function call
     * @param font Font used for functions that depend on the font size
     * @return The result of the function call as a Java object or null if function call failed
     */
    @Override
    public String convert(ParsedValue<String, String> value, Font font) {
        Matcher matcher = this.getMatcher(value.getValue(), REGEX);

        // nonsensical input
        if (matcher == null) {
            return null;
        }
        Double val = Double.valueOf(matcher.group(1)) * 100;
        return new DecimalFormat("#.######").format(val) + "%";
    }
}
