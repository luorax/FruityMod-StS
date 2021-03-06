package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.NexusPower;

public class Nexus extends CustomCard {
	public static final String ID = "Nexus";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int COST_UPGRADED = 1;
    private static final int POOL = 1;
    
    public Nexus() {
    	super(ID, NAME, FruityMod.makePath(FruityMod.NEXUS), COST, DESCRIPTION,
    			AbstractCard.CardType.POWER, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
    }
    
    
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {  
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NexusPower(p, 1), 1));
    }    
    
    @Override
    public AbstractCard makeCopy() {
        return new Nexus();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(COST_UPGRADED);
        }
    }
}
