package fr.davelog.main.java;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontManager {

	Font SPACE_GRUNGE;
	Font NASALIZATION_RG;
	
	public FontManager() {
		
		SPACE_GRUNGE = loadFont("spaceGrunge", "otf");
		NASALIZATION_RG = loadFont("nasalization_rg", "ttf");
	}
	
	private Font loadFont(String name, String extension) {
		
		Font newFont = null;
		try {
			InputStream inputS = getClass().getResourceAsStream("/fonts/" + name + "." + extension);
			newFont = Font.createFont(Font.TRUETYPE_FONT, inputS);
			return newFont;
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFont;
	}
}
