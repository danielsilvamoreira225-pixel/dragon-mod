package com.dragonkingdoms.entity;

import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Faz o dragão vagar suavemente pelo céu quando não está atacando ninguém,
 * escolhendo pontos aleatórios próximos em uma esfera ao redor de si mesmo.
 */
public class DragonWanderGoal extends Goal {

    private final AncientDragonEntity dragon;
    private Vec3 targetPoint;

    public DragonWanderGoal(AncientDragonEntity dragon) {
        this.dragon = dragon;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.dragon.getTarget() == null;
    }

    @Override
    public void start() {
        pickNewPoint();
    }

    @Override
    public void tick() {
        if (this.targetPoint == null || this.dragon.position().closerThan(this.targetPoint, 4.0D)) {
            pickNewPoint();
        }
        this.dragon.getMoveControl().setWantedPosition(targetPoint.x, targetPoint.y, targetPoint.z, 1.0D);
    }

    private void pickNewPoint() {
        Vec3i offset = new Vec3i(
                this.dragon.getRandom().nextInt(41) - 20,
                this.dragon.getRandom().nextInt(11) - 3, // mantém altitude relativamente estável
                this.dragon.getRandom().nextInt(41) - 20
        );
        double y = Mth.clamp(this.dragon.getY() + offset.getY(), this.dragon.level().getMinY() + 10, 200);
        this.targetPoint = new Vec3(this.dragon.getX() + offset.getX(), y, this.dragon.getZ() + offset.getZ());
    }
}
