package com.jco.map;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

/**
 * Java Cost Optimizer map frame implementation
 *
 * @author Bondoronok_P
 * Date: 26.02.13
 */
public class JcoMap extends JFrame implements JMapViewerEventListener {

    private static JMapViewer jMapViewer = null;
    private static JcoMap jcoMap = null;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final String FRAME_HEADER = "Open Street Map viewer";

    /**
     * Instantiate map by latitude an longitude
     */
    private JcoMap() {
        super(FRAME_HEADER);
        setSize(WIDTH, HEIGHT);
        jMapViewer = new JMapViewer();
        // Listen to the map viewer for user operations so components will
        // recieve events and update
        jMapViewer.addJMVListener(this);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        add(jMapViewer, BorderLayout.CENTER);

        jMapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    jMapViewer.getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });

        jMapViewer.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean cursorHand = jMapViewer.getAttribution().handleAttributionCursor(e.getPoint());
                if (cursorHand) {
                    jMapViewer.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    jMapViewer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
        jMapViewer.repaint();
        return jcoMap;
    };

    /**
     * Load full OSM
     *
     * @return {@link JcoMap} instance
     */
    public static JcoMap loadFullMap() {
        loadInstance();
        jMapViewer.repaint();
        return jcoMap;
    }

    /**
     * Get {@link JMapViewer} instance.
     *
     * @return {@link JMapViewer} instance
     * @throws InstantiationException
     */
    public JMapViewer getjMapViewerInstance() throws InstantiationException {
        if (jMapViewer == null) {
            throw new InstantiationException("JMapViewer instantiation exception");
        }
        return jMapViewer;
    }

    @Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM)) {

        }
    }
}
