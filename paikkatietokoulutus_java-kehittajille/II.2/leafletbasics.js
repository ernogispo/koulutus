
var map = L.map('map').setView([60.20504, 24.96362], 14);

var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
});

var ortoLayer = L.tileLayer.wms("http://tiles.kartat.kapsi.fi/ortokuva?", {
    layers: 'ortokuva',
    format: 'image/jpeg',
    transparent: true,
    attribution: '&copy; <a href="Karttamateriaali Maanmittauslaitos">http://www.maanmittauslaitos.fi/kartat-ja-paikkatieto/asiantuntevalle-kayttajalle/maastotiedot-ja-niiden-hankinta/avoimen</a>'
});

osmLayer.addTo(map);

var baseLayers = {
    "OpenStreetMap peruskartta": osmLayer,
    "Maanmittauslaitos ortokuvat": ortoLayer
}

L.control.layers(baseLayers).addTo(map);

var marker = L.marker([60.20381, 24.96083]).addTo(map);

marker.bindPopup('<div style="background-color: cornflowerblue; padding: 10px; text-align: center;">' +
    '<b><span style="color: #FFF433; font-size: xx-large;">&#x263c;</span><br>' +
    '<span style="font-size: large;"> Ilmatieteenlaitos</span></b></div>');

var circle = L.circle([60.20381, 24.96083], {
    color: '#FFF433',
    fillColor: '#FFF433',
    fillOpacity: 0.5,
    radius: 500
}).addTo(map);

