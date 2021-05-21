// $Id: PacketCaptureBase.java,v 1.5 2004/05/05 23:14:44 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package JPCAP.capture;

import JPCAP.net.Packet;
import JPCAP.net.PacketFactory;
import JPCAP.net.RawPacket;
import JPCAP.util.Timeval;
public abstract class PacketCaptureBase 
  extends PacketDispatcher implements PacketHandler
{
  // internal packet handling implementation

  /**
   * Handle arriving packets.
   * <p>
   * Arriving packets are dispatched to registered packet listeners.
   * If caplen is smaller than length, then the packet was truncated 
   * because the amount of data on the wire exceeded the snapshot length
   * specified when open() was called.
   * <p>
   * In the simulator case, fake packets are generated and this method 
   * called with the fabricated data. In the case of the capture system,
   * the native library libjpcap calls the method back.
   *
   * @param length the length of the packet off of the wire.
   * @param caplen the number of bytes actually captured. 
   * @param seconds the seconds component of the timestamp.
   * @param useconds the microseconds component of the timestamp.
   * @param data the contents of the captured packet.
   */
  public void handlePacket(int length, int caplen, 
                           int seconds, int useconds, byte [] data) {
    // create a raw packet and abstract packet from the raw data
    Timeval tv = new Timeval(seconds, useconds);

    RawPacket rp = new RawPacket(tv, data, 
                                 length > caplen ? length - caplen : 0);
    Packet p = PacketFactory.dataToPacket(linkType, data, tv);

    // dispatch the raw packet to registered listeners..
    dispatchRawPacket(rp);

    // dispatch the packet to listeners interested in packet objects..
    dispatchPacket(p);
  }


  /**
   * The link-type code for the currently open network device.
   * The type is queried and set in open() and then utilized when 
   * packets are received by handlePacket().
   */
  public int linkType;

  // the following are redundant with what's stored in CaptureStatistics.
  // this is temporary storage enabling the native code's statistics function
  // to manipulate the results directly in this class rather than trying 
  // to modify fields inside an object held by this object.
  // figure out how to do this more elegantly.
  public int receivedCount = 0;
  public int droppedCount = 0;

  private String _rcsid = 
    "$Id: PacketCaptureBase.java,v 1.5 2004/05/05 23:14:44 pcharles Exp $";
}
