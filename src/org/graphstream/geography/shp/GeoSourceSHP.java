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

package org.graphstream.geography.shp;

import java.io.IOException;
import java.net.URL;

import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureIterator;
import org.graphstream.geography.BasicSpatialIndex;
import org.graphstream.geography.Descriptor;
import org.graphstream.geography.Element;
import org.graphstream.geography.GeoSource;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * An abstract shapefile source.
 * 
 * It has the capability to read shapefiles but the accumulated data is not
 * exploited. This work is reserved to more specific implementations of this
 * class.
 * 
 * @author Merwan Achibet
 */
public abstract class GeoSourceSHP extends GeoSource {

	/**
	 * Iterator on the shapefile features.
	 */
	protected FeatureIterator<SimpleFeature> iterator;

	public GeoSourceSHP() {

		this.index = new BasicSpatialIndex();
	}

	public void begin(String fileName) throws IOException {

		if(this.iterator == null) {

			try {

				URL url = this.getClass().getResource(fileName);

				ShapefileDataStore store = new ShapefileDataStore(url);

				String type = store.getTypeNames()[0];
				FeatureSource<SimpleFeatureType, SimpleFeature> source = store.getFeatureSource(type);

				this.iterator = source.getFeatures().features();
			}
			catch (IOException e) {

				throw new RuntimeException("I/O error : " + e.getMessage());
			}
		}
	}

	public void traverse() {

		while(this.iterator != null && this.iterator.hasNext())
			process(iterator.next());

		this.iterator = null;
	}

	public void end() throws IOException {
		// Nothing to do.
	}

	/**
	 * Process a single feature coming from the data source and check if it
	 * suits the user's needs. If it is the case, keep it for a later use,
	 * ignore it otherwise.
	 * 
	 * @param feature
	 *            The GeoTools feature to consider.
	 * @throws IOException
	 */
	protected void process(SimpleFeature feature) {

		for(Descriptor descriptor : this.descriptors) {

			Element element = descriptor.newElement(feature);

			if(element != null && descriptor.matches(element))
				this.keep(element, descriptor);
		}
	}

}
