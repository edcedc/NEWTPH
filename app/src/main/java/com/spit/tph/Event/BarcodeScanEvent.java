package com.spit.tph.Event;

public class BarcodeScanEvent {
    private String barcode;

    public BarcodeScanEvent(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }
}
