package roborally.gameview.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import roborally.game.IGame;
import roborally.events.Events;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.assets.SoundAssets;

import java.util.ArrayList;
import java.util.HashMap;

// Menu under construction!
public class Menu {
    private boolean changeMap;
    private Music Song;
    private int mapId;
    private int startGame;
    private HashMap<String, ArrayList<Image>> imageLists;
    private boolean changeMapMenu;
    private Label playSong;
    private Label left;
    private Label right;
    private Label gameSpeed;
    private Label laserSpeed;
    private Label volume;
    private Slider volumeSlider;
    private Label.LabelStyle labelStyle;
    private Image mapButton;
    private Image continueButton;
    private Events events;
    private boolean resume;
    private int gSpeed;
    private int lSpeed;
    private boolean Continue;
    private Sliders sliders;
    private Skin skin;
    private TextArea nameBox;
    private TextButton nameButton;
    private String playerName;
    private Label nameHere;

    public Menu(Stage stage, Events events) {
        skin = new Skin(Gdx.files.internal("data/skin.json"));
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        this.resume = false;
        this.changeMap = false;
        this.mapId = 1;
        this.events = events;
        imageLists = new HashMap<>();
        startGame = 0;
        imageLists.put("menus", new ArrayList<>());
        Image menu = new Image(AssetManagerUtil.getMenu().getMainMenu());
        menu.setSize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
        imageLists.get("menus").add(menu);
        mapButton = new Image(AssetManagerUtil.getMenu().getMapButton());
        mapButton.setPosition(SettingsUtil.WINDOW_WIDTH / 2f, SettingsUtil.WINDOW_HEIGHT / 1.5f);
        changeMapMenu = false;
        playerName = "Angry";
        addEvenMoreListeners();
        makeLabels();
        makeOptionListeners();
        addMaps();
        addMoreListeners();
        addNameFunction();
        stage.addActor(gameSpeed);
        stage.addActor(laserSpeed);
        stage.addActor(volumeSlider);
        stage.addActor(playSong);
        stage.addActor(nameBox);
        stage.addActor(nameButton);
        makeClickableButtons(stage);
        this.sliders = new Sliders();
        sliders.abc();
    }

    public String getPlayerName() {
        return this.playerName;
    }

    private void addNameFunction() {
        nameHere = new Label("Name: ", skin);
        nameHere.setColor(Color.BLUE);
        nameHere.setFontScale(1.5f);
        nameHere.setPosition(SettingsUtil.WINDOW_WIDTH / 2f - 15 ,SettingsUtil.WINDOW_HEIGHT / 2f + 58);
        nameBox = new TextArea("Angry", skin);
        nameButton = new TextButton("OK", skin);
        nameBox.setPosition(SettingsUtil.WINDOW_WIDTH / 2f + 75 ,SettingsUtil.WINDOW_HEIGHT / 2f + 50);
        nameBox.setWidth(nameBox.getWidth() / 2);
        nameBox.getStyle().fontColor = Color.RED;
        nameButton.setPosition(SettingsUtil.WINDOW_WIDTH / 2f + 150 ,SettingsUtil.WINDOW_HEIGHT / 2f + 50);
        nameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                playerName = nameBox.getText();

            }
        });
    }
    private void addEvenMoreListeners() {

        continueButton = new Image(new Texture(Gdx.files.internal("menu/continue.png")));
        continueButton.setPosition(SettingsUtil.WINDOW_WIDTH / 2f - 70, SettingsUtil.WINDOW_HEIGHT / 2f - 25);
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
    }

    private void makeOptionListeners() {
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
    }

    private void addMoreListeners() {
        left.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mapId == 0)
                    mapId = 1;
                else
                    mapId -= 1;
                return true;
            }
        });
        right.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mapId == 1)
                    mapId = 0;
                else
                    mapId += 1;
                return true;
            }
        });

        playSong.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playSong.setColor(Color.GREEN);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playSong.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                String text = playSong.getText().toString();
                if ("Play a song: ".equals(text)) {
                    Song = AssetManagerUtil.ASSET_MANAGER.get(SoundAssets.SOUNDTRACK);
                    Song.setVolume(0.1f * SettingsUtil.VOLUME);
                    Song.play();
                    Song.setLooping(true);
                    playSong.setText("Click to stop playing.");
                } else {
                    Song.stop();
                    playSong.setText("Play a song: ");
                }
            }
        });
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        Image birdImage = new Image(new Texture("assets/icons/Icon.png"));
        birdImage.setSize(15, 20);
        birdImage.scaleBy(100);
        Pixmap pixmap = new Pixmap(10, 8, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        sliderStyle.background = new Image(new Texture(pixmap)).getDrawable();
        sliderStyle.knob = birdImage.getDrawable();
        volumeSlider = new Slider(0f, 1f, 0.1f, false, sliderStyle);
        volumeSlider.setValue(0.6f);
        volumeSlider.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                SettingsUtil.VOLUME = volumeSlider.getValue();
                volume.setText("Volume " + Math.round(volumeSlider.getValue() * 100.0) / 100.0);
            }
        });

        volumeSlider.setPosition(SettingsUtil.WINDOW_WIDTH - 200, SettingsUtil.WINDOW_HEIGHT / 1.5f + 30);
    }

    public boolean isResume(IGame game) {
        if (Continue) {
            Continue = false;
            resume = false;
            return true;
        }
        if (resume && !events.hasWaitEvent()) {
            game.startUp(getPlayerName());
            startGame = 1;
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
        stage.addActor(volume);
        stage.addActor(volumeSlider);
        stage.addActor(playSong);
    }

    public void drawMenu(SpriteBatch batch, Stage stage) {
        imageLists.get("menus").get(0).draw(batch, 1);
        if (!changeMapMenu) {
            for (Image image : imageLists.get("buttons"))
                image.draw(batch, 1);
            nameHere.draw(batch, 1);
            nameBox.draw(batch, 1);
            nameButton.draw(batch, 1);
            gameSpeed.draw(batch, 1);
            laserSpeed.draw(batch, 1);
            volumeSlider.draw(batch, 1);
            volume.draw(batch, 1);
            playSong.draw(batch, 1);
            sliders.drawSliders(batch);
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

    public void setStartGame() {
        this.startGame = 0;
    }

    private void makeLabels() {
        gameSpeed = new Label("Game Speed: normal", labelStyle);
        laserSpeed = new Label("Laser Speed: normal", labelStyle);
        playSong = new Label("Play a song: ", labelStyle);
        volume = new Label("Volume ", labelStyle);
        volume.setPosition(SettingsUtil.WINDOW_WIDTH - 200, SettingsUtil.WINDOW_HEIGHT / 1.5f + 60);
        gSpeed = 0;
        lSpeed = 2;
        laserSpeed.setPosition(SettingsUtil.WINDOW_WIDTH - 200, SettingsUtil.WINDOW_HEIGHT / 1.5f);
        gameSpeed.setPosition(SettingsUtil.WINDOW_WIDTH - 200, SettingsUtil.WINDOW_HEIGHT / 1.5f - 30);
        playSong.setPosition(SettingsUtil.WINDOW_WIDTH - 200, SettingsUtil.WINDOW_HEIGHT / 1.5f - 60);
        left = new Label(" < ", labelStyle);
        right = new Label(" > ", labelStyle);
        left.setFontScale(2);
        right.setFontScale(2);
        left.setPosition(SettingsUtil.WINDOW_WIDTH / 3f + 75, SettingsUtil.WINDOW_HEIGHT / 2f - 60);
        right.setPosition(SettingsUtil.WINDOW_WIDTH / 1.35f - 70, SettingsUtil.WINDOW_HEIGHT / 2f - 60);
        left.scaleBy(2);
        right.scaleBy(2);
    }

    private void makeClickableButtons(Stage stage) {
        ArrayList<Image> clickableButtons = new ArrayList<>();
        TextureRegion[][] buttons = TextureRegion.split(AssetManagerUtil.getMenu().getButtons(), 200, 50);
        int y = SettingsUtil.WINDOW_HEIGHT / 2;
        int x = SettingsUtil.WINDOW_WIDTH / 2;
        for (int i = 0; i < 3; i++) {
            clickableButtons.add(new Image(buttons[i][0]));
            clickableButtons.get(i).setY(y -= 75);
            clickableButtons.get(i).setX(x);
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
                startGame = 0;
                stage.clear();
                stage.addActor(nameBox);
                stage.addActor(nameButton);
                stage.addActor(gameSpeed);
                stage.addActor(laserSpeed);
                stage.addActor(volume);
                stage.addActor(volumeSlider);
                stage.addActor(playSong);
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
                if(!events.hasWaitEvent()) {
                    changeMapMenu = true;
                    stage.clear();
                    stage.addActor(left);
                    stage.addActor(right);
                    stage.addActor(mapButton);
                }
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
        for (Image image : maps)
            image.setPosition(SettingsUtil.WINDOW_WIDTH / 2.5f, SettingsUtil.WINDOW_HEIGHT / 4f);
        imageLists.put("maps", maps);
    }

    public void addContinueButtonToStage(Stage stage) {
        stage.addActor(continueButton);
    }
}
