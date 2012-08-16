/*
 * Copyright 2006 - 2012 
 *     Julien Baudry	<julien.baudry@graphstream-project.org>
 *     Antoine Dutot	<antoine.dutot@graphstream-project.org>
 *     Yoann Pigné		<yoann.pigne@graphstream-project.org>
 *     Guilhelm Savin	<guilhelm.savin@graphstream-project.org>
 * 
 * This file is part of GraphStream <http://graphstream-project.org>.
 * 
 * GraphStream is a library whose purpose is to handle static or dynamic
 * graph, create them from scratch, file or any source and display them.
 * 
 * This program is free software distributed under the terms of two licenses, the
 * CeCILL-C license that fits European law, and the GNU Lesser General Public
 * License. You can  use, modify and/ or redistribute the software under the terms
 * of the CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
 * URL <http://www.cecill.info> or under the terms of the GNU LGPL as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C and LGPL licenses and that you accept their terms.
 */

package org.graphstream.geography.test;

import org.graphstream.geography.shp.GeoSourceNavteq;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Test the shapefile import.
 * 
 * @author Antoine Dutot
 * @author Merwan Achibet
 */

public class Test_Navteq {

	public static void main(String[] args) {

		Graph graph = new SingleGraph("road network");
		graph.display(false);

		GeoSourceNavteq src = new GeoSourceNavteq("/home/merwan/navteq/Streets.shp", "/home/merwan/navteq/Zlevels.shp");
		src.addSink(graph);
		
		src.transform();
		
		/*
		// Prepare the file import.

		GeoSourceSHP src = new GeoSourceSHP() {

			@Override
			public void transform() {

				// Add the roads to the graph as edges. The Z
				// points of the spatial index are used to resolve the Z level
				// conflicts.

				ArrayList<String> addedIds = new ArrayList<String>();

				for(Element e : this.index)
					if(e.getCategory().equals("ROAD")) {

						// TODO take care of the Z index issue.

						Line line = (Line)e;

						Point[] endPoints = line.getEndPoints();

						Point from = endPoints[0];
						String idFrom = null;
						Coordinate positionFrom = from.getPosition();
						ArrayList<Element> here = this.index.getElementsAt(positionFrom.x, positionFrom.y);
						if(here.size() > 0)
							idFrom = here.get(0).getId();

						if(idFrom != null && !addedIds.contains(idFrom)) {
							sendNodeAdded(this.sourceId, idFrom);
							sendNodeAttributeAdded(this.sourceId, idFrom, "x", positionFrom.x);
							sendNodeAttributeAdded(this.sourceId, idFrom, "y", positionFrom.y);
							addedIds.add(idFrom);
						}

						Point to = endPoints[1];
						String idTo = null;
						Coordinate positionTo = to.getPosition();
						here = this.index.getElementsAt(positionTo.x, positionTo.y);
						if(here.size() > 0)
							idTo = here.get(0).getId();

						if(idTo != null && !addedIds.contains(idTo)) {
							sendNodeAdded(this.sourceId, idTo);
							sendNodeAttributeAdded(this.sourceId, idTo, "x", positionTo.x);
							sendNodeAttributeAdded(this.sourceId, idTo, "y", positionTo.y);
							addedIds.add(idTo);
						}

						if(idFrom != null && idTo != null)
							sendEdgeAdded(this.sourceId, e.getId(), idFrom, idTo, false);
					}
			}

		};
	*/
	}
	
}
