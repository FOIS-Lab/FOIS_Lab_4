package JPCAP.net;
import JPCAP.util.Timeval;
import java.io.Serializable;

public class Packet implements Serializable
{
  public String toColoredString(boolean colored) {
    return toString();
  }

  /**
   * Fetch data portion of the packet.
   */
  public byte [] getHeader() {
    return null;
  }

  /**
   * Fetch data portion of the packet.
   */
  public byte [] getData() {
    return null;
  }

  public String getColor() {
    return "";
  }

  public Timeval getTimeval() {
    return null;
  }


  private String _rcsid = 
    "$Id: Packet.java,v 1.8 2004/05/05 23:14:45 pcharles Exp $";
}
