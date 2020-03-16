package roborally.ui.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import roborally.game.IGame;
import roborally.ui.gdx.events.Events;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;
import java.util.HashMap;

// Menu under construction!
public class Menu {
    private boolean resume;
    private boolean changeMap;
    private int mapId;
    private int startGame;
    private HashMap<String, ArrayList<Image>> imageLists;
    private boolean changeMapMenu;
    private Label funMode;
    private Label left;
    private Label right;
    private Label gameSpeed;
    private Label laserSpeed;
    private Image mapButton;
    private Image continueButton;
    private Events events;
    private int gSpeed;
    private int lSpeed;
    private int fun;
    private boolean isFunMode;
    private boolean Continue;

    public Menu(Stage stage, Events events) {
        this.resume = false;
        this.changeMap = false;
        this.mapId = 1;
        this.fun = 0;
        this.events = events;
        imageLists = new HashMap<>();
        startGame = 0;
        imageLists.put("menus", new ArrayList<>());
        Image menu = new Image(AssetManagerUtil.getMenu());
        menu.setHeight(menu.getHeight() + 50);
        imageLists.get("menus").add(menu);
        mapButton = new Image(AssetManagerUtil.getMapButton());
        mapButton.setPosition(680, 480);
        changeMapMenu = false;
        continueButton = new Image(new Texture(Gdx.files.internal("menu/continue.png")));
        continueButton.setPosition(350, 450);
        continueButton.setWidth(continueButton.getWidth() + 150);
        continueButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                continueButton.setColor(Color.GOLD);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                continueButton.setColor(Color.YELLOW);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Continue = true;
            }
        });
        makeLabels();
        addMaps();
        stage.addActor(continueButton);
        stage.addActor(gameSpeed);
        stage.addActor(laserSpeed);
        stage.addActor(funMode);
        makeClickableButtons(stage);
        addMoreListeners();
    }

    private void addMoreListeners() {
        left.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mapId == 0)
                    mapId = 3;
                else
                    mapId -= 1;
                return true;
            }
        });
        right.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mapId == 3)
                    mapId = 0;
                else
                    mapId += 1;
                return true;
            }
        });
        gameSpeed.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                gameSpeed.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                gameSpeed.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gSpeed == 0) {
                    gameSpeed.setText("Game Speed: fast");
                    gSpeed++;
                    getEvents().setGameSpeed("fast");
                } else if (gSpeed == 1) {
                    gameSpeed.setText("Game Speed: fastest");
                    gSpeed++;
                    getEvents().setGameSpeed("fastest");
                } else if (gSpeed == 2) {
                    gameSpeed.setText("Game Speed: normal");
                    getEvents().setGameSpeed("normal");
                    gSpeed = 0;
                }
            }
        });
        laserSpeed.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                laserSpeed.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                laserSpeed.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (lSpeed == 1) {
                    laserSpeed.setText("Laser Speed: normal");
                    lSpeed++;
                    getEvents().setLaserSpeed("normal");
                } else if (lSpeed == 2) {
                    laserSpeed.setText("Laser Speed: fast");
                    lSpeed = 0;
                    getEvents().setLaserSpeed("fast");
                } else if (lSpeed == 0) {
                    laserSpeed.setText("Laser Speed: slow");
                    getEvents().setLaserSpeed("slow");
                    lSpeed++;
                }
            }
        });
        funMode.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                funMode.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                funMode.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (fun == 0) {
                    funMode.setText("FunMode: On");
                    isFunMode = true;
                    fun = 1;
                } else {
                    funMode.setText("FunMode: Off");
                    isFunMode = false;
                    fun = 0;
                }
            }
        });
    }

    public boolean isResume(IGame game) {
        if (Continue) {
            Continue = false;
            resume = false;
            return true;
        }
        if (resume && startGame == 0) {
            if (isFunMode)
                game.funMode();
            else {
                game.startUp();
            }
            startGame = 1;
            resume = false;
            return true;
        } else if (resume) {
            if (isFunMode && !events.hasWaitEvent())
                game.funMode();
            resume = false;
            return true;
        }
        return false;
    }

    public boolean isChangeMap() {
        if (changeMap) {
            changeMap = false;
            return true;
        }
        return false;
    }

    public int getMapId() {
        return this.mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public Events getEvents() {
        return this.events;
    }

    public Image getMap() {
        return imageLists.get("maps").get(mapId);
    }

    public void reloadStage(Stage stage) {
        for (Image image : imageLists.get("buttons"))
            stage.addActor(image);
        stage.addActor(gameSpeed);
        stage.addActor(laserSpeed);
        stage.addActor(funMode);
        stage.addActor(continueButton);
    }

    public void drawMenu(SpriteBatch batch, Stage stage) {
        imageLists.get("menus").get(0).draw(batch, 1);
        if (!changeMapMenu) {
            for (Image image : imageLists.get("buttons"))
                image.draw(batch, 1);
            gameSpeed.draw(batch, 1);
            laserSpeed.draw(batch, 1);
            funMode.draw(batch, 1);
            if (startGame == 1)
                continueButton.draw(batch, 1);
        } else {
            imageLists.get("maps").get(mapId).draw(batch, 1);
            left.draw(batch, 1);
            right.draw(batch, 1);
            mapButton.draw(batch, 1);
        }
        if (changeMap)
            changeMapMenu = false;
        stage.act();
    }

    private void makeLabels() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        gameSpeed = new Label("Game Speed: normal", labelStyle);
        laserSpeed = new Label("Laser Speed: normal", labelStyle);
        funMode = new Label("FunMode: Off", labelStyle);
        gSpeed = 0;
        lSpeed = 2;
        funMode.setPosition(700, 510);
        laserSpeed.setPosition(700, 485);
        gameSpeed.setPosition(700, 460);
        left = new Label(" < ", labelStyle);
        right = new Label(" > ", labelStyle);
        left.setSize(120, 120);
        right.setSize(120, 120);
        left.setPosition(80, 300);
        right.setPosition(635, 300);
    }

    private void makeClickableButtons(Stage stage) {
        ArrayList<Image> clickableButtons = new ArrayList<>();
        TextureRegion[][] buttons = TextureRegion.split(AssetManagerUtil.getButtons(), 200, 50);
        int y = 475;
        for (int i = 0; i < 3; i++) {
            clickableButtons.add(new Image(buttons[i][0]));
            clickableButtons.get(i).setY(y -= 75);
            clickableButtons.get(i).setX(420);
            stage.addActor(clickableButtons.get(i));
        }
        makeClickListeners(stage, clickableButtons);
    }

    private void makeClickListeners(Stage stage, ArrayList<Image> clickableButtons) {
        mapButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                mapButton.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                mapButton.setColor(Color.BLUE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeMap = true;
                changeMapMenu = false;
                stage.clear();
                stage.addActor(gameSpeed);
                stage.addActor(laserSpeed);
                stage.addActor(funMode);
                stage.addActor(continueButton);
                for (Image image : imageLists.get("buttons"))
                    stage.addActor(image);
            }
        });
        clickableButtons.get(0).addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                clickableButtons.get(0).setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                clickableButtons.get(0).setColor(Color.BLUE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                resume = true;
            }
        });
        clickableButtons.get(1).addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                clickableButtons.get(1).setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                clickableButtons.get(1).setColor(Color.BLUE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeMapMenu = true;
                stage.clear();
                stage.addActor(left);
                stage.addActor(right);
                stage.addActor(mapButton);
            }
        });
        clickableButtons.get(2).addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                clickableButtons.get(2).setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                clickableButtons.get(2).setColor(Color.BLUE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        imageLists.put("buttons", clickableButtons);
    }

    public void addMaps() {
        ArrayList<Image> maps = new ArrayList<>();
        maps.add(new Image(new Texture(Gdx.files.internal("maps/models/map0.png"))));
        maps.add(new Image(new Texture(Gdx.files.internal("maps/models/map1.png"))));
        maps.add(new Image(new Texture(Gdx.files.internal("maps/models/map2.PNG"))));
        maps.add(new Image(new Texture(Gdx.files.internal("maps/models/map3.png"))));
        for (Image image : maps)
            image.setPosition(150, 100);
        imageLists.put("maps", maps);
    }

}
