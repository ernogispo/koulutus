
var kumpulaCoordinates = ol.proj.fromLonLat([24.96362, 60.20504]);
var viewKumpula = new ol.View({
    center: kumpulaCoordinates,
    zoom: 14
});

var gispoCoordinates = ol.proj.transform([24.93288, 60.16570], 'EPSG:4326', 'EPSG:3857');
var viewGispo = new ol.View({
    center: gispoCoordinates,
    zoom: 15
});

var map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    view: viewKumpula
});

function switchView() {
    var view = map.getView();
    //console.log(view);

    if (view == viewKumpula) {
        map.setView(viewGispo);
    } else {
        map.setView(viewKumpula);
    }
}

function switchTarget() {
    var target = map.getTarget();
    if (target == 'map') {
        map.setTarget('map2');
    } else {
        map.setTarget('map');
    }
}

var overlay = new ol.Overlay({
    element: document.getElementById('event_overlay'),
    positioning: 'bottom-center'
});

map.on('change:size', function(event) {
    console.log(event);
    var element = overlay.getElement();
    var html = "event.type: " + event.type;
    element.innerHTML = html;
    if (map.getView() == viewKumpula) {
        overlay.setPosition(kumpulaCoordinates);
    } else {
        overlay.setPosition(gispoCoordinates);
    }
    map.addOverlay(overlay);
});

map.on('change:target', function(event) {
    console.log(event);
    var element = overlay.getElement();
    var html = "event.type: " + event.type;
    element.innerHTML = html;
    if (map.getView() == viewKumpula) {
        overlay.setPosition(kumpulaCoordinates);
    } else {
        overlay.setPosition(gispoCoordinates);
    }
    map.addOverlay(overlay);
});

map.on('change:view', function(event) {
    console.log(event);
    var element = overlay.getElement();
    var html = "event.type: " + event.type;
    element.innerHTML = html;
    if (map.getView() == viewKumpula) {
        overlay.setPosition(kumpulaCoordinates);
    } else {
        overlay.setPosition(gispoCoordinates);
    }
    map.addOverlay(overlay);
});

map.on('click', function(event) {
    console.log(event);
    var element = overlay.getElement();
    var html = "event.type: " + event.type;
    element.innerHTML = html;
    if (map.getView() == viewKumpula) {
        overlay.setPosition(kumpulaCoordinates);
    } else {
        overlay.setPosition(gispoCoordinates);
    }
    map.addOverlay(overlay);
});
// map.on('movestart', function(event) {
//     console.log(event);
//     var element = overlay.getElement();
//     var html = "event.type: " + event.type;
//     element.innerHTML = html;
//     if (map.getView() == viewKumpula) {
//         overlay.setPosition(kumpulaCoordinates);
//     } else {
//         overlay.setPosition(gispoCoordinates);
//     }
//     map.addOverlay(overlay);
// });
// map.on('moveend', function(event) {
//     console.log(event);
//     var element = overlay.getElement();
//     var html = "event.type: " + event.type;
//     element.innerHTML = html;
//     if (map.getView() == viewKumpula) {
//         overlay.setPosition(kumpulaCoordinates);
//     } else {
//         overlay.setPosition(gispoCoordinates);
//     }
//     map.addOverlay(overlay);
// });
map.on('pointerdrag', function(event) {
    console.log(event);
    var element = overlay.getElement();
    var html = "event.type: " + event.type;
    element.innerHTML = html;
    if (map.getView() == viewKumpula) {
        overlay.setPosition(kumpulaCoordinates);
    } else {
        overlay.setPosition(gispoCoordinates);
    }
    map.addOverlay(overlay);
});
// map.on('postcompose', function(event) {
//     console.log(event);
//     var element = overlay.getElement();
//     var html = "event.type: " + event.type;
//     element.innerHTML = html;
//     if (map.getView() == viewKumpula) {
//         overlay.setPosition(kumpulaCoordinates);
//     } else {
//         overlay.setPosition(gispoCoordinates);
//     }
//     map.addOverlay(overlay);
// });
// map.on('postrender', function(event) {
//     console.log(event);
//     var element = overlay.getElement();
//     var html = "event.type: " + event.type;
//     element.innerHTML = html;
//     if (map.getView() == viewKumpula) {
//         overlay.setPosition(kumpulaCoordinates);
//     } else {
//         overlay.setPosition(gispoCoordinates);
//     }
//     map.addOverlay(overlay);
// });
map.on('propertychange', function(event) {
    console.log(event);
    var element = overlay.getElement();
    var html = "event.type: " + event.type;
    element.innerHTML = html;
    if (map.getView() == viewKumpula) {
        overlay.setPosition(kumpulaCoordinates);
    } else {
        overlay.setPosition(gispoCoordinates);
    }
    map.addOverlay(overlay);
});