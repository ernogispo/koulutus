
/*
 Lisätään kaikki OpenLayersin tarjoamat karttakontrollit karttaan.
 */

var controlsArray = [];
controlsArray.push(new ol.control.FullScreen());
controlsArray.push(new ol.control.MousePosition({
    //coordinateFormat: ol.coordinate.createStringXY(2),
    coordinateFormat: function(coordinate) {
        return ol.coordinate.toStringHDMS(coordinate);
    },
    projection: 'EPSG:4326',
}));
controlsArray.push(new ol.control.Rotate({
    autoHide: false
}));
controlsArray.push(new ol.control.OverviewMap());
controlsArray.push(new ol.control.ScaleLine());
controlsArray.push(new ol.control.ZoomSlider());
controlsArray.push(new ol.control.ZoomToExtent());

var controls = ol.control.defaults().extend(controlsArray);

var map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    controls: controls,
    view: new ol.View({
        center: ol.proj.fromLonLat([24.96362, 60.20504]),
        zoom: 14
    })
});

/*
 Interaktiotkin mahdollistavat kartan kontrollin. DragRotateAndZoom mahdollistaa shift-painikkeen ja hiiren avulla
 kartan kääntämisen ja zoomauksen.
 */
var interaction = new ol.interaction.DragRotateAndZoom();
map.addInteraction(interaction);

/*
 Overlayt ovat kartalle sijoitettavia HTML-elementtejä
 */
var overlay = new ol.Overlay({
    element: document.getElementById('coordinate_overlay'),
    positioning: 'bottom-center'
});

map.on('click', function(event) {
    var coord = event.coordinate;
    var degrees = ol.proj.transform(coord, 'EPSG:3857', 'EPSG:4326')
    var hdms = ol.coordinate.toStringHDMS(degrees);
    // Projektioista hyvä esimerkki: http://openlayers.org/en/latest/examples/reprojection-by-code.html
    proj4.defs("EPSG:3879","+proj=tmerc +lat_0=0 +lon_0=25 +k=1 +x_0=25500000 +y_0=0 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
    proj4.defs("EPSG:3067","+proj=utm +zone=35 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs"); // Määrittely http://epsg.io/3067
    var gk25finProjection = ol.proj.get('EPSG:3879');
    var tm35finProjection = ol.proj.get('EPSG:3067');
    var gk25finCoords = ol.proj.transform(coord, 'EPSG:3857', gk25finProjection);
    var tm35finCoords = ol.proj.transform(coord, 'EPSG:3857', tm35finProjection);
    console.log(coord);
    console.log(degrees);
    console.log(hdms);
    console.log(gk25finCoords);
    console.log(tm35finCoords);

    var element = overlay.getElement();

    var coordsHTML = "EPSG:4326 - " + hdms + "<br>"
        + "EPSG:3879 (GK25FIN) - " + gk25finCoords[0] + ", " + gk25finCoords[1] + "<br>"
        + "EPSG:3067 (TM35FIN) - " + tm35finCoords[0] + ", " + tm35finCoords[1];

    element.innerHTML = coordsHTML;
    overlay.setPosition(coord);
    map.addOverlay(overlay);
});
