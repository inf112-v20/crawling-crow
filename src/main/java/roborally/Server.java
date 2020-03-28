package roborally;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import roborally.game.IGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class Server implements ApplicationListener {
    public final static float HEIGHT = 500;
    public final static float WIDTH = 1000;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Skin skin;
    private Stage stage;

    private Label messageReceived;
    private Label myIP;
    private TextArea ip;
    private TextArea msg;
    private TextButton button;
    private String ipadresse;
    private boolean startGame;
    private IGame game;

    public Server(Stage stage, OrthographicCamera camera, SpriteBatch batch, IGame game) {
        this.stage = stage;
        this.camera = camera;
        this.batch = batch;
        this.game = game;
    }

    public boolean getStart() {
        if(this.startGame) {
            this.startGame = false;
            return true;
        }
        return false;
    }

    public void setGame(IGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("data/skin.json"));

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface I : Collections.list(interfaces))
            {
                for(InetAddress addr: Collections.list(I.getInetAddresses()))
                {
                    if(addr instanceof Inet4Address)
                    {
                        ipadresse = ipadresse + addr.getHostAddress() + "\n";
                    }
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        Group group = new Group();
        group.setBounds(0, 0, WIDTH, HEIGHT);
        messageReceived = new Label("Command:", skin);
        myIP = new Label(ipadresse, skin);
        ip = new TextArea("Target IP", skin);
        msg = new TextArea("", skin);
        button = new TextButton("Send", skin);

        messageReceived.setPosition(100, 100);
        myIP.setPosition(100,150);
        ip.setPosition(100,250);
        msg.setPosition(100,350);
        button.setPosition(100,400);
        group.addActor(messageReceived);
        group.addActor(myIP);
        group.addActor(ip);
        group.addActor(msg);
        group.addActor(button);

        stage.addActor(group);

        stage.getCamera().position.set(WIDTH/2, HEIGHT/2, 0);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                String send = "";
                if(msg.getText().length() == 0)
                {
                    send = "Wops";
                }
                else
                    send = msg.getText() + "\n";

                if(ip.getText().length() == 0)
                    return;

                SocketHints sh = new SocketHints();
                sh.connectTimeout = 10000;
                Socket socket = Gdx.net.newClientSocket(Protocol.TCP, ip.getText(), 1337, sh);
                try {
                    socket.getOutputStream().write(send.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        new Thread(() -> {
            ServerSocketHints ssh = new ServerSocketHints();
            ssh.acceptTimeout = 0;

            ServerSocket socket = Gdx.net.newServerSocket(Protocol.TCP, 1337, ssh);
            while(true)
            {
                Socket s = socket.accept(null);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));

                try {
                    messageReceived.setText(buffer.readLine());
                    if(messageReceived.getText().toString().equals("Start Game")) {
                        startGame = true;
                        System.out.println("Game Started");
                    }
                    else if(messageReceived.getText().toString().equals("Move")) {
                        game.getRound().getPhase().run(game.getLayers());
                        System.out.println("Test Phase");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}