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

import com.google.gson.Gson;
import org.bigbluebutton.presentation.ConversionMessageConstants;
import org.bigbluebutton.presentation.ConversionUpdateMessage;
import org.bigbluebutton.presentation.ConversionUpdateMessage.MessageBuilder;
import org.bigbluebutton.presentation.UploadedPresentation;
import org.bigbluebutton.pubsub.MessageSender;
import org.bigbluebutton.common.messages.MessagingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SwfSlidesGenerationProgressNotifier {
    private static Logger log = LoggerFactory.getLogger(SwfSlidesGenerationProgressNotifier.class);

    private MessageSender messageSender;

    private void notifyProgressListener(Map<String, Object> msg) {

        if (messageSender != null) {
            Gson gson = new Gson();
            String updateMsg = gson.toJson(msg);
            messageSender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, updateMsg);
        } else {
            log.warn("MessageSender has not been set");
        }
    }

    public void sendConversionUpdateMessage(Map<String, Object> message) {
        notifyProgressListener(message);
    }

    public void sendConversionUpdateMessage(int slidesCompleted, UploadedPresentation pres) {
        MessageBuilder builder = new ConversionUpdateMessage.MessageBuilder(pres);
        builder.messageKey(ConversionMessageConstants.GENERATED_SLIDE_KEY);
        builder.numberOfPages(pres.getNumberOfPages());
        builder.pagesCompleted(slidesCompleted);
        notifyProgressListener(builder.build().getMessage());
    }

    public void sendCreatingThumbnailsUpdateMessage(UploadedPresentation pres) {
        MessageBuilder builder = new ConversionUpdateMessage.MessageBuilder(pres);
        builder.messageKey(ConversionMessageConstants.GENERATING_THUMBNAIL_KEY);
        notifyProgressListener(builder.build().getMessage());
    }

    public void sendConversionCompletedMessage(UploadedPresentation pres) {
        MessageBuilder builder = new ConversionUpdateMessage.MessageBuilder(pres);
        builder.messageKey(ConversionMessageConstants.CONVERSION_COMPLETED_KEY);
        builder.numberOfPages(pres.getNumberOfPages());
        builder.presBaseUrl(pres);
        notifyProgressListener(builder.build().getMessage());
    }

    public void setMessageSender(MessageSender m) {
        messageSender = m;
    }

    public void sendCreatingTextFilesUpdateMessage(UploadedPresentation pres) {
        MessageBuilder builder = new ConversionUpdateMessage.MessageBuilder(pres);
        builder.messageKey(ConversionMessageConstants.GENERATING_TEXTFILES_KEY);
        notifyProgressListener(builder.build().getMessage());
    }

    public void sendCreatingSvgImagesUpdateMessage(UploadedPresentation pres) {
        MessageBuilder builder = new ConversionUpdateMessage.MessageBuilder(pres);
        builder.messageKey(ConversionMessageConstants.GENERATING_SVGIMAGES_KEY);
        notifyProgressListener(builder.build().getMessage());
    }
}
