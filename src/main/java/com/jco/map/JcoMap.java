package com.jco.map;

import com.jco.animation.AnimationRunner;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import java.awt.event.*;

/**
 * Java Cost Optimizer map frame implementation
 *
 * @author Bondoronok_P
 * Date: 26.02.13
 */
public class JcoMap extends JFrame implements JMapViewerEventListener {

    private static JMapViewer jMapViewer = null;
    private static JcoMap jcoMap = null;

    /**
     * Instantiate map by latitude an longitude
     */
    private JcoMap() {
        super(MapUtilities.FRAME_HEADER);
        buildUi();
    }

    private void buildUi() {
        setSize(MapUtilities.WIDTH, MapUtilities.HEIGHT);
        
        jMapViewer = new JMapViewer();
        // by default we use Mapnik tile source
        jMapViewer.setTileSource(new OsmTileSource.Mapnik());
        jMapViewer.addJMVListener(this);

        // main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Components initialization
        final JButton runButton = new JButton(MapUtilities.RUN_BUTTON);
        JComboBox tileSourceSelector = new JComboBox(new TileSource[] { new OsmTileSource.Mapnik(),
                new OsmTileSource.CycleMap(), new BingAerialTileSource(),
                new MapQuestOsmTileSource(), new MapQuestOpenAerialTileSource() });
        JLabel tileSourcesLabel = new JLabel(MapUtilities.TILE_SOURCES_LABEL);
        JLabel vehicleInfo = new JLabel(MapUtilities.SELECTED_VEHICLE_INFO);
        final JCheckBox loadGpxCheckBox = new JCheckBox(MapUtilities.LOAD_GPX_LABEL);
        JLabel vehicleInfoLabel = new JLabel();
        JSeparator separator = new JSeparator();
        separator.setOrientation(JSeparator.HORIZONTAL);

        // Set maximum sizes
        tileSourceSelector.setMaximumSize(MapUtilities.TILE_SOURCE_DIMENSION);
        separator.setMaximumSize(MapUtilities.SEPARATOR_DIMENSION);
        vehicleInfoLabel.setMaximumSize(MapUtilities.VEHICLE_INFO_LABEL_DIMENSION);
        runButton.setMaximumSize(MapUtilities.SEPARATOR_DIMENSION);

        // built layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jMapViewer))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(tileSourcesLabel)
                                .addComponent(tileSourceSelector))
                        .addComponent(loadGpxCheckBox)
                        .addComponent(separator)
                        .addComponent(vehicleInfo)
                        .addComponent(vehicleInfoLabel)
                        .addComponent(runButton))

        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(jMapViewer)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tileSourcesLabel)
                                        .addComponent(tileSourceSelector))
                                .addComponent(loadGpxCheckBox)
                                .addComponent(separator)
                                .addComponent(vehicleInfo)
                                .addComponent(vehicleInfoLabel)
                                .addComponent(runButton)
                        )));

        // set components listeners
        loadGpxCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runButton.setEnabled(!loadGpxCheckBox.isSelected());
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AnimationRunner.runAnimation(jMapViewer);
            }
        });
        tileSourceSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                jMapViewer.setTileSource((TileSource) e.getItem());
            }
        });
        jMapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    jMapViewer.getAttribution().handleAttribution(e.getPoint(), true);
//                     TODO we can implement route loader by using http://www.yournavigation.org/api/1.0/saveas.php api
//                    Coordinate coordinate = jMapViewer.getPosition(e.getPoint());
//                    jMapViewer.addMapMarker(new MapMarkerDot(coordinate.getLat(), coordinate.getLon()));
                }
            }
        });
    }
    /**
     * Instantiate {@link JcoMap} instance
     *
     * @return {@link JcoMap}
     */
    private static void loadInstance() {
        if (jcoMap == null) {
            jcoMap = new JcoMap();
        }
    }

    /**
     * Load OSM by defined
     * latitude and longitude
     *
     * @param lat <code>lat</code> latitude
     * @param lon <code>lon</code> longitude
     * @param zoom <code>zoom</code> zoom
     * @return {@link JcoMap} instance
     */
    public static JcoMap loadMapByLatLon(float lat, float lon, int zoom) {
        loadInstance();
        jMapViewer.setDisplayPositionByLatLon(lat, lon, zoom);
        return jcoMap;
    };

    /**
     * Load full OSM
     *
     * @return {@link JcoMap} instance
     */
    public static JcoMap loadFullMap() {
        loadInstance();
        return jcoMap;
    }

    @Override
    public void processCommand(JMVCommandEvent command) {
    }
}
