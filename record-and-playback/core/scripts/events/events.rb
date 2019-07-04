# Set encoding to utf-8
# encoding: UTF-8
#
# BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
#
# Copyright (c) 2018 BigBlueButton Inc. and by respective authors (see below).
#
# This program is free software; you can redistribute it and/or modify it under
# the terms of the GNU Lesser General Public License as published by the Free
# Software Foundation; either version 3.0 of the License, or (at your option)
# any later version.
#
# BigBlueButton is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
# FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
# details.
#
# You should have received a copy of the GNU Lesser General Public License along
# with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
#

require '../lib/recordandplayback'
require 'logger'
require 'trollop'
require 'yaml'

def keep_events(meeting_id, redis_host, redis_port, redis_password, events_dir, break_timestamp)
  BigBlueButton.logger.info("Keeping events for #{meeting_id}")
  redis = BigBlueButton::RedisWrapper.new(redis_host, redis_port, redis_password)
  events_archiver = BigBlueButton::RedisEventsArchiver.new redis

  target_dir = "#{events_dir}/#{meeting_id}"
  if not FileTest.directory?(target_dir)
    FileUtils.mkdir_p target_dir
  end
  events_xml = "#{target_dir}/events.xml"
  events = events_archiver.store_events(meeting_id, events_xml, break_timestamp)
end

################## START ################################

opts = Trollop::options do
  opt :meeting_id, "Meeting id with events to keep", type: :string
  opt :break_timestamp, "Chapter break end timestamp", type: :integer
end
Trollop::die :meeting_id, "must be provided" if opts[:meeting_id].nil?

meeting_id = opts[:meeting_id]
break_timestamp = opts[:break_timestamp]

props = YAML::load(File.open('bigbluebutton.yml'))
recording_dir = props['recording_dir']
events_dir = props['events_dir']
redis_host = props['redis_host']
redis_port = props['redis_port']
redis_password = props['redis_password']
log_dir = props['log_dir']

ended_done_file = "#{recording_dir}/status/ended/#{meeting_id}.done"

BigBlueButton.logger = Logger.new("#{log_dir}/events.log", 'daily')

keep_events(meeting_id, redis_host, redis_port, redis_password, events_dir, break_timestamp)

BigBlueButton.logger.info("Deleting redis keys")
redis = BigBlueButton::RedisWrapper.new(redis_host, redis_port, redis_password)
events_archiver = BigBlueButton::RedisEventsArchiver.new(redis)
events_archiver.delete_events(meeting_id)

FileUtils.rm ended_done_file

