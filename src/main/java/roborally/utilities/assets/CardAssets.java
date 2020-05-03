package roborally.utilities.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import roborally.game.cards.IProgramCards;

import java.util.HashMap;

public class CardAssets implements Assets {
	private  HashMap<IProgramCards.CardType, Texture> cardTypeTextureHashMap;
	private  HashMap<IProgramCards.CardType, Texture> cardTypeTextureGrayHashMap;
	private int programCardWidth;
	private int programCardHeight;

	private static final AssetDescriptor<Texture> BACKUP
			= new AssetDescriptor<>("cards/backup.png", Texture.class);
	private static final AssetDescriptor<Texture> ROTATE_LEFT
			= new AssetDescriptor<>("cards/rotate_left.png", Texture.class);
	private static final AssetDescriptor<Texture> ROTATE_RIGHT
			= new AssetDescriptor<>("cards/rotate_right.png", Texture.class);
	private static final AssetDescriptor<Texture> MOVE_1
			= new AssetDescriptor<>("cards/move_1.png", Texture.class);
	private static final AssetDescriptor<Texture> MOVE_2
			= new AssetDescriptor<>("cards/move_2.png", Texture.class);
	private static final AssetDescriptor<Texture> MOVE_3
			= new AssetDescriptor<>("cards/move_3.png", Texture.class);
	private static final AssetDescriptor<Texture> U_TURN
			= new AssetDescriptor<>("cards/u_turn.png", Texture.class);

	// Grayscale
	private static final AssetDescriptor<Texture> BACKUP_GRAY
			= new AssetDescriptor<>("cards/gray/backup_gray.png", Texture.class);
	private static final AssetDescriptor<Texture> ROTATE_LEFT_GRAY
			= new AssetDescriptor<>("cards/gray/rotate_left_gray.png", Texture.class);
	private static final AssetDescriptor<Texture> ROTATE_RIGHT_GRAY
			= new AssetDescriptor<>("cards/gray/rotate_right_gray.png", Texture.class);
	private static final AssetDescriptor<Texture> MOVE_1_GRAY
			= new AssetDescriptor<>("cards/gray/move_1_gray.png", Texture.class);
	private static final AssetDescriptor<Texture> MOVE_2_GRAY
			= new AssetDescriptor<>("cards/gray/move_2_gray.png", Texture.class);
	private static final AssetDescriptor<Texture> MOVE_3_GRAY
			= new AssetDescriptor<>("cards/gray/move_3_gray.png", Texture.class);
	private static final AssetDescriptor<Texture> U_TURN_GRAY
			= new AssetDescriptor<>("cards/gray/u_turn_gray.png", Texture.class);

	@Override
	public void loadAssets(AssetManager manager) {
		// Program cards
		manager.load(BACKUP);
		manager.load(ROTATE_RIGHT);
		manager.load(ROTATE_LEFT);
		manager.load(MOVE_1);
		manager.load(MOVE_2);
		manager.load(MOVE_3);
		manager.load(U_TURN);

		// Grayscale Program Cards
		manager.load(BACKUP_GRAY);
		manager.load(ROTATE_RIGHT_GRAY);
		manager.load(ROTATE_LEFT_GRAY);
		manager.load(MOVE_1_GRAY);
		manager.load(MOVE_2_GRAY);
		manager.load(MOVE_3_GRAY);
		manager.load(U_TURN_GRAY);
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		cardTypeTextureHashMap = new HashMap<>();
		cardTypeTextureGrayHashMap = new HashMap<>();
		cardTypeTextureHashMap.put(IProgramCards.CardType.ROTATE_RIGHT, manager.get(ROTATE_RIGHT));
		cardTypeTextureHashMap.put(IProgramCards.CardType.ROTATE_LEFT, manager.get(ROTATE_LEFT));
		cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_1, manager.get(MOVE_1));
		cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_2, manager.get(MOVE_2));
		cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_3, manager.get(MOVE_3));
		cardTypeTextureHashMap.put(IProgramCards.CardType.U_TURN, manager.get(U_TURN));
		cardTypeTextureHashMap.put(IProgramCards.CardType.BACKUP, manager.get(BACKUP));

		cardTypeTextureGrayHashMap.put(IProgramCards.CardType.ROTATE_RIGHT, manager.get(ROTATE_RIGHT_GRAY));
		cardTypeTextureGrayHashMap.put(IProgramCards.CardType.ROTATE_LEFT, manager.get(ROTATE_LEFT_GRAY));
		cardTypeTextureGrayHashMap.put(IProgramCards.CardType.MOVE_1, manager.get(MOVE_1_GRAY));
		cardTypeTextureGrayHashMap.put(IProgramCards.CardType.MOVE_2, manager.get(MOVE_2_GRAY));
		cardTypeTextureGrayHashMap.put(IProgramCards.CardType.MOVE_3, manager.get(MOVE_3_GRAY));
		cardTypeTextureGrayHashMap.put(IProgramCards.CardType.U_TURN, manager.get(U_TURN_GRAY));
		cardTypeTextureGrayHashMap.put(IProgramCards.CardType.BACKUP, manager.get(BACKUP_GRAY));
		this.programCardHeight = manager.get(MOVE_1).getHeight();
		this.programCardWidth = manager.get(MOVE_1).getWidth();
		}

	public Texture getCardTexture(IProgramCards.CardType card) {
		return cardTypeTextureHashMap.get(card);
	}
	public Texture getCardTextureGray(IProgramCards.CardType card) {
		return cardTypeTextureGrayHashMap.get(card);
	}

	public int getProgramCardWidth() {
		return this.programCardWidth;
	}

	public int getProgramCardHeight() {
		return this.programCardHeight;
	}
	}
