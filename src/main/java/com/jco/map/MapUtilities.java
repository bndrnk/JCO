package com.jco.map;

import java.awt.*;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 07.03.13
 */
 final class MapUtilities {

    /**
     * Frame width and height
     */
     static final int WIDTH = 400;
     static final int HEIGHT = 400;

    /**
     * Components headers
     * TODO use i18n
     */
     static final String FRAME_HEADER = "Java Cost Optimizer";
     static final String TILE_SOURCES_LABEL = "Tile source:";
     static final String LOAD_GPX_LABEL = "Load GPS data";
     static final String SELECTED_VEHICLE_INFO = "Information about selected vehicle:";
     static final String LOAD_NEW_BASE_POINT = "Load new Base coordinate";
     static final String RUN_BUTTON = "Run";
     static final String STRING_FORMAT_PATTERN = "%s";

    /**
     * Components dimensions
     */
     static final Dimension VEHICLE_INFO_LABEL_DIMENSION = new Dimension(250, 800);
     static final Dimension SEPARATOR_DIMENSION = new Dimension(250, 1);
     static final Dimension TILE_SOURCE_DIMENSION = new Dimension(40, 1);


    public static final String HELP_INFO = "<html><br>Before saving data, you should select location on map.<br>In case if base coordinate doesn't defined, you should define it first of all.</html>";
    public static final String SAVE_BUTTON = "Save";
    public static final String CANCEL_BUTTON = "Cancel";
    public static final String EMPTY = "";

    private MapUtilities() {};
}
