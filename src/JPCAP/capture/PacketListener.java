package JPCAP.capture;

import JPCAP.net.Packet;


/**
 * Packet data listener.
 * <p>
 * Applications interested in listening for packets must register
 * with PacketCapture and implement PacketDataListener.
 *
 * @author Patrick Charles and Jonas Lehmann
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2001/05/25 13:46:00 $
 */
public interface PacketListener
{
  void packetArrived(Packet packet);
}
