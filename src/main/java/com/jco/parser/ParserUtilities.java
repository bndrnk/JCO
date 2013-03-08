package com.jco.parser;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 08.03.13
 */
public final class ParserUtilities {

    public static final String TIME_TAG = "traveltime";
    public static final String SPLIT_PATTERN = "\\s*(\\s)\\s*";
    public static final String COORDINATES = "coordinates";
    public static final String COMMA = ",";
    public static final byte LATITUDE_INDEX = 1;
    public static final byte LONGITUDE_INDEX = 0;
    public static final long DEFAULT_TIME_TRAVEL = 0l;
    public static final byte FIRST_ITEM = 0;
    public static final byte COORDINATE_VALUES_COUNT = 2;

    private ParserUtilities(){};

}
