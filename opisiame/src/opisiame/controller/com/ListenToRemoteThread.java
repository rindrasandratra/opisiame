/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.com;

import com.rapplogic.xbee.XBeePin;
import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.ErrorResponse;
import com.rapplogic.xbee.api.RemoteAtRequest;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.IoSample;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;
import java.util.List;
import java.util.logging.Level;
import opisiame.controller.gestion_eleve.Link_eleve_teleController;
import opisiame.model.Eleve;

/**
 *
 * @author Sandratra
 */
public class ListenToRemoteThread extends Thread {

    private XBee xbee;

    String num_port;
    
    List<Eleve> eleves;

    private Boolean running = true;

    String led_yellow = "D7";
    String led_green = "D5";
    String led_red = "D4";

    public ListenToRemoteThread(XBee xBee, String num_port,List<Eleve> Eleves) {
        this.num_port = num_port;
        this.xbee = xBee;
        this.eleves = Eleves;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                while (true) {
                    xbee.clearResponseQueue();
                    XBeeResponse response = xbee.getResponse();
                    if (response.isError()) {
                        ErrorResponse errorResponse = (ErrorResponse) response;
                        System.out.println(errorResponse.getException());
                        continue;
                    }
                    ProcessResponse processResponse = new ProcessResponse(response);
                    processResponse.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                    xbee.close();
            }
        }
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public void switch_on_led(String led_id, XBeeAddress64 address_remote) throws XBeeException {

        RemoteAtRequest request_led_on = new RemoteAtRequest(address_remote, led_id, new int[]{XBeePin.Capability.DIGITAL_OUTPUT_HIGH.getValue()});
        xbee.sendAsynchronous(request_led_on);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Link_eleve_teleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // xbee.clearResponseQueue();
    }

    public void switch_off_led(String led_id, XBeeAddress64 address_remote) throws XBeeException {
        RemoteAtRequest request_led_off = new RemoteAtRequest(address_remote, led_id, new int[]{XBeePin.Capability.DIGITAL_OUTPUT_LOW.getValue()});
        xbee.sendAsynchronous(request_led_off);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Link_eleve_teleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // xbee.clearResponseQueue();
    }

    class ProcessResponse extends Thread {

        XBeeResponse response;

        public ProcessResponse(XBeeResponse response) {
            this.response = response;
        }

        XBeeAddress64 adress_mac;

        public XBeeAddress64 getAdress_mac() {
            return adress_mac;
        }

        public void setAdress_mac(XBeeAddress64 adress_mac) {
            this.adress_mac = adress_mac;
        }

        @Override
        public void run() {
            if (response.getApiId() == ApiId.RX_64_IO_RESPONSE) {
                RxResponseIoSample ioSample = (RxResponseIoSample) response;

                System.err.println("iosample ::: " + ioSample.toString());

                XBeeAddress64 address_remote = (XBeeAddress64) ioSample.getSourceAddress();

                this.adress_mac = address_remote;

                System.err.println("address64 : " + address_remote);

                try {
                    for (IoSample sample : ioSample.getSamples()) {
                        if (!ioSample.containsAnalog()) {
                            if (!sample.isD2On()) { // bouton : rouge
                                switch_on_led(led_red, address_remote);
                                switch_off_led(led_red, address_remote);
                            }
                            if (!sample.isD1On()) {
                                switch_on_led(led_yellow, address_remote);
                                switch_off_led(led_yellow, address_remote);
                            }
                            if (!sample.isD0On()) {
                                switch_on_led(led_green, address_remote);
                                switch_off_led(led_green, address_remote);
                            }
                        }
                    }
                } catch (XBeeException e) {
                    System.out.println("bouh ya (cacha)");
                    e.printStackTrace();
                }
            }
        }
    }

}
