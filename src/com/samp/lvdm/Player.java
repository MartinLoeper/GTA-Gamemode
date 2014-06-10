package com.samp.lvdm;

import net.gtaun.event.IEventListener;
import net.gtaun.samp.CheckpointBase;
import net.gtaun.samp.MenuBase;
import net.gtaun.samp.PickupBase;
import net.gtaun.samp.PlayerBase;
import net.gtaun.samp.data.Color;
import net.gtaun.samp.data.Point;
import net.gtaun.samp.data.PointAngle;
import net.gtaun.samp.data.Vector3D;
import net.gtaun.samp.event.CheckpointEnterEvent;


public class Player extends PlayerBase
{
	static final int INITIAL_MONEY = 50000;

	static final Vector3D[] RANDOM_SPAWNS = 
	{
		new Vector3D( 1958.3783f, 1343.1572f, 15.3746f ),
		new Vector3D( 2199.6531f, 1393.3678f, 10.8203f ),
		new Vector3D( 2483.5977f, 1222.0825f, 10.8203f ), 
		new Vector3D( 2637.2712f, 1129.2743f, 11.1797f ),
		new Vector3D( 2000.0106f, 1521.1111f, 17.0625f ),
		new Vector3D( 2024.8190f, 1917.9425f, 12.3386f ),
		new Vector3D( 2261.9048f, 2035.9547f, 10.8203f ),
		new Vector3D( 2262.0986f, 2398.6572f, 10.8203f ),
		new Vector3D( 2244.2566f, 2523.7280f, 10.8203f ),
		new Vector3D( 2335.3228f, 2786.4478f, 10.8203f ),
		new Vector3D( 2150.0186f, 2734.2297f, 11.1763f ),
		new Vector3D( 2158.0811f, 2797.5488f, 10.8203f ),
		new Vector3D( 1969.8301f, 2722.8564f, 10.8203f ),
		new Vector3D( 1652.0555f, 2709.4072f, 10.8265f ),
		new Vector3D( 1564.0052f, 2756.9463f, 10.8203f ),
		new Vector3D( 1271.5452f, 2554.0227f, 10.8203f ),
		new Vector3D( 1441.5894f, 2567.9099f, 10.8203f ),
		new Vector3D( 1480.6473f, 2213.5718f, 11.0234f ),
		new Vector3D( 1400.5906f, 2225.6960f, 11.0234f ),
		new Vector3D( 1598.8419f, 2221.5676f, 11.0625f ),
		new Vector3D( 1318.7759f, 1251.3580f, 10.8203f ),
		new Vector3D( 1558.0731f, 1007.8292f, 10.8125f ),
//		new Vector3D(-857.0551f,1536.6832f,22.5870f),		// Out of Town Spawns
//		new Vector3D(817.3494f,856.5039f,12.7891f),
//		new Vector3D(116.9315f,1110.1823f,13.6094f),
//		new Vector3D(-18.8529f,1176.0159f,19.5634f),
//		new Vector3D(-315.0575f,1774.0636f,43.6406f),
		new Vector3D( 1705.2347f, 1025.6808f, 10.8203f )
	};

	static final Vector3D[] COP_SPAWNS =
	{
		new Vector3D( 2297.1064f, 2452.0115f, 10.8203f ),
		new Vector3D( 2297.0452f, 2468.6743f, 10.8203f )
	};

	
//----------------------------------------------------------
	
	boolean isSpawn;


//----------------------------------------------------------

	protected int onUpdate()
	{
		/*
		 * this.setScore(this.money());
		 */
		Vector3D point= position();
		CheckpointBase checkpoint = new CheckpointBase(point, 1.0f);
		setCheckpoint(checkpoint);
		
		return 1;
	}

	protected int onCommand( String command )
	{
		String[] cmdargs = command.split( " " );

		if (cmdargs[0].compareToIgnoreCase( "/pickup" ) == 0)
		{
			new PickupBase( 351, 15, this.position() );
			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/menu" ) == 0)
		{
			MenuBase menu = new MenuBase( "test1", 1, 0, 0, 10, 10 );
			menu.setColumnHeader( 0, "test2" );
			menu.addItem( 0, "hi" );
			menu.addItem( 0, "hey" );
			menu.show( this );

			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/checkpoint" ) == 0)
		{
			/*
			 * Checkpoint checkpoint = new Checkpoint(this.position(), 5);
			 * 
			 * checkpoint.set(this);
			 * checkpoint.disable(this);
			 */

			// Or you can do this

			IEventListener<CheckpointEnterEvent> listener = new IEventListener<CheckpointEnterEvent>()
			{
				public void handleEvent( CheckpointEnterEvent event )
				{
					event.player().playSound( 1057, new Point( 0, 0, 0 ) );
				}
			};
			
			CheckpointBase checkpoint = new CheckpointBase( (Vector3D) this.position(), 5 );
			checkpoint.eventEnter().addListener( listener );
			checkpoint.set( this );

			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/pos" ) == 0)
		{
			setPosition( Float.parseFloat( cmdargs[1] ), Float.parseFloat( cmdargs[2] ), Float.parseFloat( cmdargs[3] ) );
			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/world" ) == 0)
		{
			setWorld( Integer.parseInt( cmdargs[1] ) );
			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/interiors" ) == 0)
		{
			setInterior( Integer.parseInt( cmdargs[1] ) );
			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/angle" ) == 0)
		{
			setAngle( Float.parseFloat( cmdargs[1] ) );
			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/kill" ) == 0)
		{
			setHealth( 0 );
			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/codepage" ) == 0)
		{
			setCodepage( Integer.parseInt( cmdargs[1] ) );
			return 1;
		}

//---------------------------------------------------------- LVDM main command

		if (cmdargs[0].compareToIgnoreCase( "/help" ) == 0)
		{
			sendMessage( 0xFF004040, "Las Venturas Deathmatch: Money Grub Coded By Jax and the SA-MP Team." );
			sendMessage( 0xFF004040, "Type: /objective : to find out what to do in this gamemode." );
			sendMessage( 0xFF004040, "Type: /givecash [playerid] [money-amount] to send money to other players." );
			sendMessage( 0xFF004040, "Type: /tips : to see some tips from the creator of the gamemode." );

			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/objective" ) == 0)
		{
			sendMessage( 0xFF004040, "This gamemode is faily open, there's no specific win / endgame conditions to meet." );
			sendMessage( 0xFF004040, "In LVDM:Money Grub, when you kill a player, you will receive whatever money they have." );
			sendMessage( 0xFF004040, "Consequently, if you have lots of money, and you die, your killer gets your cash." );
			sendMessage( 0xFF004040, "However, you're not forced to kill players for money, you can always gamble in the" );
			sendMessage( 0xFF004040, "Casino's." );

			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/tips" ) == 0)
		{
			sendMessage( 0xFF004040, "Spawning with just a desert eagle might sound lame, however the idea of this" );
			sendMessage( 0xFF004040, "gamemode is to get some cash, get better guns, then go after whoever has the" );
			sendMessage( 0xFF004040, "most cash. Once you've got the most cash, the idea is to stay alive(with the" );
			sendMessage( 0xFF004040, "cash intact)until the game ends, simple right?" );

			return 1;
		}

		if (cmdargs[0].compareToIgnoreCase( "/gmx" ) == 0) {
			GameMode.gmx();
		}
		
		if (cmdargs[0].compareToIgnoreCase( "/givecash" ) == 0)
		{
			if (cmdargs.length != 3)
			{
				sendMessage( Color.WHITE, "USAGE: /givecash [playerid] [amount]" );
				return 1;
			}

			Player givePlayer = Player.get( Player.class, Integer.parseInt( cmdargs[1] ) );

			if (givePlayer == null)
			{
				sendMessage( Color.YELLOW, "Invalid player id." );
			}
			else
			{
				int money = Integer.parseInt( cmdargs[2] );

				if (money > 0 && this.money() > money)
				{
					this.giveMoney( -money );
					givePlayer.giveMoney( money );
					this.sendMessage( Color.YELLOW, "You have sent " + givePlayer.name() + "(player: " + givePlayer.id() + "), $" + money );
					givePlayer.sendMessage( Color.YELLOW, "You have recieved $" + money + " from " + this.name() + "(player: " + this.id() + ")." );

					System.out.printf( "%s(playerid:%d) has transfered %d to %s(playerid:%d)\n", this.name(), this.id(), money, givePlayer.name(), givePlayer.id() );
				}
				else
				{
					sendMessage( Color.WHITE, "Invalid transaction amount." );
				}
			}

			return 1;
		}
		return 0;
	}

	protected int onSpawn()
	{
		giveMoney( INITIAL_MONEY );
		setRandomSpawn();
		toggleClock( true );
		//giveWeapon(net.gtaun.samp.data.Weapons.WEAPON_FLAMETHROWER, 200);
		
		return 1;
	}	

	protected int onDeath( PlayerBase killer, int reason )
	{
		sendDeathMessage( killer, reason );
		if (killer != null)
		{
			killer.setScore( killer.score() + 1 );
			killer.giveMoney( this.money() );
		}
		setMoney( 0 );
		return 1;
	}

	protected int onRequestClass( int classid )
	{
		isSpawn = false;
		setupForClassSelection();
		return 1;
	}
	

//----------------------------------------------------------
	
	public void setRandomSpawn()
	{
		if (this.isSpawn)
		{
			int rand = (int) (Math.random() * COP_SPAWNS.length);
			PointAngle pointAngle = new PointAngle( COP_SPAWNS[rand].x, COP_SPAWNS[rand].y, COP_SPAWNS[rand].z, 270 );
			this.setPosition( pointAngle );
		}
		else
		{
			int rand = (int) (Math.random() * RANDOM_SPAWNS.length);
			Point point = new Point( RANDOM_SPAWNS[rand].x, RANDOM_SPAWNS[rand].y, RANDOM_SPAWNS[rand].z );
			this.setPosition( point );
		}
	}

	public void setupForClassSelection()
	{
		this.setInterior( 14 );
		this.setPosition( 258.4893f, -41.4008f, 1002.0234f );
		this.setAngle( 270.0f );
		this.setCameraPos( new Point( 256.0815f, -43.0475f, 1004.0234f ) );
		this.setCamerLookAt( new Point( 258.4893f, -41.4008f, 1002.0234f ) );
	}
}
