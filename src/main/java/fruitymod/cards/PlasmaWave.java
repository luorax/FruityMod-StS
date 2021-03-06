package fruitymod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.actions.unique.PlasmaWaveAction;
import fruitymod.patches.AbstractCardEnum;

public class PlasmaWave extends CustomCard {
	public static final String ID = "PlasmaWave";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -1;
	private static final int ATTACK_DMG = 3;
	private static final int ATTACK_UPGRADE = 2;
	private static final int POOL = 1;

	public PlasmaWave() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ESSENCE_SHRED), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
		this.baseDamage = ATTACK_DMG;
		this.isMultiDamage = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new PlasmaWaveAction(p, this.multiDamage, this.damageTypeForTurn,
				this.freeToPlayOnce, this.energyOnUse));
	}

	@Override
	public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster monster, float tmp) {
		if (monster == null) {
			return tmp;
		}
		
		if (monster.hasPower("Vulnerable")) {
			return tmp * 2;
		} else {
			return tmp;
		}
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new PlasmaWave();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(ATTACK_UPGRADE);
		}
	}
}