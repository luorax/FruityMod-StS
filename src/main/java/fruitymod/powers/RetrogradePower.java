package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.FruityMod;
import fruitymod.cards.Retrograde;

public class RetrogradePower
        extends AbstractPower {
    public static final String POWER_ID = "RetrogradePower";
    public static final String NAME = "Retrograde";
    public static final String[] DESCRIPTIONS = new String[] {
            "At the end of your turn, Shuffle-Cycle ",
            " Retrograde into your draw pile."
    };

    public RetrogradePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = FruityMod.getCreativityPowerTexture();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this.owner, this.owner, new Retrograde(), this.amount, true, true));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "RetrogradePower"));
    }
}




