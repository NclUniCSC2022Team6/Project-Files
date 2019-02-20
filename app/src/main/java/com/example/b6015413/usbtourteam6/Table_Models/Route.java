package com.example.b6015413.usbtourteam6.Table_Models;

public class Route {

    private String to;
    private String from;
    private String route;

    public Route(String to, String from, String route) {
        this.to = to;
        this.from = from;
        this.route = route;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route1 = (Route) o;

        if (to != null ? !to.equals(route1.to) : route1.to != null) return false;
        if (from != null ? !from.equals(route1.from) : route1.from != null) return false;
        return route != null ? route.equals(route1.route) : route1.route == null;
    }

    @Override
    public int hashCode() {
        int result = to != null ? to.hashCode() : 0;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (route != null ? route.hashCode() : 0);
        return result;
    }
}
