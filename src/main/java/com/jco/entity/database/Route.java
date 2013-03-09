package com.jco.entity.database;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 09.03.13
 */
public class Route {

    private long routeId;
    private String routeName;
    private long routeTime;

    public Route() {

    }

    public Route(long routeId, String name, long time) {
        this();
        this.routeId = routeId;
        this.routeName = name;
        this.routeTime = time;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public long getRouteTime() {
        return routeTime;
    }

    public void setRouteTime(long routeTime) {
        this.routeTime = routeTime;
    }
}
