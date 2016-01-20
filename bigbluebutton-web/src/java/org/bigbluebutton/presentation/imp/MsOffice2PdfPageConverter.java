/**
* BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
* 
* Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
*
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License as published by the Free Software
* Foundation; either version 3.0 of the License, or (at your option) any later
* version.
* 
* BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
* PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License along
* with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
*
*/

package org.bigbluebutton.presentation.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bigbluebutton.presentation.PageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.*;
import com.artofsolving.jodconverter.openoffice.connection.*;
import com.artofsolving.jodconverter.openoffice.converter.*;

public class MsOffice2PdfPageConverter implements PageConverter {
	private static Logger log = LoggerFactory.getLogger(MsOffice2PdfPageConverter.class);
	
	public boolean convert(File presentationFile, File output, int page){

		try {
			
			log.debug("Converting " + presentationFile.getAbsolutePath() + " to " + output.getAbsolutePath());
			
			convertToPDF(presentationFile, output);
			
			if (output.exists()) {
				return true;
			} else {
				log.warn("Failed to convert: " + output.getAbsolutePath() + " does not exist.");
				return false;
			}
				
		} catch(Exception e) {
			log.error("Exception: Failed to convert " + output.getAbsolutePath());
			return false;
		}
	}

  private void convertToPDF(File presentationFile, File output) throws Exception {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
        HttpPost httppost = new HttpPost("http://192.168.0.180:8088/upload");

        FileBody bin = new FileBody(presentationFile);
        StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("bin", bin)
                .addPart("comment", comment)
                .build();


        httppost.setEntity(reqEntity);

        System.out.println("executing request " + httppost.getRequestLine());
        CloseableHttpResponse response = httpclient.execute(httppost);
        try {
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
                InputStream is = resEntity.getContent();

                FileOutputStream fos = new FileOutputStream(output);
                int inByte;
                while((inByte = is.read()) != -1)
                     fos.write(inByte);
                is.close();
                fos.close();
            }
            EntityUtils.consume(resEntity);
        } finally {
            response.close();
        }
    } finally {
        httpclient.close();
    }
}
}
