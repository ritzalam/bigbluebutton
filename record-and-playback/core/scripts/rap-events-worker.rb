#!/usr/bin/ruby
# encoding: UTF-8

# Copyright ⓒ 2017 BigBlueButton Inc. and by respective authors.
#
# This file is part of BigBlueButton open source conferencing system.
#
# BigBlueButton is free software: you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License as published by the
# Free Software Foundation, either version 3 of the License, or (at your
# option) any later version.
#
# BigBlueButton is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
# FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
# details.
# 
# You should have received a copy of the GNU Lesser General Public License
# along with BigBlueButton.  If not, see <http://www.gnu.org/licenses/>.

require '../lib/recordandplayback'
require 'rubygems'
require 'yaml'
require 'fileutils'

def copy_events_from_archive(recording_dir, events_dir, ended_done_file)
	ended_done_base = File.basename(ended_done_file, '.done')

  # We need to trim done files with .keep_events as we don't need it
  # in this step (ralam July 3, 2019)
  if ended_done_base.end_with? ".keep_events"
    ended_done_base.slice! ".keep_events"
  end

  meeting_id = nil
  break_timestamp = nil

  if match = /^([0-9a-f]+-[0-9]+)$/.match(ended_done_base)
    meeting_id = match[1]
  elsif match = /^([0-9a-f]+-[0-9]+)-([0-9]+)$/.match(ended_done_base)
    meeting_id = match[1]
    break_timestamp = match[2]
  else
    BigBlueButton.logger.warn("Ended done file for #{ended_done_base} has invalid format")
  end

  if meeting_id != nil
		target_dir = "#{events_dir}/#{meeting_id}"
		raw_archive_dir = "#{recording_dir}/raw"
		raw_events_xml = "#{raw_archive_dir}/#{meeting_id}/events.xml"
		if not FileTest.directory?(target_dir)
		  FileUtils.mkdir_p target_dir
		  if File.exist? raw_events_xml
		    events_xml = "#{events_dir}/#{meeting_id}/events.xml"
		    BigBlueButton.logger.info("Copying events from #{raw_events_xml} to #{events_xml}")
		    FileUtils.cp(raw_events_xml, events_xml)
		  end
		  FileUtils.rm ended_done_file
		end
	end
end

def keep_events_from_non_recorded_meeting(recording_dir, ended_done_file)
  ended_done_base = File.basename(ended_done_file, '.done')
  meeting_id = nil
  break_timestamp = nil
  
  if match = /^([0-9a-f]+-[0-9]+)$/.match(ended_done_base)
    meeting_id = match[1]
  elsif match = /^([0-9a-f]+-[0-9]+)-([0-9]+)$/.match(ended_done_base)
    meeting_id = match[1]
    break_timestamp = match[2]
  else
    BigBlueButton.logger.warn("Ended done file for #{ended_done_base} has invalid format")
  end

  if meeting_id != nil
    if !break_timestamp.nil?
      ret = BigBlueButton.exec_ret("ruby", "events/events.rb", "-m", meeting_id, '-b', break_timestamp)
    else
      ret = BigBlueButton.exec_ret("ruby", "events/events.rb", "-m", meeting_id)
    end
  end
end

begin
  props = YAML::load(File.open('bigbluebutton.yml'))
  redis_host = props['redis_host']
  redis_port = props['redis_port']
  redis_password = props['redis_password']
  BigBlueButton.redis_publisher = BigBlueButton::RedisWrapper.new(redis_host, redis_port, redis_password)

  log_dir = props['log_dir']
  recording_dir = props['recording_dir']
	raw_archive_dir = "#{recording_dir}/raw"
	events_dir = props['events_dir']

  logger = Logger.new("#{log_dir}/bbb-rap-worker.log")
  #logger = Logger.new(STDOUT)
  logger.level = Logger::INFO
  BigBlueButton.logger = logger

  BigBlueButton.logger.info("Running rap-events-worker...")
  ended_done_files = Dir.glob("#{recording_dir}/status/ended/*.done")
  ended_done_files.each do |ended_done|
  	ended_done_base = File.basename(ended_done, '.done')
    if ended_done_base.end_with? ".keep_events"
    	# This is a recorded meeting. So we just copy the events.xml from
    	# the archive location (ralam July 4, 2019)
    	copy_events_from_archive(recording_dir, events_dir, ended_done)
    else
    	keep_events_from_non_recorded_meeting(recording_dir, ended_done)
    end
  end
  BigBlueButton.logger.debug("rap-events-worker done")

rescue Exception => e
  BigBlueButton.logger.error(e.message)
  e.backtrace.each do |traceline|
    BigBlueButton.logger.error(traceline)
  end
end