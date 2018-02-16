package fruitymod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class ArcaneTempest extends CustomCard {
	 public static final String ID = "ArcaneTempest";
	    public static final String NAME = "Arcane Tempest";
	    public static final String DESCRIPTION = "Deal !D! damage and apply !M! Weak and !M! Vulnerable to ALL enemies.";
	    private static final int COST = 1;
	    private static final int ATTACK_DMG = 3;
	    private static final int DEBUFF_AMT = 1;
	    private static final int DEBUFF_AMT_UPGRADE = 1;
	    private static final int POOL = 1;
	    
	 public ArcaneTempest() {
		 super(ID, NAME,  FruityMod.makePath(FruityMod.ARCANE_TEMPEST), COST, DESCRIPTION,
				 AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
				 AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
	        this.damage=this.baseDamage = ATTACK_DMG;
	        this.magicNumber = this.baseMagicNumber = DEBUFF_AMT;
	        this.isMultiDamage = true;
	    }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
	        AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
	        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
	            if (mo.isDeadOrEscaped()) continue;
	            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05f));
	        }
	        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
	        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
	        	 AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true));
	 	        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true));
	        }	       
	    }
	   	 

	    @Override
	    public AbstractCard makeCopy() {
	        return new ArcaneTempest();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	            this.upgradeName();
	            this.upgradeMagicNumber(DEBUFF_AMT_UPGRADE);
	        }
	    }
}