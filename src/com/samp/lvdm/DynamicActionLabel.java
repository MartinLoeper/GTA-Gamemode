package com.samp.lvdm;

import java.util.ArrayList;
import java.util.Iterator;

import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.TextdrawBase;


public class DynamicActionLabel extends Thread {

	Player mPlayer;
	ArrayList<DynamicItem> items = new ArrayList<DynamicItem>();
	
	public DynamicActionLabel(Player pPlayer) {
		mPlayer = pPlayer;
		start();
	}
	
	public class DynamicItem extends Thread {
		public final int MAX_QUEUE_LENGTH = 5;
		private int mPosition = 0;
		private String mText;
		private TextdrawBase mVisibleTextdraw;
		private int mRequestPosition = 0;
		private boolean mPending = true;
		
		public void run() {
			while(!interrupted()) {
				if(mRequestPosition != mPosition) {
					if(mRequestPosition < mPosition)
						move(1);
					else
						move(-1);
				}
			}
		}
		
		public void setPending(boolean pStatus) {
			mPending = pStatus;
		}
		
		public boolean isPending() {
			return mPending;
		}
		
		public DynamicItem(String pText) {
			this.mText = pText;
			mVisibleTextdraw = createTextDraw(0, 0);
		}
		
		public TextdrawBase createTextDraw(int x, int y) {
			//TextdrawBase textdraw = new TextdrawBase(220+x, 340+y);
			TextdrawBase textdraw = null;
			textdraw.setText(mText);
			return textdraw;
		}
		
		public void setCurrentTextdraw(TextdrawBase pTextdraw) {
			mVisibleTextdraw = pTextdraw;
		}
		
		// TODO dest!!!
		public void move(int dest) {
			for(int i=0;i<20;i++) {
				TextdrawBase newTextdraw = createTextDraw(0, i * dest);
				hide();
				setCurrentTextdraw(newTextdraw);
				display();
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) { }
			}
			mPosition = mPosition + dest;
		}
		
		public void display() {
			//if(mVisibleTextdraw != null)
		//		mVisibleTextdraw.show(DynamicActionLabel.this.mPlayer);
		}
		
		public void hide() {
			//if(mVisibleTextdraw != null)
				//mVisibleTextdraw.hide(DynamicActionLabel.this.mPlayer);	
		}
		
		public void init() {
			start();
		}
		
		public void down() {
			mRequestPosition++;
		}
		
		public void up() {
			mRequestPosition--;
		}
	}
	
	public void run() {
		while(!interrupted()) {
			Iterator<DynamicItem> it = items.iterator();
			while(it.hasNext()) {
				DynamicItem item = it.next();
				if(item.isPending()) {
					// move other items down
					moveItemsDown();	// blocking???
					
					// show pending item
					item.display();
					
					// unset pending status
					item.setPending(false);
				}
			}
		}
	}
	
	public void moveItemsDown() {
		Iterator<DynamicItem> it = items.iterator();
		while(it.hasNext()) {
			DynamicItem item = it.next();
			if(!item.isPending())
				item.down();
		}
	}
	
	public DynamicItem add(String text) {
		DynamicItem item = new DynamicItem(text);
		item.init();
		items.add(item);
		
		return item;
	}
	
}
