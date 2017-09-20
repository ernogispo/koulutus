
var kumpula = ol.proj.transform([24.96362, 60.20504], 'EPSG:4326', 'EPSG:3857');
var gispo = ol.proj.transform([24.93288, 60.16570], 'EPSG:4326', 'EPSG:3857');

var map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    view: new ol.View({
        center: kumpula,
        zoom: 14
    }),
    loadTilesWhileAnimating: true // Huomaa tämä, jotta kartta näkyy kunnolla animoidessa
});

function doPan(location) {
    map.getView().animate({
        center: location,
        duration: 3000,
        easing: ol.easing.inAndOut
    });
}
