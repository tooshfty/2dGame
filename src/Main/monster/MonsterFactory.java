package Main.monster;

import Main.GamePanel;
import entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class MonsterFactory {

    // key -> constructor that needs GamePanel
    private static final Map<String, Function<GamePanel, Entity>> PROVIDERS = new HashMap<>();
    private static boolean initialized = false;

    private MonsterFactory() {}

    /** Call once (e.g., in GamePanel.setupGame()) after MonsterRegistry.init(). */
    public static void init() {
        if (initialized) return;

        // Register constructors for each key you exposed in MonsterRegistry
        PROVIDERS.put("Green Slime", gp -> new MON_GreenSlime(gp));
        PROVIDERS.put("Orc",   gp -> new MON_Orc(gp));
        PROVIDERS.put("Bat",   gp -> new MON_Bat(gp));
        PROVIDERS.put("Red Slime", gp -> new MON_RedSlime(gp));
        PROVIDERS.put("Skeleton Lord", gp -> new MON_Skeleton_Lord(gp));

        initialized = true;
    }

    /** Creates a single monster instance by key; returns null if key unknown. */
    public static Entity create(String key, GamePanel gp) {
        var ctor = PROVIDERS.get(key);
        return (ctor == null) ? null : ctor.apply(gp);
    }

    /**
     * Spawns up to 'qty' monsters near the player,
     * respecting a 'cap' on total alive monsters in the current map.
     * Returns how many were actually spawned.
     */
    public static int spawnNearPlayer(String key, int qty, GamePanel gp, int cap) {
        if (qty <= 0) return 0;
        if (!initialized) init();

        int map = gp.currentMap;
        Entity[][] bucket = gp.monster; // per your GamePanel field
        if (bucket == null || bucket[map] == null) return 0;

        int aliveNow = countAlive(bucket[map]);
        int canSpawn = Math.max(0, cap - aliveNow);
        int target = Math.min(qty, canSpawn);

        int spawned = 0;
        for (int n = 0; n < target; n++) {
            Entity m = create(key, gp);
            if (m == null) break;

            // try to find a free tile near player
            int[] pos = findFreeSpotNearPlayer(gp);
            if (pos == null) break;

            m.worldX = pos[0];
            m.worldY = pos[1];

            int slot = firstFreeSlot(bucket[map]);
            if (slot == -1) break;

            bucket[map][slot] = m;
            spawned++;
        }
        return spawned;
    }

    // ---------- helpers ----------

    private static int countAlive(Entity[] arr) {
        int c = 0;
        if (arr == null) return 0;
        for (Entity e : arr) {
            if (e != null && !e.dying) c++;
        }
        return c;
    }

    private static int firstFreeSlot(Entity[] arr) {
        if (arr == null) return -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null || arr[i].dying) return i;
        }
        return -1;
    }

    /**
     * Tries a small set of offsets (in tiles) around the player and picks the first free tile.
     * Simple & fast; upgrade to a spiral search later if you want.
     */
    private static int[] findFreeSpotNearPlayer(GamePanel gp) {
        final int ts = gp.tileSize;
        final int px = gp.player.worldX;
        final int py = gp.player.worldY;

        // Offsets in tiles (center, 4-neighbors, diagonals, then 2 tiles out)
        int[][] offsets = {
                {3,0}, {-3,0}, {0,3}, {0,-3},
                {3,3}, {-3,3}, {3,-3}, {-3,-3},
                {4,0}, {-4,0}, {0,4}, {0,-4}
        };

        for (int[] off : offsets) {
            int wx = px + off[0]*ts;
            int wy = py + off[1]*ts;
            if (isTileFreeForSpawn(gp, wx, wy)) {
                return new int[]{wx, wy};
            }
        }
        return null;
    }

    /**
     * Very lightweight "is this tile OK?" check:
     * - inside map bounds
     * - not a collidable tile
     * - no other monster already occupies that tile center
     */
    private static boolean isTileFreeForSpawn(GamePanel gp, int worldX, int worldY) {
        final int ts = gp.tileSize;

        int col = worldX / ts;
        int row = worldY / ts;

        if (col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow) {
            return false;
        }

        int map = gp.currentMap;
        int tileNum = gp.tileM.mapTileNum[map][col][row];
        if (gp.tileM.tile[tileNum] != null && gp.tileM.tile[tileNum].collision) {
            return false;
        }

        // avoid overlapping an existing monster on the same tile
        Entity[] arr = gp.monster[map];
        if (arr != null) {
            for (Entity e : arr) {
                if (e == null || e.dying) continue;
                int ecol = e.worldX / ts;
                int erow = e.worldY / ts;
                if (ecol == col && erow == row) return false;
            }
        }
        return true;
    }
}
