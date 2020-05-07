package roborally.gameview.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import roborally.events.Events;
import roborally.game.IGame;
import roborally.utilities.AssetManagerUtil;

import java.util.ArrayList;
import java.util.HashMap;

// Menu under construction!
public class Menu {
    private final Stage stage;
    private final HashMap<String, ArrayList<Image>> imageLists;
    private final Events events;
    private final Skin skin;
    private boolean changeMap;
    private int mapId;
    private int startGame;
    private boolean changeMapMenu;
    private Label chooseToEndGameLabel;
    private Image mapButton;
    private Image continueButton;
    private TextButton noButton;
    private TextButton yesButton;
    private boolean resume;
    private boolean endGame;
    private boolean drawEndGameButtons;
    private boolean Continue;
    private TextArea nameInput;
    private String playerName;
    private MenuLabel menuLabel;

    public Menu(Stage stage, Events events) {
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal("data/skin.json"));
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
        setNameInputField();
        setMainMenuButtons();
        makeEndGameButtons();
        this.menuLabel = new MenuLabel(stage, this);
        reloadStage(stage);
        Sliders sliders = new Sliders();
        sliders.abc();
    }

    public String getPlayerName() {
        return this.playerName;
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

    public void addActiveGameButtonsToStage(Stage stage) {
        stage.addActor(continueButton);
        stage.addActor(noButton);
        stage.addActor(yesButton);
    }

    public boolean isChangeMap() {
        if (changeMap) {
            changeMap = false;
            return true;
        }
        return false;
    }

    public boolean isEndGame() {
        boolean retValue = endGame;
        endGame = false;
        return retValue;
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
        for(Label label : menuLabel.getMainMenuLabels())
            stage.addActor(label);
        stage.addActor(menuLabel.getVolumeSlider());
        stage.addActor(nameInput);
    }

    public void setStartGame() {
        this.startGame = 0;
    }

    public void drawMenu(SpriteBatch batch, Stage stage) {
        imageLists.get("menus").get(0).draw(batch, 1);
        if (!changeMapMenu) {
            drawMainMenu(batch);
        } else {
            drawMapChangeMenu(batch);
        }
        if (changeMap)
            changeMapMenu = false;
        stage.act();
    }

    protected void setMapID(int mapID) {
        this.mapId = mapID;
    }

    protected HashMap<String, ArrayList<Image>> getImageList() {
        return this.imageLists;
    }

    protected float centerHorizontal(float elementWidth) {
        return (stage.getWidth() / 2f) - (elementWidth / 2f);
    }

    protected float centerVertical(float elementHeight) {
        return (stage.getHeight() / 2f) - (elementHeight / 2f);
    }


    private void drawMapChangeMenu(SpriteBatch batch) {
        imageLists.get("maps").get(mapId).draw(batch, 1);
        for(Label label : menuLabel.getMapMenuLabels())
            label.draw(batch, 1);
        mapButton.draw(batch, 1);
    }

    private void drawMainMenu(SpriteBatch batch) {
        if (startGame == 1)
            continueButton.draw(batch, 1);
        if(drawEndGameButtons) {
            noButton.draw(batch, 1);
            yesButton.draw(batch, 1);
            chooseToEndGameLabel.draw(batch, 1);
        }
        for (Image image : imageLists.get("buttons"))
            image.draw(batch, 1);
        nameInput.draw(batch, 1);
        for(Label label : menuLabel.getMainMenuLabels())
            label.draw(batch, 1);
        menuLabel.getVolumeSlider().draw(batch, 1);
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
        }
        setClickListeners(menuButtons);
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
                for(Label label : menuLabel.getMainMenuLabels())
                    stage.addActor(label);
                stage.addActor(menuLabel.getVolumeSlider());
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
                if(events.hasWaitEvent() && !endGame) {
                    drawEndGameButtons = true;
                    return;
                }
                playerName = nameInput.getText();
                if (playerName.equals("") || playerName.length() > 13 || playerName.equals("Change name")) {
                    playerName = "Angry";
                }
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
                if(events.hasWaitEvent() && !endGame) {
                    drawEndGameButtons = true;
                    return;
                }
                changeMapMenu = true;
                stage.clear();
                for(Label label : menuLabel.getMapMenuLabels())
                    stage.addActor(label);
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

    private void setNameInputField() {
        nameInput = new TextArea("Change name", skin);
        nameInput.setWidth(nameInput.getWidth());
        nameInput.setPosition(centerHorizontal(nameInput.getPrefWidth())
                , centerVertical(nameInput.getPrefHeight()) + continueButton.getPrefHeight());
        nameInput.setAlignment(Align.center);
        nameInput.getStyle().fontColor = Color.RED;
    }

    private void setChangeMap() {
        mapButton = new Image(AssetManagerUtil.getMenu().getMapButton());
        mapButton.setSize(mapButton.getWidth() * 3f, mapButton.getHeight() * 3f);
        mapButton.setPosition(centerHorizontal(mapButton.getPrefWidth()) - (1 / 3f * mapButton.getWidth())
                , centerVertical(mapButton.getPrefHeight() + ( 1 / 3f * mapButton.getHeight())));
        changeMapMenu = false;
        addMaps();
    }

    private void addMaps() {
        ArrayList<Image> maps = new ArrayList<>();
        maps.add(new Image(new Texture(Gdx.files.internal("maps/models/map0.png"))));
        maps.add(new Image(new Texture(Gdx.files.internal("maps/models/map1.png"))));
        maps.add(new Image(new Texture(Gdx.files.internal("maps/models/map2.PNG"))));
        for (Image image : maps)
            image.setPosition(centerHorizontal(image.getPrefWidth()), centerVertical(image.getPrefHeight()));
        imageLists.put("maps", maps);
    }

    private void makeEndGameButtons() {
        this.chooseToEndGameLabel = new Label("Do you wish to end the game? ", skin);
        this.chooseToEndGameLabel.setPosition(stage.getWidth() / 2.25f, stage.getHeight() / 1.5f + 50);
        this.noButton = new TextButton("No", skin);
        this.yesButton = new TextButton("Yes", skin);
        this.noButton.setPosition(stage.getWidth() / 2, stage.getHeight() / 1.5f);
        this.yesButton.setPosition(stage.getWidth() / 2 - 50, stage.getHeight() / 1.5f);
        this.noButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                drawEndGameButtons = false;
            }
        });
        this.yesButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                endGame = true;
                drawEndGameButtons = false;
                setStartGame();
            }
        });
        stage.addActor(yesButton);
        stage.addActor(noButton);
    }

    private void setMenu() {
        Image menu = new Image(AssetManagerUtil.getMenu().getMainMenu());
        menu.setSize(stage.getWidth(), stage.getHeight());
        imageLists.get("menus").add(menu);
    }
}