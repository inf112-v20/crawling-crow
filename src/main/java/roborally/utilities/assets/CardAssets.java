package roborally.utilities.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import roborally.game.cards.IProgramCards;

import java.util.HashMap;

public class CardAssets implements Assets {
	private  HashMap<IProgramCards.CardType, Texture> cardTypeTextureHashMap;
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
	private static final AssetDescriptor<Texture> CARD_BACK
			= new AssetDescriptor<>("cards/card_back.png", Texture.class);

	@Override
	public void loadAssets(AssetManager manager) {
		manager.load(BACKUP);
		manager.load(ROTATE_RIGHT);
		manager.load(ROTATE_LEFT);
		manager.load(MOVE_1);
		manager.load(MOVE_2);
		manager.load(MOVE_3);
		manager.load(U_TURN);
		manager.load(CARD_BACK);
	}

	@Override
	public void putAssetsInMap(AssetManager manager) {
		cardTypeTextureHashMap = new HashMap<>();
		cardTypeTextureHashMap.put(IProgramCards.CardType.ROTATE_RIGHT, manager.get(ROTATE_RIGHT));
		cardTypeTextureHashMap.put(IProgramCards.CardType.ROTATE_LEFT, manager.get(ROTATE_LEFT));
		cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_1, manager.get(MOVE_1));
		cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_2, manager.get(MOVE_2));
		cardTypeTextureHashMap.put(IProgramCards.CardType.MOVE_3, manager.get(MOVE_3));
		cardTypeTextureHashMap.put(IProgramCards.CardType.U_TURN, manager.get(U_TURN));
		cardTypeTextureHashMap.put(IProgramCards.CardType.BACKUP, manager.get(BACKUP));
		this.programCardHeight = manager.get(MOVE_1).getHeight();
		this.programCardWidth = manager.get(MOVE_1).getWidth();
		}

	public Texture getCardTexture(IProgramCards.CardType card) {
		return cardTypeTextureHashMap.get(card);
	}

	public int getProgramCardWidth() {
		return this.programCardWidth;
	}

	public int getProgramCardHeight() {
		return this.programCardHeight;
	}
}
