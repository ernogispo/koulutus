package fi.fmi.gispo.training.geotools;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.swing.styling.JSimpleStyleDialog;

import java.io.File;

public class VectorExerciseSimpleStyleDialog {
    private File sourceFile;
    private SimpleFeatureSource featureSource;
    private MapContent map;

    public static void main(String[] args) throws Exception {
        VectorExerciseSimpleStyleDialog exercise = new VectorExerciseSimpleStyleDialog();
        exercise.displayShapefile();
    }

    private void displayShapefile() throws Exception {
        sourceFile = JFileDataStoreChooser.showOpenFile("shp", null);
        if (sourceFile == null) {
            return;
        }
        FileDataStore store = FileDataStoreFinder.getDataStore(sourceFile);
        featureSource = store.getFeatureSource();

        // Create a map context and add our shapefile to it
        map = new MapContent();
        // Create a JMapFrame with custom toolbar buttons
        JMapFrame mapFrame = new JMapFrame(map);
        mapFrame.enableToolBar(true);
        mapFrame.enableStatusBar(true);

        //Style style = SLD.createSimpleStyle(featureSource.getSchema());
        Style style = JSimpleStyleDialog.showDialog(mapFrame, store);
        Layer layer = new FeatureLayer(featureSource, style);
        map.layers().add(layer);

        // Display the map frame. When it is closed the application will exit
        mapFrame.setSize(800, 600);
        mapFrame.setVisible(true);
    }
}
