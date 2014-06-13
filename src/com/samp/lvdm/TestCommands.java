package com.samp.lvdm;

import net.gtaun.shoebill.SampObjectManager;
import net.gtaun.shoebill.common.command.Command;
import net.gtaun.shoebill.common.vehicle.VehicleUtils;
import net.gtaun.shoebill.data.AngledLocation;
import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.data.Radius;
import net.gtaun.shoebill.object.Checkpoint;
import net.gtaun.shoebill.object.Menu;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.Vehicle;


public class TestCommands
{	
	public TestCommands() {}
	
	@Command
	public boolean doublekill(Player p) {
		LvdmGamemode.queue.addPoints(p, "GEGNER ELIMINIERT", 100, DynamicActionLabel.DynamicItem.TYPE_BIG);
		LvdmGamemode.queue.addPoints(p, "GEGNER ELIMINIERT", 100, DynamicActionLabel.DynamicItem.TYPE_BIG);
		LvdmGamemode.queue.addPoints(p, "DOPPELTER KILL", 20, DynamicActionLabel.DynamicItem.TYPE_SMALL);
		return true;
	}
	
	@Command
	public boolean veh(Player p,int vID,int c1,int c2)
	{
		AngledLocation location = p.getLocation();
		Vehicle.create(vID,location.getX()+1,location.getY()+1,location.getZ(),location.getAngle(),c1,c2,-1);
		return true;
	} 
	@Command
	public boolean gethere(Player p,int pID)
	{
		AngledLocation locationp = p.getLocation();
		locationp.getX();
		locationp.getY();
		locationp.getZ();
		Player.get(pID).setLocation(locationp.getX()+1 ,locationp.getY()+1,locationp.getZ());
		return true;
	} 
	@Command
	public boolean shot2(Player p) {
		LvdmGamemode.queue.addPoints(p, "REANIMATION", 100, DynamicActionLabel.DynamicItem.TYPE_BIG);
		return true;
	}
	
	@Command
	public boolean shot3(Player p) {
		LvdmGamemode.queue.addPoints(p, "HILFE BEI KILL", 58, DynamicActionLabel.DynamicItem.TYPE_SMALL);
		return true;
	}

	@Command
	public boolean drink(Player p)
	{
		p.setDrunkLevel(50000);
		return true;
	}

	@Command
	public boolean pickup(Player p)
	{
		Location location = p.getLocation();
		location.y += 10;
		SampObjectManager.get().createPickup(351, 15, location);
		return true;
	}

	@Command
	public boolean menu(Player p)
	{
		Menu menu = SampObjectManager.get().createMenu("test1", 1, 0, 0, 100, 100);
		menu.setColumnHeader(0, "test2");
		menu.addItem(0, "hi");
		menu.addItem(0, "hey");
		menu.show(p);

		return true;
	}

	@Command
	public boolean checkpoint(Player p)
	{
		Radius location = new Radius(p.getLocation(), 10);
		location.x += 10;

		p.setCheckpoint(new Checkpoint()
		{
			@Override
			public Radius getLocation()
			{
				return location;
			}

			@Override
			public void onEnter(Player p)
			{
				p.disableCheckpoint();
				p.playSound(1057);
			}
		});

		return true;
	}

	@Command
	public boolean tp(Player p, float x, float y, float z)
	{
		p.setLocation(x, y, z);
		return true;
	}

	@Command
	public boolean world(Player p, int worldId)
	{
		p.setWorld(worldId);
		return true;
	}

	@Command
	public boolean interior(Player p, int interiorId)
	{
		p.setInterior(interiorId);
		return true;
	}

	@Command
	public boolean angle(Player p, float angle)
	{
		p.setAngle(angle);
		return true;
	}

	@Command
	public boolean codepage(Player p, int code)
	{
		p.setCodepage(code);
		return true;
	}
}
