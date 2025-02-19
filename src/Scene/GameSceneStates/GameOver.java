package Scene.GameSceneStates;
import Scene.GameScene;

public class GameOver extends GameSceneState {
    public GameOver(GameScene gameScene){
        super(gameScene);
    }

    @Override
    public boolean paused(){
        return true;
    }

    @Override
    public boolean unpausable(){
        return false;
    }

    @Override
    public String pauseText(){
        return "Game Over";
    }
}
