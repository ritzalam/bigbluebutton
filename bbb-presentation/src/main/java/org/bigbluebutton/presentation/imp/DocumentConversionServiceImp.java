/**
 * BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
 * <p>
 * Copyright (c) 2016 BigBlueButton Inc. and by respective authors (see below).
 * <p>
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 * <p>
 * BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along
 * with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
 */

package org.bigbluebutton.presentation.imp;

import org.bigbluebutton.presentation.*;
import org.bigbluebutton.pubsub.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentConversionServiceImp implements DocumentConversionService {
    private static Logger log = LoggerFactory.getLogger(DocumentConversionServiceImp.class);

    private OfficeToPdfConversionService officeToPdfConversionService;
    private PdfToSwfSlidesGenerationService pdfToSwfSlidesGenerationService;
    private ImageToSwfSlidesGenerationService imageToSwfSlidesGenerationService;
    private MessageSender messageSender;

    public void processDocument(UploadedPresentation pres) {
        SupportedDocumentFilter sdf = new SupportedDocumentFilter();
        sdf.setMessageSender(messageSender);
        log.info("Start presentation conversion. meetingId=" + pres.getMeetingId() +
                " presId=" + pres.getId() + " name=" + pres.getName());

        if (sdf.isSupported(pres)) {
            String fileType = pres.getFileType();

            if (SupportedFileTypes.isOfficeFile(fileType)) {
                officeToPdfConversionService.convertOfficeToPdf(pres);
                OfficeToPdfConversionSuccessFilter ocsf = new OfficeToPdfConversionSuccessFilter(messageSender);
                if (ocsf.didConversionSucceed(pres)) {
                    // Successfully converted to pdf. Call the process again, this time it should be handled by
                    // the PDF conversion service.
                    processDocument(pres);
                }
            } else if (SupportedFileTypes.isPdfFile(fileType)) {
                pdfToSwfSlidesGenerationService.generateSlides(pres);
            } else if (SupportedFileTypes.isImageFile(fileType)) {
                imageToSwfSlidesGenerationService.generateSlides(pres);
            } else {

            }

        } else {
            // TODO: error log
        }

        log.info("End presentation conversion. meetingId=" + pres.getMeetingId() + " presId=" +
                pres.getId() + " name=" + pres.getName());

    }

    public void setMessageSender(MessageSender m) {
        this.messageSender = m;
    }

    public void setOfficeToPdfConversionService(OfficeToPdfConversionService o) {
        this.officeToPdfConversionService = o;
    }

    public void setImageToSwfSlidesGenerationService(ImageToSwfSlidesGenerationService i) {
        this.imageToSwfSlidesGenerationService = i;
    }

    public void setPdfToSwfSlidesGenerationService(PdfToSwfSlidesGenerationService p) {
        this.pdfToSwfSlidesGenerationService = p;
    }
}
