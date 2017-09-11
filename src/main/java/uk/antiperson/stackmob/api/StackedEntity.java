package uk.antiperson.stackmob.api;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import uk.antiperson.stackmob.StackMob;
import uk.antiperson.stackmob.tools.extras.GlobalValues;

public class StackedEntity {

    private Entity entity;
    private StackMob sm;
    public StackedEntity(Entity entity, StackMob sm){
        this.entity = entity;
        this.sm = sm;
    }


    /**
     * Sets the stack size.
     * @return Returns the current size.
     */
    public int getSize(){
        return entity.getMetadata(GlobalValues.metaTag).get(0).asInt();
    }

    /**
     * Sets the stack size.
     * @param newSize The size that the current entity should have it's size changed to.
     */

    public void setSize(int newSize){
        entity.setMetadata(GlobalValues.metaTag, new FixedMetadataValue(sm, newSize));
    }

    /**
     * Entities can have special metadata added to them to prevent them from stacking.
     * @return Returns if this entity is currently ignored by the stacking task.
     */

    public boolean isStackingPrevented(){
        return entity.hasMetadata(GlobalValues.metaTag) &&
                entity.getMetadata(GlobalValues.metaTag).get(0).asBoolean();
    }

    /**
     * Entities can have special metadata added to them to prevent them from stacking.
     * @param value Boolean value for if entities should be excluded from stacking.
     */
    public void setPreventFromStacking(boolean value){
        entity.setMetadata(GlobalValues.noStackAll, new FixedMetadataValue(sm, value));
    }
}
