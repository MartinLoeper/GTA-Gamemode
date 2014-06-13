package com.samp.lvdm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.gtaun.shoebill.constant.TextDrawAlign;
import net.gtaun.shoebill.exception.CreationFailedException;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.PlayerTextdraw;

public class DynamicActionLabel extends Thread {

	private ArrayList<DynamicItem> items = new ArrayList<DynamicItem>();
	private int mPoints = 0;
	private boolean combo = false;
	private PlayerTextdraw mComboTextbase = null;
	private Player mPlayer;
	
	public DynamicActionLabel() { }
	
	public class DynamicItem {
		public static final int TYPE_BIG = 1;
		public static final int TYPE_SMALL = 2;
		private int mYPosition = 0;
		private String mText;
		private Player mPlayer;
		private PlayerTextdraw mVisibleTextdraw;
		private boolean mRemovable = false;
		private boolean mPending = true;
		private long mTimestamp;
		private int mType;
		
		public void setPending(boolean pStatus) {
			mPending = pStatus;
		}
		
		public boolean isActive() {
			return ((mTimestamp + 6) < (System.currentTimeMillis() / 1000l)) ? false : true;
		}
		
		public boolean isPending() {
			return mPending;
		}
		
		public boolean isRemovable() {
			return mRemovable;
		}
		
		public int getYPosition() {
			return mYPosition;
		}
		
		public String getText() {
			return mText;
		}
		
		public void setText(String pText) {
			mText = pText;
		}
		
		public DynamicItem(Player pPlayer, String pText, int pType) {
			this.mText = pText;
			this.mPlayer = pPlayer;
			this.mVisibleTextdraw = createTextDraw(mText, 0, 0);
			this.mTimestamp = (System.currentTimeMillis() / 1000l);
			this.mType = pType;
		}
		
		public PlayerTextdraw createTextDraw(String pText, int x, int y) {
			PlayerTextdraw textdraw = null;
			
			try {
				textdraw = PlayerTextdraw.create(mPlayer, 400+x, 310+y);
				textdraw.setText(pText);
				textdraw.setAlignment(TextDrawAlign.RIGHT);
				if(mType == TYPE_SMALL) {
					textdraw.setLetterSize(0.135f*3, 0.135f*6);
				}
				else if(mType == TYPE_BIG) {
					textdraw.setLetterSize(0.135f*4, 0.135f*8);
				}
			}
			catch(CreationFailedException e) {
				//Server.get().sendMessageToAll(Color.RED, "ERROR:"+y);
				e.printStackTrace();
			}

			return textdraw;
		}
		
		public void setCurrentTextdraw(PlayerTextdraw pTextdraw) {
			mVisibleTextdraw = pTextdraw;
		}
		
		public void moveDown() {
			mYPosition++;
			if(mYPosition > 100) {
				mRemovable = true;
			}
			else {
				PlayerTextdraw newTextdraw = createTextDraw(mText, 0, mYPosition);
				hide();
				setCurrentTextdraw(newTextdraw);
				display();
			}
		}
		
		public void display() {
			if(mVisibleTextdraw != null)
				mVisibleTextdraw.show();
		}
		
		public void hide() {
			if(mVisibleTextdraw != null) {
				mVisibleTextdraw.hide();	
				mVisibleTextdraw.destroy();
			}
		}
	}
	
	public void run() {
		while(!interrupted()) {
			//Server.get().sendMessageToAll(Color.BLUE, "Thread:"+Thread.currentThread().getName());
			checkItemDuration();
			if((mComboTextbase == null && mPlayer != null) || (mPlayer != null && !mComboTextbase.getText().equals(String.valueOf(mPoints))))
				updateComboScore(mPlayer);
			
			try {
				List<DynamicItem> newList = new ArrayList<DynamicItem>(items);
				Iterator<DynamicItem> it = newList.iterator();
				while(it.hasNext()) {
					DynamicItem item = it.next();
					if(item.isPending()) {
						moveItemsDown();
						// show pending item
						fadeItemIn(item);
						
						// unset pending status
						item.setPending(false);
					}
				}
				Thread.sleep(200);
			}
			catch(Exception e) {
				LvdmGamemode.logger().error("Error in DynamicActionLabel \n" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void fadeItemIn(DynamicItem item) {
		for(int i=0;i<=item.getText().length();i++) {
			PlayerTextdraw newTextdraw = item.createTextDraw(item.getText().substring(0, i), -10, 0);
			item.hide();
			item.setCurrentTextdraw(newTextdraw);
			item.display();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(mPlayer != null)
			mPlayer.playSound(1131);
		
		for(int i=0;i<=10;i++) {
			PlayerTextdraw newTextdraw = item.createTextDraw(item.getText(), i-10, 0);
			item.hide();
			item.setCurrentTextdraw(newTextdraw);
			item.display();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void checkItemDuration() {
		try {
			Iterator<DynamicItem> it = items.iterator();
			while(it.hasNext()) {
				DynamicItem item = it.next();
				if(!item.isActive()) {
					//Server.get().sendMessageToAll(Color.RED, "Removing item based on timestamp!");
					// custom hide animation
					for(int i=0;i<=item.getText().length();i++) {
						PlayerTextdraw newTextdraw = item.createTextDraw(item.getText().substring(0, item.getText().length()-i), 0, item.getYPosition());
						item.hide();
						item.setCurrentTextdraw(newTextdraw);
						item.display();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					item.hide();	
					it.remove();
				}
			}
		}
		catch(Exception e) {
			// TODO fix ConcurrentModificationException
		}
		
		//remove if necessary
		if(items.size() == 0) {
			combo = false;
			//Server.get().sendMessageToAll(Color.BLUE, "COMBO:FALSE ("+items.size()+") ");
			mPoints = 0;
			if(mComboTextbase != null)
				removeComboScore();
		}
	}
	
	private void moveItemsDown() {
		for(int i=0;i<18;i++) {
			Iterator<DynamicItem> it = items.iterator();
			while(it.hasNext()) {
				DynamicItem item = it.next();
				if(!item.isPending())
					item.moveDown();
				if(item.isRemovable()) {
					if(items.contains(item)) {
						item.hide();
						it.remove();
						//Server.get().sendMessageToAll(Color.RED, "REMOVE:"+items.size());
					}
				}
			}
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) { }
		} 
	}
	
	private void add(Player pPlayer, String text, int type) {
		DynamicItem item = new DynamicItem(pPlayer, text, type);
		items.add(item);
	}
	
	public void addPoints(Player pPlayer, String reason, int amount, int type) {
		this.mPlayer = pPlayer;	// TODO INSECURE!!! Solve one queue per player in constructor!!!
		this.mPoints += amount;
		add(pPlayer, reason+" "+amount, type);
		
		//check if combo mode
		if(items.size() > 1) {
			combo = true;
			//Server.get().sendMessageToAll(Color.BLUE, "COMBO:TRUE ("+items.size()+")");
		
			if(mComboTextbase != null)
				removeComboScore();
		}
	}
	
	
	private void updateComboScore(Player pPlayer) {
		if(mPoints == 0 || items.size() <= 1)
			return;
		
		String score = String.valueOf(mPoints);
		mComboTextbase = PlayerTextdraw.create(pPlayer, 425, 310);
		for(int i=0;i<=score.length();i++) {
			mComboTextbase.setText(score.substring(0, i));
			mComboTextbase.show();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void removeComboScore() {
		mComboTextbase.setText(" ");
		mComboTextbase.hide();
		mComboTextbase.destroy();
	}
}
