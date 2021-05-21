// $Id: MACAddress.java,v 1.4 2002/11/07 23:23:46 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/
package JPCAP.net;

import JPCAP.util.HexHelper;
public class MACAddress {
  public static String extract(int offset, byte [] bytes) {
    StringBuffer sa = new StringBuffer();
    for(int i=offset; i<offset + WIDTH; i++) {
      sa.append(HexHelper.toString(bytes[i]));
      if(i != offset + WIDTH - 1)
        sa.append(':');
    }
    return sa.toString();
  }

  /**
   * Generate a random MAC address.
   */
  public static long random() {
    return (long)(0xffffffffffffL * Math.random());
  }

  /**
   * The width in bytes of a MAC address.
   */
  public static final int WIDTH = 6;

  private String _rcsid = 
    "$Id: MACAddress.java,v 1.4 2002/11/07 23:23:46 pcharles Exp $";
}
