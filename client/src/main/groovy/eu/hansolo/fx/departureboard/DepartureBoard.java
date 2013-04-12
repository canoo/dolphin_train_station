package eu.hansolo.fx.departureboard;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by
 * User: hansolo
 * Date: 23.04.12
 * Time: 09:05
 */
public class DepartureBoard {
    private static final boolean  SOUND_ON     = true;
    private static final String[] DESTINATIONS = {
        " ",
        "NEW YORK",
        "LONDON",
        "BASEL",
        "FRANKFURT",
        "SYDNEY",
        "PARIS",
        "SAN FRANCISCO",
        "BOSTON",
        "MUNICH",
        "BERLIN",
        "FRANKFURT",
        "BARCELONA",
        "BEIJING",
        "SHANGHAI",
        "HONG KONG",
        "TOKYO",
        "YOKOHAMA",
        "BRISBANE",
        "STOCKHOLM",
        "OSLO",
        "MOSCOW",
        "PRAGUE",
        "LION",
        "ZURICH",
        "MADRID",
        "LISBOA",
        "CHICAGO",
        "HELSINKI"
    };

    private VBox                pane;
    private List<Row>           queue;
    private ObservableList<Row> rows;
    private List<Row>           activeRows;
    private AnimationTimer      timer;
    private Image[]             logoArray;
    private AnimationTimer      updateTimer;
    private AnimationTimer      ledTimer;
    private long                lastTimerCall;
    private long                lastUpdateCall;
    private long                lastLedCall;
    private int                 queueCounter;
    private boolean             isUpdating;

    public DepartureBoard() {
        super();
        pane = new VBox();
        pane.setSpacing(3);
        queue          = new LinkedList<Row>();
        rows           = FXCollections.observableArrayList();
        lastTimerCall  = 10000000000l;
        lastUpdateCall = 6000000000l;
        lastLedCall    = 750000000l;
        for (int i = 0 ; i < 8 ; i++) {
            rows.add(new Row(SOUND_ON, DESTINATIONS));
        }

        rows.addListener(new ListChangeListener<Row>(){
            @Override
            public void onChanged(Change<? extends Row> change) {

            }
        });
        for (Row row : rows) {
            pane.getChildren().add(row.getPane());
        }
        activeRows = new LinkedList<Row>();
        timer = new AnimationTimer() {
            @Override public void handle(final long l) {
                if ((l - lastTimerCall) >= 8000000000l) {
                    // check the rows for their departure time
//                    checkRows();
                    lastTimerCall = l;
                }
            }
        };
        updateTimer = new AnimationTimer() {
            @Override public void handle(final long l) {
                if ((l - lastUpdateCall) >= 5000000000l) {
                    // update the next row
                    update();
                    lastUpdateCall = l;
                }
            }
        };
        ledTimer = new AnimationTimer() {
            @Override public void handle(final long l) {
                if ((l - lastLedCall) >= 750000000l) {
                    final Calendar CAL = Calendar.getInstance();
                    final int HH   = CAL.get(Calendar.HOUR_OF_DAY);
                    final int MM   = CAL.get(Calendar.MINUTE);
                    for (Row row : activeRows) {
                        if (!row.isEmpty()) {
                            int hour = Integer.parseInt(row.getHours());
                            int min  = Integer.parseInt(row.getMinutes());
                            if (HH == hour) {
                                if (MM >= min || MM + 10 >= min) {
                                    row.toggleLeds();
                                } else if (HH + 1 == hour && (MM + 10) % 60 >= min) {
                                    row.toggleLeds();
                                } else {
                                    row.setLedsOff();
                                }
                            }
                        }
                    }
                    lastLedCall = l;
                }
            }
        };
        queueCounter = 0;
        isUpdating   = false;
        timer.start();
        updateTimer.start();
        ledTimer.start();
    }

    public final VBox getPane() {
        return pane;
    }

    public final void addRow(final Row ROW) {
        queue.add(ROW);
        updateTimer.start();
    }

    public final void removeRow(final Row ROW) {
        if (queue.contains(ROW)) {
            queue.remove(ROW);
        }
    }

    private final void checkRows() {
        final Calendar CAL = Calendar.getInstance();
        final int HH   = CAL.get(Calendar.HOUR_OF_DAY);
        final int MM   = CAL.get(Calendar.MINUTE);
        boolean addRow;
        for (Row row : rows) {
            try {
                int hour = Integer.parseInt(row.getHours());
                int min  = Integer.parseInt(row.getMinutes());
                addRow   = false;
                if (HH == hour) {
                    if (MM >= min || MM + 10 >= min) {
                        addRow = true;
                    } else if (HH + 1 == hour && (MM + 10) % 60 >= min) {
                        addRow = true;
                    }
                }
                if (addRow) {
                    if (!activeRows.contains(row)) {
                        activeRows.add(row);
                    }
                }
            } catch (NumberFormatException exception) {
            }
        }
        removeOverdue();
    }

    private void removeOverdue() {
        final Calendar CAL = Calendar.getInstance();
        final int HH       = CAL.get(Calendar.HOUR_OF_DAY);
        final int MM       = CAL.get(Calendar.MINUTE);
        updateTimer.stop();
        for (int i = 0 ; i < rows.size() ; i++) {
            try {
                int hh = Integer.parseInt(rows.get(i).getHours());
                int mm = Integer.parseInt(rows.get(i).getMinutes());
                if (HH > hh || (HH == hh && MM > mm)) {
                    rows.get(i).reset();
                    queue.remove(i);
                    queueCounter = 0;
                    activeRows.remove(0);
                }
            } catch (NumberFormatException exception) {

            }
        }
        updateTimer.start();
    }

    private void update() {
        if (!rows.isEmpty() && queueCounter < rows.size()) {
            rows.get(queueCounter).setRow(queue.get(queueCounter));
            rows.get(queueCounter).setLedsOff();
            isUpdating = true;
            queueCounter++;
            if (queueCounter >= queue.size()) {
                queueCounter = 0;
                isUpdating = false;
                updateTimer.stop();
            }
            for (int i = queue.size() ; i < rows.size() ; i++) {
                rows.get(i).reset();
            }
        }
    }
}
