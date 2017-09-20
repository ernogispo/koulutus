package fi.fmi.gispo.training.geotools;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.ows.Layer;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.wms.WebMapServer;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapContent;
import org.geotools.map.WMSLayer;
import org.geotools.referencing.CRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.swing.wms.WMSChooser;
import org.geotools.swing.wms.WMSLayerChooser;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.List;


public class WMSShapeExercise {
    public static void main( String[] args ) throws Exception
    {
        URL capabilitiesURL = WMSChooser.showChooseWMS();
        if( capabilitiesURL == null ) {
            System.exit(0);
        }
        WebMapServer wms = new WebMapServer( capabilitiesURL );
        List<Layer> wmsLayers = WMSLayerChooser.showSelectLayer( wms ); // http://tiles.kartat.kapsi.fi/peruskartta?Service=WMS&Version=1.1.1&Request=GetCapabilities
        if( wmsLayers == null ){
            JOptionPane.showMessageDialog(null, "Could not connect - check url");
            System.exit(0);
        }
        MapContent mapcontent = new MapContent();
        mapcontent.setTitle( wms.getCapabilities().getService().getTitle() );
        for( Layer wmsLayer : wmsLayers ) {
            WMSLayer displayLayer = new WMSLayer(wms, wmsLayer );
            mapcontent.addLayer(displayLayer);
            CoordinateReferenceSystem newCRS = CRS.decode("EPSG:3067");
            mapcontent.getViewport().setCoordinateReferenceSystem(newCRS);
        }

        File sourceFile = JFileDataStoreChooser.showOpenFile("shp", null);
        if (sourceFile == null) {
            return;
        }
        FileDataStore store = FileDataStoreFinder.getDataStore(sourceFile);
        SimpleFeatureSource featureSource = store.getFeatureSource();

        Style style = SLD.createSimpleStyle(featureSource.getSchema());
        org.geotools.map.Layer layer = new FeatureLayer(featureSource, style);
        mapcontent.layers().add(layer);

        JMapFrame.showMap(mapcontent);
    }
}
