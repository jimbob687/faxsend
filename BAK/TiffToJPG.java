package com.tannfe.faxprocess;

/* 
 * This class splits a multipage tiff into multiple jpg images
 * Not currently working ok as the jpg images have bad colors
 * Have taken some code from here
 * http://stackoverflow.com/questions/15429011/how-to-convert-tiff-to-jpeg-png-in-java
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
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.JPEGEncodeParam;



import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.IIOImage;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;


public class TiffToJPG
{



	public static void main( String[] args ) {

		String imageType = "tif";

		try {

		/*
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
		*/



			// let's get the height and width of each image
		/*
			for(int i = 0; i < nbPages; i++) {
				int height = reader.getHeight(i);
				int width = reader.getWidth(i);
				System.out.println("Image: " + i + " has a height of: " + height + " and width of: " + width);
				BufferedImage bImage = reader.read(i);    // lets get the image
				File outputfile = new File("saveimg" + i + ".jpg");
				ImageIO.write(bImage, "jpeg", outputfile);
				
			}
		*/


		/* This example gives me a dark image
			String otPath = "./outfile.jpg";

			SeekableStream s = new FileSeekableStream(args[0]);
			TIFFDecodeParam param = null;
			ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);
			RenderedImage op = dec.decodeAsRenderedImage(0);

			FileOutputStream fos = new FileOutputStream(otPath);
			JPEGEncodeParam jpgparam = new JPEGEncodeParam();
			jpgparam.setQuality(67);
			ImageEncoder en = ImageCodec.createImageEncoder("jpeg", fos, jpgparam);
			en.encode(op);
			fos.flush();
			fos.close();
		*/


			String otPath = "./outfile.jpg";
			File tiffFile = new File(args[0]);
			SeekableStream s = new FileSeekableStream(tiffFile);
			TIFFDecodeParam param = null;
			ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);
			RenderedImage op = dec.decodeAsRenderedImage(0);
			//FileOutputStream fos = new FileOutputStream(otPath);
			//JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(fos);
			//jpeg.encode(op.getData());
			//fos.close();
			File out = new File(otPath);
			ImageIO.write(op, "jpeg", out);
			

		}
		catch(Exception e) {
			System.out.println("Error: " + e);
		}

	}
}
