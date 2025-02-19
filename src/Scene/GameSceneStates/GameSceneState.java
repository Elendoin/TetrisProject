package Scene.GameSceneStates;

import Scene.GameScene;

public abstract class GameSceneState {
    protected GameScene gameScene;

    public GameSceneState(GameScene gameScene){
        this.gameScene = gameScene;
    }

    public abstract boolean paused();
    public abstract boolean unpausable();
    public abstract String pauseText();

}
