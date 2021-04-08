package com.an.event;

import java.util.ArrayList;
import java.util.List;

public class Mouse {
    private List<MouseListener> listeners = new ArrayList<MouseListener>();

    private int listenerCallbacks = 0;

    public void addListenerCallback() {
        listenerCallbacks++;
    }

    public int getListenerCallbacks() {
        return listenerCallbacks;
    }

    public void addListener(MouseListener listener) {
        listeners.add(listener);
    }

    public void click() {
        System.out.println("Clicked !");
        for (MouseListener listener : listeners) {
            listener.onClick(this);
        }
    }
}