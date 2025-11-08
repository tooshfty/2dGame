package inventory;

import combat.Weapon;

import java.awt.image.BufferedImage;

public class WeaponItem implements InventoryItem{
    private final String displayName;
    private final BufferedImage displayIcon;
    private final Weapon weapon;

    public WeaponItem(String displayName, BufferedImage displayIcon, Weapon weapon) {
        this.displayName = displayName;
        this.displayIcon = displayIcon;
        this.weapon = weapon;
    }

    @Override public String name() { return displayName; }
    @Override public BufferedImage icon() { return displayIcon; }
    @Override public boolean stackable() { return false; }

    public Weapon weapon() { return weapon; }
}
