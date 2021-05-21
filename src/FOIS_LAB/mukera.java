package FOIS_LAB;
import JPCAP.net.ICMPPacket;

import java.util.Arrays;

public class mukera{


    public static void main(String[] args) {
        String mess = "You are being DDOSed";
        byte[] payload = mess.getBytes();
        System.out.println(payload.length);
        int length = payload.length;
        ICMPPacket pkt = new ICMPPacket(length, payload);
        System.out.println(Arrays.toString(pkt.getICMPHeader()));
    }
}
