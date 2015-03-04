package com.guigarage.lessfx.converters.color.definition;

import com.guigarage.lessfx.converters.LessStyleConverter;
import javafx.css.ParsedValue;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.regex.Matcher;

/**
 * @author Robin Küster
 * @since 2015-03-04
 */
public class HSVAConverter extends LessStyleConverter<String, Color> {
    private final static String REGEX = "^hsva\\(([0-9]{1,3}),\\s*((?:[0-1]\\.[0-9]*)|(?:[0-9]{1,3}%)),\\s*((?:[0-1]\\.[0-9]*)|(?:[0-9]{1,3}%)),\\s*((?:[0-1]\\.[0-9]*)|(?:[0-9]{1,3}%))\\)$";

    private static class Holder {
        static final HSVAConverter INSTANCE = new HSVAConverter();
    }

    public static HSVAConverter getInstance() {
        return Holder.INSTANCE;
    }

    public HSVAConverter() {

    }

    @Override
    public Color convert(ParsedValue<String, Color> value, Font font) {
        Matcher matcher = getMatcher(value.getValue(), REGEX);

        // nonsensical input
        if (matcher == null) {
            return null;
        }

        int h = Integer.valueOf(matcher.group(1));
        if (h > 360 || h < 0) {
            return null;
        }

        double color[] = new double[2];

        for (int i = 2; i <= 3; i++) {
            String group = matcher.group(i);
            if (group.charAt(group.length() - 1) == '%') { // value given in percentage
                color[i-2] = Double.valueOf(group.substring(0, group.length() - 1)) / 100;
            } else { // value between 0 and 1
                color[i-2] = Double.valueOf(group);
            }
            if (color[i-2] > 1.0) {
                return null;
            }
        }

        String alphaS = matcher.group(4);
        double alpha;

        if (alphaS.charAt(alphaS.length() - 1) == '%') {
            alpha = Double.valueOf(alphaS.substring(0, alphaS.length() - 1)) / 100;
        } else {
            alpha = Double.valueOf(alphaS);
        }

        if (alpha > 1.0) {
            return null;
        }

        return Color.hsb(h, color[0], color[1], alpha);
    }
}
