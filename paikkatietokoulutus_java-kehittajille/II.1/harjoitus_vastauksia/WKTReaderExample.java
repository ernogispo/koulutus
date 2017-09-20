package fi.fmi.gispo.training.geotools;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTReader;

/*
 Toimii ainakin näillä geometrioilla.
 */
public class WKTReaderExample {
    public static void main(String[] args) throws Exception {
        String wktPoint = "POINT (24.96255 60.2045)";
        String wktPolygon = "POLYGON ((24.96179 60.20255, 24.95842 60.20455, 24.96262 60.20654, 24.96547 60.20508, 24.96179 60.20255))";

        int srid = 4326;
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), srid);
        WKTReader reader = new WKTReader(factory);
        Geometry geomPoint = reader.read(wktPoint);
        Geometry geomPolygon = reader.read(wktPolygon);
        System.out.println(geomPolygon.contains(geomPoint));
    }
}
