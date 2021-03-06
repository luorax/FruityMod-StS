package fruitymod.powers;

import java.lang.reflect.Field;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.helpers.SuperclassFinder;
import fruitymod.FruityMod;

public class EnigmaPower extends AbstractPower {
	public static final String POWER_ID = "EnigmaPower";
	public static final String NAME = "Enigma";
	public static final String[] DESCRIPTIONS = new String[] {
			"Dazed can now be played to gain ",
			" Block and deal ",
			" damage to ALL enemies."
	};
	
	public EnigmaPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		updateDescription();
		this.img = FruityMod.getEnigmaPowerTexture();
	}
	
	@Override
	public void onInitialApplication() {
		updateDazedDescriptions(null);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
				this.amount + DESCRIPTIONS[2];
	}
	
	@Override
	public void onDrawOrDiscard() {
		updateDazedDescriptions(null);
	}
	
	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		updateDazedDescriptions(power);
	}
	
	private void updateDazedDescriptions(AbstractPower applied) {
		int damage = this.amount;
		int block = this.amount;
		if (applied != null && applied.ID.equals("EnigmaPower")) {
			damage += applied.amount;
			block += applied.amount;
		}
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c instanceof Dazed) {
				c.baseBlock = block;
				c.baseDamage = damage;
				try {
					Field isMultiDamageField = SuperclassFinder.getSuperclassField(c.getClass(), "isMultiDamage");
					isMultiDamageField.setAccessible(true);
					isMultiDamageField.set(c, true);
				} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
					System.out.println("couldn't set multidamage on dazed");
					e.printStackTrace();
				}
				c.type = AbstractCard.CardType.ATTACK;
				c.rawDescription = "Ethereal. Gain !B! Block. Deal !D! damage to all enemies.";
				c.initializeDescription();
			}
		}
	}
	
}
