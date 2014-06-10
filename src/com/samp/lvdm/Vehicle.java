package com.samp.lvdm;

import net.gtaun.samp.VehicleBase;
import net.gtaun.samp.data.Point;
import net.gtaun.samp.data.PointAngle;


public class Vehicle extends VehicleBase
{
	public Vehicle(int model, float x, float y, float z, float angle, int color1, int color2, int respawnDelay)
	{
		super( model, x, y, z, angle, color1, color2, respawnDelay );
	}

	public Vehicle(int model, PointAngle point, int color1, int color2, int respawnDelay)
	{
		super( model, point, color1, color2, respawnDelay );
	}

	public Vehicle(int model, Point point, float angle, int color1, int color2, int respawnDelay)
	{
		super( model, point, angle, color1, color2, respawnDelay );
	}

	public Vehicle(int model, float x, float y, float z, int interior, int world, float angle, int color1, int color2, int respawnDelay)
	{
		super( model, x, y, z, interior, world, angle, color1, color2, respawnDelay );
	}

	protected int onSpawn()
	{
		this.setNumberPlate( "Shoebill" );
		return 1;
	}
}
