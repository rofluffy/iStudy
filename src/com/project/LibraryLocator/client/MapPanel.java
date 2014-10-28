package com.project.LibraryLocator.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class MapPanel extends Widget {

    private Element container;

    public MapPanel() {

        container = DOM.createDiv();
        container.setId("map");

        // this is required by the Widget API to define the underlying element
        setElement(container);
    }

    @Override
    protected void onLoad() {
        super.onLoad();

            initializeMap();
        }
    
    private native JavaScriptObject initializeMap() /*-{

    var latLng = new $wnd.google.maps.LatLng(41.850033, -87.6500523); //around UBC
    var mapOptions = {
        zoom : 14,
        center : latLng,
        mapTypeId : $wnd.google.maps.MapTypeId.ROADMAP
    };
    var mapDiv = $doc.getElementById('map');
    if (mapDiv == null) {
        alert("MapDiv is null!");
    }
    var map = new $wnd.google.maps.Map(mapDiv, mapOptions);
    return map;

}-*/;
    }

