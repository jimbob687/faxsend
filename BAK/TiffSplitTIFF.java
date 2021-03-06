package com.tannfe.faxprocess;

/* 
 * This class splits a multipage tiff into multiple tiff images
*/

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
 
import org.ghost4j.analyzer.AnalysisItem;
import org.ghost4j.analyzer.FontAnalyzer;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.Image;
import java.awt.Graphics2D;

import org.jpedal.PdfDecoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.lang.RuntimeException;

import com.sun.media.imageio.plugins.tiff.TIFFImageWriteParam;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.IIOImage;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;



public class TiffSplitTIFF
{

	public static void main( String[] args ) {

		String imageType = "tif";

		try {

			ImageInputStream is = ImageIO.createImageInputStream(new File(args[0]));

			if (is == null || is.length() == 0){
				System.out.println("Trying to split an empty file");
			}		

			Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);
			if (iterator == null || !iterator.hasNext()) {
				throw new IOException("Image file format not supported by ImageIO: " + args[0]);
			}

			// We are just looking for the first reader compatible:
			ImageReader reader = (ImageReader) iterator.next();
			iterator = null;
			reader.setInput(is);

			int nbPages = reader.getNumImages(true);

			System.out.println("There are: " + nbPages + " pages.");

			// This is to set the quality of the output TIFF
			//ImageWriteParam param = writer.getDefaultWriteParam();
			//param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			//param.setCompressionType("LZW");
			//param.setCompressionQuality(0.5f);


			// let's get the height and width of each image
			for(int i = 0; i < nbPages; i++) {
				int height = reader.getHeight(i);
				int width = reader.getWidth(i);
				System.out.println("Image: " + i + " has a height of: " + height + " and width of: " + width);
				BufferedImage bImage = reader.read(i);    // lets get the image
				File outputfile = new File("saveimg" + i + ".tif");
				ImageIO.write(bImage, "tiff", outputfile);
				
			}

			}
			catch(Exception e) {
				System.out.println("Error: " + e);
			}

		}
}
