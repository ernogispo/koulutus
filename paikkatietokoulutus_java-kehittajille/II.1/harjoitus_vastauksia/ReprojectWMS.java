package fi.fmi.gispo.training.geotools;

import java.net.URL;
import java.util.List;
import javax.swing.JOptionPane;
import org.geotools.data.ows.Layer;
import org.geotools.data.wms.WebMapServer;
import org.geotools.map.MapContent;
import org.geotools.map.WMSLayer;
import org.geotools.referencing.CRS;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.wms.WMSChooser;
import org.geotools.swing.wms.WMSLayerChooser;
import org.opengis.referencing.crs.CoordinateReferenceSystem;


public class ReprojectWMS {
    public static void main( String[] args ) throws Exception
    {
        URL capabilitiesURL = WMSChooser.showChooseWMS(); // http://tiles.kartat.kapsi.fi/peruskartta?Service=WMS&Version=1.1.1&Request=GetCapabilities
        if( capabilitiesURL == null ) {
            System.exit(0);
        }
        WebMapServer wms = new WebMapServer( capabilitiesURL );
        List<Layer> wmsLayers = WMSLayerChooser.showSelectLayer( wms );
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
        JMapFrame.showMap(mapcontent);
    }
}
