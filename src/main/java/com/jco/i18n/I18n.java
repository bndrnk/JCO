package com.jco.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization class
 *
 * @author Bondoronok_P
 *         Date: 13.03.13
 */
public enum I18n {

    TITLE("jco.layout.title"),
    TILE_SOURCE("jco.layout.tile.source"),
    BUTTON_SAVE("jco.layout.button.save"),
    BUTTON_CANCEL("jco.layout.button.cancel"),
    BUTTON_RUN("jco.layout.button.run"),
    LOAD_ROUTE("jco.loader.load.route"),
    LOAD_BASE_COORDINATE("jco.loader.load.baseCoordinate"),
    SELECTED_ITEM("jco.info.item.selected"),
    LOAD_GPX_TIP("jco.info.load.gpx.tip");

    private static final String LOCALE_FILE_NAME = "i18n";
    // TODO find another solution
    private static final String INPUT_CHARSET = "ISO-8859-1";
    private static final String OUTPUT_CHARSET = "UTF-8";

    private static final Locale DEFAULT_LOCALE = new Locale("ru", "RU");
    private String propertyName;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(LOCALE_FILE_NAME, DEFAULT_LOCALE);

    I18n(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Returns localised string
     * @return
     */                // TODO rewrite
    public String getText() {
        String fetchedString = BUNDLE.getString(propertyName);
        try {
            fetchedString =  new String(fetchedString.getBytes(INPUT_CHARSET), OUTPUT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            // todo handle
        }
        return fetchedString;
    }

}
