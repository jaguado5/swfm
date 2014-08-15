 /*-----------------------------------------------------------------------------
 *
 *                      Gaia CU7 variability
 *
 *         Copyright (C) 2005-2020 Gaia Data Processing and Analysis Consortium
 *
 *
 * CU7 variability software is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * CU7 variability software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this CU7 variability software; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 *
 *-----------------------------------------------------------------------------
 */


package swfm.view;

/**
 *
 * TODO Short description
 * @author juanjo
 * @version $Id$
 *
 */

public class BestPlayerClasifJornadaRegPlayer {



    private String player;
    private int totalPoints;
    private String average;

    public String getPlayer() {
        return player;
    }


    public int getTotalPuntos() {
        return totalPoints;
    }


    public String getAverage() {
        return average;
    }

    
    
    public BestPlayerClasifJornadaRegPlayer(String player, int totalPoints, String average) {
        this.player = player;
        this.totalPoints = totalPoints;
        this.average = average;
    }

}

