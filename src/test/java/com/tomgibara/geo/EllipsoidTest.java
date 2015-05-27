package com.tomgibara.geo;

import junit.framework.TestCase;

public class EllipsoidTest extends TestCase {

	public void testAxisWithInvFlat() {
		assertTrue(Math.abs(Ellipsoid.WGS84.b - 6356752.3142) <= 0.00005);
		assertTrue(Math.abs(Ellipsoid.GRS80.b - 6356752.3141) <= 0.00005);
	}

}
