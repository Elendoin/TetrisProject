package Scene.GameSceneStates;
import Scene.GameScene;


public class Paused extends GameSceneState {
    public Paused(GameScene gameScene){
        super(gameScene);
    }

    @Override
    public boolean paused(){
        return true;
    }

    @Override
    public boolean unpausable(){
        return true;
    }

    @Override
    public String pauseText(){
        return "PAUSED";
    }
}
