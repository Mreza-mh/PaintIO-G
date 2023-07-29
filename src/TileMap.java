import java.util.ArrayList;

public class TileMap {

    private int lastColCreated = 0;
    private ArrayList<Integer> lastRowForEachCol = new ArrayList<>();

    private ArrayList<ArrayList<Tile>> map = new ArrayList<>();

    public Tile getTile (int x, int y) {
        Tile tile;
        ArrayList<Tile> col;

        try {
            col = map.get(x);
        } catch (IndexOutOfBoundsException e) {
            for (int i = lastColCreated; i <= x; i++) {
                map.add(i, new ArrayList<>());
                lastRowForEachCol.add(i, 0);
                lastColCreated++;
            }
            col = map.get(x);
        }

        try {
            tile = col.get(y);
        } catch (IndexOutOfBoundsException e) {
            for (int i = lastRowForEachCol.get(x); i <= y; i++) {
                col.add(i, new Tile(x, i));
                lastRowForEachCol.set(x, lastRowForEachCol.get(x) + 1);
            }
            tile = col.get(y);
        }

        return tile;
    }


}