var map = new ol.Map({
    target: 'map',
    layers: [
        // new ol.layer.Image({
        //     source: new ol.source.ImageWMS({
        //         url: 'http://tiles.kartat.kapsi.fi/taustakartta',
        //         params: {'LAYERS': 'taustakartta'},
        //         serverType: 'mapserver'
        //     })
        // })
        new ol.layer.Tile({
            source: new ol.source.TileWMS({
                url: 'http://tiles.kartat.kapsi.fi/taustakartta',
                params: {'LAYERS': 'taustakartta', 'TILED': true},
                serverType: 'mapserver'
            })
        }),
        new ol.layer.Vector({ // Huomaa, että palvelimen tukemat ominaisuudet selviävät kyselyllä:
            // https://kartta.hel.fi/ws/geoserver/avoindata/wfs?REQUEST=Getcapabilities
            // Lisätietoja myös: http://ptp.hel.fi/avoindata/
            source: new ol.source.Vector({
                format: new ol.format.GeoJSON(),
                url: function (extent) {
                    return 'https://kartta.hel.fi/ws/geoserver/avoindata/wfs?' +
                        'service=WFS&version=1.1.0&request=GetFeature&typename=avoindata:Rakennukset_kartalla&' +
                        'outputFormat=application/json&srsname=EPSG:3857&' +
                        'bbox=' + extent.join(',') + ',EPSG:3857&' +
                        'maxFeatures=100';
                },
                strategy: ol.loadingstrategy.bbox
            }),
            style: new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: 'rgba(255, 0, 0, 1.0)',
                    width: 3
                }),
                // fill: new ol.style.Fill({
                //     color: 'rgba(255, 0, 0, 1.0)'
                // })
            })
        }),
        new ol.layer.Vector({
            source: new ol.source.Vector({
                format: new ol.format.GeoJSON(),
                url: function (extent) {
                    return 'https://kartta.hel.fi/ws/geoserver/avoindata/wfs?' +
                        'service=WFS&version=1.1.0&request=GetFeature&typename=avoindata:RaideJokeri_pysakit&' +
                        'outputFormat=application/json&srsname=EPSG:3857&' +
                        'bbox=' + extent.join(',') + ',EPSG:3857&' +
                        'maxFeatures=100';
                },
                strategy: ol.loadingstrategy.bbox
            }),
            style: new ol.style.Style({
                image: new ol.style.Circle({
                    radius: 6,
                    fill: new ol.style.Fill({
                        color: 'rgba(87, 87, 87, 1.0)'
                    }),
                    stroke: new ol.style.Stroke({
                        color: 'rgba(0, 0, 255, 1.0)',
                        width: 3
                    })
                })
            })
        }),
        // new ol.layer.Vector({
        //     source: new ol.source.Vector({
        //         format: new ol.format.GML3(),
        //         url: function (extent) {
        //             return 'https://kartta.hel.fi/ws/geoserver/avoindata/wfs?' +
        //                 'service=WFS&version=2.0.0&request=GetFeature&typename=avoindata:Rakennukset_kartalla&' +
        //                 'outputFormat=gml3&srsname=EPSG:3857&' +
        //                 'bbox=' + extent.join(',') + ',EPSG:3857&' +
        //                 'count=100';
        //         },
        //         strategy: ol.loadingstrategy.bbox
        //     }),
        //     style: new ol.style.Style({
        //         stroke: new ol.style.Stroke({
        //             color: 'rgba(255, 0, 0, 1.0)',
        //             width: 3
        //         })
        //     })
        // })
    ],
    view: new ol.View({
        center: ol.proj.fromLonLat([24.96362, 60.20504]),
        zoom: 17
    })
});
