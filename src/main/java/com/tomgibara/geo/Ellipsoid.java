/*
 * Copyright 2012 Tom Gibara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tomgibara.geo;

import static com.tomgibara.geo.GeoUtil.canonical;

/**
 * An approximation of a planetary shape against which coordinates may be defined.
 *
 * @author Tom Gibara
 */

public final class Ellipsoid {

	// used in France
	public static final Ellipsoid MAUPERTUIS = withAxisAndInvFlat(6397300.0, 191);
	public static final Ellipsoid PLESSIS = withAxisAndInvFlat(6376523.0, 308.64);
	// used for OS in UK
	public static final Ellipsoid AIRY_1830 = withAxes(6377563.396, 6356256.909);
	// used for OSI in Ireland
	public static final Ellipsoid MODIFIED_AIRY = withAxes(6377340.189, 6356034.447);
	// used in India
	public static final Ellipsoid EVEREST_1830 = withAxes(6377299.365, 6356098.359);
	// used in West Malaysia and Singapore
	public static final Ellipsoid EVEREST_1967_MOD = withAxisAndInvFlat(6377304.063, 300.8017);
	// used in Brunei and East Malaysia
	public static final Ellipsoid EVEREST_1967 = withAxisAndInvFlat(6377298.556, 300.8017);
	// used in Europe, Japan
	public static final Ellipsoid BESSEL = withAxes(6377397.155, 6356078.963);
	// used in North America
	public static final Ellipsoid CLARKE66 = withAxes(6378206.4, 6356583.8);
	public static final Ellipsoid CLARKE78 = withAxes(6378190.0, 6356456.0);
	// used in France and Africa
	public static final Ellipsoid CLARKE80 = withAxisAndInvFlat(6378249.145, 293.465);
	// Unknown
	public static final Ellipsoid HELMERT = withAxisAndInvFlat(6378200.0, 298.3);
	// used in USA
	public static final Ellipsoid HAYFORD = withAxisAndInvFlat(6378388.0, 297);
	// used in Europe, Channel Islands
	public static final Ellipsoid INT_1924 = withAxes(6378388.0, 6356911.946);
	// used in Russia, Romania
	public static final Ellipsoid KRASSOVSKY = withAxisAndInvFlat(6378245.0, 298.3);
	// used by USA DoD
	public static final Ellipsoid WGS66 = withAxisAndInvFlat(6378145.0, 298.25);
	// used in Australia
	public static final Ellipsoid AUS_NAT = withAxisAndInvFlat(6378160.0, 298.25);
	// ?
	public static final Ellipsoid INT_1967 = withAxes(6378157.5, 6356772.2);
	public static final Ellipsoid GRS67 = withAxisAndInvFlat(6378160.0, 298.247167427);
	// used in South America
	public static final Ellipsoid S_AMERICAN = withAxisAndInvFlat(6378160.0, 298.25);
	// used by USA DoD
	public static final Ellipsoid WGS72 = withAxisAndInvFlat(6378135.0, 298.26);
	// used for GPS
	public static final Ellipsoid GRS80 = withAxisAndInvFlat(6378137.0, 298.257222101);
	public static final Ellipsoid WGS84 = withAxisAndInvFlat(6378137.0, 298.257223563);
	public static final Ellipsoid IERS_1989 = withAxisAndInvFlat(6378136.0, 298.257);
	public static final Ellipsoid IERS_2003 = withAxes(6378136.6, 6356751.9);


	public static Ellipsoid withAxes(double major, double minor) {
		return canonical(new Ellipsoid(major, minor));
	}

	public static Ellipsoid withAxisAndInvFlat(double major, double inverseFlattening) {
		double ratio = 1 - 1.0 / inverseFlattening;
		return canonical(new Ellipsoid(major, major * ratio));
	}

	public final double a; // semi-major axis
	public final double b; // semi-minor axis
	public final double e2; // eccentricity

	final double n;
	final double n2;
	final double n3;

	private Ellipsoid(double a, double b) {
		if (!GeoUtil.isCoordinate(a)) throw new IllegalArgumentException("a invalid");
		if (!GeoUtil.isCoordinate(b)) throw new IllegalArgumentException("b invalid");
		if (a <= 0) throw new IllegalArgumentException("a not positive");
		if (b <= 0) throw new IllegalArgumentException("b not positive");
		if (a < b) throw new IllegalArgumentException("a not major");
		this.a = a;
		this.b = b;
		e2 = 1 - (b*b)/(a*a);
		n = (a-b)/(a+b);
		n2 = n * n;
		n3 = n2 * n;
	}

	@Override
	public int hashCode() {
		return GeoUtil.hashCode(a) ^ 31 * GeoUtil.hashCode(b);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Ellipsoid)) return false;
		Ellipsoid that = (Ellipsoid) obj;
		if (this.a != that.a) return false;
		if (this.b != that.b) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Equ. " + a +"m Pol. " + b + "m";
	}

}
