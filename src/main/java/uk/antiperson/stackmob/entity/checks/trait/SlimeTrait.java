package uk.antiperson.stackmob.entity.checks.trait;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import uk.antiperson.stackmob.entity.checks.ApplicableTrait;
import uk.antiperson.stackmob.entity.checks.TraitChecks;

public class SlimeTrait implements ApplicableTrait {

    public SlimeTrait(TraitChecks tc){
        if(tc.getStackMob().config.getCustomConfig().getBoolean("compare.slime-size")) {
            tc.registerTrait(this);
        }
    }

    @Override
    public boolean checkTrait(Entity original, Entity nearby) {
        if(original instanceof Slime){
            return  (((Slime) original).getSize() != ((Slime) nearby).getSize());
        }
        return false;
    }

    @Override
    public void applyTrait(Entity original, Entity spawned) {
        if(original instanceof Slime){
            ((Slime) spawned).setSize(((Slime) original).getSize());
        }
    }
}