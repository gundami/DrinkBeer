package lekavar.lma.drinkbeer.entity.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;

public class AlcoholDamage extends DamageSource {
    public static DamageType ALCOHOL = new DamageType("drinkbeer.alcohol", 0);
    public AlcoholDamage() {
        super(RegistryEntry.of(ALCOHOL));
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        String str = "death.attack." + this.getName();
        return Text.translatable(str, entity.getDisplayName());
    }
}