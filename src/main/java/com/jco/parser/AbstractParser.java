package com.jco.parser;

import com.jco.entity.database.Location;

import java.io.InputStream;
import java.util.List;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 26.02.13
 */
public abstract class AbstractParser {

    protected List<Location> foundedData;
    protected long parsedTime;

    /**
     *
     * @param stream
     */
    public abstract void parse(InputStream stream);

    public List<Location> getFoundedData() {
        return foundedData;
    }

    protected void setFoundedData(List<Location> foundedData) {
        this.foundedData = foundedData;
    }

    public long getParsedTime() {
        return parsedTime;
    }

    public void setParsedTime(long parsedTime) {
        this.parsedTime = parsedTime;
    }
}
