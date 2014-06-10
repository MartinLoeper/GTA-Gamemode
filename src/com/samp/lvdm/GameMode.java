package com.samp.lvdm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Vector;

import net.gtaun.samp.GameModeBase;
import net.gtaun.samp.PickupBase;
import net.gtaun.samp.PlayerBase;
import net.gtaun.samp.TimerBase;
import net.gtaun.samp.event.TimerTickEvent;
import net.gtaun.event.IEventListener;


public class GameMode extends GameModeBase
{
	static GameMode instance = null;

	
	TimerBase timer = new TimerBase( 5000 );


	protected GameMode()
	{
		super( Player.class ); // Change original init player class to "Player"
							   // class.
		instance = this; // Change current using instance.

		setGameModeText( "Ventura's DM~MG" );
		showPlayerMarkers( 1 );
		showNameTags( true );
		Player.enableStuntBonusForAll( false );

		
		IEventListener<TimerTickEvent> cashUpdateListener = new IEventListener<TimerTickEvent>()
		{
			public void handleEvent( TimerTickEvent event )
			{
				Vector<Player> players = Player.get( Player.class );
				Iterator<Player> iterator = players.iterator();
				while (iterator.hasNext())
				{
					Player player = iterator.next();
					player.setScore( player.money() );
				}
			}
		};

		timer.eventTick().addListener( cashUpdateListener );
		timer.start();

		new PickupBase( 371, 15, 1710.3359f, 1614.3585f, 10.1191f, 0 ); // para
		new PickupBase( 371, 15, 1964.4523f, 1917.0341f, 130.9375f, 0 ); // para
		new PickupBase( 371, 15, 2055.7258f, 2395.8589f, 150.4766f, 0 ); // para
		new PickupBase( 371, 15, 2265.0120f, 1672.3837f, 94.9219f, 0 ); // para
		new PickupBase( 371, 15, 2265.9739f, 1623.4060f, 94.9219f, 0 ); // para

		loadClass(); 
		loadVehicle();
	}

	
//----------------------------------------------------------

	protected int onConnect( PlayerBase p )
	{
		Player player = (Player) p;
		player.gameText( 5000, 5, "~w~SA-MP: ~r~Las Venturas ~g~MoneyGrub!!!" );
		player.sendMessage( 0xFF004040, "Welcome to Las Venturas MoneyGrub, For help type /help." );
		
		return 1; 
	}

	protected int onExit()
	{
		instance = null;
		
		return 1;
	}
	
	
//----------------------------------------------------------

	void loadVehicle()
	{
		BufferedReader reader; 

		try
		{
			File file[] = new File( "scriptfiles/vehicles" ).listFiles();
			int count = 0;

			for (int i = 0; i < file.length; i++)
			{
				reader = new BufferedReader( new InputStreamReader( new FileInputStream( file[i] ), Charset.forName( "UTF-8" ) ) );
				System.out.println( "loading " + file[i] );

				while (reader.ready())
				{
					String data = reader.readLine().trim();
					String[] datas = data.split( "," );
					if (data.length() == 0 || data.charAt( 0 ) == '/' || datas.length < 7) continue;
					new Vehicle( Integer.parseInt( datas[0].trim() ), Float.parseFloat( datas[1] ), Float.parseFloat( datas[2] ), Float.parseFloat( datas[3] ), Float.parseFloat( datas[4] ), Integer.parseInt( datas[5].trim() ), Integer.parseInt( datas[6].split( " " )[0] ), 10000 );

					count++;
				}
			}

			System.out.println( "Created " + count + " vehicles." );
		}
		catch (IOException e)
		{
			System.out.println( "Can't initialize vehicles, please check your \"vehicles\" file." );
		}
	}

	void loadClass()
	{
		BufferedReader reader;
		try
		{
			reader = new BufferedReader( new InputStreamReader( new FileInputStream( "scriptfiles/class.txt" ), Charset.forName( "UTF-8" ) ) );

			int count = 0;
			System.out.println( "loading scriptfiles/class.txt" );

			while (reader.ready())
			{
				String data = reader.readLine().trim();
				String[] datas = data.split( "," );

				if (data.length() == 0 || data.charAt( 0 ) == '/' || datas.length < 11) continue;

				addPlayerClass( Integer.parseInt( datas[0].trim() ), Float.parseFloat( datas[1] ), Float.parseFloat( datas[2] ), Float.parseFloat( datas[3] ), Float.parseFloat( datas[4] ), Integer.parseInt( datas[5].trim() ), Integer.parseInt( datas[6].trim() ), Integer.parseInt( datas[7].trim() ), Integer.parseInt( datas[8].trim() ), Integer.parseInt( datas[9].trim() ), Integer.parseInt( datas[10].split( " " )[0] ) );
				count++;
			}

			System.out.println( "Created " + count + " classes." );
		}
		catch (IOException e)
		{
			System.out.println( "Can't initialize classes, please check your \"class.txt\"" );
		}
	}
	
	public static void gmx() {
		instance.sendRconCommand("gmx");
	}
}
