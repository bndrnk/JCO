package com.jco.map;

import com.jco.animation.AnimationRunner;
import com.jco.database.table.LocationTable;
import com.jco.entity.database.Location;
import com.jco.entity.database.Route;
import com.jco.entity.vehicle.AbstractVehicle;
import com.jco.entity.vehicle.TruckMixer;
import com.jco.entity.vehicle.VehicleTypeEnum;
import com.jco.entity.vehicle.WaterTruck;
import com.jco.i18n.I18n;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private static long routeId;
    private static List<Route> routeList = new ArrayList<Route>();
    private static Image baseLocationImage;

    // Components
    private static JMapViewer jMapViewer = null;
    private static JButton runButton = new JButton(I18n.BUTTON_RUN.getText());
    private static JButton saveButton = new JButton(I18n.BUTTON_SAVE.getText());
    private static JButton cancelButton = new JButton(I18n.BUTTON_CANCEL.getText());
    private static JLabel tileSourcesLabel = new JLabel(I18n.TILE_SOURCE.getText());
    private static JLabel vehicleInfo = new JLabel(I18n.SELECTED_ITEM.getText());
    private static JLabel helpInfo = new JLabel(I18n.LOAD_GPX_TIP.getText());
    private static JLabel vehicleInfoLabel = new JLabel();
    private static JSeparator separator = new JSeparator();
    private static JCheckBox loadGpxCheckBox = new JCheckBox(I18n.LOAD_ROUTE.getText());
    private static JCheckBox loadNewBaseCoordinate = new JCheckBox(I18n.LOAD_BASE_COORDINATE.getText());
    private static JComboBox tileSourceSelector = new JComboBox(new TileSource[] { new OsmTileSource.Mapnik(),
            new OsmTileSource.CycleMap(), new BingAerialTileSource(),
            new MapQuestOsmTileSource(), new MapQuestOpenAerialTileSource() });
    private static JComboBox vehiclesTypesCombo;

    /**
     * Instantiate map by latitude an longitude
     */
    private JcoMap() {
        super(I18n.TITLE.getText());
        buildUi();
    }

    /**
     * Build application user interface
     */
    private void buildUi() {
        setSize(MapUtilities.WIDTH, MapUtilities.HEIGHT);

        try {
            // TODO remove hardoded path
            baseLocationImage = ImageIO.read(new FileInputStream("/media/37E89D99542D0DB2/Dropbox/projects/JCO/src/main/resources/base.png"));
        } catch (IOException e) {
            e.printStackTrace();// TODO handle it
        }

        jMapViewer = new JMapViewer();
        // by default we use Mapnik tile source
        jMapViewer.setTileSource(new OsmTileSource.Mapnik());
        jMapViewer.addJMVListener(this);
        loader =  new YoursLoader(jMapViewer);
        routeId = loader.getNextRouteId();
        // main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initializeVehicleCheckbox();

        // Components options
        saveButton.setEnabled(Boolean.FALSE);
        separator.setOrientation(JSeparator.HORIZONTAL);
        vehiclesTypesCombo.setEnabled(Boolean.FALSE);

        // Set maximum sizes
        tileSourceSelector.setMaximumSize(MapUtilities.TILE_SOURCE_DIMENSION);
        separator.setMaximumSize(MapUtilities.SEPARATOR_DIMENSION);
        vehicleInfoLabel.setMaximumSize(MapUtilities.VEHICLE_INFO_LABEL_DIMENSION);
        runButton.setMaximumSize(MapUtilities.SEPARATOR_DIMENSION);
        vehiclesTypesCombo.setMaximumSize(MapUtilities.TILE_SOURCE_DIMENSION);

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
        if (baseLocationIsExist) {
            setBaseLocationOnMap();
            loader.setBaseLocation(baseLocation);
        } else {
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
                        routeList.add(new Route(routeId));
                        loader.setSelectedLocation(new Location(coordinate.getLat(), coordinate.getLon(), routeId++));
                        saveButton.setEnabled(jMapViewer.getMapMarkerList().size() > 1);
                        new Thread(loader).start();
                    } else if (loadNewBaseCoordinate.isSelected()) {
                        jMapViewer.removeAllMapPolygons();
                        jMapViewer.removeAllMapMarkers();
                        jMapViewer.addMapMarker(new MapMarkerDot(coordinate.getLat(), coordinate.getLon()));
                        saveButton.setEnabled(Boolean.TRUE);
                    }

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
                AnimationRunner.getInstance(jMapViewer).startAnimation();

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (loadGpxCheckBox.isSelected()) {
                    if (loader.saveRoutesToDatabase(routeList, String.valueOf(vehiclesTypesCombo.getSelectedItem()))) {
                        loader.clearData();
                        routeList.clear();
                        removeMarkersAndPolygonsFromMap();
                    }
                } else if (loadNewBaseCoordinate.isSelected()){
                    MapMarker marker = jMapViewer.getMapMarkerList().get(0);
                    if (marker != null) {
                        Location newBaseLocation = new Location(marker.getLat(), marker.getLon());
                        if (loader.saveBaseLocation(newBaseLocation) > 0) {
                            baseLocationIsExist = Boolean.TRUE;
                            baseLocation = newBaseLocation;
                            loader.setBaseLocation(baseLocation);
                            saveButton.setEnabled(Boolean.FALSE);
                        }
                    }
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO interrupt run threads
                saveButton.setEnabled(Boolean.FALSE);
                if (loader != null) {
                    loader.clearData();
                }
                routeId = loader.getNextRouteId();
                routeList.clear();
                removeMarkersAndPolygonsFromMap();
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
            saveButton.setEnabled(jMapViewer.getMapMarkerList().size() > 1);
            vehiclesTypesCombo.setEnabled(Boolean.TRUE);
            runButton.setEnabled(Boolean.FALSE);
        } else if (loadNewBaseCoordinate.isSelected()) {
            saveButton.setEnabled(Boolean.TRUE);
            vehiclesTypesCombo.setEnabled(Boolean.FALSE);
            runButton.setEnabled(Boolean.FALSE);
        } else {
            saveButton.setEnabled(Boolean.FALSE);
            vehiclesTypesCombo.setEnabled(Boolean.FALSE);
            runButton.setEnabled(Boolean.TRUE);
        }
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

    private static void initializeVehicleCheckbox() {
        int typesSize = VehicleTypeEnum.values().length;
        int index = 0;
        String[] allVehileTypes = new String[typesSize];
        for (VehicleTypeEnum value : VehicleTypeEnum.values()) {
            allVehileTypes[index++] = value.getName();
        }
        vehiclesTypesCombo = new JComboBox(allVehileTypes);
    }

    private static void removeMarkersAndPolygonsFromMap() {
        jMapViewer.removeAllMapMarkers();
        jMapViewer.removeAllMapPolygons();
        setBaseLocationOnMap();
    }

    private static void setBaseLocationOnMap() {
        if (baseLocationIsExist)
            jMapViewer.addMapMarker(new MapMarkerDot(Color.CYAN, baseLocation.getLatitude(), baseLocation.getLongitude()));
    }
}
