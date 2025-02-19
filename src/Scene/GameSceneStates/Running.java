package Scene.GameSceneStates;
import Scene.GameScene;

public class Running extends GameSceneState {
    public Running(GameScene gameScene){
        super(gameScene);
    }

    @Override
    public boolean paused(){
        return false;
    }

    @Override
    public boolean unpausable(){
        return false;
    }

    @Override
    public String pauseText(){
        return "";
    }
}
