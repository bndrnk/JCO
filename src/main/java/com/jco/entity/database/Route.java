package com.jco.entity.database;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 09.03.13
 */
public class Route {

    private long routeId;
    private long routeTime;

    public Route() {
    }

    public Route(long routeId) {
        this();
        this.routeId = routeId;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public long getRouteTime() {
        return routeTime;
    }

    public void setRouteTime(long routeTime) {
        this.routeTime = routeTime;
    }
}
