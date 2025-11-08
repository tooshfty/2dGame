package inventory;

import java.awt.image.BufferedImage;

public interface InventoryItem {
    String name();
    BufferedImage icon();     // can be null; UI handles fallback text
    boolean stackable();      // weapons: false; consumables may return true later
}
