package com.samp.lvdm;

import net.gtaun.samp.CheckpointBase;
import net.gtaun.samp.PlayerBase;
import net.gtaun.samp.data.Point;
import net.gtaun.samp.data.Vector3D;


public class Checkpoint extends CheckpointBase
{
	public Checkpoint(float x, float y, float z, float size)
	{
		super( x, y, z, size );
	}

	public Checkpoint(Vector3D point, float size)
	{
		super( point, size );
	}

	public int onEnter( PlayerBase player )
	{
		player.playSound( 1057, new Point( 0, 0, 0 ) );
		return 0;
	}
}
