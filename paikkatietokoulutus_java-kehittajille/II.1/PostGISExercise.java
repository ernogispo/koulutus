package fi.fmi.gispo.training.geotools;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.geotools.data.*;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.action.SafeAction;
import org.geotools.swing.data.JDataStoreWizard;
import org.geotools.swing.wizard.JWizard;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.spatial.Intersects;
import org.opengis.referencing.crs.CoordinateReferenceSystem;


public class PostGISExercise {
    private DataStore dataStoreRead;
    private SimpleFeatureSource sourceRead;
    private DataStore dataStoreWrite;

    private JMapFrame mapFrame;
    private MapContent map;
    private Layer layer;

    public static void main(String[] args) throws Exception {
        PostGISQueryMap postGISQueryMap = new PostGISQueryMap();
    }

    public PostGISExercise() {

        map = new MapContent();
        mapFrame = new JMapFrame(map);
        mapFrame.setSize(300, 600);
        mapFrame.enableStatusBar(true);
        //frame.enableTool(JMapFrame.Tool.ZOOM, JMapFrame.Tool.PAN, JMapFrame.Tool.RESET);
        mapFrame.enableToolBar(true);
        mapFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        mapFrame.setJMenuBar(menuBar);

        JMenu connectMenu = new JMenu("Connect");
        menuBar.add(connectMenu);

        connectMenu.add(new SafeAction("PostGIS database...") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                connect(new PostgisNGDataStoreFactory());
            }
        });

        connectMenu.add(new SafeAction("PostGIS database with default parameters") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                connectPostGISWithDefaultParameters();
            }
        });

        JMenu modifyMenu = new JMenu("Modify");
        menuBar.add(modifyMenu);

        modifyMenu.add(new SafeAction("Add features") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                addFeatures();
            }
        });

        modifyMenu.add(new SafeAction("Update a feature") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                updateFeature();
            }
        });

        modifyMenu.add(new SafeAction("Delete a feature") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                deleteFeature();
            }
        });

        modifyMenu.add(new SafeAction("Drop a database") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                dropDatabase();
            }
        });


        JMenu spatialMenu = new JMenu("Spatial queries");
        menuBar.add(spatialMenu);

        spatialMenu.add(new SafeAction("CQL BBOX query") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                doCQLBBOXQuery();
            }
        });

        spatialMenu.add(new SafeAction("CQL DISJOINT query") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                doCQLDISJOINTQuery();
            }
        });

        spatialMenu.add(new SafeAction("FilterFactory2 query") {
            @Override
            public void action(ActionEvent actionEvent) throws Throwable {
                doFilterFactory2Query();
            }
        });

        mapFrame.setVisible(true);
    }

    private void doFilterFactory2Query() throws Exception {

        SimpleFeatureType schema = dataStoreRead.getSchema("osm_places");
        String geomName = schema.getGeometryDescriptor().getLocalName();

        CoordinateReferenceSystem crs = CRS.decode("EPSG:4326");

        // Tähän suodattimen hyödyntämiseen tarkoitettu koodi. Alla olevia poiskommentoituja rivejä voi hyödyntää, mutta ne
        // eivät auta varsinaisen suodattimen toteutuksessa.

//        Query query = new Query("osm_places", intersectsFilter, new String[]{geomName});
//        SimpleFeatureCollection simpleFeatureCollection = sourceRead.getFeatures(query);
//
//        Style style = SLD.createSimpleStyle(sourceRead.getSchema());
//
//        map.removeLayer(layer);
//        layer = new FeatureLayer(simpleFeatureCollection, style);
//        map.addLayer(layer);
    }

    private void doCQLBBOXQuery() throws Exception {
        Filter filter = CQL.toFilter("");
        SimpleFeatureCollection simpleFeatureCollection = sourceRead.getFeatures(filter);

        Style style = SLD.createSimpleStyle(sourceRead.getSchema());

        map.removeLayer(layer);
        layer = new FeatureLayer(simpleFeatureCollection, style);
        map.addLayer(layer);
    }

    private void doCQLDISJOINTQuery() throws Exception {
        Filter filter = CQL.toFilter("");
        SimpleFeatureCollection simpleFeatureCollection = sourceRead.getFeatures(filter);

        Style style = SLD.createSimpleStyle(sourceRead.getSchema());

        map.removeLayer(layer);
        layer = new FeatureLayer(simpleFeatureCollection, style);
        map.addLayer(layer);
    }

    private void dropDatabase() {
        PostgisNGDataStoreFactory postgisNGDataStoreFactory = new PostgisNGDataStoreFactory();

        //JDataStoreWizard wizard = new JDataStoreWizard(postgisNGDataStoreFactory);
        //int result = wizard.showModalDialog();
        //if (result == JWizard.FINISH) {
        //Map<String, Object> connectionParameters = wizard.getConnectionParameters();

        Map<String, Object> connectionParameters = new HashMap<>();
        connectionParameters.put("dbtype", "postgis");
        connectionParameters.put("host", "localhost");
        connectionParameters.put("port", 5432);
        connectionParameters.put("user", "postgres");
        connectionParameters.put("passwd", "postgres");
        connectionParameters.put("database", "osm_cities");

        System.out.println(postgisNGDataStoreFactory.canProcess(connectionParameters));

        try {
            dataStoreWrite.dispose();

            postgisNGDataStoreFactory.dropDatabase(connectionParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(dataStoreWrite);
    }

    private void deleteFeature() throws Exception {
        SimpleFeatureSource sourceWrite = dataStoreWrite.getFeatureSource("city");

        Transaction transaction = new DefaultTransaction("deleteExample");
        if (sourceWrite instanceof SimpleFeatureStore) {
            SimpleFeatureStore featureStore = (SimpleFeatureStore) sourceWrite;
            featureStore.setTransaction(transaction);

            Filter filter = CQL.toFilter("name='Helsinki'");

            SimpleFeatureType featureType = featureStore.getSchema();
            try {
                featureStore.removeFeatures(filter);
                transaction.commit();
                System.out.println("Deleted a feature");
            }
            catch( Exception problem ){
                problem.printStackTrace();
                transaction.rollback();
            }
            finally {
                transaction.close();
            }
        }
    }

    private void updateFeature() throws Exception {
        SimpleFeatureSource sourceWrite = dataStoreWrite.getFeatureSource("city");

        Transaction transaction = new DefaultTransaction("updateCity");
        if (sourceWrite instanceof SimpleFeatureStore) {
            SimpleFeatureStore featureStore = (SimpleFeatureStore) sourceWrite;
            featureStore.setTransaction(transaction);

            Filter filter = CQL.toFilter("code=1005");

            SimpleFeatureType featureType = featureStore.getSchema();
            try {
                featureStore.modifyFeatures( "code", "1001", filter );
                transaction.commit();
                System.out.println("Updated a feature");
            }
            catch( Exception problem ){
                problem.printStackTrace();
                transaction.rollback();
            }
            finally {
                transaction.close();
            }
        }
    }

    private void addFeatures() throws Exception {
        if (dataStoreRead == null) {
            connectPostGISWithDefaultParameters();
        }
        if (dataStoreWrite == null) {
            createPostGISDatabase();
        }

        Filter filter = CQL.toFilter("fclass='city' OR fclass='national_capital'");
        SimpleFeatureCollection sourceFeatures = sourceRead.getFeatures(filter);

        final SimpleFeatureType CITY_FEATURE_TYPE = createFeatureType();
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(CITY_FEATURE_TYPE);

        List<SimpleFeature> features = new ArrayList<>();

        try (SimpleFeatureIterator iterator = sourceFeatures.features()){
            while (iterator.hasNext()) {
                SimpleFeature sourceFeature = iterator.next();
                Geometry geom = (Geometry) sourceFeature.getDefaultGeometry();
                Point point = geom.getCentroid();
                String name = (String) sourceFeature.getAttribute("name");
                Short code = (Short) sourceFeature.getAttribute("code");

                featureBuilder.add(point);
                featureBuilder.add(name);
                featureBuilder.add(code);
                SimpleFeature cityFeature = featureBuilder.buildFeature(null);
                features.add(cityFeature);
            }
        }

        dataStoreWrite.createSchema(CITY_FEATURE_TYPE);
        SimpleFeatureSource sourceWrite = dataStoreWrite.getFeatureSource("city");

        Transaction transaction = new DefaultTransaction("createCityTableWithFeatures");
        if (sourceWrite instanceof SimpleFeatureStore) {
            SimpleFeatureStore featureStore = (SimpleFeatureStore) sourceWrite;
            SimpleFeatureCollection collection = new ListFeatureCollection(CITY_FEATURE_TYPE, features);
            featureStore.setTransaction(transaction);
            try {
                featureStore.addFeatures(collection);
                transaction.commit();
                System.out.println("Added features");
            } catch (Exception problem) {
                problem.printStackTrace();
                transaction.rollback();
            } finally {
                transaction.close();
            }
        }
        else {
            System.out.println("City table in the database does not support read/write access");
            System.exit(1);
        }

    }

    private static SimpleFeatureType createFeatureType() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("city");
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.add("the_geom", Point.class);
        builder.length(100).add("name", String.class);
        builder.add("code", Short.class);

        final SimpleFeatureType CITY_FEATURE_TYPE = builder.buildFeatureType();

        return CITY_FEATURE_TYPE;
    }

    private void createPostGISDatabase() {
        PostgisNGDataStoreFactory postgisNGDataStoreFactory = new PostgisNGDataStoreFactory();

        //JDataStoreWizard wizard = new JDataStoreWizard(postgisNGDataStoreFactory);
        //int result = wizard.showModalDialog();
        //if (result == JWizard.FINISH) {
        //Map<String, Object> connectionParameters = wizard.getConnectionParameters();

        Map<String, Object> connectionParameters = new HashMap<>();
        connectionParameters.put("dbtype", "postgis");
        connectionParameters.put("host", "localhost");
        connectionParameters.put("port", 5432);
        connectionParameters.put("user", "postgres");
        connectionParameters.put("passwd", "postgres");
        connectionParameters.put("database", "osm_cities");
        connectionParameters.put(PostgisNGDataStoreFactory.CREATE_DB_IF_MISSING.key, true);

        System.out.println(postgisNGDataStoreFactory.canProcess(connectionParameters));

        try {
            dataStoreWrite = postgisNGDataStoreFactory.createDataStore(connectionParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(dataStoreWrite);
    }

    private void connect(DataStoreFactorySpi format) throws Exception {
        JDataStoreWizard wizard = new JDataStoreWizard(format);
        int result = wizard.showModalDialog();
        if (result == JWizard.FINISH) {
            Map<String, Object> connectionParameters = wizard.getConnectionParameters();
            dataStoreRead = DataStoreFinder.getDataStore(connectionParameters);
            if (dataStoreRead == null) {
                JOptionPane.showMessageDialog(null, "Could not connect - check parameters");
            }
            updateUI();
        }
    }

    private void connectPostGISWithDefaultParameters() throws Exception {
        // See also: http://docs.geotools.org/latest/userguide/library/jdbc/dataStoreRead.html
        Map<String, Object> connectionParameters = new HashMap<>();
        connectionParameters.put("dbtype", "postgis");
        connectionParameters.put("host", "localhost");
        connectionParameters.put("port", 5432);
        connectionParameters.put("user", "postgres");
        connectionParameters.put("passwd", "postgres");
        connectionParameters.put("database", "osm");
        dataStoreRead = DataStoreFinder.getDataStore(connectionParameters);
        if (dataStoreRead == null) {
            JOptionPane.showMessageDialog(null, "Could not connect - check parameters");
        }
        updateUI();
    }

    private void updateUI() throws Exception {
        sourceRead = dataStoreRead.getFeatureSource("osm_places");
        Style style = SLD.createSimpleStyle(sourceRead.getSchema());

        map.removeLayer(layer);
        layer = new FeatureLayer(sourceRead, style);
        map.addLayer(layer);
    }
}
