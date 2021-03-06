package uk.antiperson.stackmob.listeners.entity;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import uk.antiperson.stackmob.api.IStackMob;
import uk.antiperson.stackmob.api.entity.StackTools;
import uk.antiperson.stackmob.api.tools.GlobalValues;

public class EntityRemoveListener implements Listener {

    private IStackMob sm;

    public EntityRemoveListener(IStackMob sm) {
        this.sm = sm;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        if (sm.getCustomConfig().getStringList("no-stack-worlds")
                .contains(e.getWorld().getName())) {
            return;
        }

        for (Entity entity : e.getChunk().getEntities()) {
            cleanupEntity(entity);
        }
    }

    // Paper event
    @EventHandler
    public void onEntityRemove(EntityRemoveFromWorldEvent e) {
        cleanupEntity(e.getEntity());
    }

    private void cleanupEntity(Entity entity) {
        // Check if entity is a mob, since they despawn on chunk unload.
        if (entity instanceof Monster) {
            sm.getCache().remove(entity.getUniqueId());
            StackTools.removeSize(entity);
            return;
        }

        // Add to storage
        if (StackTools.hasValidData(entity)) {
            int stackSize = StackTools.getSize(entity);
            StackTools.removeSize(entity);
            if (stackSize <= 1 && stackSize != GlobalValues.NO_STACKING) {
                return;
            }
            if (sm.getCustomConfig().getBoolean("remove-chunk-unload")) {
                entity.remove();
                return;
            }
            sm.getCache().put(entity.getUniqueId(), stackSize);
        }
    }
}
