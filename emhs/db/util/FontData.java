package emhs.db.util;

public class FontData {
    public final int width, height, ascent, descent, leading;

    public FontData(int width, int height, int ascent, int descent, int leading) {
        this.width = width;
        this.height = height;
        this.ascent = ascent;
        this.descent = descent;
        this.leading = leading;
    }
}
