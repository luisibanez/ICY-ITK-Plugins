/*=========================================================================
 *
 *  Copyright Insight Software Consortium
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *=========================================================================*/

package plugins.ITK;

import icy.gui.frame.progress.AnnounceFrame;
import icy.image.IcyBufferedImage;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.sequence.Sequence;
import icy.type.TypeUtil;

import org.itk.simple.BinaryErodeImageFilter;
import org.itk.simple.BinaryThresholdImageFilter;
import org.itk.simple.Image;
import org.itk.simple.ImportImageFilter;
import org.itk.simple.PixelIDValueEnum;

/**
 *
 * This plugins applies a binary threshold to the input image.
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

    ImportImageFilter importer = new ImportImageFilter();

    int numberOfPixels = icyImage.getSizeX() * icyImage.getSizeY();
    
    //
    //  Transfer image from ICY to ITK
    //
    switch ( icyImage.getDataType() )
    {
        case TypeUtil.TYPE_BYTE:
        {
        byte[] dataBuffer = icyImage.getDataXYAsByte(0);

        // connect here the buffer to the importer
        }
    }
   	Image inputImage = importer.execute();
   	
   	BinaryThresholdImageFilter thresholder = new BinaryThresholdImageFilter();

   	thresholder.setLowerThreshold(10);
   	thresholder.setUpperThreshold(100);

   	Image output1 = thresholder.execute( inputImage );

   	BinaryErodeImageFilter eroder = new BinaryErodeImageFilter();

   	Image output2 = eroder.execute( output1 );
    	
   	//
   	// Transfer image from ITK to ICY
   	//
	switch ( icyImage.getDataType() )
    {
        case TypeUtil.TYPE_BYTE:
        {

        byte[] outputDataBuffer = icyImage.getDataXYAsByte(0); // FIXME
    	//byte[] outputDataBuffer = output2.getBufferAsUnsignedInt8();

    	icyImage.setDataXYAsByte(numberOfPixels,outputDataBuffer);
        }
    }

	
    Sequence sequence = new Sequence("Byte Image",icyImage);

    addSequence(sequence);

  }

}
