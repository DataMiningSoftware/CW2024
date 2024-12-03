package com.example.demo;

public class LevelTwo extends Scene_Properties {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.gif";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private Level2_Interface levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected Level_Interface instantiateLevelView() {
		levelView = new Level2_Interface(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

}
