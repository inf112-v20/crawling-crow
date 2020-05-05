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
import roborally.events.Events;
import roborally.game.IGame;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;
import roborally.utilities.assets.SoundAssets;

import java.util.ArrayList;
import java.util.HashMap;

// Menu under construction!
public class Menu {
    private Stage stage;
    private boolean changeMap;
    private Music Song;
    private int mapId;
    private int startGame;
    private HashMap<String, ArrayList<Image>> imageLists;
    private boolean changeMapMenu;
    private Label playSongLabel;
    private Label previousMapLabel;
    private Label nextMapLabel;
    private Label gameSpeedLabel;
    private Label laserSpeedLabel;
    private Label volumeLabel;
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
    private TextArea nameInput;
    private TextButton nameButton;
    private String playerName;
    private Label nameLabel;

    public Menu(Stage stage, Events events) {
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal("data/skin.json"));
        this.labelStyle = new Label.LabelStyle();
        this.labelStyle.font = new BitmapFont();
        this.resume = false;
        this.changeMap = false;
        this.mapId = 1;
        this.events = events;
        this.imageLists = new HashMap<>();
        this.startGame = 0;
        this.imageLists.put("menus", new ArrayList<>());
        setMenu();
        this.playerName = "Angry";
        setChangeMap();
        setContinueButton();
        setLabels();
        setOptionsListeners();
        setOptionsListeners2();
        setNameInputField();
        stage.addActor(gameSpeedLabel);
        stage.addActor(laserSpeedLabel);
        stage.addActor(volumeSlider);
        stage.addActor(playSongLabel);
        stage.addActor(nameInput);
        stage.addActor(nameButton);
        setMainMenuButtons();
        this.sliders = new Sliders();
        this.sliders.abc();
    }

    private void setMenu() {
        Image menu = new Image(AssetManagerUtil.getMenu().getMainMenu());
        menu.setSize(stage.getWidth(), stage.getHeight());
        imageLists.get("menus").add(menu);
    }

    private void setChangeMap() {
        mapButton = new Image(AssetManagerUtil.getMenu().getMapButton());
        mapButton.setSize(mapButton.getWidth() * 3f, mapButton.getHeight() * 3f);
        mapButton.setPosition(centerHorizontal(mapButton.getPrefWidth()) - (1 / 3f * mapButton.getWidth())
                , centerVertical(mapButton.getPrefHeight() + ( 1 / 3f * mapButton.getHeight())));
        changeMapMenu = false;
        addMaps();
    }

    public String getPlayerName() {
        return this.playerName;
    }

    private void setNameInputField() {
        nameInput = new TextArea("Angry", skin);
        nameInput.setPosition(centerHorizontal(nameInput.getPrefWidth())
                , centerVertical(nameInput.getPrefHeight()) + continueButton.getPrefHeight());
        nameInput.setWidth(nameInput.getWidth() - 50);
        nameInput.getStyle().fontColor = Color.RED;
        /*nameLabel = new Label("Name", skin);
        nameLabel.setColor(Color.BLUE);
        nameLabel.setFontScale(1.5f);
        nameLabel.setPosition(centerHorizontal(nameLabel.getPrefWidth()) - (nameLabel.getPrefWidth() / 2f)
        , centerVertical(nameLabel.getPrefHeight()) + continueButton.getPrefHeight());*/
        nameButton = new TextButton("Change name", skin);
        nameButton.setPosition(nameInput.getX() + nameInput.getWidth(), nameInput.getY());
        nameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                playerName = nameInput.getText();
                if("".equals(playerName) || playerName.length() > 13)
                    playerName = "Angry";
            }
        });
    }

    private void setContinueButton() {
        continueButton = new Image(new Texture(Gdx.files.internal("menu/continue.png")));
        continueButton.setPosition(centerHorizontal(continueButton.getPrefWidth())
                , centerVertical(continueButton.getPrefHeight()));
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

    private float centerHorizontal(float elementWidth) {
        return (stage.getWidth() / 2f) - (elementWidth / 2f);
    }

    private float centerVertical(float elementHeight) {
        return (stage.getHeight() / 2f) - (elementHeight / 2f);
    }

    private void setOptionsListeners() {
        gameSpeedLabel.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                gameSpeedLabel.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                gameSpeedLabel.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gSpeed == 0) {
                    gameSpeedLabel.setText("Game Speed: fast");
                    gSpeed++;
                    getEvents().setGameSpeed("fast");
                } else if (gSpeed == 1) {
                    gameSpeedLabel.setText("Game Speed: fastest");
                    gSpeed++;
                    getEvents().setGameSpeed("fastest");
                } else if (gSpeed == 2) {
                    gameSpeedLabel.setText("Game Speed: normal");
                    getEvents().setGameSpeed("normal");
                    gSpeed = 0;
                }
            }
        });
        laserSpeedLabel.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                laserSpeedLabel.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                laserSpeedLabel.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (lSpeed == 1) {
                    laserSpeedLabel.setText("Laser Speed: normal");
                    lSpeed++;
                    getEvents().setLaserSpeed("normal");
                } else if (lSpeed == 2) {
                    laserSpeedLabel.setText("Laser Speed: fast");
                    lSpeed = 0;
                    getEvents().setLaserSpeed("fast");
                } else if (lSpeed == 0) {
                    laserSpeedLabel.setText("Laser Speed: slow");
                    getEvents().setLaserSpeed("slow");
                    lSpeed++;
                }
            }
        });
    }

    private void setOptionsListeners2() {
        playSongLabel.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playSongLabel.setColor(Color.GREEN);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playSongLabel.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                String text = playSongLabel.getText().toString();
                if ("Play a song: ".equals(text)) {
                    Song = AssetManagerUtil.ASSET_MANAGER.get(SoundAssets.SOUNDTRACK);
                    Song.setVolume(0.1f * SettingsUtil.VOLUME);
                    Song.play();
                    Song.setLooping(true);
                    playSongLabel.setText("Click to stop playing.");
                } else {
                    Song.stop();
                    playSongLabel.setText("Play a song: ");
                }
            }
        });
        volumeSlider.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                SettingsUtil.VOLUME = volumeSlider.getValue();
                volumeLabel.setText("Volume " + Math.round(volumeSlider.getValue() * 100.0) / 100.0);
            }
        });
    }

    private void setVolumeSlider() {
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
        volumeSlider.setPosition(playSongLabel.getX(), playSongLabel.getY()
                - (volumeSlider.getHeight() * 2f));
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
        stage.addActor(gameSpeedLabel);
        stage.addActor(laserSpeedLabel);
        stage.addActor(volumeLabel);
        stage.addActor(volumeSlider);
        stage.addActor(playSongLabel);
    }

    public void drawMenu(SpriteBatch batch, Stage stage) {
        imageLists.get("menus").get(0).draw(batch, 1);
        if (!changeMapMenu) {
            for (Image image : imageLists.get("buttons"))
                image.draw(batch, 1);
            //nameLabel.draw(batch, 1);
            nameInput.draw(batch, 1);
            nameButton.draw(batch, 1);
            gameSpeedLabel.draw(batch, 1);
            laserSpeedLabel.draw(batch, 1);
            volumeSlider.draw(batch, 1);
            volumeLabel.draw(batch, 1);
            playSongLabel.draw(batch, 1);
            //sliders.drawSliders(batch);
            if (startGame == 1)
                continueButton.draw(batch, 1);
        } else {
            imageLists.get("maps").get(mapId).draw(batch, 1);
            previousMapLabel.draw(batch, 1);
            nextMapLabel.draw(batch, 1);
            mapButton.draw(batch, 1);
        }
        if (changeMap)
            changeMapMenu = false;
        stage.act();
    }

    public void setStartGame() {
        this.startGame = 0;
    }

    private void setLabels() {
        gameSpeedLabel = new Label("Game Speed: normal", labelStyle);
        laserSpeedLabel = new Label("Laser Speed: normal", labelStyle);
        playSongLabel = new Label("Play a song", labelStyle);
        volumeLabel = new Label("Volume ", labelStyle);
        playSongLabel.setPosition(stage.getWidth() - 200, (stage.getHeight() / 1.5f) + 60);
        setVolumeSlider();
        volumeLabel.setPosition(playSongLabel.getX()
                , volumeSlider.getY() - (volumeLabel.getPrefHeight() * 2f));
        gSpeed = 0;
        lSpeed = 2;
        laserSpeedLabel.setPosition(playSongLabel.getX(),
                volumeLabel.getY() - (laserSpeedLabel.getPrefHeight() * 2f));
        gameSpeedLabel.setPosition(playSongLabel.getX(),
                laserSpeedLabel.getY() - (gameSpeedLabel.getPrefHeight() * 2f));
        previousMapLabel = new Label("<", labelStyle);
        nextMapLabel = new Label(">", labelStyle);
        previousMapLabel.setFontScale(2);
        nextMapLabel.setFontScale(2);
        float mapImagePosStartX = imageLists.get("maps").get(0).getX();
        float mapImagePosEndX = imageLists.get("maps").get(0).getWidth()
                + imageLists.get("maps").get(0).getX();
        previousMapLabel.setPosition(mapImagePosStartX - previousMapLabel.getPrefWidth() - 50
                , centerVertical(previousMapLabel.getPrefHeight() - 50));
        nextMapLabel.setPosition(mapImagePosEndX + 50
                , centerVertical(nextMapLabel.getPrefHeight() - 50));
        previousMapLabel.scaleBy(2);
        nextMapLabel.scaleBy(2);
        previousMapLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mapId == 0)
                    mapId = 2;
                else
                    mapId -= 1;
                return true;
            }
        });
        nextMapLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mapId == 2)
                    mapId = 0;
                else
                    mapId += 1;
                return true;
            }
        });
    }

    private void setMainMenuButtons () {
        ArrayList<Image> menuButtons = new ArrayList<>();
        TextureRegion[][] buttons = TextureRegion.split(AssetManagerUtil.getMenu().getButtons()
                , 200, 50);
        float y = stage.getHeight() / 2f;
        for (int i = 0; i < 3; i++) {
            menuButtons.add(new Image(buttons[i][0]));
            menuButtons.get(i).setX(centerHorizontal(menuButtons.get(i).getPrefWidth()));
            menuButtons.get(i).setY(y -= menuButtons.get(i).getPrefHeight());
            stage.addActor(menuButtons.get(i));
        }
        setClickListeners(menuButtons);
    }

    private void setClickListeners(ArrayList<Image> clickableButtons) {
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
                stage.addActor(nameInput);
                stage.addActor(nameButton);
                stage.addActor(gameSpeedLabel);
                stage.addActor(laserSpeedLabel);
                stage.addActor(volumeLabel);
                stage.addActor(volumeSlider);
                stage.addActor(playSongLabel);
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
                    stage.addActor(previousMapLabel);
                    stage.addActor(nextMapLabel);
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
            image.setPosition(centerHorizontal(image.getPrefWidth()), centerVertical(image.getPrefHeight()));
        imageLists.put("maps", maps);
    }

    public void addContinueButtonToStage(Stage stage) {
        stage.addActor(continueButton);
    }
}
