package com.jco.map;

import com.jco.animation.AnimationRunner;
import com.jco.database.table.LocationTable;
import com.jco.entity.database.Location;
import com.jco.entity.vehicles.TruckMixer;
import com.jco.entity.vehicles.Vehicle;
import com.jco.entity.vehicles.WaterTruck;
import com.jco.loaders.Loader;
import com.jco.loaders.yours.YoursLoader;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import java.awt.*;
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
    private static boolean baseLocationIsExist = Boolean.FALSE;
    private Loader loader;

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
        final Location baseLocation = LocationTable.selectBaseCoordinate();
        baseLocationIsExist =  baseLocation != null;
        // TODO use images for static markers
        if (baseLocationIsExist)
            jMapViewer.addMapMarker(new MapMarkerDot(Color.CYAN, baseLocation.getLatitude(), baseLocation.getLongitude()));


        // main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Components initialization;
        final JButton runButton = new JButton(MapUtilities.RUN_BUTTON);
        final JButton saveButton = new JButton(MapUtilities.SAVE_BUTTON);
        saveButton.setEnabled(Boolean.FALSE);
        final JButton cancelButton = new JButton(MapUtilities.CANCEL_BUTTON);
        cancelButton.setEnabled(Boolean.FALSE);
        final JTextField routeName = new JTextField(MapUtilities.ROUTE_NAME);
        routeName.setEnabled(Boolean.FALSE);
        final JLabel tileSourcesLabel = new JLabel(MapUtilities.TILE_SOURCES_LABEL);
        final JLabel vehicleInfo = new JLabel(MapUtilities.SELECTED_VEHICLE_INFO);
        final JLabel helpInfo = new JLabel(MapUtilities.HELP_INFO);
        final JLabel vehicleInfoLabel = new JLabel();
        final JSeparator separator = new JSeparator();
        separator.setOrientation(JSeparator.HORIZONTAL);

        final JCheckBox loadGpxCheckBox = new JCheckBox(MapUtilities.LOAD_GPX_LABEL);
        final JCheckBox loadNewBaseCoordinate = new JCheckBox(MapUtilities.LOAD_NEW_BASE_POINT);
        final JComboBox tileSourceSelector = new JComboBox(new TileSource[] { new OsmTileSource.Mapnik(),
                new OsmTileSource.CycleMap(), new BingAerialTileSource(),
                new MapQuestOsmTileSource(), new MapQuestOpenAerialTileSource() });
        final JComboBox vehiclesTypesCombo = new JComboBox(new Vehicle[] { new TruckMixer(), new WaterTruck()});
        vehiclesTypesCombo.setEnabled(Boolean.FALSE);

        // Set maximum sizes
        tileSourceSelector.setMaximumSize(MapUtilities.TILE_SOURCE_DIMENSION);
        separator.setMaximumSize(MapUtilities.SEPARATOR_DIMENSION);
        vehicleInfoLabel.setMaximumSize(MapUtilities.VEHICLE_INFO_LABEL_DIMENSION);
        runButton.setMaximumSize(MapUtilities.SEPARATOR_DIMENSION);
        vehiclesTypesCombo.setMaximumSize(MapUtilities.TILE_SOURCE_DIMENSION);
        routeName.setMaximumSize(MapUtilities.ROUTE_SOURCE_DIMENSION);

        // built layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(tileSourcesLabel)
                                .addComponent(tileSourceSelector))
                        .addComponent(jMapViewer)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(loadGpxCheckBox)
                                        .addComponent(loadNewBaseCoordinate))
                                .addComponent(helpInfo)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                  .addComponent(routeName)
                                                  .addComponent(vehiclesTypesCombo)
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                  .addComponent(saveButton)
                                                  .addComponent(cancelButton)
                                        )
                                )
                        )
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(vehicleInfo)
                        .addComponent(vehicleInfoLabel)
                        .addComponent(separator)
                        .addComponent(runButton))

                        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(tileSourcesLabel)
                                        .addComponent(tileSourceSelector))
                                .addComponent(jMapViewer)
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(loadGpxCheckBox)
                                                .addComponent(loadNewBaseCoordinate))
                                        .addComponent(helpInfo)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup()
                                                        .addComponent(routeName)
                                                        .addComponent(vehiclesTypesCombo)
                                                )
                                                .addGroup(layout.createParallelGroup()
                                                        .addComponent(saveButton)
                                                        .addComponent(cancelButton)
                                                )
                                        )
                                ))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(vehicleInfo)
                                .addComponent(vehicleInfoLabel)
                                .addComponent(separator)
                                .addComponent(runButton)
                        )));

        // set components listeners
        loadGpxCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO use methods instead check conditions
                if (baseLocationIsExist) {
                    runButton.setEnabled(!loadGpxCheckBox.isSelected() &&
                                         !loadNewBaseCoordinate.isSelected() &&
                                         !baseLocationIsExist);

                    saveButton.setEnabled(loadGpxCheckBox.isSelected() || loadNewBaseCoordinate.isSelected());
                    cancelButton.setEnabled(loadGpxCheckBox.isSelected() || loadNewBaseCoordinate.isSelected());
                    routeName.setEnabled(loadGpxCheckBox.isSelected());
                    vehiclesTypesCombo.setEnabled(loadGpxCheckBox.isSelected());
                }
            }
        });
        loadNewBaseCoordinate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runButton.setEnabled(!loadNewBaseCoordinate.isSelected() && !loadGpxCheckBox.isSelected());

                saveButton.setEnabled(loadNewBaseCoordinate.isSelected() || loadGpxCheckBox.isSelected());
                cancelButton.setEnabled(loadNewBaseCoordinate.isSelected() || loadGpxCheckBox.isSelected());
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AnimationRunner.runAnimation(jMapViewer);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO Check data saving
                loader.saveToDatabase(routeName.getText(), String.valueOf(vehiclesTypesCombo.getSelectedItem()));
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        routeName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                saveButton.setEnabled(!MapUtilities.EMPTY.equals(routeName.getText()));
            }
        });
        routeName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MapUtilities.ROUTE_NAME.equalsIgnoreCase(routeName.getText())) {
                    routeName.setText(MapUtilities.EMPTY);
                }
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
                    if (loadGpxCheckBox.isSelected()) {
                        Coordinate coordinate = jMapViewer.getPosition(e.getPoint());
                        int red = (int)(255*Math.random());
                        int green = (int)(255*Math.random());
                        int blue = (int)(255*Math.random());
                        Color routeColor = new Color(red,green,blue);
                        jMapViewer.addMapMarker(new MapMarkerDot(routeColor,coordinate.getLat(), coordinate.getLon()));
                        loader =  new YoursLoader(jMapViewer, routeColor, coordinate);
                        new Thread(loader).start();
                    }
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
