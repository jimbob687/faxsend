package com.tannfe.faxprocess;

/* 
 * This class splits a multipage tiff into multiple jpg images
 * Not currently working ok as the jpg images have bad colors
*/

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
 

import org.jpedal.PdfDecoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.lang.RuntimeException;

import magick.ImageInfo; //This class will be used to accept the input BMP file
import magick.MagickImage; //This class will convert the image to JPG format



public class TiffSplitJPGmagick
{

	public static void main( String[] args ) {

		String imageType = "tif";

		try {
			String inputfileName = args[0];

			ImageInfo info = new ImageInfo(inputfileName);

			MagickImage magick_converter = new MagickImage(info);

			String outputfile = "jmagick_converted_image.jpg"; //Output File name
			magick_converter.setFileName(outputfile); //set output file format
			magick_converter.writeImage(info); //do the conversion
				

		}
		catch(Exception e) {
			System.out.println("Error: " + e);
		}

	}
}
