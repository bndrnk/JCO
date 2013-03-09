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
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
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

    private static JcoMap jcoMap = null;
    private static Loader loader;
    private static boolean baseLocationIsExist = Boolean.FALSE;
    private static Location baseLocation = null;

    // Components
    private static JMapViewer jMapViewer = null;
    private static JButton runButton = new JButton(MapUtilities.RUN_BUTTON);
    private static JButton saveButton = new JButton(MapUtilities.SAVE_BUTTON);
    private static JButton cancelButton = new JButton(MapUtilities.CANCEL_BUTTON);
    private static JTextField routeName = new JTextField(MapUtilities.ROUTE_NAME);
    private static JLabel tileSourcesLabel = new JLabel(MapUtilities.TILE_SOURCES_LABEL);
    private static JLabel vehicleInfo = new JLabel(MapUtilities.SELECTED_VEHICLE_INFO);
    private static JLabel helpInfo = new JLabel(MapUtilities.HELP_INFO);
    private static JLabel vehicleInfoLabel = new JLabel();
    private static JSeparator separator = new JSeparator();
    private static JCheckBox loadGpxCheckBox = new JCheckBox(MapUtilities.LOAD_GPX_LABEL);
    private static JCheckBox loadNewBaseCoordinate = new JCheckBox(MapUtilities.LOAD_NEW_BASE_POINT);
    private static JComboBox tileSourceSelector = new JComboBox(new TileSource[] { new OsmTileSource.Mapnik(),
            new OsmTileSource.CycleMap(), new BingAerialTileSource(),
            new MapQuestOsmTileSource(), new MapQuestOpenAerialTileSource() });
    private static JComboBox vehiclesTypesCombo = new JComboBox(new Vehicle[] { new TruckMixer(), new WaterTruck()});

    /**
     * Instantiate map by latitude an longitude
     */
    private JcoMap() {
        super(MapUtilities.FRAME_HEADER);
        buildUi();
    }

    /**
     * Build application user interface
     */
    private void buildUi() {
        setSize(MapUtilities.WIDTH, MapUtilities.HEIGHT);

        jMapViewer = new JMapViewer();
        // by default we use Mapnik tile source
        jMapViewer.setTileSource(new OsmTileSource.Mapnik());
        jMapViewer.addJMVListener(this);

        // main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Components options
        saveButton.setEnabled(Boolean.FALSE);
        routeName.setEnabled(Boolean.FALSE);
        separator.setOrientation(JSeparator.HORIZONTAL);
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

        setEventsHandlers();
    }

    /**
     * Handle events
     */
    private static void setEventsHandlers() {
        baseLocation = LocationTable.selectBaseLocation();
        baseLocationIsExist =  baseLocation != null;
        if (baseLocationIsExist)
            jMapViewer.addMapMarker(new MapMarkerDot(Color.CYAN, baseLocation.getLatitude(), baseLocation.getLongitude()));
        else {
            loadNewBaseCoordinate.setSelected(Boolean.TRUE);
            saveButton.setEnabled(Boolean.TRUE);
            runButton.setEnabled(Boolean.FALSE);
        }

        jMapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Coordinate coordinate = jMapViewer.getPosition(e.getPoint());

                    if (loadGpxCheckBox.isSelected()) {
                        Color routeColor = new Color(getRandomValue(), getRandomValue(), getRandomValue());
                        jMapViewer.addMapMarker(new MapMarkerDot(routeColor,coordinate.getLat(), coordinate.getLon()));

                        // todo send to loader locations (base and selected)
//                       TODO selected location must contain incremented routeID
                        loader =  new YoursLoader(jMapViewer, routeColor, new Coordinate(baseLocation.getLatitude(), baseLocation.getLongitude()), coordinate);
                        new Thread(loader).start();
                    } else if (loadNewBaseCoordinate.isSelected()) {
                        jMapViewer.removeAllMapMarkers();
                        jMapViewer.addMapMarker(new MapMarkerDot(coordinate.getLat(), coordinate.getLon()));
                    }
                    saveButton.setEnabled(!MapUtilities.EMPTY.equals(routeName.getText()) &&
                            !MapUtilities.ROUTE_NAME.equals(routeName.getText())
                            && jMapViewer.getMapMarkerList().size() > 1);
                }
            }
        });

        // checkboxes
        loadGpxCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (baseLocationIsExist) {
                    loadNewBaseCoordinate.setSelected(Boolean.FALSE);
                    disableComponents();
                } else {
                    loadGpxCheckBox.setSelected(Boolean.FALSE);
                }
            }
        });
        loadNewBaseCoordinate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (baseLocationIsExist) {
                    loadGpxCheckBox.setSelected(Boolean.FALSE);
                    disableComponents();
                } else {
                    loadNewBaseCoordinate.setSelected(Boolean.TRUE);
                }
            }
        });

        // Buttons
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AnimationRunner.runAnimation(jMapViewer);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (loadGpxCheckBox.isSelected()) {
                    loader.saveToDatabase(routeName.getText(), String.valueOf(vehiclesTypesCombo.getSelectedItem()));
                } else if (loadNewBaseCoordinate.isSelected()){
                    // todo save base coordinate;
                    MapMarker marker = jMapViewer.getMapMarkerList().get(0);
                    if (!baseLocationIsExist && marker != null) {
                        Location location = new Location(marker.getLat(), marker.getLon());
                        location.setLocationName(LocationTable.BASE);
                        if (LocationTable.insert(location) > 0) {
                            baseLocationIsExist = Boolean.TRUE;
                            baseLocation = location;
                            saveButton.setEnabled(Boolean.FALSE);
                        }
                    } else {
                        // TODO show user confirmation message if base location is exist
                    }
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO interrupt run threads
                jMapViewer.removeAllMapMarkers();
                jMapViewer.removeAllMapPolygons();
                saveButton.setEnabled(Boolean.FALSE);
                if (baseLocationIsExist) {
                    jMapViewer.addMapMarker(new MapMarkerDot(baseLocation.getLatitude(), baseLocation.getLongitude()));
                }
            }
        });

        routeName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                saveButton.setEnabled(!MapUtilities.EMPTY.equals(routeName.getText())
                        && jMapViewer.getMapMarkerList().size() > 1);
            }
        });
        routeName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MapUtilities.ROUTE_NAME.equalsIgnoreCase(routeName.getText())) {
                    routeName.setText(MapUtilities.EMPTY);
                }
                if (routeName.getText().isEmpty()
                        && jMapViewer.getMapMarkerList().size() > 1) {
                    saveButton.setEnabled(Boolean.FALSE);
                }
            }
        });

        tileSourceSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                jMapViewer.setTileSource((TileSource) e.getItem());
            }
        });
    }

    /**
     * Logic of components locking
     */
    private static void disableComponents() {

        if (loadGpxCheckBox.isSelected()) {
            saveButton.setEnabled(!routeName.getText().isEmpty() && jMapViewer.getMapMarkerList().size() > 1);
            routeName.setEnabled(Boolean.TRUE);
            vehiclesTypesCombo.setEnabled(Boolean.TRUE);
            runButton.setEnabled(Boolean.FALSE);
        } else if (loadNewBaseCoordinate.isSelected()) {
            saveButton.setEnabled(Boolean.TRUE);
            routeName.setEnabled(Boolean.FALSE);
            vehiclesTypesCombo.setEnabled(Boolean.FALSE);
            runButton.setEnabled(Boolean.FALSE);
        } else {
            saveButton.setEnabled(Boolean.FALSE);
            routeName.setEnabled(Boolean.FALSE);
            vehiclesTypesCombo.setEnabled(Boolean.FALSE);
            runButton.setEnabled(Boolean.TRUE);
        }
    }

    /**
     * Returns random integer
     * value for built color;
     *
     * @return integer
     */
    private static int getRandomValue() {
        return (int)(255*Math.random());
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
