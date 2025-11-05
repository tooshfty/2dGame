package Main.monster;

import Main.GamePanel;
import entity.Entity;

import java.util.*;

public class MonsterRegistry {
    private static final List<MonsterMeta> ITEMS = new ArrayList<>();
    private static boolean initialized = false;

    private MonsterRegistry() {}

    /** Call once during setup. Pure metadata; no Entity creation. */
    public static void init() {
        if (initialized) return;

        // --- Register your monster types ---
        ITEMS.add(new MonsterMeta("Bat",   "Bat"));
        ITEMS.add(new MonsterMeta("Green Slime", "Green Slime"));
        ITEMS.add(new MonsterMeta("Orc",   "Orc"));
        ITEMS.add(new MonsterMeta("Red Slime", "Red Slime"));
        ITEMS.add(new MonsterMeta("Skeleton Lord", "Skeleton Lord"));

        // Deterministic order for UI (alphabetical by displayName)
        Collections.sort(ITEMS, Comparator.comparing(MonsterMeta::displayName, String.CASE_INSENSITIVE_ORDER));

        initialized = true;
    }

    public static int size() {
        return ITEMS.size();
    }

    public static MonsterMeta get(int index) {
        if (index < 0 || index >= ITEMS.size()) return null;
        return ITEMS.get(index);
    }

    public static String getDisplayNameAt(int index) {
        MonsterMeta m = get(index);
        return m == null ? null : m.displayName();
    }

    /** Optional: safe copy for iteration if needed */
    public static List<MonsterMeta> items() {
        return new ArrayList<>(ITEMS);
    }
    //NEW: resolve the internal key for a given UI index
    public static String getKeyAt(int index) {
        MonsterMeta m = get(index);
        return (m == null) ? null : m.key();
    }
}
