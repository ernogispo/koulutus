
var helsinkiBuildingSource = new ol.source.Vector({
    format: new ol.format.GeoJSON(),
    url: function (extent) {
        return 'https://kartta.hel.fi/ws/geoserver/avoindata/wfs?' +
            'service=WFS&version=1.1.0&request=GetFeature&typename=avoindata:Rakennukset_kartalla&' +
            'outputFormat=application/json&srsname=EPSG:3857&' +
            'bbox=' + extent.join(',') + ',EPSG:3857&' +
            'maxFeatures=100';
    },
    strategy: ol.loadingstrategy.bbox
});

var map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.TileWMS({
                url: 'http://tiles.kartat.kapsi.fi/taustakartta',
                params: {'LAYERS': 'taustakartta', 'TILED': true},
                serverType: 'mapserver'
            })
        }),
        new ol.layer.Vector({
            source: helsinkiBuildingSource,
            style: new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: 'rgba(255, 0, 0, 1.0)',
                    width: 3
                })
            })
        })
    ],
    view: new ol.View({
        center: ol.proj.fromLonLat([24.96362, 60.20504]),
        zoom: 17
    })
});

var overlay = new ol.Overlay({
    element: document.getElementById('featureinfo_overlay'),
    positioning: 'center-center'
});

map.on('pointermove', onPointerMove);

function onPointerMove(mapBrowserEvent) {
    var coord = mapBrowserEvent.coordinate;

    var featuresHTML = "";

    if (map.hasFeatureAtPixel(mapBrowserEvent.pixel)) {
        map.forEachFeatureAtPixel(mapBrowserEvent.pixel, function (feature) {
            console.log(feature.getProperties());
            var props = feature.getProperties();
            var html = "<p>" +
                "id: " + props.id + "<br>" +
                "ratu: " + (props.ratu != null ? props.ratu : "-") + "<br>" +
                "tyyppi: " + props.tyyppi +
                "</p>";
            featuresHTML += html;
        });
        var element = overlay.getElement();
        element.innerHTML = featuresHTML;
        overlay.setPosition(coord);
        map.addOverlay(overlay);
    }
    else {
        map.removeOverlay(overlay);
    }
}