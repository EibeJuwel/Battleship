package game;

import game.gamestates.GameStateManager;

public class Update implements Runnable{

	private Game game;
	private GameStateManager gameStateManager;
	
	public Update(Game game, GameStateManager gameStateManager) {
		this.game = game;
		this.gameStateManager = gameStateManager;
	}

	@Override
	public void run() {
		while (game.isRunning()) {
			long lastTime = System.currentTimeMillis();

			gameStateManager.update();
			
			int wishFPS = Game.wishFPS;
			long time = System.currentTimeMillis();
			time -= lastTime;
			if(1000 / wishFPS > time)time = (1000/wishFPS)-time;
			else time = 0;
			
			try {
				Thread.sleep(time);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
