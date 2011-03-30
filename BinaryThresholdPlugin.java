/*
 * Copyright 2010, 2011 Institut Pasteur.
 * 
 * This file is part of ICY.
 * 
 * ICY is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ICY is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ICY. If not, see <http://www.gnu.org/licenses/>.
 */
package plugins.ICY-ITK-Plugins;

import icy.gui.frame.progress.AnnounceFrame;
import icy.image.IcyBufferedImage;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.sequence.Sequence;

import org.itk.simple.BinaryErodeImageFilter;
import org.itk.simple.BinaryThresholdImageFilter;
import org.itk.simple.Image;
import org.itk.simple.PixelContainer;
import org.itk.simple.PixelIDValueEnum;

/**
 * 
 * @author Fabrice de Chaumont & Stephane Dallongeville
 *
 * This tutorial display a simple message at the bottom of the screen, in a scrolling annonceFrame.
 * A class is an ICY plugin as it extends icy.plugin.abstract_.Plugin
 * It can then be visible in the menu if it implements icy.plugin.interface_.PluginImageAnalysis
 *
 */
public class BinaryThresholdPlugin extends Plugin implements PluginImageAnalysis {

	/**
	 * This method will be called as the user click on the plugin button.
	 */
	@Override
	public void compute() {

		new AnnounceFrame("Hello ICY + ITK !");

		IcyBufferedImage icyImage = getFocusedImage();
		
		byte[] dataBuffer = icyImage.getDataXYAsByte(0);
		
		PixelIDValueEnum pixelID = null; // How to initialize ?
		
		Image inputImage = new Image( icyImage.getSizeX(), icyImage.getSizeY(), pixelID );
		
		PixelContainer pixelContainer = inputImage.getPixelContainer();
		
		int numberOfPixels = icyImage.getSizeX() * icyImage.getSizeY();
		
		// pixelContainer.setBufferAsInt8( dataBuffer, numberOfPixels );
		
		BinaryThresholdImageFilter thresholder = new BinaryThresholdImageFilter();
		
		thresholder.setLowerThreshold(10);
		thresholder.setUpperThreshold(100);
		
		Image output1 = thresholder.execute( inputImage );
		
		BinaryErodeImageFilter eroder = new BinaryErodeImageFilter();
		
		Image output2 = eroder.execute( output1 );
		
		PixelContainer pixelsContainerOutput = output2.getPixelContainer();
		
		// dataBuffer = pixelsContainerOutput.getBufferAsUnsignedInt8();
		
		icyImage.setDataXYAsByte(0,dataBuffer);
		
		Sequence sequence = new Sequence("Byte Image",icyImage);
		
		addSequence(sequence);
		
	}

}
