/* infoweather.jar — ImageCanvas.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

public class ImageCanvas
extends Canvas {
    private Image a;

    public void paint(Graphics graphics) {
        this.update(graphics);
    }

    private void a(Image image, MediaTracker mediaTracker) {
        this.a = image;
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        }
        catch (InterruptedException interruptedException) {
            System.out.println("Cannot load image");
        }
        this.setSize(image.getWidth(null), image.getHeight(null));
    }

    public void update(Graphics graphics) {
        if (this.a != null) {
            graphics.setColor(Color.lightGray);
            graphics.fillRect(0, 0, this.a.getWidth(null), this.a.getHeight(null));
            graphics.drawImage(this.a, 0, 0, this);
        }
    }

    public void refresh(Image image, MediaTracker mediaTracker) {
        if (image != null) {
            this.a(image, mediaTracker);
            Graphics graphics = this.getGraphics();
            this.update(graphics);
        }
    }

    private static String a__Ljava_lang_String(String string) {
        char[] cArray = string.toCharArray();
        int n = cArray.length;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4 = n2;
            char c = cArray[n4];
            switch (n2 % 5) {
                case 0: {
                    n3 = 4;
                    break;
                }
                case 1: {
                    n3 = 8;
                    break;
                }
                case 2: {
                    n3 = 80;
                    break;
                }
                case 3: {
                    n3 = 26;
                    break;
                }
                default: {
                    n3 = 84;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
