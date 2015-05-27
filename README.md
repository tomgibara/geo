# geo
A geographic coordinate library for Java.

Overview
--------

Geo library provides a simple basis for geographic coordinates. It
includes built-in support for converting between British and Irish
Ordnance Survey grid references and WGS84 latitudes and
longitudes. But the software is easily configured to support many
other mapping standards.

Licencing
---------

Geo is released under the Apache 2.0 licence. This gives it an
extremely wide scope for use in closed source, commercially licenced
software and other open source projects. Please read the licence for
full details.

Downloading

The project is packaged as a single jar with no dependencies. The
project is hosted on Github.

Documentation
-------------

Limited javadocs are available. These are part of the Maven generated
project documentation.

Usage
-----

To use the library with Maven include it as a regular compile-scoped
dependency.  It is available from the Maven central repository:

> Group ID:    `com.tomgibara.geo`
> Artifact ID: `geo`
> Version:     `0.3.0`

The Maven dependency being:


    <dependency>
      <groupId>com.tomgibara.geo</groupId>
      <artifactId>geo</artifactId>
      <version>0.3.0</version>
    </dependency>

You can build the geo library from source by cloning it from the
GitHub repository and then executing `maven install` on the geo
project.

Example Code
------------

The library has been designed to be simple to use by developers who
don't have an intricate understanding of geographic coordinate
systems. Below are some code examples to convey its idioms.

Convert an Ordnance Survey grid reference...

```java
GridRef ref = GridRefSystem.OSGB36.createGridRef("TQ 270 775");
```

... into eastings and northings...

```java
ref.getEasting();
ref.getNorthing();
```

... or a latitude and longitude (which remains relative to the
Ordnance Survey reference).

```java
ref.toLatLon();
```

If you want to change the coordinates to use a different standard, say
to WGS84 (used by GPS), you need to obtain a transform.

DatumTransform transform =
DatumTransforms.getDefaultTransforms().getTransform(Datum.WSG84); The
transform operates on points in 3D space, so you need to be explict
about the height when you apply the transform.

```java
LatLonHeight coords = transform.transform(ref.toLatLon().atHeight(0));
```

The resulting coordinates also include a height, but the underlying
latitude and longitude is easily recovered.

```java
coords.getLatLon();
```

If you need to support other reference systems, you can define your
own ellipsoids...

```java
Ellipsoid ED50 = Ellipsoid.withAxes(6378388.000, 6356911.946);
```

... datums ...

```java
Datum UTM_32N = Datum.withDegreesMeters(ED50, 0.9996, 0, 9, 500000, 0);
```

... and grids.

```java
Grid grid = new Grid() { /*...*/ };
```

Which can be combined into a new reference system.

```java
GridRefSystem ELD79 = GridRefSystem.withDatumAndGrid(UTM_32N, grid);
```
