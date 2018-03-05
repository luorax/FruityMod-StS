package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Vacuum
extends CustomCard {
    public static final String ID = "Vacuum";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 4;
    private static final int UPGRADED_DMG_AMT = 2;
    private static final int POOL = 1;

    public Vacuum() {
        super(ID, NAME, FruityMod.makePath(FruityMod.VACUUM), COST, DESCRIPTION, 
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE, 
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.magicNumber = this.baseMagicNumber = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	
    	int debuffCount = GetAllDebuffCount(p);
    	

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Weakened"));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Frail"));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Vulnerable"));

    	if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0f, m.hb.cY - m.hb.height / 4.0f)));
        }
    	for (int i = 0; i < debuffCount; i++) {
    		AbstractDungeon.actionManager.addToBottom(
    				new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    	}
    	
    	this.rawDescription = DESCRIPTION;
    	initializeDescription();
    }
    
    @Override
    public void applyPowers() {
    	int count = GetAllDebuffCount(AbstractDungeon.player);
    	this.baseDamage = count * this.magicNumber;
    	
    	super.applyPowers();
    	
    	if (AbstractDungeon.player.hasPower("Weakened")) {
    		// cancel out effect of weak b/c we are going
    		// to remove it before dealing damage
    		this.damage *= 1.34f;
    	}
    	
    	if (this.damage == count * this.magicNumber) {
    		this.isDamageModified = false;
    	}
    	
    	this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
    	initializeDescription();
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
    	int count = GetAllDebuffCount(AbstractDungeon.player);
    	super.calculateCardDamage(mo);
    	
    	if (AbstractDungeon.player.hasPower("Weakened")) {
    		// cancel out effect of weak b/c we are going
    		// to remove it before dealing damage
    		this.damage *= 1.34f;
    	}
    	
    	if (this.damage == count * this.magicNumber) {
    		this.isDamageModified = false;
    	}
    }
    
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        canUse = GetAllDebuffCount(p) > 0;
        this.cantUseMessage = "Must be Weak, Frail, or Vulnerable.";
        return canUse;
    }
    
    private int GetAllDebuffCount(AbstractPlayer p) {
    	return GetDebuffCount(p, "Weakened") + GetDebuffCount(p, "Vulnerable") + GetDebuffCount(p, "Frail");
    }
    
    private int GetDebuffCount(AbstractPlayer p, String powerId) {
    	int debuffCount = 0;    	
    	AbstractPower power = p.getPower(powerId);    	
    	if(power != null) debuffCount = power.amount;
    	return debuffCount;    	
    }    
    
    @Override
    public void onMoveToDiscard() {
    	this.rawDescription = DESCRIPTION;
    	initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Vacuum();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_DMG_AMT);
        }
    }
}



